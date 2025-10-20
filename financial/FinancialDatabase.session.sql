DROP TABLE subscription_customer_financial;
DROP TABLE delivery_financial;
DROP TABLE customer_financial;
DROP TABLE subscription;

SELECT * FROM customer_financial;
SELECT * FROM delivery_financial;
SELECT * FROM subscription;

UPDATE customer_financial SET cancel = false WHERE subscription_id = 'f1b2ba32-60df-4fc3-8545-8e95536cb988';
UPDATE delivery_financial SET total_delivery = 20, total_value_delivery = 200.0 WHERE id = '3b7ac078-748b-4592-9cd5-3c42f7799a4e';