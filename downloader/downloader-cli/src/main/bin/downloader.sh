#! /bin/sh

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

if [ -z "$DOWNLOADER_HOME" -o ! -d "$DOWNLOADER_HOME" ] ; then
  ## resolve links - $0 may be a link to ant's home
  PRG="$0"
  progname=`basename "$0"`

  # need this for relative symlinks
  while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
    else
    PRG=`dirname "$PRG"`"/$link"
    fi
  done

  DOWNLOADER_HOME=`dirname "$PRG"`/..

  # make it fully qualified
  DOWNLOADER_HOME=`cd "$DOWNLOADER_HOME" > /dev/null && pwd`
fi

# set DOWNLOADER_LIB location
DOWNLOADER_LIB="${DOWNLOADER_HOME}/lib"

if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    # IBM's JDK on AIX uses strange locations for the executables
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      JAVACMD="$JAVA_HOME/jre/sh/java"
    elif [ -x "$JAVA_HOME/jre/bin/java" ] ; then
      JAVACMD="$JAVA_HOME/jre/bin/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java 2> /dev/null `
    if [ -z "$JAVACMD" ] ; then
        JAVACMD=java
    fi
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi

# Build classpath 
DOWN_CLASSPATH="$DOWNLOADER_LIB/downloader-cli-1.0.0-SNAPSHOT.jar:$DOWNLOADER_LIB/downloader-lib-1.0.0-SNAPSHOT.jar"

# Exec
exec "$JAVACMD" -classpath "$DOWN_CLASSPATH" $ant_sys_opts org.esquivo.downloader.cli.Main $@

