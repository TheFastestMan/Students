
ALTER SEQUENCE students_student_id_seq RESTART WITH 1;

CREATE TABLE courses (
                          course_id SERIAL PRIMARY KEY,
                          course_name VARCHAR (255)

);

CREATE TABLE students (
                          student_id SERIAL PRIMARY KEY,
                          student_name VARCHAR (255),
                          course_id bigint REFERENCES courses (course_id)

);