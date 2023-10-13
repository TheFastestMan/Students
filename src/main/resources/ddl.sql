ALTER SEQUENCE students_student_id_seq RESTART WITH 1;
ALTER SEQUENCE courses_course_id_seq RESTART WITH 1;
ALTER SEQUENCE student_profile_profile_id_seq RESTART WITH 1;
ALTER SEQUENCE trainers_trainer_id_seq RESTART WITH 1;

-- Courses Table
CREATE TABLE courses
(
    course_id   SERIAL PRIMARY KEY,
    course_name VARCHAR(255) UNIQUE NOT NULL
);

-- Students Table
CREATE TABLE students
(
    student_id   SERIAL PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    course_id    bigint
);

-- Student Profiles Table
CREATE TABLE student_profile
(
    profile_id           SERIAL PRIMARY KEY,
    academic_performance INT NOT NULL,
    student_id           bigint
);

CREATE TABLE trainers
(
    trainer_id   SERIAL PRIMARY KEY,
    trainer_name VARCHAR(255) NOT NULL
);

CREATE TABLE course_trainers
(
    course_id  INT NOT NULL REFERENCES courses (course_id),
    trainer_id INT NOT NULL REFERENCES trainers (trainer_id),
    PRIMARY KEY (course_id, trainer_id)
);


ALTER TABLE students
    ADD FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE SET NULL;

ALTER TABLE student_profile
    ADD FOREIGN KEY (student_id) REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE;

