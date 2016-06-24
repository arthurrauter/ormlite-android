package example.arthur.ormlite.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import example.arthur.ormlite.DatabaseHelper;

public class Artwork {

    public final static String ID_COLNAME = "_id";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = ID_COLNAME)
    Long id;

    @DatabaseField
    String name;

    @DatabaseField
    String file;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "from_collection_id")
    ArtCollection fromArtCollection;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    User owner;

    List<ArtCollection> artCollections;

    public Artwork() {
    }

    public Artwork(Long id, String name, String file) {
        this.id = id;
        this.name = name;
        this.file = file;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ArtCollection getFromArtCollection() {
        return fromArtCollection;
    }

    public void setFromArtCollection(ArtCollection fromArtCollection) {
        this.fromArtCollection = fromArtCollection;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<ArtCollection> getArtCollections() {
        return artCollections;
    }

    public void setArtCollections(List<ArtCollection> artCollections) {
        this.artCollections = artCollections;
    }

    public void addArtCollection(ArtCollection artCollection) {
        if (artCollections == null) {
            artCollections = new ArrayList<>();
        }

        this.artCollections.add(artCollection);
    }

    public List<ArtCollection> getArtCollections(DatabaseHelper dbHelper) throws SQLException {
        return lookupArtCollectionsForArtwork(this, dbHelper);
    }

    public void saveToDb(DatabaseHelper dbHelper) throws java.sql.SQLException{
        Dao<Artwork, Long> dao = dbHelper.getArtworkDao();
        dao.createOrUpdate(this);
    }

    public static Artwork loadFromDb(DatabaseHelper dbHelper, Long id) throws SQLException {
        Dao<Artwork, Long> dao = dbHelper.getArtworkDao();
        Artwork artwork = dao.queryForId(id);
        artwork.setArtCollections(lookupArtCollectionsForArtwork(artwork, dbHelper));
        return artwork;
    }

    private static PreparedQuery<ArtCollection> artCollectionsForArtworksQuery;

    public static List<ArtCollection> lookupArtCollectionsForArtwork(Artwork artwork, DatabaseHelper dbHelper) throws SQLException {

        if (artCollectionsForArtworksQuery == null) {
            artCollectionsForArtworksQuery = queryArtCollectionsForArtwork(dbHelper);
        }
        artCollectionsForArtworksQuery.setArgumentHolderValue(0, artwork);
        Dao<ArtCollection, Long> artCollectionDao = dbHelper.getArtCollectionDao();
        return artCollectionDao.query(artCollectionsForArtworksQuery);
    }

    private static PreparedQuery<ArtCollection> queryArtCollectionsForArtwork(DatabaseHelper dbHelper) throws SQLException {
        Dao<ArtCollection, Long> artCollectionDao = dbHelper.getArtCollectionDao();
        Dao<ArtworkArtCollection, Integer> artworkArtCollectionDao = dbHelper.getArtworkArtCollectionDao();

        QueryBuilder<ArtworkArtCollection, Integer> artworkArtCollectionQB = artworkArtCollectionDao.queryBuilder();

        artworkArtCollectionQB.selectColumns(ArtworkArtCollection.ARTCOLLECTION_ID_COLNAME);
        SelectArg artworkSelectArg = new SelectArg();
        artworkArtCollectionQB.where().eq(ArtworkArtCollection.ARTWORK_ID_COLNAME, artworkSelectArg);

        QueryBuilder<ArtCollection, Long> artCollectionQb = artCollectionDao.queryBuilder();
        artCollectionQb.where().in(Artwork.ID_COLNAME, artworkArtCollectionQB);

        return artCollectionQb.prepare();

    }
}

