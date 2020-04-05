INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('taro-yamada', '太郎', '山田', 'USER', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK')/;
INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('aaaa', 'Aaa', 'Aaa', 'USER', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK')/;
INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('bbbb', 'Bbb', 'Bbb', 'USER', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK')/;
INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('cccc', 'Ccc', 'Ccc', 'ADMIN', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK')/;
--
INSERT INTO meeting_room (room_name) VALUES ('新木場')/;
INSERT INTO meeting_room (room_name) VALUES ('辰巳')/;
INSERT INTO meeting_room (room_name) VALUES ('豊洲')/;
INSERT INTO meeting_room (room_name) VALUES ('月島')/;
INSERT INTO meeting_room (room_name) VALUES ('新富町')/;
INSERT INTO meeting_room (room_name) VALUES ('銀座一丁目')/;
INSERT INTO meeting_room (room_name) VALUES ('有楽町')/;
-- Stored Procedure

-- 既存のファンクションをDROP
DROP FUNCTION IF EXISTS REGISTER_RESERVABLE_ROOMS/;

CREATE FUNCTION REGISTER_RESERVABLE_ROOMS()
-- ルーチン定義
  RETURNS
    -- 同じ入力パラメータに対して常に同じ結果を生成する場合:決定的(DETERMINISTIC)
    -- それ以外の場合:非決定的(NOT DETERMINISTIC)
    INT NOT DETERMINISTIC
BEGIN
-- 変数宣言
  DECLARE total, i, id INT;
  DECLARE done INT DEFAULT FALSE;
  DECLARE cursor1 CURSOR FOR SELECT room_id FROM meeting_room; -- カーソル
  /*
    ハンドラ(例外処理)
    CONTINUE:実行制御を元の場所に戻す
    NOT FOUND:'02'で始まるSQLSTATE(カーソルがデータセットの最後に達したときの動作を制御)
  */
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE; 

-- 変数に値を代入
  SET total = 0;

-- カーソルを開く
  OPEN cursor1;

  read_loop: LOOP
    FETCH cursor1 INTO id;
    IF done THEN
      LEAVE read_loop;
    END IF;

    SET i = 0;
    WHILE i < 76 DO
      INSERT INTO reservable_room (reserved_date, room_id)
        VALUES (DATE_ADD( CURRENT_DATE, INTERVAL i - 7 DAY), id);
      SET i = i + 1;
    END WHILE;
    SET total = total + i;
  END LOOP;

  RETURN total;
END /;

SELECT REGISTER_RESERVABLE_ROOMS() /;
COMMIT /;