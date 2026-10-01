import { createTheme } from '@mui/material/styles'

export const theme = createTheme({

  palette: {

    primary: {
      main: '#1B396A',
      dark: '#10284C',
      light: '#31578C',
    },

    secondary: {
      main: '#807E82',
    },

    background: {
      default: '#F5F7FA',
      paper: '#FFFFFF',
    },

  },

  typography: {

    fontFamily: [
      'Inter',
      'Roboto',
      'Arial',
      'sans-serif',
    ].join(','),

    h4: {
      fontWeight: 700,
    },

    h5: {
      fontWeight: 700,
    },

    button: {
      textTransform: 'none',
      fontWeight: 600,
    },

  },

  shape: {
    borderRadius: 12,
  },

})