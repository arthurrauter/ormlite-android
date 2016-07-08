package example.arthur.ormlite.provider;

import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;

import example.arthur.ormlite.DatabaseHelper;
import example.arthur.ormlite.models.ArtCollection;
import example.arthur.ormlite.models.Artwork;
import example.arthur.ormlite.provider.GalleryContract;
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
                .add(ArtCollection.class, MimeTypeVnd.SubType.ITEM, "#", ArtCollectionContract.CONTENT_URI_PATTERN_ONE)
                .add(ArtCollection.class, MimeTypeVnd.SubType.DIRECTORY, "", ArtCollectionContract.CONTENT_URI_PATTERN_MANY)

                .add(Artwork.class, MimeTypeVnd.SubType.ITEM, "#", ArtworkContract.CONTENT_URI_PATTERN_ONE)
                .add(Artwork.class, MimeTypeVnd.SubType.DIRECTORY, "", ArtworkContract.CONTENT_URI_PATTERN_MANY)

                .add(User.class, MimeTypeVnd.SubType.ITEM, "#", UserContract.CONTENT_URI_PATTERN_ONE)
                .add(User.class, MimeTypeVnd.SubType.DIRECTORY, "", UserContract.CONTENT_URI_PATTERN_MANY)

        );
        return true;
    }
}
