import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Divider,
    Grid,
    TextField,
    Typography,
} from '@mui/material'

import {
    useEffect,
    useState,
} from 'react'

import {
    createVisit,
    getStudentByControlNumber,
    getVisitReasons,
} from './api'

import type {
    Student,
    VisitReason,
    VisitResponse,
} from './types'

export default function CheckInPage() {

    const [
        controlNumber,
        setControlNumber,
    ] = useState('')

    const [
        student,
        setStudent,
    ] = useState<Student | null>(null)

    const [
        reasons,
        setReasons,
    ] = useState<VisitReason[]>([])

    const [
        selectedReason,
        setSelectedReason,
    ] = useState<VisitReason | null>(null)

    const [
        loading,
        setLoading,
    ] = useState(false)

    const [
        error,
        setError,
    ] = useState<string | null>(null)

    const [
        confirmationOpen,
        setConfirmationOpen,
    ] = useState(false)

    const [
        success,
        setSuccess,
    ] = useState<VisitResponse | null>(null)

    useEffect(() => {

        getVisitReasons()
            .then(setReasons)
            .catch(() => {
                setError(
                    'No fue posible cargar los motivos de visita.'
                )
            })

    }, [])

    async function handleSearchStudent() {

        setLoading(true)
        setError(null)

        try {

            const result =
                await getStudentByControlNumber(
                    controlNumber.trim()
                )

            setStudent(result)

        } catch {

            setStudent(null)

            setError(
                'No encontramos un estudiante con ese número de control.'
            )

        } finally {

            setLoading(false)

        }
    }

    async function handleConfirm() {

        if (!student || !selectedReason) {
            return
        }

        setLoading(true)
        setError(null)

        try {

            const result = await createVisit(
                student.controlNumber,
                selectedReason.id
            )

            setSuccess(result)

            setConfirmationOpen(false)

        } catch {

            setError(
                'No fue posible registrar el ingreso.'
            )

        } finally {

            setLoading(false)

        }
    }

    function reset() {

        setControlNumber('')
        setStudent(null)
        setSelectedReason(null)
        setSuccess(null)
        setError(null)

    }

    if (success) {

        return (
            <Box
                sx={{
                    maxWidth: 600,
                    mx: 'auto',
                    mt: 8,
                }}
            >

                <Card>

                    <CardContent
                        sx={{
                            textAlign: 'center',
                            p: 5,
                        }}
                    >

                        <Typography
                            variant="h3"
                            color="success.main"
                            sx={{
                                mb: 2,
                            }}
                        >
                            ✓
                        </Typography>

                        <Typography
                            variant="h4"
                            gutterBottom
                        >
                            Bienvenido
                        </Typography>

                        <Typography
                            variant="h6"
                            sx={{
                                mb: 1,
                            }}
                        >
                            {success.student.fullName}
                        </Typography>

                        <Typography
                            color="text.secondary"
                            sx={{
                                mb: 3,
                            }}
                        >
                            Tu ingreso ha sido registrado correctamente.
                        </Typography>

                        <Typography>
                            Motivo:
                            {' '}
                            <strong>
                                {success.reason.name}
                            </strong>
                        </Typography>

                        <Button
                            variant="contained"
                            sx={{
                                mt: 4,
                            }}
                            onClick={reset}
                        >
                            Finalizar
                        </Button>

                    </CardContent>

                </Card>

            </Box>
        )
    }

    return (
        <Box
            sx={{
                maxWidth: 900,
                mx: 'auto',
            }}
        >

            <Typography
                variant="h4"
                gutterBottom
            >
                Registro de ingreso
            </Typography>

            <Typography
                color="text.secondary"
                sx={{
                    mb: 4,
                }}
            >
                Biblioteca del Instituto Tecnológico Superior de Pátzcuaro
            </Typography>

            {error && (
                <Alert
                    severity="error"
                    sx={{
                        mb: 3,
                    }}
                >
                    {error}
                </Alert>
            )}

            <Card>

                <CardContent sx={{ p: 4 }}>

                    <Typography
                        variant="h6"
                        gutterBottom
                    >
                        Identificación
                    </Typography>

                    <Typography
                        color="text.secondary"
                        sx={{
                            mb: 3,
                        }}
                    >
                        Ingresa tu número de control.
                    </Typography>

                    <Box
                        sx={{
                            display: 'flex',
                            gap: 2,
                        }}
                    >

                        <TextField
                            fullWidth
                            label="Número de control"
                            value={controlNumber}
                            disabled={student !== null}
                            onChange={(event) =>
                                setControlNumber(
                                    event.target.value
                                )
                            }
                            onKeyDown={(event) => {
                                if (
                                    event.key === 'Enter'
                                    && !student
                                ) {
                                    handleSearchStudent()
                                }
                            }}
                        />

                        {!student && (
                            <Button
                                variant="contained"
                                disabled={
                                    loading
                                    || !controlNumber.trim()
                                }
                                onClick={handleSearchStudent}
                            >
                                {loading
                                    ? <CircularProgress size={24} />
                                    : 'Continuar'
                                }
                            </Button>
                        )}

                    </Box>

                    {student && (
                        <>

                            <Divider sx={{ my: 4 }} />

                            <Typography variant="h6">
                                {student.fullName}
                            </Typography>

                            <Typography color="text.secondary">
                                {student.career}
                            </Typography>

                            {student.semester && (
                                <Typography color="text.secondary">
                                    {student.semester}.º semestre
                                </Typography>
                            )}

                            <Button
                                size="small"
                                sx={{
                                    mt: 1,
                                }}
                                onClick={() => {
                                    setStudent(null)
                                    setSelectedReason(null)
                                }}
                            >
                                Cambiar número de control
                            </Button>

                            <Divider sx={{ my: 4 }} />

                            <Typography
                                variant="h6"
                                gutterBottom
                            >
                                ¿Cuál es el motivo de tu visita?
                            </Typography>

                            <Grid
                                container
                                spacing={2}
                                sx={{
                                    mt: 1,
                                }}
                            >

                                {reasons.map((reason) => (

                                    <Grid
                                        size={{
                                            xs: 12,
                                            sm: 6,
                                        }}
                                        key={reason.id}
                                    >

                                        <Card
                                            variant="outlined"
                                            onClick={() =>
                                                setSelectedReason(reason)
                                            }
                                            sx={{
                                                cursor: 'pointer',

                                                borderWidth: 2,

                                                borderColor:
                                                    selectedReason?.id
                                                        === reason.id
                                                        ? 'primary.main'
                                                        : 'divider',

                                                backgroundColor:
                                                    selectedReason?.id
                                                        === reason.id
                                                        ? 'action.selected'
                                                        : 'background.paper',

                                                '&:hover': {
                                                    borderColor:
                                                        'primary.main',
                                                },
                                            }}
                                        >

                                            <CardContent>

                                                <Typography
                                                    sx={{ fontWeight: 600 }}
                                                >
                                                    {selectedReason?.name}
                                                </Typography>

                                                <Typography
                                                    variant="body2"
                                                    color="text.secondary"
                                                    sx={{
                                                        mt: 1,
                                                    }}
                                                >
                                                    {reason.description}
                                                </Typography>

                                            </CardContent>

                                        </Card>

                                    </Grid>

                                ))}

                            </Grid>

                            <Box
                                sx={{
                                    display: 'flex',
                                    justifyContent: 'flex-end',
                                    mt: 4,
                                }}
                            >

                                <Button
                                    variant="contained"
                                    size="large"
                                    disabled={!selectedReason}
                                    onClick={() =>
                                        setConfirmationOpen(true)
                                    }
                                >
                                    Continuar
                                </Button>

                            </Box>

                        </>
                    )}

                </CardContent>

            </Card>

            <Dialog
                open={confirmationOpen}
                onClose={() =>
                    setConfirmationOpen(false)
                }
                fullWidth
                maxWidth="sm"
            >

                <DialogTitle>
                    Confirmar ingreso
                </DialogTitle>

                <DialogContent>

                    <Typography
                        variant="h6"
                        sx={{
                            mt: 1,
                        }}
                    >
                        {student?.fullName}
                    </Typography>

                    <Typography color="text.secondary">
                        {student?.career}
                    </Typography>

                    <Divider sx={{ my: 3 }} />

                    <Typography
                        variant="body2"
                        color="text.secondary"
                    >
                        Motivo de visita
                    </Typography>

                    <Typography
                        sx={{ fontWeight: 600 }}
                    >
                        {selectedReason?.name}
                    </Typography>

                </DialogContent>

                <DialogActions>

                    <Button
                        onClick={() =>
                            setConfirmationOpen(false)
                        }
                    >
                        Volver
                    </Button>

                    <Button
                        variant="contained"
                        onClick={handleConfirm}
                        disabled={loading}
                    >
                        Confirmar ingreso
                    </Button>

                </DialogActions>

            </Dialog>

        </Box>
    )
}