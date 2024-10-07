-- Set the sequence for ourusers to the max id value
SELECT setval('ourusers_id_seq', (SELECT MAX(id) FROM ourusers));

-- Set the sequence for products to the max id value
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
