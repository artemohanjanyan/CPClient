package pack.cpclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pack.cpclient.activity.ArtistConsumer;
import pack.cpclient.activity.ArtistProvider;
import pack.cpclient.data.Artist;

public class PagerAdapter extends FragmentStatePagerAdapter
        implements ArtistConsumer, MoreClickListener {

    private ArtistProvider artistProvider;
    private MoreClickListener moreClickListener;

    public PagerAdapter(FragmentManager fm, MoreClickListener moreClickListener) {
        super(fm);
        this.moreClickListener = moreClickListener;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoFragment fragment = new PhotoFragment();

        Bundle args = new Bundle();
        args.putParcelable(DescriptionFragment.ARGUMENT_ARTIST, artistProvider.getArtist(position));
        fragment.setArguments(args);

        fragment.setMoreClickListener(this);

        return fragment;
    }

    @Override
    public int getCount() {
        return artistProvider != null ? artistProvider.getCount() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return artistProvider.getArtist(position).name();
    }

    @Override
    public void onMoreClicked(Artist artist) {
        moreClickListener.onMoreClicked(artist);
    }

    @Override
    public void attachProvider(ArtistProvider provider) {
        this.artistProvider = provider;
        notifyDataSetChanged();
    }

    @Override
    public void detachProvider() {
        this.artistProvider = null;
        notifyDataSetChanged();
    }
}
