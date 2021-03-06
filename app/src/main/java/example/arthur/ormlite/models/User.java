package example.arthur.ormlite.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.sql.SQLException;
import java.util.ArrayList;

import example.arthur.ormlite.DatabaseHelper;

public class User {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    Long id;

    @DatabaseField
    String name;

    @ForeignCollectionField(eager = true)
    java.util.Collection<ArtCollection> artCollections;

    @ForeignCollectionField(eager = true)
    java.util.Collection<Artwork> artworks;

    public User() {
    }

    public User(Long id, String name) {
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

    public java.util.Collection getArtCollections() {
        return artCollections;
    }

    public void setArtCollections(java.util.Collection artCollections) {
        this.artCollections = artCollections;
    }

    public java.util.Collection getArtworks() {
        return artworks;
    }

    public void setArtworks(java.util.Collection artworks) {

        this.artworks = artworks;
    }

    public void addArtCollection(ArtCollection artCollection) {
        if (artCollections == null) {
            artCollections = new ArrayList<>();
        }

        this.artCollections.add(artCollection);
    }

    public void addArtwork(Artwork artwork) {
        if (artworks == null) {
            artworks = new ArrayList<>();
        }
        artworks.add(artwork);
    }

    public void saveToDb(DatabaseHelper dbHelper) throws java.sql.SQLException{
        Dao<User, Long> dao = dbHelper.getUserDao();
        dao.createOrUpdate(this);
    }

    public static User loadFromDb(DatabaseHelper dbHelper, Long id) throws SQLException {
        Dao<User, Long> dao = dbHelper.getUserDao();
        User user = dao.queryForId(id);
        return user;
    }
}
