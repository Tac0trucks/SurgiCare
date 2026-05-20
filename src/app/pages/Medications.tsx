import React from 'react';
import { Pill, CheckCircle2, Circle } from 'lucide-react';
import { useAppContext } from '../store';

export default function Medications() {
  const { medications, incrementStreak } = useAppContext();

  return (
    <div className="flex flex-col h-full bg-slate-50">
      <div className="p-6 pt-10 pb-6 bg-teal-600 text-white shadow-sm">
        <h1 className="text-2xl font-bold">Medications</h1>
        <p className="text-teal-100 text-sm mt-1">Track your daily intake</p>
      </div>

      <div className="flex-1 overflow-y-auto p-6 space-y-4">
        {medications.map(med => (
          <div key={med.id} className="bg-white rounded-2xl p-5 shadow-sm border border-slate-100">
            <div className="flex justify-between items-start mb-4">
              <div className="flex items-center space-x-3">
                <div className="w-10 h-10 bg-purple-100 rounded-full flex items-center justify-center text-purple-600">
                  <Pill size={20} />
                </div>
                <div>
                  <h3 className="font-bold text-slate-800">{med.name}</h3>
                  <p className="text-xs text-slate-500">{med.dosage} • {med.timesPerDay}x/day for {med.timeInDays} days</p>
                </div>
              </div>
              <div className="text-right">
                <span className="text-xs font-bold text-slate-400 uppercase">Streak</span>
                <div className="text-lg font-bold text-orange-500">{med.streak} 🔥</div>
              </div>
            </div>

            {/* Manual check-off buttons */}
            <div className="bg-slate-50 rounded-xl p-3 flex items-center justify-between">
              <span className="text-sm font-medium text-slate-600">Mark as taken today?</span>
              <button 
                onClick={() => incrementStreak(med.id)}
                className="bg-teal-100 text-teal-700 px-4 py-2 rounded-lg text-sm font-bold hover:bg-teal-200 transition-colors"
              >
                +1 Streak
              </button>
            </div>
          </div>
        ))}

        {medications.length === 0 && (
          <div className="text-center text-slate-500 mt-10">
            No medications prescribed.
          </div>
        )}
      </div>
    </div>
  );
}
