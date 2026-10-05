# BiblioTECa — Frontend v3 User First

## Objetivo del rediseño

Esta versión corrige el enfoque del frontend anterior: la experiencia principal ya no es un dashboard administrativo.

El sistema queda dividido en tres experiencias visual y funcionalmente separadas:

1. **Usuario público / comunidad** (`/`, `/catalogo`, `/circulacion`, `/espacios`)
2. **Acceso / kiosco** (`/acceso`, perfiles y `/salida`)
3. **Administración interna** (`/admin/*`)

La interfaz pública no contiene enlaces, menús ni referencias a la administración.

> Importante: ocultar `/admin` no es seguridad. La protección real de rutas administrativas requiere autenticación/autorización en el backend, funcionalidad que todavía no existe.

## Flujo principal

```text
/
└── Registrar acceso
    └── /acceso
        ├── /acceso/estudiante
        ├── /acceso/docente
        ├── /acceso/personal
        ├── /acceso/visitante
        └── /salida
```

### Estudiante, docente y personal

1. Seleccionan su perfil.
2. Introducen número de control o clave institucional.
3. Se consulta `GET /api/v1/persons/identifier/{identifier}`.
4. El frontend comprueba que el tipo de persona coincide con la vista elegida.
5. Se consulta si ya existe una entrada abierta.
6. Si no existe, se registra con `POST /api/v1/access-records/check-in`.

### Visitante

1. Captura datos personales.
2. Selecciona motivo.
3. Captura destino.
4. Se crea con `POST /api/v1/persons/visitors`.
5. Se registra la entrada.

### Salida

- Comunidad institucional: búsqueda por identificador.
- Visitante: búsqueda por nombre entre accesos abiertos.
- Se cierra el registro mediante `POST /api/v1/access-records/{id}/check-out`.

## Identidad visual

Colores oficiales proporcionados como base:

- TecNM Pantone 294 C: `#1B396A`
- Cool Gray 10 C: `#807E82`
- Black K 100%: `#000000`

Se añadieron variantes tonales derivadas en `src/theme/tokens.ts`.

Tipografía:

- **Noto Sans**: cuerpo, navegación, formularios y controles.
- **Patria**: únicamente títulos destacados.

El proyecto carga Noto Sans desde Google Fonts durante desarrollo. Para Patria no se distribuye ningún archivo de fuente: la CSS utiliza la fuente si está instalada/provista localmente y cae a Noto Sans si no está disponible.

## Logos

Se integra el logotipo TecNM proporcionado por el usuario.

El logotipo del instituto no fue inventado ni sustituido por uno no oficial. Cuando se proporcione el archivo autorizado puede agregarse en `src/assets/` y conectarse al componente `Brand.tsx`.

## Backend esperado

Vite tiene proxy:

```text
/api -> http://localhost:8081
```

Por tanto el backend debe estar ejecutándose en el puerto 8081.

## Ejecutar

```powershell
cd C:\BiblioTECa\frontend
npm install
npm run dev
```

Abrir:

```text
http://localhost:5173
```

## Build

```powershell
npm run build
```

## Rutas

### Usuario

```text
/
 /catalogo
 /circulacion
 /espacios
```

### Acceso

```text
/acceso
/acceso/estudiante
/acceso/docente
/acceso/personal
/acceso/visitante
/salida
```

### Administración interna

```text
/admin
/admin/registros
/admin/personas
/admin/catalogos
```
