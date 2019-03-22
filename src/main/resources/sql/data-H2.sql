insert into photo (name, url)
values ('hello', 'photo/hello-world');

insert into user (id, user_name, pass_word)
values (1, 'admin', '$2a$10$wxlkZZQh6uhNJ.Wx/joizeYY8khmQyqeN9qPpN3YQf.rTPagWXE/y');

insert into role(id, role_name)
values (1, 'admin'),
       (2, 'boss'),
       (3, 'staff');

insert into user_role(user_id, role_id)
values (1, 1),
       (1, 2),
       (1, 3);