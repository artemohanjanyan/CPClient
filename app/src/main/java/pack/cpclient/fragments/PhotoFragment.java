package pack.cpclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pack.cpclient.R;
import pack.cpclient.data.Artist;

public class PhotoFragment extends Fragment {

    public static final String ARGUMENT_ARTIST = "artist";
    @BindView(R.id.big_photo)
    ImageView bigPhoto;
    @BindView(R.id.more)
    Button button;
    private MoreClickListener moreClickListener;
    private Artist artist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            artist = getArguments().getParcelable(ARGUMENT_ARTIST);
            Picasso.with(getContext()).load(artist.cover().big())
                    .into(bigPhoto);
        } else if (savedInstanceState != null) {
            artist = savedInstanceState.getParcelable(ARGUMENT_ARTIST);
            Picasso.with(getContext()).load(artist.cover().big())
                    .into(bigPhoto);
        } else {
            bigPhoto.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (artist != null) {
            outState.putParcelable(ARGUMENT_ARTIST, artist);
        }
    }

    public void setMoreClickListener(MoreClickListener moreClickListener) {
        this.moreClickListener = moreClickListener;
    }

    public void setArtist(Artist artist) {
        bigPhoto.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        this.artist = artist;
        Picasso.with(getContext()).load(artist.cover().big())
                .into(bigPhoto);
    }

    @SuppressWarnings("UnusedParameters")
    @OnClick(R.id.more)
    void onClick(View view) {
        moreClickListener.onMoreClicked(artist);
    }
}
