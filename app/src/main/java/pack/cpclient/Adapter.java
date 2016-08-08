package pack.cpclient;

import android.content.Context;
import android.database.Cursor;
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

/**
 * Adapter for displaying artists' preview.
 */
class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    interface OnArtistSelectListener {
        void onArtistSelected(Artist artist);
    }

    private static final String TAG = Adapter.class.getSimpleName();

    private Cursor cursor;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //OnArtistSelectListener listener;
        Context context;

        Artist artist;

        View view;
        @BindView(R.id.item_cover) ImageView cover;
        @BindView(R.id.item_name) TextView name;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            context = view.getContext();
            //listener = (OnArtistSelectListener) view.getContext();

            ButterKnife.bind(this, view);
        }
    }

    /**
     * Creates adapter with empty list of artists.
     */
    Adapter() {
        Log.d(TAG, "adapter created");
        this.cursor = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            // Launch DescriptionFragment
            //viewHolder.listener.onArtistSelected(viewHolder.artist);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Artist artist = Artist.create(cursor);

        holder.artist = artist;

        holder.name.setText(artist.name());

        Picasso.with(holder.context)
                .load(holder.artist.cover().small())
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    /**
     * Set cursor pointing to artists' information.
     * @param cursor cursor pointing to the information to be displayed.
     *                Adapter keeps the reference to this cursor
     *                until {@link Adapter#dropCursor()} is called.
     */
    void setCursor(Cursor cursor) {
        dropCursor();
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    /**
     * Removes the reference to the cursor.
     */
    void dropCursor() {
        notifyDataSetChanged();
        cursor = null;
    }
}
