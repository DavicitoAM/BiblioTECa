import {
  Box,
  Typography,
} from '@mui/material'

export default function CirculationPage() {
  return (
    <Box>
      <Typography
        variant="h4"
        sx={{ fontWeight: 700 }}
        gutterBottom
      >
        Circulación
      </Typography>

      <Typography color="text.secondary">
        Módulo de préstamos, devoluciones y renovaciones.
      </Typography>
    </Box>
  )
}