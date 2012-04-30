/*По названию альбома вывести содержание альбома (исполнитель, трек).*/

SELECT "group".name, track.name
FROM groups "group", albums album, tracks track
WHERE
album."group" = "group".id AND 
track.album = album.id AND
album.name LIKE '%Blush Music%';

/*Определить самую популярную группу. (по рейтингу).*/


SELECT "group".id
FROM (SELECT "group".id,  AVG(rated_tracks.avgrate) as rate
      FROM 
	(SELECT track.id, AVG(rate.value) as avgrate
	 FROM groups "group", albums album, tracks track, rates rate
	 WHERE 
	 album."group" = "group".id AND 
	 track.album = album.id AND
	 rate.track = track.id 
	 GROUP BY track.id) rated_tracks, 
         groups "group", albums album, tracks track
      WHERE
      album."group" = "group".id AND 
      track.album = album.id AND
      rated_tracks.id = track.id 
      GROUP BY "group".id) "group"
WHERE rownum < 2
ORDER BY "group".rate DESC;


/*По исполнителю вывести альбомы с его участием.*/

SELECT album.name
FROM musicians musician, group_members groupref, groups "group", albums album
WHERE
musician.name LIKE '%Ordy Garrison%' AND
groupref.member = musician.id AND groupref."group" = "group".id AND
album."group" = "group".id;


/*Отчеты:*/
/* Вывести рейтинг каждого альбома (определять как сумму рейтингов треков). */

SELECT album.id, album.name, rated_albums.rate as rate
FROM 
 (SELECT album.id, SUM(rated_tracks.avgrate) as rate
  FROM 
   (SELECT track.id, AVG(rate.value) as avgrate
    FROM groups "group", albums album, tracks track, rates rate
    WHERE 
    album."group" = "group".id AND 
    track.album = album.id AND
    rate.track = track.id 
    GROUP BY track.id) rated_tracks, groups "group", albums album, tracks track
  WHERE album."group" = "group".id AND 
  track.album = album.id AND
  rated_tracks.id = track.id
  GROUP BY album.id) rated_albums, albums album
WHERE album.id = rated_albums.id;


/* Вывести исполнителей в некотором жанре.*/

SELECT musician.id, musician.name
FROM (SELECT musician.id
      FROM musicians musician, group_members groupref, groups "group", albums album, genres genre
      WHERE
      groupref.member = musician.id AND groupref."group" = "group".id AND
      album."group" = "group".id AND
      album.genre = genre.id AND
      genre.name LIKE '%Alternative country%'
      GROUP BY musician.id) genre_musician, musicians musician
WHERE 
musician.id = genre_musician.id;


/* Вывести исполнителей, определяющих некоторое настроение. */

SELECT musician.id, musician.name
FROM (SELECT musician.id
      FROM musicians musician, group_members groupref, groups "group", albums album, tracks track,
       (SELECT MAX(value) as minvalue, m.maxvalue as maxvalue
        FROM moods, (SELECT value as maxvalue FROM moods WHERE name LIKE '%Mystic%') m
        WHERE moods.value < m.maxvalue
        GROUP BY m.maxvalue) mood
WHERE
groupref.member = musician.id AND groupref."group" = "group".id AND
album."group" = "group".id AND
track.album = album.id AND
track.mood > mood.minvalue AND track.mood <= mood.maxvalue
GROUP BY musician.id) res_musicians, musicians musician
WHERE res_musicians.id = musician.id;

/*tsp*/
(select * from (SELECT MAX(value) as minvalue FROM moods WHERE value < 3) m1, (SELECT MIN(value) as maxvalue FROM moods WHERE value > 3) m2)

/*binded table */

SELECT "group".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, "group".name as "Group", album.name as Album, track.name as Track, genre.name as Genre, 
mood.name as Mood, rated_tracks.avgrate as AvgRate
FROM 
   (SELECT track.id, AVG(rate.value) as avgrate
    FROM groups "group", albums album, tracks track, rates rate
    WHERE 
    album."group" = "group".id AND 
    track.album = album.id AND
    rate.track = track.id 
    GROUP BY track.id) rated_tracks, groups "group", albums album, tracks track, moods mood, genres genre, (SELECT track.id as track_id, MIN(value) as moodvalue 
                                                                                  FROM moods mood, tracks track WHERE mood.value >= track.mood 
                                                                                  GROUP BY track.id) mooded_tracks                                                                                  
WHERE rated_tracks.id (+)= track.id AND
mooded_tracks.track_id = track.id AND
mooded_tracks.moodvalue = mood.value AND 
track.album = album.id AND
album."group" = "group".id AND 
album.genre = genre.id;




/*track by PK*/

SELECT "group".id as ID, album.id as ID, track.id as ID, genre.id as ID, mood.value as ID, "group".name as "Group", album.name as Album, track.name as Track, genre.name as Genre, 
mood.name as Mood, rated_tracks.avgrate as AvgRate
FROM 
   (SELECT  AVG(rate.value) as avgrate
    FROM groups "group", albums album, tracks track, rates rate
    WHERE 
    album."group" = "group".id AND 
    track.album = album.id AND
    rate.track = track.id AND track.id = 3) rated_tracks, groups "group", albums album, tracks track, moods mood, genres genre, (SELECT MIN(value) as moodvalue 
                                                                                  FROM moods mood, tracks track WHERE mood.value >= track.mood AND track.id = 3) mooded_tracks                                                                                  
WHERE mooded_tracks.moodvalue = mood.value AND 
track.id = 3 AND
track.album = album.id AND
album."group" = "group".id AND 
album.genre = genre.id;


