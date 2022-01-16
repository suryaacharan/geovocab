# Geovocab - Locate any place on earth with just 3 english words
Geovocab simplifies geographic coordinates into 3 simple english words. It makes use of REST APIs to bidirectionally translate geographic coordinates and english words.
This project makes use of spring boot framework to build the REST APIs and package it into a WAR. Docker is used as the deployment platform.

# How it works ?
Geovocab makes use of [Geohashes](https://en.wikipedia.org/wiki/Geohash). It uses 9 character long Geohash to locate in the precision of ≤ 4.77m 	× 	4.77m.
Every single location on earth is split into grids of 4.77m 	× 	4.77m dimension (approx)
Geovocab uses a 3 word sentence to uniquely identify any geohash combination.
One can find the location with the three magic words in matter of milli seconds or share their coordinates with ease.

# Other interesting features
It has an API to create a unique 3 word combo for specific geohash, this is a really cool thing in the space of virtual real-estate. Organizations and individuals can choose to personalize their geo-spatial data
APIs can be extended to support height, and would instantaneously become 3-D

# How to deploy ?
Any code change would involve rebuilding the war file using the command <b>mvn clean package</b>
The modified war file can be copied from the target folder, into Docker/app/api
To start the services navigate to the Docker folder, and run <b>VocabMapper.bat up --build -d</b>; this should setup the mysql container with data, and tomcat container with deployment

# How to test ?
One can make use of the  [Postman collection](https://github.com/suryaacharan/geovocab/blob/dev/PostmanCollection/VocabMapper.postman_collection.json) included in the project, set the environment variables and test the APIs
Alternatively, the HTML file can also be modified and used to test.
