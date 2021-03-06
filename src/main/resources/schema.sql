DROP TABLE IF EXISTS reservation/;
DROP TABLE IF EXISTS usr/;
DROP TABLE IF EXISTS reservable_room/;
DROP TABLE IF EXISTS meeting_room/;

CREATE TABLE IF NOT EXISTS meeting_room (
  room_id INT unsigned NOT NULL AUTO_INCREMENT,
  room_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (room_id)
)/;
CREATE TABLE IF NOT EXISTS reservable_room (
  reserved_date DATE NOT NULL,
  room_id INT unsigned NOT NULL,
  PRIMARY KEY (reserved_date, room_id)
)/;
CREATE TABLE IF NOT EXISTS reservation (
  reservation_id INT unsigned NOT NULL AUTO_INCREMENT,
  end_time TIME NOT NULL,
  start_time TIME NOT NULL,
  reserved_date DATE NOT NULL,
  room_id INT unsigned NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (reservation_id)
)/;
CREATE TABLE IF NOT EXISTS usr (
  user_id VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id)
)/;
#ALTER TABLE reservable_room DROP FOREIGN KEY FK_f4wnx2qj0d59s9tl1q5800fw7/;
#ALTER TABLE reservation DROP FOREIGN KEY FK_p1k4iriqd4eo1cpnv79uvni9g/;
#ALTER TABLE reservation DROP FOREIGN KEY FK_recqnfjcp370rygd9hjjxjtg/;


ALTER TABLE reservable_room ADD CONSTRAINT FK_f4wnx2qj0d59s9tl1q5800fw7 FOREIGN KEY (room_id) REFERENCES meeting_room(room_id)/;
ALTER TABLE reservation ADD CONSTRAINT FK_p1k4iriqd4eo1cpnv79uvni9g FOREIGN KEY (reserved_date, room_id) REFERENCES reservable_room(reserved_date, room_id)/;
ALTER TABLE reservation ADD CONSTRAINT FK_recqnfjcp370rygd9hjjxjtg FOREIGN KEY (user_id) REFERENCES usr(user_id)/;