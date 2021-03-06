# omniture-clickstream
High level API for [Adobe Analytics Clickstream Data Feeds](https://marketing.adobe.com/resources/help/en_US/sc/clickstream/) metadata and log parsing.

The library provides a log parser and several convenience methods for manifest file loading and parsing, including lookup data and column type resolution.

The implementation is able to read all data from several popular data sources, 
including Amazon S3, Hadoop/HDFS, and local file systems.

Internally, the Amazon S3 [DataSource](https://github.com/bartekdobija/omniture-clickstream/blob/master/src/main/java/com/github/bartekdobija/omniture/loader/S3DataLoader.java) implementation uses [AWSCredentialsProviderChain](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/AWSCredentialsProviderChain.html) 
with [SystemPropertiesCredentialsProvider](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/SystemPropertiesCredentialsProvider.html),
 [EnvironmentVariableCredentialsProvider](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/EnvironmentVariableCredentialsProvider.html) and 
 [InstanceProfileCredentialsProvider](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/InstanceProfileCredentialsProvider.html).

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
  <version>65002e3321</version>
</dependency>
```

SBT:

```sbt

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.bartekdobija" % "omniture-clickstream" % "65002e3321"

```

Gradle:

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.bartekdobija:omniture-clickstream:65002e3321'
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
