create database drivingschool;

use drivingschool;

create table administrator(
                              adm_id varchar(7) primary key,
                              adm_name varchar(50) not null,
                              adm_address varchar(60) not null,
                              contact_number int not null,
                              user_name varchar(20) not null,
                              password varchar(40) not null
);

insert into administrator(adm_id,adm_name,adm_address,contact_number,user_name,password) values
    ('A001','Mr.Iresh Chathuranga','Ambalangoda',0758563998,'ireshchathuranga','Iresh1234');

create table booking(
                        book_id varchar(7) primary key,
                        book_date date not null,
                        book_time varchar(8) not null,
                        reschedule_reason varchar(200)
);

insert into booking(book_id,book_date,book_time,reschedule_reason) values
                                                                       ('B001','2024-12-6','2:00 PM','N/A'),
                                                                       ('B002','2024-11-4','11:00 AM','Exam'),
                                                                       ('B003','2024-12-2','9:00 AM','N/A'),
                                                                       ('B004','2024-9-12','2:00 PM','N/A'),
                                                                       ('B005','2024-11-4','3:00 PM','Personal reason'),
                                                                       ('B006','2024-12-7','4:00 PM','N/A');

create table maintainer(
                           maintain_id varchar(7) primary key,
                           maintain_name varchar(50) not null,
                           maintain_task varchar(100) not null,
                           contact_number int not null
);

insert into maintainer(maintain_id,maintain_name,maintain_task,contact_number) values
                                                                                   ('M001','Mr.Sunil Silva','Vehicle Maintenance,Safety Checks,Scheduling Maintenance',0763456567),
                                                                                   ('M002','Mr.Ajantha Mendis','Inventory Management',0775612245),
                                                                                   ('M003','Mr.Kamal Perera','Vehicle Maintenance,Safety Checks',0765699234);

create table staff(
                      staff_id varchar(7) primary key,
                      staff_name varchar(50) not null,
                      description varchar(200) not null,
                      admin_id varchar(7),
                      foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade
);

insert into staff(staff_id,staff_name,description,admin_id) values
                                                                ('S001','Administrator','Manage Driving School','A001'),
                                                                ('S002','instructors','Providing training for children','A001'),
                                                                ('S003','maintainers','Maintenance of school vehicles','A001'),
                                                                ('S004','students','The students in the school','A001');

create table salary(
                       salary_id varchar(7) primary key,
                       amount decimal(10,2) not null,
                       pay_day date not null,
                       holidays int not null,
                       is_received varchar(5) not null,
                       admin_id varchar(7),
                       staf_id varchar(7),
                       foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade,
                       foreign key(staf_id) references staff(staff_id) on update cascade on delete cascade
);

insert into salary(salary_id,amount,pay_day,holidays,is_received,admin_id,staf_id) values
                                                                                       ('L001','100000','2024-11-30',0,'Yes','A001','S001'),
                                                                                       ('L002','60000','2024-11-30',3,'Yes','A001','S002'),
                                                                                       ('L003','40000','2024-11-30',6,'Yes','A001','S003');

create table course(
                       course_id varchar(7) primary key,
                       course_name varchar(50) not null,
                       admin_id varchar(7),
                       foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade
);

insert into course(course_id,course_name,admin_id) values
                                                       ('C001','Beginner driving course','A001'),
                                                       ('C002','Advanced Driving Courses','A001'),
                                                       ('C003','Driving License Test Preparation','A001'),
                                                       ('C004','Safety Driving Training','A001');

create table vehicle(
                        vehi_id varchar(7) primary key,
                        vehi_type varchar(30) not null,
                        lesson_fee decimal(7,2) not null,
                        admin_id varchar(7),
                        foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade
);

insert into vehicle(vehi_id,vehi_type,lesson_fee,admin_id) values
                                                               ('V001','Van(Manual)','35000','A001'),
                                                               ('V002','Bike1(Manual)','1000','A001'),
                                                               ('V003','Bike2(Auto)','1000','A001'),
                                                               ('V004','Car(Auto)','35000','A001'),
                                                               ('V005','Threeweel(Manual)','2000','A001');

create table instructor(
                           instru_id varchar(7) primary key,
                           instru_name varchar(50) not null,
                           instru_age int not null,
                           instru_address varchar(20) not null,
                           certification_detail varchar(350) not null,
                           admin_id varchar(7),
                           foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade
);

insert into instructor(instru_id,instru_name,instru_age,instru_address,certification_detail,admin_id) values
                                                                                                          ('I001','Mr.Gayan Iduwara',35,'Ambalangoda','Driver Education Instructor Certificate,Behind-the-Wheel Instruction Certification,Background Check','A001'),
                                                                                                          ('I002','Mr.Nimal De Silva',28,'Galle','Driver Education Instructor Certificate,Behind-the-Wheel Instruction Certification,Background Check','A001'),
                                                                                                          ('I003','Mr.Hashan Perera',40,'Hikkaduwa','Driver Education Instructor Certificate,Behind-the-Wheel Instruction Certification,Background Check','A001'),
                                                                                                          ('I004','Mr.Sadun Nimsara',30,'Ambalangoda','Driver Education Instructor Certificate,Behind-the-Wheel Instruction Certification,Background Check','A001'),
                                                                                                          ('I005','Mr.Ruwan Maleesha',37,'Balapitiya','Driver Education Instructor Certificate,Behind-the-Wheel Instruction Certification,Background Check','A001');

create table vehicle_details(
                                instruc_id varchar(7),
                                veh_id varchar(7),
                                foreign key(instruc_id) references instructor(instru_id) on update cascade on delete cascade,
                                foreign key(veh_id) references vehicle(vehi_id) on update cascade on delete cascade
);

insert into vehicle_details(instruc_id,veh_id) values
                                                   ('I001','V001'),
                                                   ('I001','V002'),
                                                   ('I001','V005'),
                                                   ('I002','V003'),
                                                   ('I002','V004'),
                                                   ('I003','V001'),
                                                   ('I003','V002'),
                                                   ('I003','V003'),
                                                   ('I003','V004'),
                                                   ('I003','V005'),
                                                   ('I004','V001'),
                                                   ('I004','V004'),
                                                   ('I005','V002'),
                                                   ('I005','V003'),
                                                   ('I005','V005');

create table payment(
                        pay_id varchar(7) primary key,
                        pay_date date not  null,
                        pay_method varchar(10) not null,
                        admin_id varchar(7),
                        foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade
);

insert into payment(pay_id,pay_date,pay_method,admin_id) values
                                                             ('P001','2024-08-06','Card','A001'),
                                                             ('P002','2024-09-10','Cash','A001'),
                                                             ('P003','2024-08-25','Cash','A001');

create table payment_plan(
                             payplan_id varchar(7) primary key,
                             amount decimal(10,2) not null,
                             rate int not null,
                             rate_price decimal(7,2) not null,
                             description varchar(30) not null,
                             payment_id varchar(7),
                             foreign key(payment_id) references payment(pay_id) on update cascade on delete cascade
);

insert into payment_plan(payplan_id,amount,rate,rate_price,description,payment_id) values
                                                                                       ('Z001','35000',15,'29750','Full Price','P001'),
                                                                                       ('Z002','35000',0,'35000','Installments','P002'),
                                                                                       ('Z003','35000',0,'35000','Installments','P003');

create table student(
                        student_id varchar(7) primary key,
                        stu_name varchar(25) not null,
                        DOB date not null,
                        NIC varchar(100) not null,
                        stu_address varchar(150) not null,
                        register_date date not null,
                        gender varchar(10) not null,
                        advance_payment decimal(10,2) not null,
                        helping_aids varchar(50) not null,
                        phone_number int not null,
                        email varchar(60) not null,
                        admin_id varchar(7),
                        curs_id varchar(7),
                        paymentplan_id varchar(7),
                        payment_id varchar(7),
                        veh_id varchar(7),
                        foreign key(admin_id) references administrator(adm_id) on update cascade on delete cascade,
                        foreign key(curs_id) references course(course_id) on update cascade on delete cascade,
                        foreign key(paymentplan_id) references payment_plan(payplan_id) on update cascade on delete cascade,
                        foreign key(payment_id) references payment(pay_id) on update cascade on delete cascade,
                        foreign key(veh_id) references vehicle(vehi_id) on update cascade on delete cascade
);

insert into student(student_id,stu_name,DOB,NIC,stu_address,register_date,gender,advance_payment,helping_aids,phone_number,email,admin_id,curs_id,paymentplan_id,payment_id,veh_id) values
                                                                                                                                                                                        ('S001','Mr.Sadun Maleesha','2002-04-08','200204089765','Ambalangoda','2024-08-06','Male',0,'None','0768976854','sadun@gmail.com','A001','C001','Z001','P001','V001'),
                                                                                                                                                                                        ('S002','Mrs.Malsha Lakmalie','2000-07-28','200007286789','Galle','2024-09-10','Female','200000','Glasses','0758121249','malsha@gmail.com','A001','C001','Z002','P002','V004'),
                                                                                                                                                                                        ('S003','Mr.Ravindu Thamara','2005-06-10','200506104534','Balapitiya','2024-08-25','Male',200000,'None','0763434447','ravidu@gmail.com','A001','C001','Z003','P003','V001');

create table booking_details(
                                book_id varchar(7),
                                student_id varchar(7),
                                foreign key(book_id) references booking(book_id) on update cascade on delete cascade,
                                foreign key(student_id) references student(student_id) on update cascade on delete cascade
);

insert into booking_details(book_id,student_id) values
                                                    ('B001','S001'),
                                                    ('B005','S001'),
                                                    ('B002','S002'),
                                                    ('B003','S002'),
                                                    ('B004','S003'),
                                                    ('B006','S003');

create table choose_trainer(
                               book_id varchar(7),
                               instru_id varchar(7),
                               foreign key(book_id ) references booking(book_id) on update cascade on delete cascade,
                               foreign key(instru_id) references instructor(instru_id) on update cascade on delete cascade
);

insert into choose_trainer(book_id,instru_id) values
                                                  ('B001','I001'),
                                                  ('B005','I003'),
                                                  ('B002','I002'),
                                                  ('B003','I004'),
                                                  ('B004','I001'),
                                                  ('B006','I003');

create table maintain_detail(
                                veh_id varchar(7),
                                mtain_id varchar(7),
                                foreign key(veh_id) references vehicle(vehi_id) on update cascade on delete cascade,
                                foreign key(mtain_id) references maintainer(maintain_id) on update cascade on delete cascade
);

insert into maintain_detail(veh_id,mtain_id) values
                                                 ('V001','M001'),
                                                 ('V002','M003'),
                                                 ('V003','M001'),
                                                 ('V004','M002'),
                                                 ('V005','M001');

create table training_lesson(
                                less_name varchar(50) not null,
                                time_period varchar(10) not null,
                                student_id varchar(7),
                                instru_id varchar(7),
                                foreign key(student_id) references student(student_id) on update cascade on delete cascade,
                                foreign key(instru_id) references instructor(instru_id) on update cascade on delete cascade
);

insert into training_lesson(less_name,time_period,student_id,instru_id) values
                                                                            ('Vehicle control and handling','30 minites','S001','I001'),
                                                                            ('Basic maneuvers','40 minites','S001','I003'),
                                                                            ('Vehicle control and handling','30 minites','S002','I002'),
                                                                            ('Vehicle control and handling','30 minites','S002','I004'),
                                                                            ('Basic maneuvers','40 minites','S003','I001'),
                                                                            ('Basic maneuvers','40 minites','S003','I003');

create table signup(
                       user_name varchar(25) not null,
                       username varchar(20) primary key,
                       contact_number int not null,
                       user_address varchar(60) not null,
                       userpassword varchar(40) not null
);


