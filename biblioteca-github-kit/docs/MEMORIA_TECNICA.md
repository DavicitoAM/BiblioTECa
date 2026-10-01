# Memoria técnica breve — BiblioTECa

## Propósito
Sistema de gestión bibliotecaria para el Instituto Tecnológico Superior de Pátzcuaro.

## Primera funcionalidad
Registro optimizado de ingreso:

1. Número de control.
2. Consulta de datos escolares.
3. Selección controlada de motivo.
4. Confirmación.
5. Fecha/hora generadas por backend.
6. Persistencia en PostgreSQL.

## Stack
- React + TypeScript + Vite.
- Material UI.
- Spring Boot.
- Spring Data JPA / Hibernate.
- PostgreSQL.
- Flyway.
- REST + JSON.

## Entidades actuales
- careers
- students
- visit_reasons
- visits

## Reglas
- Número de control único.
- Carrera obligatoria.
- Estados académicos controlados.
- Motivos administrables.
- Hora generada en backend.
- Integridad referencial con claves foráneas.

## Próximas etapas
- Catálogo.
- Inventario.
- Circulación.
- Espacios.
- Administración.
- Auditoría.
- Seguridad.
- Reportes.
- Automatizaciones.

## Principio de diseño
Evitar volver a pedir datos que el sistema ya conoce y preferir catálogos controlados sobre texto libre cuando sea posible.
