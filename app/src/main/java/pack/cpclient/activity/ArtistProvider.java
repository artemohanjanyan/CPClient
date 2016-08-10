package pack.cpclient.activity;

import pack.cpclient.data.Artist;

public interface ArtistProvider {
    Artist getArtist(int position);

    void setArtistConsumer(ArtistConsumer artistConsumer);

    void resetArtistConsumer();

    int getCount();
}
