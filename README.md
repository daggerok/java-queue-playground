# java-queue-playground

```bash
./mvnw
```

## increase version

```bash
./mvnw build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} -DgenerateBackupPoms=false
```
