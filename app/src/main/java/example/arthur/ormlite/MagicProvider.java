package example.arthur.ormlite;

import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;

import example.arthur.ormlite.models.ArtCollection;
import example.arthur.ormlite.models.Artwork;
import example.arthur.ormlite.models.User;

/**
 * Created by arthurrauter on 07/07/16.
 */
public class MagicProvider extends OrmLiteSimpleContentProvider<DatabaseHelper> {

    @Override
    protected Class<DatabaseHelper> getHelperClass() {
        return DatabaseHelper.class;
    }

    @Override
    public boolean onCreate() {
        setMatcherController(new MatcherController()
                .add(Artwork.class, MimeTypeVnd.SubType.ITEM, "#", GalleryContract.CONTENT_URI_PATTERN_ONE)
                .add(Artwork.class, MimeTypeVnd.SubType.DIRECTORY, "", GalleryContract.CONTENT_URI_PATTERN_MANY)
                .add(User.class, MimeTypeVnd.SubType.ITEM, "#", GalleryContract.CONTENT_URI_PATTERN_ONE)
                .add(User.class, MimeTypeVnd.SubType.DIRECTORY, "", GalleryContract.CONTENT_URI_PATTERN_MANY)
                .add(ArtCollection.class, MimeTypeVnd.SubType.ITEM, "#", GalleryContract.CONTENT_URI_PATTERN_ONE)
                .add(ArtCollection.class, MimeTypeVnd.SubType.DIRECTORY, "", GalleryContract.CONTENT_URI_PATTERN_MANY)
        );
        return true;
    }
}
