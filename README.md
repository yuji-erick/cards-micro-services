Cards Micro-Services
==================

## Getting started

To run this Micro-Service:

1- MySQL is mandatory in the local machine.

2- Configure connectivity to MySQL in the application.yml. Default root / root for user and password.

2- Create the Database called "cards".

3- Within this database, run the create tables script, in the root of this project. File: CREATE_TABLES.sql.

4- Run the application.

### Postman documentation

You will find a postman documentation here to help within tests: https://documenter.getpostman.com/view/28276292/2sB2cPkRaa

### APIs

It was developed 6 APIs, which can be described below.

#### Authentication API

* Endpoint to get a JWT Token to use in other calls as Bearer Authentication

#### Create Card API

* Endpoint that have the ability to create a single card.
* Only valid cards will be inserted.
* A valid card can be, in this scenario, a card with 16 position length.
* No duplicated card is accepted.

#### Create Card via Bulk Process API

* Endpoint that receives as input a TXT file with a list of cards.
* This API works with assyncronous process.
* It will return a ID for the bulk process, which can be queried into another API to get the process status.
* The bulk process has this status: PENDING / PROCESSING / PROCESSED / ERROR.
* Pending status is the initial status for the bulk process.
* Processing status occurs when the application starts processing the file.
* Processed status occurs when the file already is processed.
* Error status indicates that the process was aborted due to some systemic inconsistency.

#### Get Bulk Process API

* Endpoint to query status for a bulk process per ID.

#### Get Card API

* Endpoint to retrieve a card via Card Number.

#### Get Cards API

* Endpoint to retrieve all cards from the database.
* The response is paginated, to avoid bigger responses.
* The limit per page is 100 cards.

## Improvements

Below are a few items that can be considered in a future version of this Micro-Service.

1- Run MySQL and SpringBoot within docker;

2- Improve card validation numbers;

3- Implement a RabbitMQ or Kafka to be responsible for card creation;
