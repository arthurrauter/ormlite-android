package example.arthur.ormlite;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import example.arthur.ormlite.models.ArtCollection;
import example.arthur.ormlite.models.Artwork;
import example.arthur.ormlite.models.ArtworkArtCollection;
import example.arthur.ormlite.models.User;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{Artwork.class, User.class, ArtCollection.class, ArtworkArtCollection.class};

    public static void main(String[] args) throws SQLException, IOException {
        String currDirectory = "user.dir";
        String configPath = "/app/src/main/res/raw/ormlite_config.txt";
        String projectRoot = System.getProperty(currDirectory);
        String fullConfigPath = projectRoot + configPath;

        File configFile = new File(fullConfigPath);

        if (configFile.exists()) {
            configFile.delete();
            configFile = new File(fullConfigPath);
        }

        writeConfigFile(configFile, classes);
    }
}
