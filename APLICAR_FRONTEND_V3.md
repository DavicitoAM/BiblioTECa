# Aplicar BiblioTECa Frontend v3 — User First

## Qué cambia

El frontend queda dividido en tres experiencias distintas:

- **Usuario/comunidad:** `/`, `/catalogo`, `/circulacion`, `/espacios`.
- **Acceso/kiosco:** `/acceso`, perfiles institucionales, visitante y `/salida`.
- **Administración interna:** `/admin/*`.

La experiencia pública no contiene enlaces ni referencias al panel administrativo.

## Reemplazo recomendado

1. Detén Vite si está ejecutándose.
2. Haz respaldo de `C:\BiblioTECa\frontend`.
3. Sustituye el contenido de tu carpeta `frontend` por la carpeta `frontend` de este paquete.
4. No copies `node_modules` desde respaldos anteriores.
5. Ejecuta:

```powershell
cd C:\BiblioTECa\frontend
npm install
npm run dev
```

6. Mantén el backend en `http://localhost:8081`.
7. Abre `http://localhost:5173`.

## Prueba rápida

- `/` debe mostrar la experiencia para usuarios.
- `/acceso` debe preguntar quién ingresa.
- `/acceso/estudiante`, `/acceso/docente`, `/acceso/personal` deben pedir identificador.
- `/acceso/visitante` debe mostrar formulario específico.
- `/salida` debe permitir cerrar una entrada.
- `/admin` es una vista separada y no aparece enlazada desde la interfaz pública.

## Seguridad

La separación visual ya existe, pero **ocultar `/admin` no protege la ruta**. Para que un usuario realmente no pueda entrar aunque conozca la URL, el backend necesita autenticación y autorización administrativa. Eso debe implementarse como una fase posterior.
