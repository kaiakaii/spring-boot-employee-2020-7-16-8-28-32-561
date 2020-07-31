create table employee(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(255),
    age INT,
    salary INT,
    company_id INT,
    foreign key (company_id) references company(company_id)
)