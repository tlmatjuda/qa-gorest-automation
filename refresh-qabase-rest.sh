#!/bin/bash

#set -e  # Exit immediately on error
#set -x  # Debug mode: print commands as they are executed

# refresh-qabase-rest.sh
# Copies the latest qabase-rest JAR from local Maven repo to the project's libs/ folder



# CONFIGURATION
# ======================================================================================================================
GROUP_ID_PATH="com/toob"
ARTIFACT_ID="qabase-rest"
VERSION="0.0.1"
M2_REPO="${HOME}/.m2/repository"
M2_JAR_PATH="${M2_REPO}/${GROUP_ID_PATH}/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar"
TARGET_LIB_DIR="./libs"
TARGET_LIB_PATH="${TARGET_LIB_DIR}/${ARTIFACT_ID}-${VERSION}.jar"


# ACTUAL LOGIC / WORK STARTS HERE
# ======================================================================================================================


clear


echo "🚀 Refreshing $ARTIFACT_ID from local Maven repository..."

# Check if the source JAR exists
if [ ! -f "$M2_JAR_PATH" ]; then
  echo "❌ Error: JAR not found in local Maven repository at $M2_JAR_PATH"
  echo "Please make sure you have built qabase-rest or installed it locally."
  exit 1
fi

# Create libs/ directory only if it does not exist
if [ ! -d "$TARGET_LIB_DIR" ]; then
  echo "📁 'libs/' folder not found. Creating it..."
  mkdir "$TARGET_LIB_DIR"
fi

# Copy JAR to libs folder
cp "$M2_JAR_PATH" "$TARGET_LIB_PATH"

echo "✅ Successfully refreshed $ARTIFACT_ID-$VERSION.jar into $TARGET_LIB_DIR/"