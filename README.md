# java-queue-playground [![Build Status](https://travis-ci.org/daggerok/java-queue-playground.svg?branch=master)](https://travis-ci.org/daggerok/java-queue-playground)

```bash
./mvnw
```

## increase version

```bash
./mvnw versions:display-property-updates
./mvnw build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}-SNAPSHOT -DgenerateBackupPoms=false
```
