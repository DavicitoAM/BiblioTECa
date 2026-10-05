# Aplicar Frontend v2 sobre C:\BiblioTECa

## 1. Mantén el backend ejecutándose

```powershell
cd C:\BiblioTECa\backend
.\mvnw.cmd spring-boot:run
```

Debe responder en `http://localhost:8081`.

## 2. Haz respaldo del frontend actual

Desde otra terminal:

```powershell
cd C:\BiblioTECa
Compress-Archive `
  -Path ".\frontend\src", ".\frontend\public", ".\frontend\package.json", ".\frontend\package-lock.json", ".\frontend\vite.config.ts" `
  -DestinationPath ".\frontend-v1-backup.zip" `
  -Force
```

## 3. Sustituye el frontend

Descomprime `BiblioTECa_Frontend_v2_TecNM.zip` y copia el contenido de la carpeta `frontend` sobre:

```text
C:\BiblioTECa\frontend
```

Puedes reemplazar `src` completa. El paquete no incluye `node_modules`.

## 4. Dependencias

```powershell
cd C:\BiblioTECa\frontend
npm install
```

## 5. Verificación de TypeScript y build

```powershell
npm run build
```

## 6. Desarrollo

```powershell
npm run dev
```

Abre:

```text
http://localhost:5173
```

El `vite.config.ts` ya apunta el proxy `/api` hacia:

```text
http://localhost:8081
```

## 7. Prueba funcional mínima

- Abre Resumen: debe cargar accesos recientes y abiertos.
- Abre Registrar entrada: deben aparecer Estudiante, Docente, Personal y Visitante desde la API.
- Estudiante/docente/personal: busca por identificador institucional.
- Visitante: captura datos, motivo y destino.
- Abre Registrar salida: debe listar accesos abiertos.
- Historial: debe mostrar los últimos registros.

## Identidad visual

Base de color:

- `#1B396A` TecNM / Pantone 294 C.
- `#807E82` Cool Gray 10 C.
- `#000000` Black K.

Tipografía:

- Noto Sans para interfaz y cuerpo.
- Patria únicamente para títulos destacados. Patria no se distribuye en este paquete; se usa como primera familia con fallback a Noto Sans.

El activo de TecNM está en `src/assets/tecnm-logo.png`. El logo oficial de la escuela no fue proporcionado y por eso hay un bloque `ITSPA` explícitamente reservado para sustituirlo por el archivo oficial.
