import {
  AppBar,
  Box,
  Button,
  Container,
  Toolbar,
  Typography,
} from '@mui/material'

import {
  Link,
  Outlet,
} from 'react-router-dom'

export default function MainLayout() {

  return (
    <Box
      sx={{
        minHeight: '100vh',
        backgroundColor: 'background.default',
      }}
    >

      <AppBar
        position="static"
        elevation={0}
      >

        <Toolbar>

          <Typography
            variant="h6"
            sx={{
              flexGrow: 1,
              fontWeight: 700,
            }}
          >
            Biblioteca ITSP
          </Typography>

          <Button
            color="inherit"
            component={Link}
            to="/"
          >
            Inicio
          </Button>

          <Button
            color="inherit"
            component={Link}
            to="/ingreso"
          >
            Registro de ingreso
          </Button>

          <Button
            color="inherit"
            component={Link}
            to="/catalogo"
          >
            Catálogo
          </Button>

          <Button
            color="inherit"
            component={Link}
            to="/circulacion"
          >
            Circulación
          </Button>

          <Button
            color="inherit"
            component={Link}
            to="/espacios"
          >
            Espacios
          </Button>

          <Button
            color="inherit"
            component={Link}
            to="/admin"
          >
            Administración
          </Button>

        </Toolbar>

      </AppBar>

      <Container
        maxWidth="xl"
        sx={{
          py: 4,
        }}
      >
        <Outlet />
      </Container>

    </Box>
  )
}