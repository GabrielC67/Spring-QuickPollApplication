
-- Poll Creation
insert into poll (poll_id, question) values (1, 'What is your favorite color?');

-- Option Creation
insert into option (option_id, option_value, poll_id) values (1, 'Red', 1);
