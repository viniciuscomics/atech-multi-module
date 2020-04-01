INSERT INTO tbl_airplane(model,manufacturing_year,manufactur)
SELECT * FROM (SELECT 'F12', 2012, 'Embraer') AS tmp 
WHERE NOT EXISTS (
	SELECT model FROM tbl_airplane WHERE model = 'F12'
) LIMIT 1;

INSERT INTO tbl_city (name, name_country)
SELECT * FROM (SELECT 'Sao Paulo', 'Brasil') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM tbl_city WHERE name = 'Sao Paulo'
) LIMIT 1; 

INSERT INTO tbl_city (name, name_country)
SELECT * FROM (SELECT 'Rio de Janeiro', 'Brasil') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM tbl_city WHERE name = 'Rio de Janeiro'
) LIMIT 1; 

INSERT INTO tbl_pilot (name, age, document)
SELECT * FROM (SELECT 'Jose Silva', 50, '12345678') AS tmp
WHERE NOT EXISTS (
    SELECT document FROM tbl_pilot WHERE document = '12345678'
) LIMIT 1; 

INSERT INTO tbl_flight(departure,arrive, status_flight, id_airplane, id_pilot, id_city_source, id_city_destiny)
VALUES ('2020-04-02 19:10:25-07','2020-04-02 21:10:25-07',1,1,1,1,2);