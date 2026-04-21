-- -------------------------
-- MOVIES (6)
-- IDs esperados: 1..6
-- -------------------------
INSERT INTO movies (title, release_year, genre, active) VALUES
                                                            ('Inception',            2010, 'SCI_FI',  true),
                                                            ('The Dark Knight',      2008, 'ACTION',  true),
                                                            ('Interstellar',         2014, 'SCI_FI',  true),
                                                            ('The Prestige',         2006, 'DRAMA',   true),
                                                            ('Dunkirk',              2017, 'WAR',     true),
                                                            ('Batman Begins',        2005, 'ACTION',  false); -- inactiva para filtros

-- -------------------------
-- ACTORS (6)
-- IDs esperados: 1..6
-- -------------------------
INSERT INTO actors (stage_name, full_name, nationality, active) VALUES
                                                                    ('DiCaprio',   'Leonardo DiCaprio', 'American',   true),
                                                                    ('Caine',      'Michael Caine',     'British',    true),
                                                                    ('Nolan',      'Christian Bale',    'British',    true),
                                                                    ('Murphy',     'Cillian Murphy',    'Irish',      true),
                                                                    ('Cotillard',  'Marion Cotillard',  'French',     true),
                                                                    ('Oldman',     'Gary Oldman',       'British',    false); -- inactivo para filtros

-- -------------------------
-- MOVIE_CAST (tabla intermedia con atributos)
-- Diseñado para cubrir todos los casos del plan de pruebas:
--   - Un actor en varias películas     (DiCaprio → Inception + Interstellar)
--   - Una película con varios actores  (Inception → DiCaprio + Caine + Cotillard)
--   - Actor inactivo con rol activo    (Oldman en Batman Begins)
-- -------------------------
INSERT INTO movie_cast (movie_id, actor_id, character_name, screen_minutes, salary_override, active) VALUES
--  Inception (1)
(1, 1, 'Cobb',          110, 5000000.00, true),   -- DiCaprio en Inception
(1, 2, 'Miles',          40, 1000000.00, true),   -- Caine en Inception
(1, 5, 'Mal',            55,        null, true),  -- Cotillard en Inception

--  The Dark Knight (2)
(2, 3, 'Batman',        120, 8000000.00, true),   -- Bale en The Dark Knight
(2, 4, 'Scarecrow',      15,        null, true),  -- Murphy en The Dark Knight
(2, 6, 'Gordon',         60, 2000000.00, true),   -- Oldman en The Dark Knight

--  Interstellar (3)
(3, 1, 'Cooper',        120, 7000000.00, true),   -- DiCaprio en Interstellar (actor en 2 películas)
(3, 2, 'Professor',      30, 1500000.00, true),   -- Caine en Interstellar

--  The Prestige (4)
(4, 3, 'Angier',         95, 4000000.00, true),   -- Bale en The Prestige
(4, 2, 'Cutter',         45,        null, true),  -- Caine en The Prestige (actor en 3 películas)

--  Dunkirk (5)
(5, 4, 'Tommy',         100, 3000000.00, true),   -- Murphy en Dunkirk

--  Batman Begins (6) — película inactiva
(6, 3, 'Bruce Wayne',   110, 5000000.00, true),   -- Bale en Batman Begins
(6, 6, 'Gordon',         50, 1000000.00, false);  -- Oldman en Batman Begins (rol inactivo)

-- FASE II
-- =============================================================================
-- FASE II — Taquilla
-- =============================================================================

-- -----------------------------------------------------------------------------
-- COUNTRIES — catálogo de países (ISO 3166-1 alpha-3)
-- -----------------------------------------------------------------------------
INSERT INTO countries (id, code, name, active) VALUES
                                                   (1, 'USA', 'United States',  true),
                                                   (2, 'ESP', 'Spain',          true),
                                                   (3, 'FRA', 'France',         true),
                                                   (4, 'GBR', 'United Kingdom', true);

-- -----------------------------------------------------------------------------
-- DISTRIBUTORS — FK → countries
-- -----------------------------------------------------------------------------
INSERT INTO distributors (id, name, country_id, active) VALUES
                                                            (1, 'Warner Bros USA',    1, true),
                                                            (2, 'Warner Bros España', 2, true),
                                                            (3, 'Universal France',   3, true),
                                                            (4, 'Sony Pictures UK',   4, true);

-- -----------------------------------------------------------------------------
-- RELEASES — FK → movies, distributors
-- Restricción: una misma película NO puede tener dos estrenos
--              con la misma distribuidora (uk_release_movie_dist)
--
-- release 1 → Inception        / Warner USA
-- release 2 → Inception        / Warner España
-- release 3 → The Dark Knight  / Warner USA
-- release 4 → The Dark Knight  / Sony UK
-- release 5 → Interstellar     / Warner USA
-- release 6 → Interstellar     / Universal France
-- release 7 → Dunkirk          / Warner USA  (Fase I extra)
-- -----------------------------------------------------------------------------
INSERT INTO releases (id, movie_id, distributor_id, release_date, active) VALUES
                                                                              (1, 1, 1, '2010-07-16', true),   -- Inception        - Warner USA
                                                                              (2, 1, 2, '2010-08-20', true),   -- Inception        - Warner España
                                                                              (3, 2, 1, '2008-07-18', true),   -- The Dark Knight  - Warner USA
                                                                              (4, 2, 4, '2008-08-01', true),   -- The Dark Knight  - Sony UK
                                                                              (5, 3, 1, '2014-11-07', true),   -- Interstellar     - Warner USA
                                                                              (6, 3, 3, '2014-11-12', true),   -- Interstellar     - Universal France
                                                                              (7, 5, 1, '2017-07-21', true);   -- Dunkirk          - Warner USA

-- -----------------------------------------------------------------------------
-- BOX_OFFICE_ENTRIES — FK → releases, countries
-- Cada fila = recaudación de un estreno en un país durante un periodo
--
-- Columnas: release_id | country_id | period_start | period_end | gross | screens
-- -----------------------------------------------------------------------------
INSERT INTO box_office_entries (release_id, country_id, period_start, period_end, gross, screens, active) VALUES

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- Inception USA (release 1) — total: 118.000.000 $
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (1, 1, '2010-07-16', '2010-07-22',  62000000.00, 3792, true),
                                                                                                              (1, 1, '2010-07-23', '2010-07-29',  36000000.00, 3792, true),
                                                                                                              (1, 1, '2010-07-30', '2010-08-05',  20000000.00, 3500, true),

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- Inception España (release 2) — total: 7.300.000 €
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (2, 2, '2010-08-20', '2010-08-26',   4500000.00,  350, true),
                                                                                                              (2, 2, '2010-08-27', '2010-09-02',   2800000.00,  300, true),

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- The Dark Knight USA (release 3) — total: 276.000.000 $
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (3, 1, '2008-07-18', '2008-07-24', 158000000.00, 4366, true),
                                                                                                              (3, 1, '2008-07-25', '2008-07-31',  75000000.00, 4366, true),
                                                                                                              (3, 1, '2008-08-01', '2008-08-07',  43000000.00, 4200, true),

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- The Dark Knight UK (release 4) — total: 19.000.000 £
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (4, 4, '2008-08-01', '2008-08-07',  12000000.00,  650, true),
                                                                                                              (4, 4, '2008-08-08', '2008-08-14',   7000000.00,  600, true),

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- Interstellar USA (release 5) — total: 76.500.000 $
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (5, 1, '2014-11-07', '2014-11-13',  47500000.00, 3561, true),
                                                                                                              (5, 1, '2014-11-14', '2014-11-20',  29000000.00, 3500, true),

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- Interstellar France (release 6) — total: 8.300.000 €
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (6, 3, '2014-11-12', '2014-11-18',   5200000.00,  420, true),
                                                                                                              (6, 3, '2014-11-19', '2014-11-25',   3100000.00,  380, true),

                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              -- Dunkirk USA (release 7) — total: 56.000.000 $
                                                                                                              -- -------------------------------------------------------------------------
                                                                                                              (7, 1, '2017-07-21', '2017-07-27',  50400000.00, 3720, true),
                                                                                                              (7, 1, '2017-07-28', '2017-08-03',   5600000.00, 3500, true);
