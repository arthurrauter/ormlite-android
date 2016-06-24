package example.arthur.ormlite.models;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.util.ArrayList;
import java.util.List;

import example.arthur.ormlite.DatabaseHelper;

public class ArtCollection {

    public final static String ID_COLNAME = "_id";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = ID_COLNAME)
    Long id;

    @DatabaseField
    String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    User curator;

    List<Artwork> artworks = null;

    public ArtCollection() {
    }

    public ArtCollection(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public void addArtwork(Artwork artwork) {
        if (artworks == null) {
            artworks = new ArrayList<>();
        }
        artworks.add(artwork);
    }

    public List<Artwork> getArtworks(DatabaseHelper dbHelper) throws SQLException {
        return lookupArtworksForArtCollection(this, dbHelper);
    }

    public void saveToDb(DatabaseHelper dbHelper) throws java.sql.SQLException{
        Dao<ArtCollection, Long> dao = dbHelper.getArtCollectionDao();
        dao.createOrUpdate(this);
    }

    public static ArtCollection loadFromDb(DatabaseHelper dbHelper, Long id) throws SQLException {
        Dao<ArtCollection, Long> dao = dbHelper.getArtCollectionDao();
        ArtCollection artCollection = dao.queryForId(id);
        artCollection.setArtworks(lookupArtworksForArtCollection(artCollection, dbHelper));
        return artCollection;
    }

    private static PreparedQuery<Artwork> artworksForArtCollectionQuery;

    public static List<Artwork> lookupArtworksForArtCollection(ArtCollection artCollection, DatabaseHelper dbHelper) throws SQLException {

        if (artworksForArtCollectionQuery == null) {
                artworksForArtCollectionQuery = queryArtworksForArtCollection(dbHelper);
        }
        artworksForArtCollectionQuery.setArgumentHolderValue(0, artCollection);
        Dao<Artwork, Long> artworkDao = dbHelper.getArtworkDao();
        return artworkDao.query(artworksForArtCollectionQuery);
    }

    private static PreparedQuery<Artwork> queryArtworksForArtCollection(DatabaseHelper dbHelper) throws SQLException {
        Dao<Artwork, Long> artworkDao = dbHelper.getArtworkDao();
        Dao<ArtworkArtCollection, Integer> artworkArtCollectionDao = dbHelper.getArtworkArtCollectionDao();

        QueryBuilder<ArtworkArtCollection, Integer> artworkArtCollectionQB = artworkArtCollectionDao.queryBuilder();

        artworkArtCollectionQB.selectColumns(ArtworkArtCollection.ARTWORK_ID_COLNAME);
        SelectArg artworkSelectArg = new SelectArg();
        artworkArtCollectionQB.where().eq(ArtworkArtCollection.ARTCOLLECTION_ID_COLNAME, artworkSelectArg);

        QueryBuilder<Artwork, Long> artworkQb = artworkDao.queryBuilder();
        artworkQb.where().in(Artwork.ID_COLNAME, artworkArtCollectionQB);

        return artworkQb.prepare();

    }

}
