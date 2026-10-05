# BiblioTECa — Frontend v2

Rediseño visual y funcional alineado con el nuevo modelo `Person + PersonType + AccessRecord`.

## Identidad

Colores oficiales usados como base:

- TecNM Pantone 294 C — `#1B396A`
- Cool Gray 10 C — `#807E82`
- Black K 100% — `#000000`

El sistema genera variantes más claras/oscuras únicamente para contraste, superficies y estados de interfaz.

### Tipografía

- Cuerpo/UI: `Noto Sans`.
- Títulos destacados: `Patria`, con fallback a `Noto Sans`.

El proyecto **no incluye archivos de fuente Patria**. Si cuentas con el archivo oficial/autorizado, puedes cargarlo en tu propia infraestructura y declararlo con `@font-face`. Mientras tanto el sistema usa el fallback.

## Logos

`src/assets/tecnm-logo.png` fue preparado a partir de la referencia suministrada para el proyecto.

La sidebar contiene un bloque `ITSPA` reservado para sustituirse por el **logo oficial de la escuela** cuando se proporcione el activo. No se inventó ni reprodujo un logotipo institucional que no fue suministrado.

## Rutas

- `/` — Resumen operativo
- `/ingreso` — Registro de entrada por tipo de persona
- `/salida` — Registro de salida
- `/registros` — Historial
- `/catalogo` — Catálogos operativos
- `/circulacion` — Módulo reservado
- `/espacios` — Módulo reservado
- `/admin` — Administración base

## Backend

`vite.config.ts` conserva el proxy:

```ts
'/api' -> 'http://localhost:8081'
```

Por ello el frontend consume `/api/v1` sin CORS durante desarrollo.

## Ejecutar

Desde `C:\BiblioTECa\frontend`:

```powershell
npm install
npm run dev
```

Abrir:

```text
http://localhost:5173
```

El backend debe seguir ejecutándose en `http://localhost:8081`.

## Funcionalidad conectada actualmente

- Dashboard con accesos abiertos y recientes.
- Registro de estudiante/docente/personal por identificador institucional.
- Registro de visitante nuevo.
- Motivos de visita.
- Registrar entrada.
- Registrar salida.
- Historial de últimos 100 accesos.
- Catálogos de tipos, motivos y carreras.

### Límite actual del backend

El backend no expone todavía búsqueda/listado de visitantes existentes. Por eso el flujo de visitante actual crea una persona nueva. La interfaz lo indica explícitamente en lugar de simular una función inexistente.
