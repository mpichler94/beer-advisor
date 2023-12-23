merge into Users (ID, USERNAME, PASSWORD) key (ID) values (1, 'user',
                                                           '{bcrypt}$2y$10$SqAvGqZRdu5sCIXmoCl6mOj5lfjP1Fh616Fz3UROBgOi03IyiOQvS');
merge into Users (ID, USERNAME, PASSWORD) key (ID) values (2, 'admin',
                                                           '{bcrypt}$2y$10$G.Xv9Dx4fOZWYPZ8CyX4.OzyXsgNhKfCQT3B39Bhw6R0smsKX533O');

merge into Authorities (ID, AUTHORITY) key (ID) values ( 2, 'admin' );
merge into Authorities (ID, AUTHORITY) key (ID) values ( 2, 'user' )