
#mysql -h localhost -p 1234 -u root
DROP DATABASE IF EXISTS SuperMarket;
CREATE DATABASE IF NOT EXISTS SuperMarket;
SHOW DATABASES ;
USE SuperMarket;

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    custId VARCHAR(6),
    custTitle VARCHAR(30),
    custName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    custAddress VARCHAR(30),
    city VARCHAR(20),
    province VARCHAR(20),
    postalCode VARCHAR(9),
    CONSTRAINT PRIMARY KEY (custId)
    );
SHOW TABLES ;
DESCRIBE Customer;
#---------------------
DROP TABLE IF EXISTS `Orders`;
CREATE TABLE IF NOT EXISTS `Orders`(
    orderId VARCHAR(6),
    orderDate VARCHAR(10) ,
    custId VARCHAR(6),
    total INT(5),
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (custId) REFERENCES Customer(custId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Orders`;
#---------------------
DROP TABLE IF EXISTS `ReportOrders`;
CREATE TABLE IF NOT EXISTS `ReportOrders`(
   orderId VARCHAR(6),
   orderDate VARCHAR(10) ,
   custId VARCHAR(6),
   total INT(5)
);
SHOW TABLES ;
DESCRIBE `ReportOrders`;
#---------------------
DROP TABLE IF EXISTS SystemReport;
CREATE TABLE IF NOT EXISTS SystemReport(
    todayIncome Int(6),
    monthlyIncome Int(6),
    yearlyIncome Int(6),
    mostMovableItem VARCHAR(10) ,
    leastMovableItem VARCHAR(6)
);
SHOW TABLES ;
DESCRIBE SystemReport;


#-----------------------
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR(6),
    description VARCHAR(50),
    packSize VARCHAR(20),
    unitPrice decimal(6,2),
    qtyOnHand int(5),
    CONSTRAINT PRIMARY KEY (itemCode)
    );
SHOW TABLES ;
DESCRIBE Item;
#------------------------
DROP TABLE IF EXISTS `OrderDetail`;
CREATE TABLE IF NOT EXISTS `OrderDetail`(
    orderId VARCHAR(6),
    custId VARCHAR(6),
    itemCode VARCHAR(6),
    orderQty INT(11),
    discount decimal(6,2),
    CONSTRAINT PRIMARY KEY (itemCode, orderId,custId),
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (custId) REFERENCES Customer(custId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `OrderDetail`;
#------------------------
DROP TABLE IF EXISTS NewUser;
CREATE TABLE IF NOT EXISTS NewUser(
      name VARCHAR(20),
      password VARCHAR(20),
      type VARCHAR(11)
);
SHOW TABLES ;
DESCRIBE NewUser;
