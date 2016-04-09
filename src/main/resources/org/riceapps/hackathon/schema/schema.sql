/**
 * Requires: MySQL >= 5
 * Don't forget to import the framework schema.sql first!
 */

/**
 * Use UTF8 encoding.
 */
SET NAMES utf8;
SET CHARACTER SET utf8;
SET character_set_connection = utf8;
SET collation_connection = utf8_general_ci;

/**
 * Delete any copies of the tables already in the database.
 */
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS applications;
SET FOREIGN_KEY_CHECKS = 1;

/**
 * APPLICATION SCHEMA
 */
CREATE TABLE cas_links (
  user_id int(64) unsigned not null,
  cas_user varchar(255) not null,
  PRIMARY KEY(user_id, cas_user),
  UNIQUE(cas_user),
  UNIQUE(user_id),
  FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
) CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE applications (
  id int(64) unsigned not null auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  email varchar(255) not null,
  email_verified tinyint(1) UNSIGNED NOT NULL,
  university varchar(255) not null,
  team_name varchar(255) default null,
  team_email1 varchar(255) default null,
  team_email2 varchar(255) default null,
  team_email3 varchar(255) default null,
  team_email4 varchar(255) default null,
  resume_file_id int(64) unsigned not null,
  user_id int(64) unsigned not null,
  submission_time int(64) unsigned not null,
  last_update_time int(64) unsigned not null,
  gender int(64) unsigned not null,
  linkedin_url varchar(255) default null,
  github_url varchar(255) default null,
  personal_url varchar(255) default null,
  first_hackathon tinyint(1) UNSIGNED NOT NULL,
  accepted tinyint(1) UNSIGNED NOT NULL,
  confirmed tinyint(1) UNSIGNED NOT NULL,
  attended tinyint(1) UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  UNIQUE(email),
  FOREIGN KEY(resume_file) REFERENCES files(id) ON DELETE CASCADE,
  FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
  INDEX(user_id)
) CHARACTER SET utf8 COLLATE utf8_general_ci;


  /* birthdate */
  /* grad year & school (e.g. grad, freshman, soph, etc.) */
  /* major/degree */
/*
Travel
  - What country are you in? (ARE YOU NOT IN US)
  - US Citizen? (IF NOT WHAT COUNTRY)
  - Are you traveling?
  - Where from?
  - How? (Plane/Train/Bus/Car/Bike/Other)
  - Require reimbursement?


Would like place to sleep w/ host university student?
  - Restrictions on host gender
  - Willing to sign a waiver

Dietary Restrictions?
*/

/*
Reviews
 */

/*
Sleeping Hosts?
 */

/*
Reimbursements?
 */

/*
Electronic Waiver Signing?
 */
