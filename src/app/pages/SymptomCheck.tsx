import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router';
import { Check, ArrowRight } from 'lucide-react';
import { useAppContext, Status } from '../store';

const symptomList = [
  'Abdominal pain',
  'Nausea and vomiting',
  'Bloating',
  'Fever',
  'Loss of appetite',
  'Constipation or diarrhea',
  'Difficulty passing gas',
  'Abdominal swelling',
  'Heartburn',
  'Unexplained weight loss',
  'Pus or foul-smelling drainage',
  'Heavy bleeding',
  'Difficulty breathing or chest pain'
];

export default function SymptomCheck() {
  const navigate = useNavigate();
  const location = useLocation();
  const { addAssessment, currentDate } = useAppContext();
  const image = location.state?.image || null;

  const [selectedSymptoms, setSelectedSymptoms] = useState<string[]>([]);

  const toggleSymptom = (symptom: string) => {
    setSelectedSymptoms(prev => 
      prev.includes(symptom) ? prev.filter(s => s !== symptom) : [...prev, symptom]
    );
  };

  const determineStatus = (): Status => {
    // Red flag symptoms
    const redFlags = ['Pus or foul-smelling drainage', 'Heavy bleeding', 'Difficulty breathing or chest pain', 'Fever'];
    // Yellow flag symptoms
    const yellowFlags = ['Abdominal swelling', 'Nausea and vomiting', 'Abdominal pain', 'Constipation or diarrhea'];

    if (selectedSymptoms.some(s => redFlags.includes(s))) return 'Red';
    if (selectedSymptoms.some(s => yellowFlags.includes(s))) return 'Yellow';
    return 'Green'; // No symptoms or only mild ones
  };

  const handleSubmit = () => {
    const status = determineStatus();
    const newAssessment = {
      id: Math.random().toString(36).substring(7),
      date: currentDate.toISOString(),
      dayPostOp: 1, // simplified
      image,
      symptoms: selectedSymptoms.length > 0 ? selectedSymptoms : ['None reported'],
      status
    };
    
    addAssessment(newAssessment);
    navigate('/dashboard/result', { state: { assessment: newAssessment } });
  };

  return (
    <div className="flex flex-col h-full bg-slate-50">
      <div className="p-6 pt-10 pb-4 border-b border-slate-200 flex items-center bg-white shadow-sm z-10">
        <button onClick={() => navigate(-1)} className="text-teal-600 font-medium">Back</button>
        <h1 className="flex-1 text-center font-bold text-slate-800 text-lg">Daily Assessment</h1>
        <div className="w-12"></div>
      </div>

      <div className="flex-1 overflow-y-auto p-6 pb-24">
        <div className="mb-6">
          <p className="text-xs font-bold text-teal-600 tracking-wider mb-1">STEP 2 OF 2</p>
          <h2 className="text-2xl font-bold text-slate-800">Symptom Check</h2>
          <p className="text-slate-500 mt-2 text-sm">Please select any symptoms you are currently experiencing.</p>
        </div>

        <div className="space-y-3">
          {symptomList.map(symptom => {
            const isSelected = selectedSymptoms.includes(symptom);
            return (
              <button
                key={symptom}
                onClick={() => toggleSymptom(symptom)}
                className={`w-full text-left p-4 rounded-2xl border-2 transition-all flex items-center justify-between ${
                  isSelected ? 'border-teal-500 bg-teal-50' : 'border-slate-100 bg-white'
                }`}
              >
                <span className={`font-medium ${isSelected ? 'text-teal-800' : 'text-slate-700'}`}>{symptom}</span>
                <div className={`w-6 h-6 rounded-full flex items-center justify-center border-2 ${
                  isSelected ? 'border-teal-500 bg-teal-500 text-white' : 'border-slate-300'
                }`}>
                  {isSelected && <Check size={14} strokeWidth={3} />}
                </div>
              </button>
            )
          })}
        </div>
      </div>

      <div className="absolute bottom-0 w-full p-6 bg-white border-t border-slate-200">
        <button 
          onClick={handleSubmit}
          className="w-full flex items-center justify-center space-x-2 bg-teal-600 hover:bg-teal-700 py-4 rounded-2xl font-bold text-white transition-all shadow-md"
        >
          <span>Submit Assessment</span>
          <ArrowRight size={20} />
        </button>
      </div>
    </div>
  );
}
