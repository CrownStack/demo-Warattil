#!/bin/bash

fastlane hockey_beta
f='./app/build.gradle'
v=`grep 'versionName' $f`
value=v$(echo `grep 'versionName' $f` | grep -Eo '[0-9]{1,4}.[0-9]{1,4}.[0-9]{1,4}')
echo "Version number is "$value
git tag $value
git push origin $value
echo "Successfully uploaded on hockey app."

