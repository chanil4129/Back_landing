INSERT INTO account (id, created_at, email, name, pwd, role, email_verified, locked)
VALUES ('testid', '2003-08-16T11:11:11', 'test@naver.com', 'ckswls', 'testtestPWD1!', 'ROLE_USER', 1, 0);
INSERT INTO PERSON (id, name, phone, account_id)
VALUES (1, '남정수', '010-1234-1234', 'testid');
INSERT INTO AUTH_MAIL (id, auth_code, email, end_at, is_success, created_at)
VALUES (2, 'test12', 'test@naver.com', '2099-08-16T11:11:11', 0, '2003-08-16T11:11:11');
INSERT INTO AUTH_MAIL (id, auth_code, email, end_at, is_success, created_at)
VALUES (3, 'testsx', 'success@naver.com', '2099-08-16T11:11:11', 1, '2003-08-16T11:11:11')