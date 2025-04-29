#!/bin/bash

set -e  # Exit immediately on error
#set -x  # Debug mode: print commands as they are executed

# qabase-rest-install.sh
# Installs the qabase-rest JAR into the local Maven repository (~/.m2)


# CONFIGURATION
# ======================================================================================================================
GROUP_ID="com.toob"
ARTIFACT_ID="qabase-rest"
VERSION="0.0.1"
PACKAGING="jar"
JAR_FILE="./libs/$ARTIFACT_ID-$VERSION.jar"


# ACTUAL LOGIC / WORK STARTS HERE
# ======================================================================================================================

clear

# Check if the JAR file exists
if [ ! -f "$JAR_FILE" ]; then
  echo "❌ Error: JAR file not found at $JAR_FILE"
  echo "Please make sure the qabase-rest JAR is placed inside ./libs/"
  exit 1
fi

# Install the JAR
mvn install:install-file \
  -Dfile="$JAR_FILE" \
  -DgroupId="$GROUP_ID" \
  -DartifactId="$ARTIFACT_ID" \
  -Dversion="$VERSION" \
  -Dpackaging="$PACKAGING"

echo "✅ Successfully installed $ARTIFACT_ID:$VERSION into local Maven repository."