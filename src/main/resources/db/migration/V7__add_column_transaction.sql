CREATE TABLE IF NOT EXISTS transaction (
     id SERIAL PRIMARY KEY,
     owner_id INTEGER UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS  history (
   id SERIAL PRIMARY KEY,
   value NUMERIC(15,2) NOT NULL,
   transaction_uuid UUID DEFAULT gen_random_uuid() UNIQUE,
   date TIMESTAMP NOT NULL,
   type VARCHAR(50) NOT NULl,
   transaction_id INT NOT NULl,
   status VARCHAR(50) NOT NULL DEFAULT 'COMPLETED',
   transfer_to VARCHAR(100) NOT NULl,
   is_entry_money BOOLEAN,

    CONSTRAINT fk_history_transaction
                 FOREIGN KEY(transaction_id)
                 REFERENCES transaction(id)
                 ON DELETE CASCADE
);

