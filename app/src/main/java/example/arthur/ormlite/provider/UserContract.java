package example.arthur.ormlite.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arthurrauter on 08/07/16.
 */
public final class UserContract implements BaseColumns {

    public static final Uri CONTENT_URI = GalleryContract.BASE_CONTENT_URI.buildUpon().appendEncodedPath(GalleryContract.PATH_USER).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + GalleryContract.CONTENT_AUTHORITY + "/" + GalleryContract.PATH_USER;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + GalleryContract.CONTENT_AUTHORITY + "/" + GalleryContract.PATH_USER;

    public static final int CONTENT_URI_PATTERN_ONE = 5;
    public static final int CONTENT_URI_PATTERN_MANY = 6;

    public static Uri buildUserUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    //user table:
    public static final String TABLE_NAME = "users";
    public static final String NAME = "name";
}
