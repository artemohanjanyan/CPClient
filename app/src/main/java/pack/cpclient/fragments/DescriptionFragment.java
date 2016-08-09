package pack.cpclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pack.cpclient.R;
import pack.cpclient.data.Artist;

public class DescriptionFragment extends DialogFragment {

    public static final String ARGUMENT_ARTIST = "artist";

    @BindView(R.id.description_name)
    TextView name;
    @BindView(R.id.description_description)
    TextView description;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        unbinder = ButterKnife.bind(this, view);
        Artist artist = getArguments().getParcelable(ARGUMENT_ARTIST);
        assert artist != null;
        name.setText(artist.name());
        description.setText(artist.description());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
