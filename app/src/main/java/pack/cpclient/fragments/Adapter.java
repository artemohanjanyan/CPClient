package pack.cpclient.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pack.cpclient.R;
import pack.cpclient.activity.ArtistConsumer;
import pack.cpclient.activity.ArtistProvider;
import pack.cpclient.data.Artist;

/**
 * Adapter for displaying artists' preview.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements ArtistConsumer {

    private ArtistProvider artistProvider;
    private ArtistClickListener artistClickListener;

    /**
     * Creates adapter with empty list of artists.
     */
    public Adapter(ArtistClickListener artistClickListener) {
        this.artistClickListener = artistClickListener;
    }

    @Override
    public void attachProvider(ArtistProvider provider) {
        artistProvider = provider;
        notifyDataSetChanged();
        Log.d("ww", "here");
    }

    @Override
    public void detachProvider() {
        artistProvider = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> artistClickListener.onArtistClicked(viewHolder.artist));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = artistProvider.getArtist(position);

        holder.artist = artist;
        holder.name.setText(artist.name());
        Picasso.with(holder.context)
                .load(holder.artist.cover().small())
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return artistProvider != null ? artistProvider.getCount() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Context context;

        Artist artist;

        View view;
        @BindView(R.id.item_cover)
        ImageView cover;
        @BindView(R.id.item_name)
        TextView name;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            context = view.getContext();

            ButterKnife.bind(this, view);
        }
    }
}
