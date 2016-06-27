# ormlite-android
Using ORMLite with Android. 'One to Many' and 'Many to Many' relation included. Developed with Android Studio.

## setup
Go to `Project Structure` -> `app` (or whatever your main module is) and in the `Dependecies` tab add the follow modules:
- com.j256.ormlite:ormlite-android:4.48
- com.j256.ormlite:ormlite-android:4.48

You will need to create:
- `raw` folder inside you project's `res` folder
- an empty `ormlite_config.txt` inside the `raw` folder
(otherwise you can't run DatabaseConfigUtil)

## overview
### what the classes mean
You have Users, Artworks and ArtCollections. Users have Artworks and ArtCollections. Therefore we have 2 one-to-many relationships:
- User to (many) Artworks
- User to (many) ArtCollections

ArtCollections have Artworks, but the Artworks also reference the ArtCollections they belong to. Therefore we have 1 many-to-many relationship.

### how to implement the relationships with ORMLite
The one-to-many relationship is very easy:
- User has to have a @ForeignCollectionField with a COLLECTION (no, lists won't work):
```java
@ForeignCollectionField(eager = true)
    java.util.Collection<Artwork> artworks;
```
- Artworks has to reference back to User:
```java
@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    User owner;
```

The many-to-many relationship is trickier. You will need to manage a linker table (or proxy table) between the Artwork and ArtCollection. This is done with the ArtworkArtCollection class.

```java
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
```

Instead of having an annotation to handle the relationship for you, you will have to populate this an object of this class for every link between an ArtCollection and an Artwork and save it to the database.











