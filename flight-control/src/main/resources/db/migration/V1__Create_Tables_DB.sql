CREATE TABLE tbl_airplane (
id BIGSERIAL PRIMARY KEY NOT NULL,
model VARCHAR(30),
manufacturing_year INT NOT NULL,
manufactur VARCHAR(20) NOT NULL
);

CREATE TABLE tbl_city (
id BIGSERIAL PRIMARY KEY NOT NULL,
name VARCHAR(50) NOT NULL,
name_country VARCHAR(30) NOT NULL,
UNIQUE (name, name_country)
);

CREATE TABLE tbl_pilot(
id BIGSERIAL PRIMARY KEY NOT NULL,
name VARCHAR(50) NOT NULL,
age INT NOT NULL,
document VARCHAR(14) NOT NULL UNIQUE
);

CREATE TABLE tbl_flight(
id BIGSERIAL PRIMARY KEY NOT NULL,
departure TIMESTAMP NOT NULL,
arrive TIMESTAMP NOT NULL,
status_flight INT NOT NULL,
id_airplane INT NOT NULL,
id_pilot INT NOT NULL,
id_city_source INT NOT NULL,
id_city_destiny INT NOT NULL,
FOREIGN KEY (id_airplane) REFERENCES tbl_airplane (id) ON DELETE CASCADE,
FOREIGN KEY (id_pilot) REFERENCES tbl_pilot (id) ON DELETE CASCADE,
FOREIGN KEY (id_city_source) REFERENCES tbl_city (id) ON DELETE CASCADE,
FOREIGN KEY (id_city_destiny) REFERENCES tbl_city (id) ON DELETE CASCADE
);




