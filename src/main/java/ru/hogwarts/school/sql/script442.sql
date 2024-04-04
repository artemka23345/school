CREATE TABLE cars
(
    id    INTEGER PRIMARY KEY,
    brand VARCHAR,
    model VARCHAR,
    price  INTEGER
);
CREATE TABLE users
  (
      id          INTEGER PRIMARY KEY,
      name        VARCHAR,
      age         INTEGER,
      has_license BOOLEAN,
      car_id      INTEGER REFERENCES cars (id));