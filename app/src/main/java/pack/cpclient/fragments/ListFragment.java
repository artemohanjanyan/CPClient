package pack.cpclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import pack.cpclient.R;
import pack.cpclient.activity.ArtistConsumer;
import pack.cpclient.activity.ArtistProvider;
import pack.cpclient.data.Artist;

public class ListFragment extends Fragment implements ArtistClickListener, ArtistConsumer {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Adapter adapter;
    private ArtistClickListener artistClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        ((ArtistProvider) getActivity()).setArtistConsumer(this);
        return view;
    }

    public void setArtistClickListener(ArtistClickListener artistClickListener) {
        this.artistClickListener = artistClickListener;
    }

    @Override
    public void onArtistClicked(Artist artist) {
        artistClickListener.onArtistClicked(artist);
    }

    @Override
    public void attachProvider(ArtistProvider provider) {
        adapter.attachProvider(provider);
    }

    @Override
    public void detachProvider() {
        adapter.detachProvider();
    }
}
