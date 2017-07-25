# DigitalCollections: Core (Backend IMPL File)

## Usage

pom.xml:

```xml
<dependency>
  <groupId>de.digitalcollections</groupId>
  <artifactId>digitalcollections-core-backend-impl-file</artifactId>
  <version>1.2.5-SNAPSHOT</version>
  <scope>runtime</scope>
</dependency>
```

## Configuration

This file repository can be configured by using regular expressions.

Your file resolving configuration files must be placed on the classpath under:

- src/main/resources/de/digitalcollections/core/config/multiPatternResolving-local.yml
- src/main/resources/de/digitalcollections/core/config/multiPatternResolving-DEV.yml
- src/main/resources/de/digitalcollections/core/config/multiPatternResolving-PROD.yml

This configuration file defines a list of patterns with one ore more substitutions.
These are used for resolving IDs to a concrete URI, e.g. on the file system, the classpath or even a remote HTTP endpoint.
You can specify multiple substitutions, the resolver will try to match them against the desired MIME type and return all that matches.
The repository will then verify which of these URIs are actually readable and return the first matching substitution.
In the example below, we have two MIME types (tiff/jpeg) and for JPEG two resolutions in decreasing order of quality, so that the higher-resolution image will be chosen if it is available. The MIME type JSON is resolved using the second pattern.

Example configuration:

```yml
- pattern: bsb(\d{8})_(\d{5})
  substitutions:
    - 'file:/var/local/bsb$1/images/original/bsb$1_$2.tif'
    - 'file:/var/local/bsb$1/images/300/bsb$1_$2.jpg'
    - 'file:/var/local/bsb$1/images/150/bsb$1_$2.jpg'

- pattern: bsb(\d{8})
  substitutions:
    - 'file:/var/local/bsb$1/manifest/bsb$1.json'
```
