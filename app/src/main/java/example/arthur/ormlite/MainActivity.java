package example.arthur.ormlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

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

        //creating users
        List<User> userList = new ArrayList<>();
        userList.add(new User(11L, "John Coltrane"));
        userList.add(new User(12L, "Arthur"));

        //creating artworks
        List<Artwork> artworkList = new ArrayList<>();
        artworkList.add(new Artwork(1L, "Giant Steps", "music/giant-steps.mp3"));
        artworkList.add(new Artwork(2L, "Cousin Mary", "music/cousin-mary.mp3"));
        artworkList.add(new Artwork(3L, "Equinox", "music/equinox.mp3"));

        //creating collections
        List<ArtCollection> artCollectionList = new ArrayList<>();
        artCollectionList.add(new ArtCollection(21L, "some Jazz"));
        artCollectionList.add(new ArtCollection(22L, "test collection"));

        //joining artworks and users
        //all artworks so far belong to John Coltrane and none to Arthur
        userList.get(0).addArtwork(artworkList.get(0));
        userList.get(0).addArtwork(artworkList.get(1));
        userList.get(0).addArtwork(artworkList.get(2));
        artworkList.get(0).setOwner(userList.get(0));
        artworkList.get(1).setOwner(userList.get(0));
        artworkList.get(2).setOwner(userList.get(0));
        //Arthur has no artworks
        userList.get(1).setArtworks(null);

        //making the collections have artworks and users have collections
        //Coltrane has testcol, which has equinox. Equinox references testcol.
        ArtCollection testcol = artCollectionList.get(1);
        User coltrane = userList.get(0);
        Artwork equinox = artworkList.get(2);
        testcol.addArtwork(equinox);
        equinox.addArtCollection(testcol);
        coltrane.addArtCollection(testcol);
        testcol.setCurator(coltrane);

        //arthur has a jazzcol with 3 artworks
        ArtCollection jazzcol = artCollectionList.get(0);
        User arthur = userList.get(1);
        jazzcol.addArtwork(artworkList.get(0));
        jazzcol.addArtwork(artworkList.get(1));
        jazzcol.addArtwork(artworkList.get(2));
        jazzcol.setCurator(arthur);
        //the artworks reference the collections they belong to
        for (Artwork a : artworkList){
            a.addArtCollection(jazzcol);
        }
        arthur.addArtCollection(jazzcol);

        DatabaseHelper dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        try {

            Dao<Artwork, Long> artworkDao = dbHelper.getArtworkDao();
            Dao<User, Long> userDao = dbHelper.getUserDao();
            Dao<ArtCollection, Long> collectionDao = dbHelper.getArtCollectionDao();
            Dao<ArtworkArtCollection, Integer> artworkArtCollectionDao = dbHelper.getArtworkArtCollectionDao();

            //save everything to the DB
            for (Artwork aw : artworkList) {
                aw.saveToDb(dbHelper);
            }

            for (ArtCollection ac : artCollectionList) {
                ac.saveToDb(dbHelper);
            }

            for (User u : userList) {
                u.saveToDb(dbHelper);
            }

            List<User> ownersOnDb = userDao.queryForAll();
            List<Artwork> artworksOnDb = artworkDao.queryForAll();
            List<ArtCollection> collectionsOnDb = collectionDao.queryForAll();

            main.append(" trycatch");
            dbHelper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        main.append(" done");

    }
}
