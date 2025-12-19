-- Schema for H2 database

CREATE TABLE clients (
    id INT PRIMARY KEY,
    nom VARCHAR(255)
);

CREATE TABLE productes (
    id INT PRIMARY KEY,
    nom VARCHAR(255),
    preu DECIMAL(10,2),
    estoc INT
);

CREATE TABLE comandes (
    id IDENTITY PRIMARY KEY,
    client_id INT,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2)
);

CREATE TABLE linies_comanda (
    comanda_id INT,
    producte_id INT,
    quantitat INT,
    preu_unitari DECIMAL(10,2)
);

-- Insert some sample data
INSERT INTO clients VALUES (1, 'Client 1');
INSERT INTO clients VALUES (2, 'Client 2');

INSERT INTO productes VALUES (1, 'Producte 1', 10.00, 100);
INSERT INTO productes VALUES (2, 'Producte 2', 20.00, 50);
