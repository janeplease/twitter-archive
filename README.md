1. Store bookmarks of a user
2. Store tweets and retweets of a user
3. Figure out the time thing
4. Auto create user and start session? Close session after every request?
5. Error handling


Broad aims:
1. Write a program that retrieves and stores
(i) All the tweets that a user has liked
(ii) All the bookmarks of the user
(iii) All the tweets and retweets of a user
3. Find a way to store threads.
2. Zip all these files and store them in remote location, in my case in Amazon S3.
3. Periodically update database and remove older datastores. 
4. Make a UI to view liked, bookmarked and profile tweets.


To-do:
1. Protect sensitive information. Read up about security best practices.
2. Figure out how to store the tweets information i.e. what database to use. SQL or NoSQL. 
2. Store all the tweets information including pictures and videos in a database. First the database should be local and then it can be hosted on AWS.
