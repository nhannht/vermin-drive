CREATE TABLE IF NOT EXISTS USERS (
                                     userid INT PRIMARY KEY auto_increment,
                                     username VARCHAR(20),
    salt VARCHAR,
    password VARCHAR,
    firstname VARCHAR(20),
    lastname VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS FILES (
                                     id INT PRIMARY KEY auto_increment,
                                     name VARCHAR,
                                     content_type VARCHAR,
                                     size VARCHAR,
                                     userid INT,
                                     data BLOB,
                                     foreign key (userid) references USERS(userid)
    );
