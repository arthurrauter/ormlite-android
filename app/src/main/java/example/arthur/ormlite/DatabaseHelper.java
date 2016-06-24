package example.arthur.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import example.arthur.ormlite.models.ArtCollection;
import example.arthur.ormlite.models.Artwork;
import example.arthur.ormlite.models.ArtworkArtCollection;
import example.arthur.ormlite.models.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "bright.db";
    private static final int DATABASE_VERSION = 3;

    //DATA ACCESS OBJECTS: interface between DB and program
    private Dao<Artwork, Long> artworkDao = null;
    private Dao<User, Long> userDao = null;
    private Dao<ArtCollection, Long> artCollectionDao = null;
    private Dao<ArtworkArtCollection, Integer> artworkArtCollectionDao = null;

    //by default, Data Access Objects throw SQLExceptions, but in Android most Exceptions extend RuntimeException. RuntimeExceptionDaos wrap the above Daos to throw RuntimeException instead of SQLException
    private RuntimeExceptionDao<Artwork, Long> artworkRuntimeExceptionDao = null;

    //this will stay here as an example
    public RuntimeExceptionDao<Artwork, Long> getArtworkDataDao() {
        if (artworkRuntimeExceptionDao == null) {
            artworkRuntimeExceptionDao = getRuntimeExceptionDao(Artwork.class);
        }
        return artworkRuntimeExceptionDao;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    //Singleton pattern for the DAOs
    public Dao<Artwork, Long> getArtworkDao() throws SQLException {
        if (artworkDao == null) {
            artworkDao = getDao(Artwork.class);
        }
        return artworkDao;
    }

    public Dao<User, Long> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<ArtCollection, Long> getArtCollectionDao() throws SQLException {
        if (artCollectionDao == null) {
            artCollectionDao = getDao(ArtCollection.class);
        }
        return artCollectionDao;
    }

    public Dao<ArtworkArtCollection, Integer> getArtworkArtCollectionDao() throws SQLException {
        if (artworkArtCollectionDao == null) {
            artworkArtCollectionDao = getDao(ArtworkArtCollection.class);
        }

        return artworkArtCollectionDao;
    }

    @Override
    public void close() {
        super.close();
        artworkDao = null;
        userDao = null;
        artCollectionDao = null;
        artworkArtCollectionDao = null;
        artworkRuntimeExceptionDao = null;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Artwork.class);
            TableUtils.createTable(connectionSource, ArtCollection.class);
            TableUtils.createTable(connectionSource, ArtworkArtCollection.class);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Artwork.class, true);
            TableUtils.dropTable(connectionSource, ArtCollection.class, true);
            TableUtils.dropTable(connectionSource, ArtworkArtCollection.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
