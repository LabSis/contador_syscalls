DROP DATABASE IF EXISTS gestion_ransomware;
CREATE DATABASE IF NOT EXISTS gestion_ransomware;
USE gestion_ransomware;

CREATE TABLE ransomwares(
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    descripcion VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE parametros(
	id INT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50),
    valor VARCHAR(150),
    id_ransomware INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_ransomware) REFERENCES ransomwares (id)
);

CREATE TABLE pruebas(
	id INT NOT NULL AUTO_INCREMENT,
    id_ransomware INT NOT NULL,
    cantidad_datos INT,
    cantidad_archivos INT,
    detector_habilitado BIT,
    deteccion_positiva BIT,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    tiempo_ejecucion_ms LONG NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_ransomware) REFERENCES ransomwares (id)
);

CREATE TABLE syscall_resultados(
	id INT NOT NULL AUTO_INCREMENT,
    syscall VARCHAR(50),
    cantidad INT,
    id_prueba INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_prueba) REFERENCES pruebas (id)
);

CREATE TABLE procesamiento_resultados(
	id INT NOT NULL AUTO_INCREMENT,
    user DOUBLE,
    system DOUBLE,
    id_prueba INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_prueba) REFERENCES pruebas (id)
);

CREATE TABLE disco_resultados(
	id INT NOT NULL AUTO_INCREMENT,
    iowait DOUBLE,
    idle DOUBLE,
    id_prueba INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_prueba) REFERENCES pruebas (id)
);

CREATE TABLE memoria_resultados(
	id INT NOT NULL AUTO_INCREMENT,
    memused DOUBLE,
    porcentaje_memused DOUBLE,
    id_prueba INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id_prueba) REFERENCES pruebas (id)
);

INSERT INTO ransomwares (nombre,descripcion) VALUES ("JAES-128", "Ransomware de cifrado simétrico AES - 128 bits para análisis académico. Parámetros: clave=1234567812345678");
INSERT INTO ransomwares (nombre,descripcion) VALUES ("Jamsomware", "Ransomware adaptado y descargado desde https://github.com/julupu/jamsomware");