--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3
-- Dumped by pg_dump version 11.3

-- Started on 2019-05-28 08:05:08

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

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 32875)
-- Name: Ratings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ratings" (
    grade integer NOT NULL,
    id integer NOT NULL,
    username text
);


ALTER TABLE public."Ratings" OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 32866)
-- Name: Songs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Songs" (
    id integer NOT NULL,
    duration integer,
    genre text,
    singer text NOT NULL,
    title text NOT NULL
);


ALTER TABLE public."Songs" OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 32864)
-- Name: Songs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Songs" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Songs_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 2817 (class 0 OID 32875)
-- Dependencies: 198
-- Data for Name: Ratings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Ratings" (grade, id, username) FROM stdin;
5	2	user
5	2	user
5	2	user
5	2	user
\.


--
-- TOC entry 2816 (class 0 OID 32866)
-- Dependencies: 197
-- Data for Name: Songs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Songs" (id, duration, genre, singer, title) FROM stdin;
1	1	[C@4c83ccdc	[C@7cce7457	[C@43353649
2	1	aha	haa	haha
3	1	genre1	singer1	title1
4	2	genre2	singer2	title2
5	3	genre	singer3	title3
6	4	genre4	singer4	title4
7	5	genre5	singer5	title5
16	3	genretest	singertest	titletest
17	2	test genre	test singer	test title
18	4	Epic	The National	Rains of Castemere
\.


--
-- TOC entry 2826 (class 0 OID 0)
-- Dependencies: 196
-- Name: Songs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Songs_id_seq"', 18, true);


--
-- TOC entry 2692 (class 2606 OID 32873)
-- Name: Songs Songs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Songs"
    ADD CONSTRAINT "Songs_pkey" PRIMARY KEY (id);


--
-- TOC entry 2693 (class 2606 OID 32889)
-- Name: Ratings id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ratings"
    ADD CONSTRAINT id FOREIGN KEY (id) REFERENCES public."Songs"(id);


--
-- TOC entry 2823 (class 0 OID 0)
-- Dependencies: 198
-- Name: TABLE "Ratings"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public."Ratings" TO testuser;


--
-- TOC entry 2824 (class 0 OID 0)
-- Dependencies: 197
-- Name: TABLE "Songs"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public."Songs" TO testuser;


--
-- TOC entry 2825 (class 0 OID 0)
-- Dependencies: 196
-- Name: SEQUENCE "Songs_id_seq"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public."Songs_id_seq" TO testuser;


-- Completed on 2019-05-28 08:05:09

--
-- PostgreSQL database dump complete
--

