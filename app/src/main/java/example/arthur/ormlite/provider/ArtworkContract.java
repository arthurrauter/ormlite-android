package example.arthur.ormlite.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arthurrauter on 08/07/16.
 */
public final class ArtworkContract implements BaseColumns {

    public static final Uri CONTENT_URI = GalleryContract.BASE_CONTENT_URI.buildUpon().appendEncodedPath(GalleryContract.PATH_ARTWORK).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + GalleryContract.CONTENT_AUTHORITY + "/" + GalleryContract.PATH_ARTWORK;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + GalleryContract.CONTENT_AUTHORITY + "/" + GalleryContract.PATH_ARTWORK;

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
