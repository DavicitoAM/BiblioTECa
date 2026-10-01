import {
    Box,
    Typography,
} from '@mui/material'

export default function AdminPage() {
    return (
        <Box>
            <Typography
                variant="h4"
                sx={{ fontWeight: 700 }}
                gutterBottom
            >
                Administración
            </Typography>

            <Typography color="text.secondary">
                Panel administrativo de la Biblioteca del Instituto
                Tecnológico Superior de Pátzcuaro.
            </Typography>
        </Box>
    )

}