# JMongo - a Java to MongoDB mapper

MongoDB (mongodb.org) comes with a Java Driver. However, the DBObject instances which come with map semantics need to be mapped manually to the domain objects. This project is inspired from Google's GSON which provides mapping between JSON and Java objects. The idea is just to do Java to Mongo mapping, not to add layers of complexity, sessions, etc., like some projects out there. Mongo Query language and Mongo API are pretty simple, and I think we don't need any additional layers. The only annoying bit was the mapping. I didn't like the complexity of other projects, this is why I wrote my simple utility.

## Installation:
JMongo is available on Maven central.  Just add the dependency to your pom.xml:


`<groupId>uk.co.innoltd</groupId>`

`<artifactId>jmongo</artifactId>`

`<version>1.0.0</version>`


## Usage:
// create an instance

JMongo jMongo = new JMongo();

// convert your entity to DBObject

MyClass myObject = new MyClass();

DBObject dbObject = jMongo.toDBObject(myObject);


// use the DBObject with MongoDB Java driver

collection.save(dbObject);


// convert DBObject to your entity type

dbObject = collection.findOne();

myObject = jMongo.fromDBObject(MyClass.class, dbObject);


## Details
JMongo assumes that field names in Java will be the same as the field names of the MongoDB document. The persisted classes need to have: 

*   _id field of type ObjectId or any other type
*   a no argument constructor


Attributes that you do not want to persist can be marked with the @MongoIgnore annotation.

