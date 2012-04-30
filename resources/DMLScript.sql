/*Filling moods table*/

INSERT INTO moods (value, name)
VALUES (4, 'Mystic');

INSERT INTO moods (value, name)
VALUES (2, 'Depressive');

INSERT INTO moods (value, name)
VALUES (5, 'Cool');

Commit;

/*FILLING INFORMATION FOR WOVENHAND GROUP*/

/*Filling musicians table*/

INSERT INTO musicians(id, name, link)
VALUES(musician_id.NEXTVAL, 'David Eugene Edwards', 'http://en.wikipedia.org/wiki/David_Eugene_Edwards'); 

INSERT INTO musicians(id, name, link)
VALUES(musician_id.NEXTVAL, 'Ordy Garrison', NULL); 

INSERT INTO musicians(id, name, link)
VALUES(musician_id.NEXTVAL, 'Pascal Humbert', NULL); 

Commit;
/*Filling genres table*/

INSERT INTO genres(id, name)
VALUES(genre_id.NEXTVAL, 'Alternative country'); 

Commit;
/*Filling groups table*/

INSERT INTO groups(id, name)
VALUES (group_id.NEXTVAL, 'Wovenhand'); 

Commit;
/*Filling group_members table*/

INSERT INTO group_members("group", member)
VALUES ((SELECT id FROM groups WHERE name = 'Wovenhand') , (SELECT id FROM musicians WHERE name = 'David Eugene Edwards'));

INSERT INTO group_members("group", member)
VALUES ((SELECT id FROM groups WHERE name = 'Wovenhand') , (SELECT id FROM musicians WHERE name = 'Ordy Garrison'));

INSERT INTO group_members("group", member)
VALUES ((SELECT id FROM groups WHERE name = 'Wovenhand') , (SELECT id FROM musicians WHERE name = 'Pascal Humbert'));

Commit;
/*Filling albums table*/

INSERT INTO albums (id, name, "group", genre)
VALUES (album_id.NEXTVAL, 'Blush Music', (SELECT id FROM groups WHERE name = 'Wovenhand'), (SELECT id FROM genres WHERE name LIKE 'Alternative country'));

Commit;
/*FILLING tracks table*/

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Cripplegate (Standing On Glass)', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Animalitos (Ain''t No Sunshine)', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'White Bird', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Snake Bite', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'My Russia (Standing On Hands)', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'The Way', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Aeolian Harp (Under the World)', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Your Russia (Without Hands)', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Another White Bird', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Story and Pictures', (SELECT id FROM albums WHERE name = 'Blush Music'), (SELECT value FROM moods WHERE name = 'Mystic'));

Commit;

/*Filling rates table*/

INSERT INTO rates (id, track, value)
VALUES (rate_id.NEXTVAL, (SELECT id FROM tracks WHERE name = 'Cripplegate (Standing On Glass)'), 10);

INSERT INTO rates (id, track, value)
VALUES (rate_id.NEXTVAL, (SELECT id FROM tracks WHERE name = 'Cripplegate (Standing On Glass)'), 6);

INSERT INTO rates (id, track, value)
VALUES (rate_id.NEXTVAL, (SELECT id FROM tracks WHERE name = 'Cripplegate (Standing On Glass)'), 12);

INSERT INTO rates (id, track, value)
VALUES (rate_id.NEXTVAL, (SELECT id FROM tracks WHERE name = 'Your Russia (Without Hands)'), 15);

Commit;




/*FILLING INFORMATION FOR ZAZ GROUP*/

/*Filling musicians table*/

INSERT INTO musicians(id, name, link)
VALUES(musician_id.NEXTVAL, 'Isabelle Geffroy', 'http://ru.wikipedia.org/wiki/ZAZ'); 

Commit;
/*Filling genres table*/

INSERT INTO genres(id, name)
VALUES(genre_id.NEXTVAL, 'Pop'); 

Commit;
/*Filling groups table*/

INSERT INTO groups(id, name)
VALUES (group_id.NEXTVAL, 'Zaz'); 

Commit;
/*Filling group_members table*/

INSERT INTO group_members("group", member)
VALUES ((SELECT id FROM groups WHERE name = 'Zaz') , (SELECT id FROM musicians WHERE name = 'Isabelle Geffroy'));

Commit;
/*Filling albums table*/

INSERT INTO albums (id, name, "group", genre)
VALUES (album_id.NEXTVAL, 'ZAZ', (SELECT id FROM groups WHERE name = 'Zaz'), (SELECT id FROM genres WHERE name LIKE 'Pop'));

Commit;
/*FILLING tracks table*/

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Les passants', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Je veux', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Le long de la route', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'La fee', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Trop sensible', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Prends garde a ta langue', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Ni oui ni non', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Port Coton', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'J''aime a nouveau', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Dans ma rue', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

INSERT INTO tracks (id, name, album, mood)
VALUES (track_id.NEXTVAL, 'Eblouie par la nuit', (SELECT id FROM albums WHERE name = 'ZAZ'), (SELECT value FROM moods WHERE name = 'Cool'));

Commit;

/*Filling rates table*/

INSERT INTO rates (track, value)
VALUES ((SELECT id FROM tracks WHERE name = 'Je veux'), 10);

INSERT INTO rates (track, value)
VALUES ((SELECT id FROM tracks WHERE name = 'Je veux'), 15);

INSERT INTO rates (track, value)
VALUES ((SELECT id FROM tracks WHERE name = 'Je veux'), 12);

INSERT INTO rates (track, value)
VALUES ((SELECT id FROM tracks WHERE name = 'Port Coton'), 7);

Commit;
