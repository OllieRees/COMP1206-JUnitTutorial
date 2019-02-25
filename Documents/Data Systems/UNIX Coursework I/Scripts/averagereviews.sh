#!/bin/bash

hotelDir=$1

for file in $hotelDir/* 
do
	
	fileName=$(basename $file .dat) 
	
	reviewCount=$(grep -c "<Author>" $file) 
	
	ratingSum=$(grep "<Overall>" $file | sed 's/<Overall>//g' 
		| awk '{sum += $1}END{print sum}')	

	averageRating=$(awk -v c=$reviewCount -v s=$ratingSum 
		'BEGIN {printf("%0.2f", (s/c));}')
	
	echo $fileName $averageRating

done | sort -n -k2 -r

