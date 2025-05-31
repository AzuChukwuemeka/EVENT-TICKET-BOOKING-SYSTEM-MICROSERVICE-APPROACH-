
CREATE TABLE public.tbl_booking_outbox (
    id uuid NOT NULL,
    aggregate_type text NOT NULL,
    payload text NOT NULL,
);

CREATE TABLE IF NOT EXISTS public.tbl_seat_generated_outbox
(
    event_id uuid NOT NULL,
    payload text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tbl_seat_generated_outbox_pkey PRIMARY KEY (event_id)
)

ALTER TABLE public.tbl_booking_outbox OWNER TO code;

CREATE TABLE public.tbl_bookings (
    booking_id uuid NOT NULL,
    seat_id uuid NOT NULL,
    username text NOT NULL,
    price text NOT NULL,
    booking_status boolean
);


ALTER TABLE public.tbl_bookings OWNER TO code;

CREATE TABLE public.tbl_event_outbox (
    id uuid NOT NULL,
    aggregate_type text NOT NULL,
    payload text NOT NULL
);


ALTER TABLE public.tbl_event_outbox OWNER TO code;

CREATE TABLE public.tbl_events (
    event_id uuid NOT NULL,
    event_name text NOT NULL,
    venue_id uuid NOT NULL,
    on_sale boolean NOT NULL,
    event_date timestamp with time zone NOT NULL,
    normal_seat_prce bigint NOT NULL,
    timezone text NOT NULL,
    seat_generated boolean,
    created_by text
);


ALTER TABLE public.tbl_events OWNER TO code;

CREATE TABLE public.tbl_seats (
    seat_id uuid NOT NULL,
    row_no text NOT NULL,
    col_no text NOT NULL,
    section text NOT NULL,
    event_id uuid NOT NULL,
    event_name text,
    event_date timestamp with time zone,
    event_venue text,
    booked boolean
);


ALTER TABLE public.tbl_seats OWNER TO code;

CREATE TABLE public.tbl_users (
    user_id uuid NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    role text NOT NULL,
    banned boolean DEFAULT false NOT NULL
);


ALTER TABLE public.tbl_users OWNER TO code;

CREATE TABLE public.tbl_venues (
    venue_id uuid NOT NULL,
    venue_name text NOT NULL,
    seat_sections bigint NOT NULL,
    seat_rows bigint NOT NULL,
    seat_columns bigint NOT NULL
);

ALTER TABLE IF EXISTS public.tbl_events
    ADD CONSTRAINT duplicate_event UNIQUE (venue_id, event_date);

ALTER TABLE public.tbl_venues OWNER TO code;

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT email_unique UNIQUE (email);

ALTER TABLE ONLY public.tbl_booking_outbox
    ADD CONSTRAINT tbl_booking_outbox_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.tbl_bookings
    ADD CONSTRAINT tbl_bookings_pkey PRIMARY KEY (booking_id);

ALTER TABLE ONLY public.tbl_event_outbox
    ADD CONSTRAINT tbl_event_outbox_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.tbl_events
    ADD CONSTRAINT tbl_events_pkey PRIMARY KEY (event_id);

ALTER TABLE ONLY public.tbl_seats
    ADD CONSTRAINT tbl_seats_pkey PRIMARY KEY (seat_id);

ALTER TABLE ONLY public.tbl_users
    ADD CONSTRAINT tbl_users_pkey PRIMARY KEY (user_id);

ALTER TABLE ONLY public.tbl_venues
    ADD CONSTRAINT tbl_venues_pkey PRIMARY KEY (venue_id);

ALTER TABLE ONLY public.tbl_events
    ADD CONSTRAINT venue_id_foreignkey FOREIGN KEY (venue_id) REFERENCES public.tbl_venues(venue_id);

ALTER TABLE IF EXISTS public.tbl_seats
    ADD CONSTRAINT unique_seat_constraint UNIQUE (row_no, col_no, section, event_id);