SET FOREIGN_KEY_CHECKS=0;
DELETE FROM bank_accounts WHERE user_id = 1;
DELETE FROM users WHERE id = 1;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO users (
    id,
    email,
    password,
    birth_date,
    first_name,
    last_name,
    num_identification,
    phone,
    created_at,
    updated_at
) VALUES (
             1,
             'testuser@example.com',
             '1234',
             '1990-01-01',
             'Test',
             'User',
             '7654321',
             '999999999',
             NOW(),
             NOW()
         );

INSERT INTO bank_accounts (
    id,
    account_number,
    balance,
    status,
    user_id,
    created_at,
    updated_at
) VALUES (
             1,
             '123456789',
             1000.00,
             'ACTIVE',
             1,
             NOW(),
             NOW()
         );
