package pack.cpclient.activity;

public interface ArtistConsumer {

    void attachProvider(ArtistProvider provider);

    void detachProvider();
}
