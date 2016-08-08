package pack.cpclient;

import android.provider.BaseColumns;

@SuppressWarnings("WeakerAccess")
public interface ContentContract {
    @SuppressWarnings("SpellCheckingInspection")
    String AUTHORITY = "pack.cpserver.cp.ContentProvider";

    String ARTISTS = "ARTISTS";

    interface Artists extends BaseColumns {
        @SuppressWarnings("unused")
        String
                NAME = "NAME",
                TRACKS = "TRACKS",
                GENRES = "GENRES",
                ALBUMS = "ALBUMS",
                LINK = "LINK",
                DESCRIPTION = "DESCRIPTION",
                SMALL_COVER = "SMALL_COVER",
                BIG_COVER = "BIG_COVER";
    }

    String GENRES_JOIN_DELIMITER = "$";
}
