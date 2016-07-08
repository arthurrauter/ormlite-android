package example.arthur.ormlite.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class GalleryContract implements BaseColumns {

    public static final String CONTENT_AUTHORITY = "example.arthur.ormlite";
    public static final String PATH_ARTWORK = "artworks";
    public static final String PATH_USER = "users";
    public static final String PATH_ARTCOLLECTION = "artcollections";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}
