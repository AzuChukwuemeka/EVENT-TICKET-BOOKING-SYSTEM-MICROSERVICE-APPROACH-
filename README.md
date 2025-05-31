EVENT TICKET BOOKING SYSTEM INSPIRED BY TICKET MASTER PROBLEM

Event Core Service

A scalable microservice managing events, venues, users, and administrative operations for a ticket booking system. This service exposes REST APIs with authentication and uses distributed locking mechanisms to handle concurrent ticket booking safely.

Overview
The Event Core Service is part of a distributed TicketMaster-like microservices architecture. It allows:

Creating and managing events and venues

User registration and authentication

Admin-level control over events, venues, and users

This service requires authentication for most endpoints except user registration and token refresh.

Features
Event Management: Create, list, and retrieve event details; check if event seats are generated.

Venue Management: Add, list, and retrieve venues.

User Management: User registration, token refresh, and role-based operations.

Admin Controls: List and delete events/venues, ban users, and search users.

Secure Booking: Booking handled via distributed locks to prevent concurrent conflicts.

Pagination: List endpoints support pagination with page and size query parameters.

Authentication: JWT-based authentication protects all APIs except for /api/v1/auth/**.

API Endpoints
Event Controller (/api/v1/event)
Method	Endpoint	Description	Auth Required
POST	/create	Create a new event	Yes
GET	/{id}	Get event by UUID	Yes
GET	/events	List events created by current user (paginated)	Yes
GET	/generated/{id}	Check if event seats have been generated	Yes

Venue Controller (/api/v1/venue)
Method	Endpoint	Description	Auth Required
POST	/create	Create a new venue	Yes
GET	/{id}	Get venue by UUID	Yes
GET	/venues	List venues (paginated)	Yes

User Controller (/api/v1/auth)
Method	Endpoint	Description	Auth Required
POST	/create	Register a new user	No
POST	/refresh	Refresh JWT token via refresh cookie	No

Admin Controller (/api/v1/admin)
Method	Endpoint	Description	Auth Required	Role Required
GET	/events	List all events (paginated)	Yes	Admin
GET	/venues	List all venues (paginated)	Yes	Admin
DELETE	/del/event/{id}	Delete event by UUID	Yes	Admin
DELETE	/del/venue/{id}	Delete venue by UUID	Yes	Admin
POST	/ban/user/{id}	Ban user by UUID	Yes	Admin
GET	/search/user?user=xyz	Search user by username	Yes	Admin

Booking Service
This microservice handles the critical flow of ticket booking including seat reservation and payment processing. It integrates with the Event Core Service to ensure tickets are booked securely with distributed locking to prevent double-booking conflicts.

Features
Book seats for events safely using distributed locks to manage concurrency.

Retrieve user booking history.

Webhooks to handle payment success and failure notifications.

Authorization required for booking operations.

API Endpoints
Method	Endpoint	Description	Auth Required
POST	/api/v1/booking/book_seat	Book a seat with booking details; returns payment intent	Yes
GET	/api/v1/booking/bookings	Get all booking records of the authenticated user	Yes
POST	/api/v1/booking/webhook/payment_successful	Webhook to notify payment success (internal use)	Yes
POST	/api/v1/booking/webhook/payment_failed	Webhook to notify payment failure (internal use)	No

Booking Flow
Seat Booking Request:
User requests seat booking by submitting booking data along with JWT for authentication.

Distributed Locking:
Booking service applies distributed locking (via Redis) to ensure no two users can book the same seat simultaneously.

Payment Intent:
The service creates and returns a payment intent (e.g., Stripe PaymentIntent) to the client for completing payment.

Payment Webhooks:
After payment is processed, payment gateway calls the /payment_successful or /payment_failed webhook to update booking status.

Sample Request Headers
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Example Booking Payload
json
{
  "eventId": "uuid-string",
  "seatNumber": "A12",
  "userId": "uuid-string",
  "paymentMethod": "card"
}


Seat Service Consumer
The Seat Service Consumer listens to CDC events published to Kafka, capturing changes in the event database table. When a new event is created in the Event Core Service’s database, the CDC system publishes a message to the Kafka topic event.public.tbl_event_outbox. The consumer reads these messages to trigger seat generation asynchronously.

Architecture & Workflow
Event Core Service writes event data to its database.

CDC tool (e.g., Debezium) monitors the event table for inserts/updates.

When a new event row is inserted, CDC streams the change event to the Kafka topic event.public.tbl_event_outbox.

The Seat Service Consumer listens on this Kafka topic.

Upon receiving a valid CDC event, it extracts the new event data from the message.

The seat service generates seats for that event asynchronously.

The consumer acknowledges the Kafka message only after successful seat generation, ensuring reliable processing.

Why CDC?
Using CDC decouples event creation logic from event propagation, providing:

Asynchronous and eventual consistency between microservices.

Decoupling of services from direct messaging responsibilities.

Reliable event streaming from the database without manual publishing in application code.

Helps eliminate the dual write problem

SeatController
Endpoints:

GET /api/v1/seat/{id}: Fetch details of a specific seat by its UUID.

GET /api/v1/seat/availableSeats: Get a paginated list of available seats.

GET /api/v1/seat/seats: Get a paginated list of all seats.

Booking CDC Consumer
Listens to Kafka topic event.public.tbl_booking_outbox — which is populated by CDC monitoring the booking table in the Booking Service database.

For each new booking record (insert event), it updates the corresponding seat to booked status in the Seat Service.

Uses manual Kafka acknowledgments to ensure at-least-once processing reliability.


All services are containerized using Docker.

An NGINX reverse proxy routes incoming requests to the appropriate service endpoints.

Communication between services happens asynchronously via Kafka topics, primarily using CDC events to synchronize state changes.



Technologies Used

Java 17+ and Spring Boot

Spring Security for authentication & authorization

Distributed Locking: (Redis) for handling concurrent bookings

REST APIs with JSON payloads

Validation: Jakarta Validation (JSR-380)

UUID for resource identifiers

Running the Service
Clone the repository:

Ensure the distributed lock backend (Redis) is running.

use docker-compose up --build to run it:

Authentication & Authorization
Uses JWT tokens for securing APIs.

/api/v1/auth/create and /api/v1/auth/refresh are open for user registration and token renewal.

Other endpoints require a valid JWT token.

Admin endpoints require an ADMIN role.

Notes on Distributed Locking
Ticket booking involves multiple microservices and requires strong concurrency control. This service integrates distributed locks (implemented via Redis) to:

Prevent double bookings of the same ticket

Synchronize booking operations across multiple instances

Ensure data consistency under heavy load and concurrent access

IMPROVEMENTS TO THE SYSTEM:

1. Use of a CDN to implement a waiting room or a simple rate limiter could help
2. Jwt endpoint for better rotation of jwt secret keys
3. Seperate DB for each service