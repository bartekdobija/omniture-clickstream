# omniture-clickstream
Omniture clickstream log parser (alpha version)

### Configuration

Maven:

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>

<dependency>
  <groupId>com.github.bartekdobija</groupId>
  <artifactId>omniture-clickstream</artifactId>
  <version>71deaff440</version>
</dependency>
```

SBT:

```sbt

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.bartekdobija" % "omniture-clickstream" % "71deaff440"

```

Gradle:

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.bartekdobija:omniture-clickstream:71deaff440'
}
```

### API

Java - metadata load from the Omniture manifest file

```java
String localManifest = "file://omniture_manifest.txt";
String hdfsManifest = "hdfs://hadoopnode/omniture_manifest.txt";
String s3Manifest = "s3://my-omniture/manifest_a.txt,s3://my-omniture/manifest_b.txt";
String row = "a\tb\tc";

OmnitureMetadata metadata = new OmnitureMetadataFactory().create(hdfsManifest);

// or get a metadata list
List<OmnitureMetadata> metadatas = new OmnitureMetadataFactory().create(s3Manifest, ",");

RowParser parser = DenormalizedDataRowParser.newInstance(metadata);
Row row = parser.parse(row);

RowParserStats stats = parser.getRowParserStats();
```

[![Build Status](https://travis-ci.org/bartekdobija/omniture-clickstream.svg?branch=master)](https://travis-ci.org/bartekdobija/omniture-clickstream)