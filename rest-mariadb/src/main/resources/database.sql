CREATE TABLE `authors` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL
);

ALTER TABLE `authors` ADD PRIMARY KEY (`id`);

CREATE TABLE `books` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `release_year` smallint(6) NOT NULL,
  `genre` varchar(128) NOT NULL,
  `approved_by` varchar(256) NOT NULL,
  `pages` smallint(6) NOT NULL,
  `summary` mediumtext DEFAULT NULL
);

ALTER TABLE `books` ADD PRIMARY KEY (`id`);

CREATE TABLE `books_authors` (
  `book_id` int(10) UNSIGNED NOT NULL,
  `author_id` int(10) UNSIGNED NOT NULL
);

ALTER TABLE `books_authors`
    ADD KEY `book_id` (`book_id`),
    ADD KEY `author_id` (`author_id`);

ALTER TABLE `books_authors`
  ADD CONSTRAINT `books_authors_fk_book` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `books_authors_fk_author` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`);
COMMIT;

CREATE TABLE `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(128) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `role` TINYINT NOT NULL ,
    `author_id` int(10) UNSIGNED NULL,
    PRIMARY KEY (`id`),
    UNIQUE `users_name_unique` (`name`),
    FOREIGN KEY (`author_id`) REFERENCES `authors`(`id`)
);