DROP TABLE IF EXISTS task;

CREATE TABLE task (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(50)  NOT NULL,
  description VARCHAR(250) NOT NULL,
  worth INT NOT NULL
);

INSERT INTO task (id, name, description, worth) VALUES
    (1, 'Beer Bottles', 'Take empty beer bottles to the collection point', 1),
    (2, '100 burpees', 'Make `em in 10 mins like you could 1 year ago', 3),
    (3, 'Bachelor Party Video', 'You`ve been postponing putting that thing together for like 7 years', 5),
    (4, 'Dog Vet', 'Take the dog to the vets and find out why she`s coughing', 2),
    (5, 'Handstand', 'Learn how to do a handstand and walk 30m on hands', 5),
    (6, 'Roadmap', 'You know what to do, do you do not?', 13),
    (7, 'Hygienist', 'Get your teeth cleaned at hygienist`s', 3);