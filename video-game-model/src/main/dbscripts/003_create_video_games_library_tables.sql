--DROP TABLES
DROP TABLE platform CASCADE;
DROP TABLE video_game CASCADE;
DROP TYPE video_game_status_type CASCADE;

CREATE TABLE platform (
    platform_id SERIAL PRIMARY KEY,
    platform_name varchar(50) NOT NULL UNIQUE
);
ALTER TABLE platform OWNER TO vg;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "platform" TO vg;

CREATE TYPE video_game_status_type as enum ('COMPLETED', 'PLAYING', 'BACKLOG');

CREATE TABLE video_game (
    video_game_id SERIAL PRIMARY KEY,
    video_game_name varchar(50) NOT NULL,
    video_game_status_type character varying,
    date_released date NOT NULL,
    platform_id integer references "platform"(platform_id)
);

ALTER TABLE video_game OWNER TO vg;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "video_game" TO vg;

