#!/bin/bash

hotelDir=$1

for file in $hotelDir/* 
do
	fileName=$(basename $file .dat) 
	reviewCount=$(grep -c "<Author>" $file)  
	echo $fileName $reviewCount 
done | sort -n -k2 -r

