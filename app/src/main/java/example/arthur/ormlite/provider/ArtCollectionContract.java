package example.arthur.ormlite.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arthurrauter on 08/07/16.
 */
public final class ArtCollectionContract implements BaseColumns {

    public static final Uri CONTENT_URI = GalleryContract.BASE_CONTENT_URI.buildUpon().appendEncodedPath(GalleryContract.PATH_ARTCOLLECTION).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + GalleryContract.CONTENT_AUTHORITY + "/" + GalleryContract.PATH_ARTCOLLECTION;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + GalleryContract.CONTENT_AUTHORITY + "/" + GalleryContract.PATH_ARTCOLLECTION;

    public static final int CONTENT_URI_PATTERN_ONE = 1;
    public static final int CONTENT_URI_PATTERN_MANY = 2;

    public static Uri buildArtcollectionUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    //artcollections table:
    public static final String TABLE_NAME = "artcollections";
    public static final String NAME = "name";
    public static final String CURATOR = "user_id";
}
