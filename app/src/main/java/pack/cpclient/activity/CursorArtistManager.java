package pack.cpclient.activity;

import android.database.Cursor;

import pack.cpclient.data.Artist;

public class CursorArtistManager {
    private Cursor cursor;
    private Artist[] artists;

    public CursorArtistManager(Cursor cursor) {
        this.cursor = cursor;
        this.artists = new Artist[cursor.getCount()];
    }

    public Artist getArtist(int position) {
        Artist artist = artists[position];
        if (artist == null) {
            cursor.moveToPosition(position);
            artist = Artist.create(cursor);
            artists[position] = artist;
        }
        return artist;
    }

    public int getCount() {
        return cursor.getCount();
    }
}
