package pack.cpclient.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import pack.cpclient.R;
import pack.cpclient.data.Artist;
import pack.cpclient.data.ContentContract;
import pack.cpclient.fragments.DescriptionFragment;
import pack.cpclient.fragments.ListFragment;
import pack.cpclient.fragments.PhotoFragment;
import pack.cpclient.fragments.ViewPagerFragment;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, ArtistProvider {

    private static final int ID = 1;

    private CursorArtistManager cursorArtistManager;
    private ArtistConsumer artistConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String LIST_FRAGMENT_TAG = "list";
        final String PHOTO_FRAGMENT_TAG = "photo";

        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.tablet)) {
                ListFragment listFragment = new ListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.tablet_first_layout, listFragment, LIST_FRAGMENT_TAG)
                        .commit();

                PhotoFragment photoFragment = new PhotoFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.tablet_second_layout, photoFragment, PHOTO_FRAGMENT_TAG)
                        .commit();

                setupFragments(listFragment, photoFragment);
            } else {
                ViewPagerFragment fragment = new ViewPagerFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.portrait_layout, fragment)
                        .commit();
            }
        }

        if (savedInstanceState != null && getResources().getBoolean(R.bool.tablet)) {
            ListFragment listFragment = (ListFragment)
                    getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
            PhotoFragment photoFragment = (PhotoFragment)
                    getSupportFragmentManager().findFragmentByTag(PHOTO_FRAGMENT_TAG);

            setupFragments(listFragment, photoFragment);
        }

        getSupportLoaderManager().initLoader(ID, null, this);
    }

    private void setupFragments(ListFragment listFragment, PhotoFragment photoFragment) {
        listFragment.setArtistClickListener(photoFragment::setArtist);
        photoFragment.setMoreClickListener(artist -> {
            DialogFragment dialogFragment = new DescriptionFragment();
            Bundle args = new Bundle();
            args.putParcelable(DescriptionFragment.ARGUMENT_ARTIST, artist);
            dialogFragment.setArguments(args);
            dialogFragment.show(photoFragment.getChildFragmentManager(), "description");
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = new Uri.Builder()
                .scheme("content")
                .authority(ContentContract.AUTHORITY)
                .appendPath(ContentContract.ARTISTS)
                .build();
        Log.d(this.getClass().getSimpleName(), uri.toString());
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            Log.d(this.getClass().getSimpleName(), "loaded " + data.getCount());
            cursorArtistManager = new CursorArtistManager(data);
            if (artistConsumer != null) {
                artistConsumer.attachProvider(this);
            }
        } else {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (artistConsumer != null) {
            artistConsumer.detachProvider();
        }
        this.cursorArtistManager = null;
    }

    @Override
    public Artist getArtist(int position) {
        return cursorArtistManager.getArtist(position);
    }

    @Override
    public void setArtistConsumer(ArtistConsumer artistConsumer) {
        this.artistConsumer = artistConsumer;
        if (cursorArtistManager != null) {
            artistConsumer.attachProvider(this);
        }
    }

    @Override
    public void resetArtistConsumer() {
        if (artistConsumer != null) {
            artistConsumer.detachProvider();
            artistConsumer = null;
        }
    }

    @Override
    public int getCount() {
        return cursorArtistManager.getCount();
    }
}
