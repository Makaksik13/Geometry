CREATE TABLE IF NOT EXISTS showplace
(
    id                 UUID           PRIMARY KEY,
    city               VARCHAR(64)    NOT NULL,
    description        VARCHAR(2048)  NOT NULL,
    location           GEOMETRY(Point, 4326),
    category           integer        NOT NULL,
    total_sum          BIGINT         DEFAULT 0,
    amount             BIGINT         DEFAULT 0
);

CREATE INDEX polygons_geom_idx ON showplace USING GIST (location);

CREATE TABLE IF NOT EXISTS comment
(
    id                 UUID           PRIMARY KEY,
    content            varchar(4096)  NOT NULL,
    showplace_id       UUID           NOT NULL,
    created_at         timestamptz    DEFAULT current_timestamp,

    CONSTRAINT fk_showplace_id FOREIGN KEY (showplace_id) REFERENCES showplace (id) ON DELETE CASCADE
);