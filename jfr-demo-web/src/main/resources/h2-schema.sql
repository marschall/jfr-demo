
-- author
DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR (
  ID INTEGER NOT NULL,
  NAME VARCHAR(64) NOT NULL,
  PRIMARY KEY (ID)
);
DROP SEQUENCE AUTHOR_ID IF EXISTS;
CREATE SEQUENCE AUTHOR_ID
  START WITH 1
  MAXVALUE 2147483647
  CACHE 1;

-- article
DROP TABLE IF EXISTS ARTICLE;
CREATE TABLE ARTICLE (
  ID INTEGER NOT NULL,
  AUTHOR_ID INTEGER NOT NULL,
  TITLE VARCHAR(32) NOT NULL,
  PUBLISHED_AT TIMESTAMP(0) WITH TIME ZONE NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY(AUTHOR_ID) REFERENCES AUTHOR(ID)
);
DROP SEQUENCE ARTICLE_ID IF EXISTS;
CREATE SEQUENCE ARTICLE_ID
  START WITH 1
  MAXVALUE 2147483647
  CACHE 1;

-- comment
DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT (
  ID INTEGER NOT NULL,
  ARTICLE_ID INTEGER NOT NULL,
  COMMENT VARCHAR(4096) NOT NULL,
  PUBLISHED_AT TIMESTAMP(0) WITH TIME ZONE NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY(ARTICLE_ID) REFERENCES ARTICLE(ID)
);
DROP SEQUENCE COMMENT_ID IF EXISTS;
CREATE SEQUENCE COMMENT_ID
  START WITH 1
  MAXVALUE 2147483647
  CACHE 1;
