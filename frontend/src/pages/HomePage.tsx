import {
  Box,
  Button,
  Card,
  CardContent,
  Grid,
  Typography,
} from '@mui/material'

import { Link } from 'react-router-dom'

export default function HomePage() {

  return (
    <Box>

      <Typography
        variant="h4"
        gutterBottom
      >
        Biblioteca
      </Typography>

      <Typography
        color="text.secondary"
        sx={{
          mb: 4,
        }}
      >
        Instituto Tecnológico Superior de Pátzcuaro
      </Typography>

      <Grid
        container
        spacing={3}
      >

        <Grid size={{ xs: 12, md: 4 }}>
          <Card>

            <CardContent>

              <Typography variant="h6">
                Registro de ingreso
              </Typography>

              <Typography
                color="text.secondary"
                sx={{
                  my: 2,
                }}
              >
                Registra de manera rápida tu ingreso
                a la biblioteca.
              </Typography>

              <Button
                variant="contained"
                component={Link}
                to="/ingreso"
              >
                Registrar ingreso
              </Button>

            </CardContent>

          </Card>
        </Grid>

        <Grid size={{ xs: 12, md: 4 }}>
          <Card>

            <CardContent>

              <Typography variant="h6">
                Catálogo
              </Typography>

              <Typography
                color="text.secondary"
                sx={{
                  my: 2,
                }}
              >
                Consulta material y disponibilidad.
              </Typography>

              <Button
                component={Link}
                to="/catalogo"
              >
                Explorar catálogo
              </Button>

            </CardContent>

          </Card>
        </Grid>

        <Grid size={{ xs: 12, md: 4 }}>
          <Card>

            <CardContent>

              <Typography variant="h6">
                Servicios
              </Typography>

              <Typography
                color="text.secondary"
                sx={{
                  my: 2,
                }}
              >
                Consulta los servicios disponibles
                dentro de la biblioteca.
              </Typography>

            </CardContent>

          </Card>
        </Grid>

      </Grid>

    </Box>
  )
}