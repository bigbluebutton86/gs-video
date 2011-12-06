--// Create Anime Tables
-- Migration SQL that makes the change goes here.

Create Table Anime (
    anime_id int PRIMARY KEY,
    name_main varchar(500) NOT NULL,
    name_english varchar(500) NULL,
    last_loaded timestamp with time zone NOT NULL,
    episode_count int NULL,
    type varchar(100) NOT NULL,
    description text NULL,
	start_date date NULL,
	end_date date NULL,
	rating_permanent numeric(6, 2) NULL,
	rating_permanent_votes int NULL,
	rating_temp numeric(6, 2) NULL,
	rating_temp_votes int NULL,
	hentai boolean NOT NULL,
	picture_filename varchar(200) NULL
);

Create Table AnimeName (
    anime_id int NOT NULL REFERENCES Anime ON DELETE CASCADE,
    anime_name varchar(1000) NOT NULL,
    name_type varchar(20) NOT NULL,
    name_lang varchar(10) NOT NULL,
	PRIMARY KEY (anime_id, anime_name, name_lang)
);

Create Table AnimeCategoryMapping (
    anime_id int NOT NULL REFERENCES Anime ON DELETE CASCADE,
	category_id int NOT NULL REFERENCES AnimeCategory ON DELETE CASCADE,
    weight int NOT NULL,
	PRIMARY KEY (anime_id, category_id)
);

Create Table AnimeRelation (
    anime_id int NOT NULL REFERENCES Anime ON DELETE CASCADE,
	related_anime_id int NOT NULL,
    relation_type_id int NOT NULL,
	PRIMARY KEY (anime_id, related_anime_id)
);

--//@UNDO
-- SQL to undo the change goes here.

Drop Table AnimeRelation;
Drop Table AnimeCategoryMapping;
Drop Table AnimeName;
Drop Table Anime;
