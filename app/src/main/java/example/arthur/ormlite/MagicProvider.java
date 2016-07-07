package example.arthur.ormlite;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;

import example.arthur.ormlite.models.Artwork;

/**
 * Created by arthurrauter on 07/07/16.
 */
public class MagicProvider extends OrmLiteSimpleContentProvider<DatabaseHelper>{

    @Override
    protected Class<DatabaseHelper> getHelperClass() {
        return DatabaseHelper.class;
    }

    @Override
    public boolean onCreate() {
        setMatcherController(new MatcherController()
        .add(Artwork.class, MimeTypeVnd.SubType.ITEM, "#", 2));
        return true;
    }
}
