-- public.product definition

-- Drop table

-- DROP TABLE public.product;

CREATE TABLE IF NOT EXISTS public.product (
                                              id varchar(10) NOT NULL,
    "name" varchar(50) NOT NULL,
    qty int4 NOT NULL,
    price int8 NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
    );