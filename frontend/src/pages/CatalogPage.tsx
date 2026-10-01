import {
  Box,
  Typography,
} from '@mui/material'

export default function CatalogPage() {
  return (
    <Box>
      <Typography
        variant="h4"
        sx={{ fontWeight: 700 }}
        gutterBottom
      >
        Catálogo
      </Typography>

      <Typography color="text.secondary">
        Consulta de material bibliográfico y disponibilidad.
      </Typography>
    </Box>
  )
}