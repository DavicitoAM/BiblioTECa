import {
  BrowserRouter,
  Route,
  Routes,
} from 'react-router-dom'

import MainLayout from '../layout/MainLayout'

import HomePage from '../pages/HomePage'
import CatalogPage from '../pages/CatalogPage'
import CirculationPage from '../pages/CirculationPage'
import SpacesPage from '../pages/SpacesPage'
import AdminPage from '../pages/AdminPage'

import CheckInPage
  from '../features/check-in/CheckInPage'

export default function App() {

  return (
    <BrowserRouter>

      <Routes>

        <Route element={<MainLayout />}>

          <Route
            path="/"
            element={<HomePage />}
          />

          <Route
            path="/ingreso"
            element={<CheckInPage />}
          />

          <Route
            path="/catalogo"
            element={<CatalogPage />}
          />

          <Route
            path="/circulacion"
            element={<CirculationPage />}
          />

          <Route
            path="/espacios"
            element={<SpacesPage />}
          />

          <Route
            path="/admin"
            element={<AdminPage />}
          />

        </Route>

      </Routes>

    </BrowserRouter>
  )
}