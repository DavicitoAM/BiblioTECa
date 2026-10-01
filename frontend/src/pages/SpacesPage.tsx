import {
  Box,
  Typography,
} from '@mui/material'

export default function SpacesPage() {
  return (
    <Box>
      <Typography
        variant="h4"
        sx={{ fontWeight: 700 }}
        gutterBottom
      >
        Espacios
      </Typography>

      <Typography color="text.secondary">
        Consulta y gestión de cubículos, salas y otros espacios
        de la biblioteca.
      </Typography>
    </Box>
  )
}