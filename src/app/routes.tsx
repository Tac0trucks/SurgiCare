import React from 'react';
import { createBrowserRouter } from 'react-router';
import MobileLayout from './layouts/MobileLayout';
import Login from './pages/Login';
import Register from './pages/Register';
import PatientDashboard from './pages/PatientDashboard';
import UploadPhoto from './pages/UploadPhoto';
import SymptomCheck from './pages/SymptomCheck';
import AssessmentResult from './pages/AssessmentResult';
import Appointments from './pages/Appointments';
import Medications from './pages/Medications';
import Progress from './pages/Progress';
import Profile from './pages/Profile';
import DoctorDashboard from './pages/DoctorDashboard';

export const router = createBrowserRouter([
  {
    path: '/',
    Component: MobileLayout,
    children: [
      { index: true, Component: Login },
      { path: 'home', Component: Register },
      { path: 'dashboard', Component: PatientDashboard },
      { path: 'dashboard/upload', Component: UploadPhoto },
      { path: 'dashboard/assessment', Component: SymptomCheck },
      { path: 'dashboard/result', Component: AssessmentResult },
      { path: 'dashboard/appointments', Component: Appointments },
      { path: 'dashboard/medications', Component: Medications },
      { path: 'dashboard/progress', Component: Progress },
      { path: 'dashboard/profile', Component: Profile },
      { path: 'doctor', Component: DoctorDashboard },
    ],
  },
]);
