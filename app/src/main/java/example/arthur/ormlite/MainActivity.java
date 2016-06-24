package example.arthur.ormlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import example.arthur.ormlite.models.ArtCollection;
import example.arthur.ormlite.models.Artwork;
import example.arthur.ormlite.models.ArtworkArtCollection;
import example.arthur.ormlite.models.User;

public class MainActivity extends AppCompatActivity {

    TextView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (TextView) findViewById(R.id.main);

//        DatabaseHelper killEmAll = OpenHelperManager.getHelper(this, DatabaseHelper.class);
//        killEmAll.clear();

        //creating artworks
        Artwork artwork = new Artwork(100L, "As", "music/as.mp3");
        Artwork artwork2 = new Artwork(101L, "I wish", "music/iwish.mp3");

        User user = new User(200L, "Stevie Wonder");
        User user2 = new User(201L, "Little Arthur");
        ArtCollection artCollection = new ArtCollection(300L, "best of Stevie");
        ArtCollection artCollection2 = new ArtCollection(301L, "test artCollection");


        //List of artworks
        List<Artwork> artworkList = new ArrayList<>();
        artworkList.add(artwork);
        artworkList.add(artwork2);

        //joining artworks and users
        user.setArtworks(artworkList);
        artwork.setOwner(user);
        artwork2.setOwner(user);
        //user2 has nothing to do with this
        user2.setArtworks(null);

        //joining collections and artworks
        artCollection.addArtwork(artwork);
        artCollection.addArtwork(artwork2);
        artwork.addArtCollection(artCollection);
        artwork2.addArtCollection(artCollection);
        artwork.setFromArtCollection(artCollection);
        artwork2.setFromArtCollection(artCollection);

        //joining users and collections
        user.setArtCollections(null);
        user2.addArtCollection(artCollection);
        artCollection.setCurator(user2);
        user2.addArtCollection(artCollection2);
        artCollection2.setCurator(user2);

        DatabaseHelper dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        try {

            Dao<Artwork, Long> artworkDao = dbHelper.getArtworkDao();
            Dao<User, Long> userDao = dbHelper.getUserDao();
            Dao<ArtCollection, Long> collectionDao = dbHelper.getArtCollectionDao();
            Dao<ArtworkArtCollection, Integer> artworkArtCollectionDao = dbHelper.getArtworkArtCollectionDao();

            artworkDao.createOrUpdate(artwork);
            artworkDao.createOrUpdate(artwork2);
//            collectionDao.createOrUpdate(artCollection);
//            collectionDao.createOrUpdate(artCollection2);
            artCollection.saveToDb(dbHelper);
            artCollection2.saveToDb(dbHelper);
            userDao.createOrUpdate(user);
            userDao.createOrUpdate(user2);

            for (Artwork aw : artworkList) {
                for (ArtCollection k : aw.getArtCollections()) {
                    ArtworkArtCollection ak = new ArtworkArtCollection(k, aw);
                    artworkArtCollectionDao.createOrUpdate(ak);
                }
            }

            List<Artwork> artworksOnDb = artworkDao.queryForAll();
            List<User> ownersOnDb = userDao.queryForAll();
            List<ArtCollection> collectionsOnDb = collectionDao.queryForAll();

            java.util.Collection test = artworksOnDb.get(0).getArtCollections();

            ArtCollection col1 = ArtCollection.loadFromDb(dbHelper, 300L);
            ArtCollection col2 = ArtCollection.loadFromDb(dbHelper, 301L);

            main.append(" trycatch");
            dbHelper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        main.append(" done");

    }
}
