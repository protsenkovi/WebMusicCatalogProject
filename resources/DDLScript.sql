-- Generated by Oracle SQL Developer Data Modeler 3.0.0.665
--   at:        2012-02-25 15:02:27 MSK
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g
DELETE FROM rates;
DELETE FROM moods;
DELETE FROM tracks;
DELETE FROM albums;
DELETE FROM group_members;
DELETE FROM groups;
DELETE FROM genres;
DELETE FROM musicians;
DROP SEQUENCE group_id;
DROP SEQUENCE album_id;
DROP SEQUENCE track_id;
DROP SEQUENCE genre_id;
DROP SEQUENCE musician_id;
DROP SEQUENCE rate_id;
DROP TABLE rates;
DROP TABLE moods;
DROP TABLE tracks;
DROP TABLE albums;
DROP TABLE group_members;
DROP TABLE groups;
DROP TABLE genres;
DROP TABLE musicians;



CREATE SEQUENCE group_id
    INCREMENT BY 1
    START WITH 0
    MAXVALUE 1000000000
	MINVALUE 0
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE album_id
    INCREMENT BY 1
    START WITH 0
    MAXVALUE 1000000000000
	MINVALUE 0
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE track_id
    INCREMENT BY 1
    START WITH 0
    MAXVALUE 1000000000000
	MINVALUE 0
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE genre_id
    INCREMENT BY 1
    START WITH 0
    MAXVALUE 1000000000000
	MINVALUE 0
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE musician_id
    INCREMENT BY 1
    START WITH 0
    MAXVALUE 1000000000000
	MINVALUE 0
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE rate_id
    INCREMENT BY 1
    START WITH 0
    MAXVALUE 100000000000000
	MINVALUE 0
    NOCACHE
    NOCYCLE;

CREATE TABLE musicians
    ( 
     id NUMBER (9), 
     name VARCHAR2 (100 CHAR), 
     link VARCHAR2 (300),
     CONSTRAINT musician_PK PRIMARY KEY ( id )
    ) 
;


CREATE TABLE genres 
    ( 
     id NUMBER (9), 
     name VARCHAR2 (100),
     CONSTRAINT genre_PK PRIMARY KEY ( id )
    ) 
;

CREATE TABLE groups 
    ( 
     id NUMBER (9), 
     name VARCHAR2 (100 CHAR),
     CONSTRAINT group_PK PRIMARY KEY ( id )
    ) 
;

CREATE TABLE albums 
    ( 
     id NUMBER (9), 
     name VARCHAR2 (100 CHAR), 
     "group" NUMBER (9), 
     genre NUMBER (9),
     CONSTRAINT album_PK PRIMARY KEY ( id ),
     CONSTRAINT album_group_fk FOREIGN KEY ("group") REFERENCES groups (id),
     CONSTRAINT album_genre_fk FOREIGN KEY (genre) REFERENCES genres (id)
    ) 
;

CREATE TABLE tracks
    ( 
     id NUMBER (12), 
     name VARCHAR2 (100 CHAR), 
     album NUMBER (9), 
     mood NUMBER (3),
     CONSTRAINT track_PK PRIMARY KEY ( id ),
     CONSTRAINT track_album_fk FOREIGN KEY (album) REFERENCES albums (id)
    ) 
;

CREATE TABLE group_members 
    ( 
     "group" NUMBER (9), 
     member NUMBER (9),
     CONSTRAINT group_fk FOREIGN KEY ("group") REFERENCES groups (id),
     CONSTRAINT member_fk FOREIGN KEY (member) REFERENCES musicians (id)
    ) 
;

CREATE TABLE moods 
    ( 
     value NUMBER (3), 
     name VARCHAR2 (100 CHAR),
     CONSTRAINT mood_PK PRIMARY KEY ( value )
    ) 
;


CREATE TABLE rates
    ( 
     id NUMBER (14),
     track NUMBER (12), 
     value NUMBER (3),
     CONSTRAINT rate_track_fk FOREIGN KEY (track) REFERENCES tracks (id)
    ) 
;


INSERT INTO moods (value, name)
VALUES (0, 'lowest_boundary'); 
