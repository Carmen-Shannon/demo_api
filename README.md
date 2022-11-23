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


##Running the application
Firstly, a MySQL database needs to be created and set up:

```
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema forbesapi
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema forbesapi
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `forbesapi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `forbesapi` ;

-- -----------------------------------------------------
-- Table `forbesapi`.`words`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forbesapi`.`words` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 62
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

```

To run the application, run a maven install to a .war file, then in the command line run: "java -jar *filename*.war"
