package example.arthur.ormlite.models;

import com.j256.ormlite.field.DatabaseField;

public class ArtworkArtCollection {
    public static final String ARTWORK_ID_COLNAME = "artwork_id";
    public static final String ARTCOLLECTION_ID_COLNAME = "collection_id";

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(foreign = true, columnName = ARTCOLLECTION_ID_COLNAME)
    ArtCollection artCollection;

    @DatabaseField(foreign = true, columnName = ARTWORK_ID_COLNAME)
    Artwork artwork;

    public ArtworkArtCollection() {
    }

    public ArtworkArtCollection(ArtCollection artCollection, Artwork artwork) {
        this.artCollection = artCollection;
        this.artwork = artwork;
    }

}
