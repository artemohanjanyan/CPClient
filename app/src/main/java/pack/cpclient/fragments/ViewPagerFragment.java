package pack.cpclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pack.cpclient.R;
import pack.cpclient.activity.ArtistConsumer;
import pack.cpclient.activity.ArtistProvider;
import pack.cpclient.data.Artist;

public class ViewPagerFragment extends Fragment
        implements MoreClickListener, ArtistConsumer {

    @BindView(R.id.view_pager_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.view_pager_linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ButterKnife.bind(this, view);

        pagerAdapter = new PagerAdapter(getChildFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        ((ArtistProvider) getActivity()).setArtistConsumer(this);

        return view;
    }

    @Override
    public void onMoreClicked(Artist artist) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();

        Bundle args = new Bundle();
        args.putParcelable(DescriptionFragment.ARGUMENT_ARTIST, artist);
        descriptionFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.portrait_layout, descriptionFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void attachProvider(ArtistProvider provider) {
        progressBar.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        pagerAdapter.attachProvider(provider);
    }

    @Override
    public void detachProvider() {
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        pagerAdapter.detachProvider();
    }
}
