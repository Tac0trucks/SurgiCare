import React, { createContext, useContext, useState, ReactNode } from 'react';

export type Status = 'Green' | 'Yellow' | 'Red';

export interface Patient {
  name: string;
  age: string;
  sex: string;
  surgeryType: string;
  surgeryDate: string;
  hospital: string;
  history: string;
}

export interface Medication {
  id: string;
  name: string;
  dosage: string;
  timesPerDay: number;
  timeInDays: number;
  streak: number;
}

export interface Assessment {
  id: string;
  date: string;
  dayPostOp: number;
  image: string | null;
  symptoms: string[];
  status: Status;
}

export interface AppState {
  patient: Patient | null;
  setPatient: (patient: Patient) => void;
  medications: Medication[];
  setMedications: (meds: Medication[]) => void;
  incrementStreak: (id: string) => void;
  assessments: Assessment[];
  addAssessment: (assessment: Assessment) => void;
  currentDate: Date; // For time travel
  advanceDays: (days: number) => void;
  resetData: () => void;
}

const mockMedications: Medication[] = [
  { id: '1', name: 'Amoxicillin', dosage: '500mg', timesPerDay: 3, timeInDays: 7, streak: 0 },
  { id: '2', name: 'Ibuprofen', dosage: '400mg', timesPerDay: 2, timeInDays: 5, streak: 0 },
];

const mockPatient: Patient = {
  name: 'Jane Doe',
  age: '45',
  sex: 'Female',
  surgeryType: 'Laparoscopic appendectomy',
  surgeryDate: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000).toISOString().split('T')[0], // 3 days ago
  hospital: 'General Hospital',
  history: 'None',
};

const mockAssessments: Assessment[] = [
  {
    id: '1',
    date: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000).toISOString(),
    dayPostOp: 1,
    image: null,
    symptoms: ['Mild pain or tenderness'],
    status: 'Green',
  },
  {
    id: '2',
    date: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000).toISOString(),
    dayPostOp: 2,
    image: null,
    symptoms: ['Mild pain or tenderness'],
    status: 'Green',
  }
];

const AppContext = createContext<AppState | undefined>(undefined);

export const AppProvider = ({ children }: { children: ReactNode }) => {
  const [patient, setPatient] = useState<Patient | null>(null); // Start null to show registration
  const [medications, setMedications] = useState<Medication[]>(mockMedications);
  const [assessments, setAssessments] = useState<Assessment[]>([]);
  const [currentDate, setCurrentDate] = useState<Date>(new Date());

  const incrementStreak = (id: string) => {
    setMedications(meds => meds.map(m => m.id === id ? { ...m, streak: m.streak + 1 } : m));
  };

  const addAssessment = (assessment: Assessment) => {
    setAssessments(prev => [...prev, assessment]);
  };

  const advanceDays = (days: number) => {
    setCurrentDate(prev => new Date(prev.getTime() + days * 24 * 60 * 60 * 1000));
  };

  const resetData = () => {
    setPatient(null);
    setMedications(mockMedications);
    setAssessments([]);
    setCurrentDate(new Date());
  };

  // Provide an easy way to seed mock data for testing
  const seedMockData = () => {
    setPatient(mockPatient);
    setAssessments(mockAssessments);
  };

  return (
    <AppContext.Provider value={{
      patient,
      setPatient: (p) => { setPatient(p); if (!patient) seedMockData(); }, // Seed on register for demo
      medications,
      setMedications,
      incrementStreak,
      assessments,
      addAssessment,
      currentDate,
      advanceDays,
      resetData
    }}>
      {children}
    </AppContext.Provider>
  );
};

export const useAppContext = () => {
  const context = useContext(AppContext);
  if (!context) throw new Error('useAppContext must be used within AppProvider');
  return context;
};
