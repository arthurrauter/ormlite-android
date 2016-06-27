# ORMLite-Android
- Using ORMLite with Android. 
- 'One to Many' and 'Many to Many' relationships. 
- Developed with Android Studio.
- Database is Sqlite3.

##TL;DR
- Run the debugger with a breakpoint in line 117 of the [MainActivity](https://github.com/arthurrauter/ormlite-android/blob/master/app/src/main/java/example/arthur/ormlite/MainActivity.java) and have fun.
- The magic happens mostly in the [models](https://github.com/arthurrauter/ormlite-android/tree/master/app/src/main/java/example/arthur/ormlite/models). See the SaveToDB and LoadFromDb methods.
- Users have Artworks and ArtCollections. ArtCollections and Artworks reference each other.

## Setup
Go to `Project Structure` click the `app` module (or whatever your main module is) and in the `Dependecies` tab add the follow modules:
- com.j256.ormlite:ormlite-android:4.48
- com.j256.ormlite:ormlite-core:4.48

You will need to create:
- a `raw` folder inside you project's `res` folder
- an empty `ormlite_config.txt` inside the `raw` folder
(otherwise you can't run DatabaseConfigUtil)

and finally:
- run `DatabaseConfigUtil.java`

## Overview
Pretend you are managing a Gallery. You have Users, Artworks and ArtCollections. This repo is a very simple application that presents one possible solution to persisting the objects into the database. 

Relationship between classes:

- User to (many) Artworks
- User to (many) ArtCollections
- Artworks (many) to (many) ArtCollections

Users have Artworks and ArtCollections. Therefore we have 2 one-to-many relationships. ArtCollections have Artworks, but the Artworks also reference the ArtCollections they belong to. Therefore we have 1 many-to-many relationship.

## Implementing the relationships with ORMLite
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

## Commentary & Observations
At first I was trying to avoid writing all the SQL statements to generate the tables, because I will use ORMLite for a real application were the objects have much more fields than the ones in this example. Then, I thought that the ORM could help me easily persist all the objects withing my application in way that I could basically just "save all to db" and then "load all from the db" without having to worry about SQL or table structure at all. This was perhaps a bit utopic, but a man has the right to dream. Even after the first disappointments, I thought the ORM would be able to identify that some objects already existed and avoid duplication, but that was not the case.

In the end, I think that my classes are more strongly coupled with the Database than I wanted to (they now depend on ORMLite, for example), but I didn't have to write any SQL statements. 

The end result is that I have to be aware I am using a database and that objects will get duplicated. I cannot recreate the pool of objects I had before and work with unless I created a class to manage such pool, avoiding the creation of unnecessary instances and keeping the references correct. I don't think that the effort is worth for what I need right now. 

### duplicated objects
Every query done to the DB will generate new instances, even if the objects already exist inside you application (regardless of being a one-to-many or many-to-many relationship). For example: query for all ArtCollections, you will get a List of ArtCollections, all containing an User object. Then query for all the Users. You will get a List of Users. When you get the List of Users, new instances will be created for each User. From the Logical point of view at least some of these Users already exist inside the application (they were created and are being referenced by ArtCollection from the List of ArtCollection that we queried before), but in practice new objects for those Users will be created.

## useful links
(don't forget to read the ORMLite docs!)
- https://horaceheaven.com/android-ormlite-tutorial/ 
- https://github.com/j256/ormlite-jdbc/tree/master/src/test/java/com/j256/ormlite/examples 
- http://stackoverflow.com/questions/17673461/ormlite-many-to-many-relation/17701447?noredirect=1#comment63360249_17701447
- http://www.singingeels.com/Articles/Understanding_SQL_Many_to_Many_Relationships.aspx











