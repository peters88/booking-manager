Booking Manager
===

H2 Database URL:

````
http://localhost:8080/h2-console/
user: sa
password: 
````


Endpoints available:
````
======= Get Bookings
    - Endpoint: GET http://localhost:8080/api/v1/getbooking?bookingId=3
    - Returns a booking given the booking id as parameter

======= New Booking
    - Endpoint: POST http://localhost:8080/api/v1/newbooking
    - raw data body example:
        {
            "dateFrom": "2022-06-01",
            "dateTo": "2022-06-15",
            "guestId": 2,
            "propertyId": 100
        }
    - Saves a Booking and Blocks returns Booking information

======= Update Booking
    - Endpoint: PUT http://localhost:8080/api/v1/updatebooking
    - raw data body example:
        {
            "id": 1,
            "dateFrom": "2022-07-01",
            "dateTo": "2022-07-15"
        }
    - Deletes the old Blocks and Saves the new ones, Updates dates of Booking and modify status to REBOOKED

======= Delete Booking
    - Endpoint: PUT http://localhost:8080/api/v1/deletebooking
    - Modifies Booking status to CANCELED and deletes Blocks of a Booking given the Booking id as parameter

======= New OwnerBlock
    - Endpoint: POST http://localhost:8080/api/v1/newblock
    - raw data body example:
        {
            "ownerId": 1,
            "propertyId": 100,
            "blockFrom": "2022-05-01",
            "blockTo": "2022-05-15"
        }
    - Saves a Owner Block. Generates a UUID to identify the complete Block

======= Delete Block
    - Endpoint: PUT http://localhost:8080/api/v1/deleteblock
    - Given a ownerBlockId as parameter it deletes the Block


````

Initial Data Inserted for testing purpose:
````

INSERT INTO booking_user (type, id, email, first_name, last_name, password, phone_number, owner_type) VALUES
('OWNER',1,'test@test.com','Jhon','Doe','123456','111111','PERSON'),
('GUEST',2,'test2@test.com','Juan','Lopez','123456','222222',null);

INSERT INTO location (id, country, state, city, postal_code) VALUES
(1,'Argentina', 'Buenos Aires', 'CABA', 1426);

INSERT INTO property (id, max_pax, type, fk_location, fk_owner) VALUES
(1000, 8, 'HOUSE',1,1);

INSERT INTO booking (id,date_from,date_to,status,fk_guest,fk_property) VALUES
(100,'2022-07-12','2022-07-14','BOOKED',2,1000),
(200,'2022-07-12','2022-07-13','BOOKED',2,1000);

INSERT INTO BLOCK (type,id,block_day,owner_block_id,fk_property,fk_booking,fk_owner) VALUES
('BOOKING',1000,'2022-07-12',null,1000,100,null),
('BOOKING',1001,'2022-07-13',null,1000,100,null),
('BOOKING',1002,'2022-07-14',null,1000,100,null),
('BOOKING',1003,'2022-08-13',null,1000,200,null),
('BOOKING',1004,'2022-08-14',null,1000,200,null),
('OWNER',1005,'2022-10-13','fc00d4a0-b50f-436f-86eb-9f32f3395239',1000,null,1),
('OWNER',1006,'2022-10-14','fc00d4a0-b50f-436f-86eb-9f32f3395239',1000,null,1),
('OWNER',1007,'2023-10-13','fc00d4a0-b50f-436f-86eb-9f32f3395240',1000,null,1),
('OWNER',1008,'2023-10-14','fc00d4a0-b50f-436f-86eb-9f32f3395240',1000,null,1);

````
