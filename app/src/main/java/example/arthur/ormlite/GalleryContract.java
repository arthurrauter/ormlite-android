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

    public static final int CONTENT_URI_PATTERN_ONE = 1;
    public static final int CONTENT_URI_PATTERN_MANY = 2;

    public static final class ArtworkEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_ARTWORK).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTWORK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTWORK;

        public static Uri buildArtworkUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //artwork table:
        public static final String TABLE_NAME = "artworks";
        public static final String NAME = "name";
        public static final String FILE = "file";
        public static final String FROM_ARTCOLLECTION = "from_artcollection";
        public static final String OWNER = "user_id";

    }

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_USER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //user table:
        public static final String TABLE_NAME = "users";
        public static final String NAME = "name";
    }

    public static final class ArtCollectionEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_ARTCOLLECTION).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTCOLLECTION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTCOLLECTION;

        public static Uri buildArtcollectionUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //artcollections table:
        public static final String TABLE_NAME = "artcollections";
        public static final String NAME = "name";
        public static final String CURATOR = "user_id";
    }

}
