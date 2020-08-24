#!/bin/sh -x
APP_USER=`whoami`
APP_PID=`ps -ef | grep 'java ' | grep '/rfg-qpid-embedded.jar' | grep ${APP_USER} | grep -v grep | awk '{print $2}'`
if [ -n "$APP_PID" ]; then
        kill -9 $APP_PID
        exit 0
fi
exit 0

