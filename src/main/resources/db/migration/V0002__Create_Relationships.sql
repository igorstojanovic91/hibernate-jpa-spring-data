DROP TABLE IF EXISTS employee;

CREATE TABLE EMPLOYEE (
    ID              INTEGER not null,
    NAME            VARCHAR not null,
    SALARY          BIGINT not null,
    ADDRESS_ID      INTEGER,
    BOSS_ID         INTEGER,
    DEPARTMENT_ID   INTEGER
);
CREATE SEQUENCE EMPLOYEE_SEQ
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE PHONE (
    ID              INTEGER NOT NULL,
    PHONENUMBER     VARCHAR NOT NULL,
    TYPE            VARCHAR NOT NULL,
    EMPLOYEE_ID     INTEGER NOT NULL
);
CREATE SEQUENCE PHONE_SEQ
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE PROJECT (
    ID              INTEGER NOT NULL,
    NAME            VARCHAR NOT NULL
);
CREATE SEQUENCE PROJECT_SEQ
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE PROJECT_EMPLOYEES (
     EMPLOYEES_ID           INTEGER NOT NULL,
     PROJECTS_ID            INTEGER NOT NULL
);


CREATE TABLE ADDRESS (
    ID                     INTEGER NOT NULL,
    STREET                 VARCHAR NOT NULL,
    CITY                   VARCHAR NOT NULL,
    STATE                  VARCHAR NOT NULL,
    ZIP                    VARCHAR NOT NULL
);
CREATE SEQUENCE ADDRESS_SEQ
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE DEPARTMENT (
     ID                     INTEGER NOT NULL,
     NAME                   VARCHAR NOT NULL
);
CREATE SEQUENCE DEPARTMENT_SEQ
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ADDRESS
    ADD CONSTRAINT ADDRESS_PK PRIMARY KEY (ID);

ALTER TABLE DEPARTMENT
    ADD CONSTRAINT DEPARTMENT_PK PRIMARY KEY (ID);

ALTER TABLE EMPLOYEE
    ADD CONSTRAINT EMPLOYEE_PK PRIMARY KEY (ID);

ALTER TABLE PROJECT_EMPLOYEES
    ADD CONSTRAINT PROJECT_EMPLOYEES_PK PRIMARY KEY (PROJECTS_ID, EMPLOYEES_ID);

ALTER TABLE PHONE
    ADD CONSTRAINT PHONE_PK PRIMARY KEY (ID);

ALTER TABLE PROJECT
    ADD CONSTRAINT PROJECT_PK PRIMARY KEY (ID);

ALTER TABLE PHONE
    ADD CONSTRAINT PHONE_EMPLOYEE_FK FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEE (ID);

ALTER TABLE PROJECT_EMPLOYEES
    ADD CONSTRAINT PROJECT_EMPLOYEES_EMPLOYEE_FK FOREIGN KEY (EMPLOYEES_ID) REFERENCES EMPLOYEE (ID);

ALTER TABLE EMPLOYEE
    ADD CONSTRAINT EMPLOYEE_DEPARTMENT_FK FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT (ID);

ALTER TABLE EMPLOYEE
    ADD CONSTRAINT BOSS_EMPLOYEE_FK FOREIGN KEY (BOSS_ID) REFERENCES EMPLOYEE (ID);

ALTER TABLE PROJECT_EMPLOYEES
    ADD CONSTRAINT PROJECT_EMPLOYEES_PROJECT_FK FOREIGN KEY (PROJECTS_ID) REFERENCES PROJECT (ID);

ALTER TABLE EMPLOYEE
    ADD CONSTRAINT EMPLOYEE_ADDRESS_FK FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS (ID);

