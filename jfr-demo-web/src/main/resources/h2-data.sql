
-- author

INSERT INTO AUTHOR (ID,                   NAME)
SELECT              NEXTVAL('AUTHOR_ID'), 'Author ' || x
  FROM SYSTEM_RANGE(1, 10);


-- article
INSERT INTO ARTICLE (ID,                    TITLE,             PUBLISHED_AT,        AUTHOR_ID)
    SELECT           NEXTVAL('ARTICLE_ID'), 'Article ' || r.x, CURRENT_TIMESTAMP(), a.ID
      FROM SYSTEM_RANGE(1, 20) r
CROSS JOIN AUTHOR a;

-- comment
INSERT INTO COMMENT (ID,                    COMMENT,           PUBLISHED_AT,        ARTICLE_ID)
    SELECT           NEXTVAL('COMMENT_ID'), 'Comment ' || c.x, CURRENT_TIMESTAMP(), a.ID
      FROM SYSTEM_RANGE(1, 200) c
CROSS JOIN ARTICLE a;

