CREATE TABLE IF NOT EXISTS card (
    card_id INT AUTO_INCREMENT PRIMARY KEY,
    create_date DATETIME,
	card_number VARCHAR(255),
	creation_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS bulk_process (
    bulk_process_id INT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
	status VARCHAR(255),
	create_date DATETIME
);

CREATE TABLE IF NOT EXISTS bulk_process_item (
    bulk_process_item_id INT AUTO_INCREMENT PRIMARY KEY,
	bulk_process_id INT,
	card_id INT,
    card_number VARCHAR(255),
    line_id VARCHAR(255),
    bulk_id VARCHAR(255),
    status VARCHAR(255)
);