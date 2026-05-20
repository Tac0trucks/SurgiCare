import React, { useState } from 'react';
import { Calendar as CalendarIcon, Clock, Video, Building2, CheckCircle2 } from 'lucide-react';
import { useNavigate } from 'react-router';

export default function Appointments() {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);
  const [type, setType] = useState('Teleconsult');
  
  const [dateMode, setDateMode] = useState<'Today' | 'Tomorrow' | 'Other' | ''>('');
  const [date, setDate] = useState('');
  
  const [time, setTime] = useState('');

  const times = ['09:00 AM', '10:30 AM', '01:00 PM', '03:30 PM'];

  const handleDateModeSelect = (mode: 'Today' | 'Tomorrow' | 'Other') => {
    setDateMode(mode);
    if (mode === 'Today') {
      setDate('Today');
    } else if (mode === 'Tomorrow') {
      setDate('Tomorrow');
    } else {
      setDate('');
    }
  };

  if (step === 2) {
    return (
      <div className="flex flex-col h-full bg-slate-50 items-center justify-center p-6 text-center">
        <div className="w-24 h-24 bg-emerald-100 text-emerald-500 rounded-full flex items-center justify-center mb-6">
          <CheckCircle2 size={48} />
        </div>
        <h1 className="text-2xl font-bold text-slate-800 mb-2">Appointment Confirmed!</h1>
        <p className="text-slate-500 mb-8">
          Your {type.toLowerCase()} is scheduled for {date} at {time}.
        </p>
        <button 
          onClick={() => navigate('/dashboard')}
          className="w-full bg-teal-600 text-white font-bold py-4 rounded-xl hover:bg-teal-700"
        >
          Back to Dashboard
        </button>
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full bg-slate-50">
      <div className="p-6 pt-10 pb-4 border-b border-slate-200 bg-white">
        <h1 className="text-xl font-bold text-slate-800">Schedule Appointment</h1>
      </div>

      <div className="flex-1 overflow-y-auto p-6 space-y-8">
        
        {/* Type Selection */}
        <div>
          <h2 className="text-sm font-bold text-slate-500 uppercase tracking-wider mb-3">Consultation Type</h2>
          <div className="grid grid-cols-2 gap-4">
            <button 
              onClick={() => setType('Teleconsult')}
              className={`p-4 rounded-xl border-2 flex flex-col items-center justify-center space-y-2 transition-all ${
                type === 'Teleconsult' ? 'border-teal-500 bg-teal-50 text-teal-700' : 'border-slate-200 bg-white text-slate-500'
              }`}
            >
              <Video size={28} />
              <span className="font-semibold text-sm">Teleconsult</span>
            </button>
            <button 
              onClick={() => setType('Clinic visit')}
              className={`p-4 rounded-xl border-2 flex flex-col items-center justify-center space-y-2 transition-all ${
                type === 'Clinic visit' ? 'border-teal-500 bg-teal-50 text-teal-700' : 'border-slate-200 bg-white text-slate-500'
              }`}
            >
              <Building2 size={28} />
              <span className="font-semibold text-sm">Clinic Visit</span>
            </button>
          </div>
        </div>

        {/* Date Selection */}
        <div>
          <h2 className="text-sm font-bold text-slate-500 uppercase tracking-wider mb-3">Select Date</h2>
          <div className="flex space-x-3 mb-4">
            {['Today', 'Tomorrow', 'Other'].map((d) => (
              <button
                key={d}
                onClick={() => handleDateModeSelect(d as any)}
                className={`flex-1 py-3 rounded-xl border-2 transition-all ${
                  dateMode === d ? 'border-teal-500 bg-teal-500 text-white' : 'border-slate-200 bg-white text-slate-600'
                }`}
              >
                <div className="font-bold text-sm">{d}</div>
              </button>
            ))}
          </div>

          {dateMode === 'Other' && (
            <div className="mt-3">
              <label className="block text-xs font-medium text-slate-500 mb-1">Custom Date</label>
              <input 
                type="date" 
                value={date} 
                onChange={(e) => setDate(e.target.value)}
                className="w-full border border-slate-200 rounded-lg p-3 text-sm focus:ring-2 focus:ring-teal-500 focus:outline-none"
              />
            </div>
          )}
        </div>

        {/* Time Selection */}
        <div>
          <h2 className="text-sm font-bold text-slate-500 uppercase tracking-wider mb-3">Select Time</h2>
          <div className="grid grid-cols-2 gap-3">
            {times.map((t) => (
              <button
                key={t}
                onClick={() => setTime(t)}
                className={`p-3 rounded-xl border-2 transition-all ${
                  time === t ? 'border-teal-500 bg-teal-50 text-teal-700 font-bold' : 'border-slate-200 bg-white text-slate-600'
                }`}
              >
                {t}
              </button>
            ))}
          </div>
        </div>

      </div>

      <div className="p-6 bg-white border-t border-slate-100">
        <button 
          disabled={!date || !time}
          onClick={() => setStep(2)}
          className={`w-full py-4 rounded-xl font-bold transition-all shadow-md ${
            date && time ? 'bg-teal-600 text-white hover:bg-teal-700' : 'bg-slate-200 text-slate-400 cursor-not-allowed'
          }`}
        >
          Confirm Appointment
        </button>
      </div>
    </div>
  );
}
