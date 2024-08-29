CREATE TABLE products (
    id TEXT UNIQUE NOT NULL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    price INT NOT NULL,
    categorie TEXT NOT NULL,
    image TEXT
);

CREATE TABLE categories (
    id TEXT UNIQUE NOT NULL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    image TEXT
);