-- Database: ricksoft_ads_reselling

-- DROP DATABASE IF EXISTS ricksoft_ads_reselling;

CREATE DATABASE ricksoft_ads_reselling
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE ricksoft_ads_reselling
    IS 'A database of platform for reselling things by ads';

GRANT ALL ON DATABASE ricksoft_ads_reselling TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE ricksoft_ads_reselling TO PUBLIC;

GRANT TEMPORARY ON DATABASE ricksoft_ads_reselling TO ads_owner;

-- Variant 2

CREATE DATABASE ricksoft_ads_reselling
    WITH 
    OWNER = ads_owner
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE ricksoft_ads_reselling
    IS 'A database of platform for reselling things by ads';

