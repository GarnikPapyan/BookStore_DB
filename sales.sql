--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: sales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales (
    saleid integer NOT NULL,
    bookid integer NOT NULL,
    customerid integer NOT NULL,
    dateofsale character varying(50),
    quantitysold integer,
    totalprice double precision
);


ALTER TABLE public.sales OWNER TO postgres;

--
-- Name: sales_saleid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sales_saleid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sales_saleid_seq OWNER TO postgres;

--
-- Name: sales_saleid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sales_saleid_seq OWNED BY public.sales.saleid;


--
-- Name: sales saleid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales ALTER COLUMN saleid SET DEFAULT nextval('public.sales_saleid_seq'::regclass);


--
-- Name: sales sales_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sales_pkey PRIMARY KEY (saleid);


--
-- Name: sales sales_bookid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sales_bookid_fkey FOREIGN KEY (bookid) REFERENCES public.books(bookid);


--
-- Name: sales sales_customerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sales_customerid_fkey FOREIGN KEY (customerid) REFERENCES public.customers(customerid);


--
-- PostgreSQL database dump complete
--

