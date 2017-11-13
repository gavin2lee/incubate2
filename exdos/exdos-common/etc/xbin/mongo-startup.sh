#!/bin/sh
MONGODB_PATH=/opt/mongo/mongo-3.4.10/mongodb/bin
MONGOD_EXEC=$MONGODB_PATH/mongod

MONGODB_CONFIG=./mongodb.conf



if [ -z $1 ];then
	echo "to start mongodb via shell script"
	$MONGOD_EXEC --config $MONGODB_CONFIG
fi

for args in $@
do
        echo $args
done

if [ "stop" = "$1" ];then
	echo "to shutdown mongodb via shell script"
	$MONGOD_EXEC --config $MONGODB_CONFIG --shutdown
fi
