import React, { useState } from 'react';
import { useNavigate } from 'react-router';
import { useAppContext } from '../store';
import { Activity } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';

const surgeryOptions = [
  'Laparoscopic surgery',
  'Robotic-assisted surgery',
  'Endoscopic surgery',
  'Single-incision laparoscopic surgery (SILS)',
  'Natural Orifice Transluminal Endoscopic Surgery (NOTES)',
  'Laparoscopic appendectomy',
  'Laparoscopic cholecystectomy',
  'Laparoscopic hernia repair',
  'Laparoscopic colectomy',
  'Laparoscopic bariatric surgery'
];

export default function Register() {
  const navigate = useNavigate();
  const { setPatient } = useAppContext();
  const [formData, setFormData] = useState({
    name: '',
    age: '',
    sex: 'Female',
    surgeryType: surgeryOptions[0],
    surgeryDate: '',
    hospital: '',
    history: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setPatient(formData);
    navigate('/dashboard');
  };

  return (
    <div className="flex flex-col h-full bg-teal-50 relative">
      <div className="flex-1 overflow-y-auto p-6 space-y-6 pb-20">
        
        <div className="flex flex-col items-center justify-center pt-8 pb-4">
          <div className="w-24 h-24 bg-white rounded-full flex items-center justify-center shadow-md mb-4 p-2">
            <ImageWithFallback src="figma:asset/image.png" alt="SurgiCare Logo" className="w-full h-full object-contain" />
          </div>
          <h1 className="text-3xl font-bold text-slate-800">SurgiCare</h1>
          <p className="text-slate-500 text-sm mt-1">Post-operative Wound Monitoring</p>
        </div>

        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100">
          <h2 className="text-lg font-semibold text-slate-800 mb-4">Patient Registration</h2>
          
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-xs font-medium text-slate-500 mb-1">Full Name</label>
              <input required name="name" value={formData.name} onChange={handleChange} type="text" className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none" placeholder="Jane Doe" />
            </div>

            <div className="flex gap-4">
              <div className="flex-1">
                <label className="block text-xs font-medium text-slate-500 mb-1">Age</label>
                <input required name="age" value={formData.age} onChange={handleChange} type="number" className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none" placeholder="45" />
              </div>
              <div className="flex-1">
                <label className="block text-xs font-medium text-slate-500 mb-1">Sex</label>
                <select name="sex" value={formData.sex} onChange={handleChange} className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none bg-white">
                  <option>Female</option>
                  <option>Male</option>
                  <option>Other</option>
                </select>
              </div>
            </div>

            <div>
              <label className="block text-xs font-medium text-slate-500 mb-1">Type of Surgery</label>
              <select required name="surgeryType" value={formData.surgeryType} onChange={handleChange} className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none bg-white">
                {surgeryOptions.map(opt => <option key={opt} value={opt}>{opt}</option>)}
              </select>
            </div>

            <div>
              <label className="block text-xs font-medium text-slate-500 mb-1">Date of Surgery</label>
              <input required name="surgeryDate" value={formData.surgeryDate} onChange={handleChange} type="date" className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none" />
            </div>

            <div>
              <label className="block text-xs font-medium text-slate-500 mb-1">Hospital</label>
              <input required name="hospital" value={formData.hospital} onChange={handleChange} type="text" className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none" placeholder="General Hospital" />
            </div>

            <div>
              <label className="block text-xs font-medium text-slate-500 mb-1">Medical History (Optional)</label>
              <textarea name="history" value={formData.history} onChange={handleChange} className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none" rows={3} placeholder="Diabetes, Hypertension, etc." />
            </div>

            <button type="submit" className="w-full bg-[#009689] text-white font-semibold rounded-xl py-3.5 mt-2 hover:bg-teal-700 transition-colors shadow-md">
              Complete Registration
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
