package example.arthur.ormlite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class GalleryContract implements BaseColumns {

    public static final String CONTENT_AUTHORITY = "example.arthur.ormlite";
    public static final String PATH_ARTWORK = "artwork";
    public static final String PATH_USER = "user";
    public static final String PATH_ARTCOLLECTION = "artcollection";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ArtworkEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_ARTWORK).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTWORK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTWORK;

        public static Uri buildArtworkUri (long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //artwork table:
        public static final String TABLE_NAME = "table_name";
        public static final String NAME = "name";
        public static final String FILE = "file";
        public static final String FROM_ARTCOLLECTION = "from_artcollection";
        public static final String OWNER = "owner";

    }

    public static final class UserEntry implements BaseColumns {
    }

    public static final class ArtCollectionEntry implements BaseColumns {
    }

}
