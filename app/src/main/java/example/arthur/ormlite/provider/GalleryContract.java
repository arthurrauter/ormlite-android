package example.arthur.ormlite.provider;

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

}
