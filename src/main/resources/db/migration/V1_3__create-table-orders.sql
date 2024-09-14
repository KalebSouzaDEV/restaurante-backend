CREATE TABLE orders (
    id TEXT UNIQUE NOT NULL PRIMARY KEY,
    valueOrder INTEGER NOT NULL,
    method TEXT NOT NULL,
    client TEXT NOT NULL,
    createdAt TIMESTAMP
);