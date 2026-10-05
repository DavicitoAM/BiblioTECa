# BiblioTECa — Diagrama de clases final v1.0

> Alcance: modelo de dominio completo para la aplicación BiblioTECa, incluyendo
> personas, control de acceso, catálogo, circulación, espacios y usuarios internos.

## Decisiones de diseño

- `Person` es la entidad central para cualquier persona: estudiante, docente, personal o visitante.
- `PersonType` clasifica a `Person`; no existen clases separadas `Student`, `Teacher`, `Staff` o `Visitor`.
- `StudentProfile` contiene únicamente los datos académicos exclusivos del estudiante.
- `SystemUser` representa una cuenta interna del sistema, separada de `Person`.
- `AccessRecord` representa entrada y salida en un solo objeto.
- `Book` representa la obra bibliográfica y `BookCopy` el ejemplar físico.
- `Loan` representa el préstamo de un ejemplar a una persona.
- `BookReservation` representa una reserva/espera sobre una obra bibliográfica.
- `Space` representa un espacio físico reservable.
- `SpaceReservation` representa una reserva de espacio.
- Los roles y estados cerrados se modelan como enumeraciones.

## Diagrama UML

```mermaid
classDiagram
direction LR

class PersonType {
    +Long id
    +String code
    +String name
    +String description
    +boolean active
}

class Person {
    +Long id
    +String institutionalIdentifier
    +String firstName
    +String paternalSurname
    +String maternalSurname
    +String email
    +String phone
    +boolean active
    +Instant createdAt
    +Instant updatedAt
    +String getFullName()
}

class StudentProfile {
    +Long personId
    +Short semester
    +String academicStatus
}

class Career {
    +Long id
    +String code
    +String name
    +boolean active
}

class SystemUser {
    +Long id
    +String username
    +String passwordHash
    +String displayName
    +String email
    +SystemUserRole role
    +boolean active
    +Instant lastLoginAt
    +Instant createdAt
    +Instant updatedAt
}

class SystemUserRole {
    <<enumeration>>
    ADMIN
    RECEPTION
    LIBRARIAN
}

class VisitReason {
    +Long id
    +String code
    +String name
    +String description
    +Integer displayOrder
    +boolean active
}

class AccessRecord {
    +Long id
    +Instant checkedInAt
    +Instant checkedOutAt
    +String destination
    +String notes
    +String source
    +CaptureMode captureMode
    +Instant createdAt
    +Instant updatedAt
    +void checkOut()
    +boolean isOpen()
}

class CaptureMode {
    <<enumeration>>
    SELF_SERVICE
    RECEPTION
    ADMIN
}

class Category {
    +Long id
    +String code
    +String name
    +String description
    +boolean active
}

class Author {
    +Long id
    +String firstName
    +String paternalSurname
    +String maternalSurname
    +String displayName
}

class Book {
    +Long id
    +String isbn
    +String title
    +String subtitle
    +String publisher
    +Integer publicationYear
    +String edition
    +String language
    +String description
    +boolean active
    +Instant createdAt
    +Instant updatedAt
}

class BookCopy {
    +Long id
    +String inventoryCode
    +String barcode
    +String shelfLocation
    +CopyStatus status
    +String notes
    +Instant createdAt
    +Instant updatedAt
}

class CopyStatus {
    <<enumeration>>
    AVAILABLE
    LOANED
    RESERVED
    MAINTENANCE
    LOST
    WITHDRAWN
}

class Loan {
    +Long id
    +Instant loanedAt
    +Instant dueAt
    +Instant returnedAt
    +LoanStatus status
    +Integer renewalCount
    +String notes
    +Instant createdAt
    +Instant updatedAt
    +void returnCopy()
    +void renew()
}

class LoanStatus {
    <<enumeration>>
    ACTIVE
    RETURNED
    OVERDUE
    LOST
    CANCELLED
}

class BookReservation {
    +Long id
    +Instant reservedAt
    +Instant expiresAt
    +ReservationStatus status
    +Instant fulfilledAt
    +Instant cancelledAt
    +Instant createdAt
    +Instant updatedAt
}

class ReservationStatus {
    <<enumeration>>
    ACTIVE
    FULFILLED
    EXPIRED
    CANCELLED
}

class Space {
    +Long id
    +String code
    +String name
    +String description
    +String location
    +Integer capacity
    +boolean reservable
    +boolean active
    +Instant createdAt
    +Instant updatedAt
}

class SpaceReservation {
    +Long id
    +Instant startsAt
    +Instant endsAt
    +String purpose
    +SpaceReservationStatus status
    +Instant createdAt
    +Instant updatedAt
    +void cancel()
}

class SpaceReservationStatus {
    <<enumeration>>
    PENDING
    CONFIRMED
    CANCELLED
    COMPLETED
}

PersonType "1" --> "0..*" Person : clasifica
Person "1" --> "0..1" StudentProfile : perfil académico
Career "1" --> "0..*" StudentProfile : carrera

Person "0..1" --> "0..1" SystemUser : cuenta interna
SystemUser --> SystemUserRole

Person "1" --> "0..*" AccessRecord : accesos
AccessRecord "0..*" --> "0..1" VisitReason : motivo
SystemUser "0..1" --> "0..*" AccessRecord : registrado por
AccessRecord --> CaptureMode

Category "1" --> "0..*" Book : clasifica
Book "0..*" --> "0..*" Author : autores
Book "1" --> "1..*" BookCopy : ejemplares
BookCopy --> CopyStatus

Person "1" --> "0..*" Loan : prestatario
BookCopy "1" --> "0..*" Loan : historial
SystemUser "0..1" --> "0..*" Loan : registrado por
Loan --> LoanStatus

Person "1" --> "0..*" BookReservation : solicita
Book "1" --> "0..*" BookReservation : reserva
BookReservation --> ReservationStatus

Space "1" --> "0..*" SpaceReservation : reservas
Person "1" --> "0..*" SpaceReservation : solicita
SystemUser "0..1" --> "0..*" SpaceReservation : gestiona
SpaceReservation --> SpaceReservationStatus
```

## Módulos del dominio

### Personas e identidad
- PersonType
- Person
- StudentProfile
- Career
- SystemUser
- SystemUserRole

### Control de acceso
- VisitReason
- AccessRecord
- CaptureMode

### Catálogo bibliográfico
- Category
- Author
- Book
- BookCopy
- CopyStatus

### Circulación
- Loan
- LoanStatus
- BookReservation
- ReservationStatus

### Espacios
- Space
- SpaceReservation
- SpaceReservationStatus

## Reglas principales

1. Una persona tiene un único tipo principal.
2. Sólo las personas de tipo estudiante deben poseer `StudentProfile`.
3. No toda persona tiene `SystemUser`; las cuentas internas son independientes del registro de acceso.
4. Una persona no debe tener más de un `AccessRecord` abierto al mismo tiempo.
5. Un ejemplar físico (`BookCopy`) sólo puede tener un préstamo activo al mismo tiempo.
6. Un préstamo activo debe referenciar un ejemplar existente y una persona activa.
7. Un ejemplar prestado no puede marcarse como disponible hasta cerrar el préstamo.
8. Una reserva bibliográfica se realiza sobre `Book`, no sobre un ejemplar concreto; al cumplirse puede asignarse un `BookCopy` disponible.
9. Una reserva de espacio no puede solaparse con otra reserva confirmada del mismo espacio.
10. Los usuarios internos gestionan operaciones administrativas, pero no sustituyen a `Person`.
11. Las eliminaciones históricas de préstamos, accesos y reservas deben evitar borrado físico cuando afecten trazabilidad.

## Nota sobre persistencia

En base de datos, la relación muchos-a-muchos entre `Book` y `Author` requiere una tabla de unión, por ejemplo `book_authors`. Esa tabla de unión no necesita ser una clase de dominio independiente salvo que en el futuro se agreguen atributos propios a la relación.

Este documento representa el modelo de clases que debe tomarse como referencia para cerrar el diseño antes de crear las migraciones restantes.
