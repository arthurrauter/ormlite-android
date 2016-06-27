# ormlite-android
Using ORMLite with Android. 'One to Many' and 'Many to Many' relation included. Developed with Android Studio. Database is Sqlite3.

## setup
Go to `Project Structure` -> `app` (or whatever your main module is) and in the `Dependecies` tab add the follow modules:
- com.j256.ormlite:ormlite-android:4.48
- com.j256.ormlite:ormlite-android:4.48

You will need to create:
- `raw` folder inside you project's `res` folder
- an empty `ormlite_config.txt` inside the `raw` folder
(otherwise you can't run DatabaseConfigUtil)

and finally:
- run `DatabaseConfigUtil.java`

## overview
### what is this?
Pretend you are managing a Gallery: you have Users, Artworks and ArtCollections. This repo is a very simple application that presents one possible solution to persisting the objects into the database. 

- User to (many) Artworks
- User to (many) ArtCollections
- Artworks (many) to (many) ArtCollections

Users have Artworks and ArtCollections. Therefore we have 2 one-to-many relationships. ArtCollections have Artworks, but the Artworks also reference the ArtCollections they belong to. Therefore we have 1 many-to-many relationship.

### how to implement the relationships with ORMLite
One-to-Many:
- User has to have a @ForeignCollectionField with a COLLECTION (no, lists won't work):
```java
@ForeignCollectionField(eager = true)
java.util.Collection<Artwork> artworks;
```
- Artwork *has* to reference back to the User it belongs to:
```java
@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
User owner;
```

The Many-to-Many relationship is trickier. You will need to manage a linker table (or proxy table) between the Artwork and ArtCollection. This is done with the ArtworkArtCollection class.

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

Instead of having an annotation to handle the relationship for you, you will have to populate an object of this class for every link between an ArtCollection and an Artwork and save it to the database.

When you want to retrieve which Artworks belong to an ArtCollection you will have to query the linker table as well. See: `List<Artwork> lookupArtworksForArtCollection(DatabaseHelper dbHelper)` inside ArtCollection.

## commentary & observations
I could not find a way to keep the Database transparent for the rest of the application because of the Many-to-many relationship. I had to manage myself the linker table and there is no way to "hide" it. I tried wrapping all the operations inside the `saveToDb` and `loadFromDb` methods, but those methods still require a DatabaseHelper to function.

Every query done to the DB will generate new instances, even if the objects already exist inside you application (regardless of being a one-to-many or many-to-many relationship). For example: query for all ArtCollections, you will get a List of ArtCollections, all containing an User object. Then query for all the Users. You will get a List of Users. When you get the List of Users, new instances will be created for each User. From the Logical point of view at least some of these Users already exist inside the application (they were created and are being referenced by ArtCollection from the List of ArtCollection that we queried before), but in practice new objects for those Users will be created.



## useful links
(don't forget to read the ORMLite docs!)
- https://horaceheaven.com/android-ormlite-tutorial/ 
- https://github.com/j256/ormlite-jdbc/tree/master/src/test/java/com/j256/ormlite/examples 
- http://stackoverflow.com/questions/17673461/ormlite-many-to-many-relation/17701447?noredirect=1#comment63360249_17701447
- http://www.singingeels.com/Articles/Understanding_SQL_Many_to_Many_Relationships.aspx











