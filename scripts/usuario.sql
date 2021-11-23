create user 'admin'@'%' IDENTIFIED BY 'admin';
grant all privileges on *.* to 'admin'@'%';
flush privileges;
