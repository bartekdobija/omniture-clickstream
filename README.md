# omniture-clickstream
High level API for [Omniture Clickstream Data Feeds](https://marketing.adobe.com/resources/help/en_US/sc/clickstream/) metadata and log parsing.

The library provides an Omniture log parser and several convenience methods for 
Omniture manifest file loading and parsing, including lookup data and column type resolution.  

The implementation is able to read all data from several popular data sources, 
including Amazon S3, Hadoop/HDFS, and local file systems.

Internally, the Amazon S3 implementation uses [AWSCredentialsProviderChain](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/AWSCredentialsProviderChain.html) 
with [SystemPropertiesCredentialsProvider](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/SystemPropertiesCredentialsProvider.html),
 [EnvironmentVariableCredentialsProvider](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/EnvironmentVariableCredentialsProvider.html) and 
 [InstanceProfileCredentialsProvider](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/InstanceProfileCredentialsProvider.html)

The library has been tested in Java and Scala applications.

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
  <version>10b9161753</version>
</dependency>
```

SBT:

```sbt

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.bartekdobija" % "omniture-clickstream" % "10b9161753"

```

Gradle:

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.bartekdobija:omniture-clickstream:10b9161753'
}
```

### API

Java - metadata load from the Omniture manifest file

```java
String localManifest = "file://omniture_manifest.txt";
String hdfsManifest = "hdfs://namenode/omniture_manifest.txt";
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