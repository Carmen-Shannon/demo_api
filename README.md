# Forbes API Demo using Spring Boot and Java

## Overview
This application is designed as a REST API with two different paths with multiple endpoints, /dictionary and /story to deliver different data

## Paths

#### Dictionary
The /dictionary path can be accessed via URL 'http://localhost:8080/dictionary' and will return a list of all the words currently stored in the dictionary. 

Within this /dictionary path a user can append /add/{input} where {input} is the user defined string to add to the dictionary. If a word is added that already exists within the dictionary, a 200 status will be returned. If the word does not currently exist within the dictionary, a 202 status will be returned.

A user can also append /delete/{input} where {input} is a word in the dictionary the user wishes to remove from the dictionary. A 202 status will be returned if the word exists within the dictionary and is successfully removed.

#### Story
The /story path can be accessed via URL 'http://localhost:8080/story' and will return a body that consists of a list of words that are not found in the dictionary as well as their closest match to words that are found in the dictionary.


## Design
This application is using MySQL as a database to store all of the words in the dictionary. This allows to easy manipulation of the data and since this dataset is not due to change in the future, MySQL is an easy way to get the data integrated. The story is simply stored locally on the server as a string since it is not modifyable.

# Path Examples
###### /dictionary
http://localhost:8080/dictionary will return a list of words currently stored in the dictionary, not in order. This is a get request. 

Successful request with 200 status:

```json
[
    "20",
    "and",
    "apple",
    ...
]
```

Unsuccessful request with 500 status:

```json
	"Unable to retrieve data"
```

##### /dictionary/add/{newWord}
http://localhost:8080/dictionary/add/{newWord} will allow users to add new words to the dictionary. It will perform a check against the database in the event that word already exists. This is a post request.

Successful request with 202 status will return nothing in the body of the response, just the 202 status.

Successful request with a duplicate match existing will return a 200 status:

```json
	"Duplicate entry"
```

Unsuccessful request with 400 status:

```json
	"Bad request"
```

##### /dictionary/delete/{wordToBeDeleted}
http://localhost:8080/dictionary/delete/{wordToBeDeleted} will allow the user to delete a specified word through a path variable from the dictionary. If the word does not exist in the dictionary a 404 status will be returned.

Successful request with 202 status will return nothing in the body of the response, just the 202 status.

Unsuccessful request due to 400 status will return the status accordingly:

```json
	"Bad request"
```

Unsuccessful request due to word not existing in the dictionary will return a 404 status:

```json
	"Entry not found"
```

##### /story
http://localhost:8080/story is a post route that allows a user to retrieve all of the words within a set story that do not currently exist in the database and returns them as a 2d array that contains each word and it's closest match.

Successful request with 200 status:

```json
[
    [
        "belives",
        "believes"
    ],
    [
        "capitalizm",
        "capitalism"
    ],
    [
        "ignight",
        "ignite"
    ],
    ...
]
    
```

Unsuccessful request with 400 status:

```json
	"Bad request"
```
