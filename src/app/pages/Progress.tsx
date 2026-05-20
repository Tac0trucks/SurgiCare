import React from 'react';
import { useAppContext } from '../store';
import { format, parseISO } from 'date-fns';

export default function Progress() {
  const { assessments, patient } = useAppContext();

  // Sort assessments by date
  const sorted = [...assessments].sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());

  return (
    <div className="flex flex-col h-full bg-slate-50">
      <div className="p-6 pt-10 pb-6 bg-teal-600 text-white shadow-sm">
        <h1 className="text-2xl font-bold">Healing Progress</h1>
        <p className="text-teal-100 text-sm mt-1">Timeline since surgery</p>
      </div>

      <div className="flex-1 overflow-y-auto p-6">
        <div className="relative border-l-2 border-slate-200 ml-4 space-y-8 pb-10">
          
          {sorted.map((assessment, idx) => {
            const date = parseISO(assessment.date);
            const statusColor = assessment.status === 'Green' ? 'bg-emerald-500' :
                               assessment.status === 'Yellow' ? 'bg-amber-500' : 'bg-rose-500';

            return (
              <div key={assessment.id} className="relative pl-6">
                {/* Timeline Dot */}
                <div className={`absolute -left-[9px] top-1 w-4 h-4 rounded-full border-2 border-white shadow-sm ${statusColor}`} />
                
                <div className="bg-white p-4 rounded-xl shadow-sm border border-slate-100">
                  <div className="flex justify-between items-start mb-2">
                    <div>
                      <h3 className="font-bold text-slate-800">Day {assessment.dayPostOp}</h3>
                      <p className="text-xs text-slate-500">{format(date, 'MMM d, yyyy')}</p>
                    </div>
                    <span className={`text-xs font-bold px-2 py-1 rounded-full ${
                      assessment.status === 'Green' ? 'bg-emerald-100 text-emerald-700' :
                      assessment.status === 'Yellow' ? 'bg-amber-100 text-amber-700' : 'bg-rose-100 text-rose-700'
                    }`}>
                      {assessment.status}
                    </span>
                  </div>
                  
                  {assessment.image ? (
                    <img src={assessment.image} alt="Wound" className="w-full h-32 object-cover rounded-lg mb-3" />
                  ) : (
                    <div className="w-full h-16 bg-slate-100 rounded-lg mb-3 flex items-center justify-center text-slate-400 text-xs">
                      No photo uploaded
                    </div>
                  )}

                  <div>
                    <p className="text-xs font-semibold text-slate-600 mb-1">Symptoms:</p>
                    <div className="flex flex-wrap gap-1">
                      {assessment.symptoms.map((sym, i) => (
                        <span key={i} className="text-[10px] bg-slate-100 text-slate-600 px-2 py-1 rounded">
                          {sym}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            );
          })}

          <div className="relative pl-6 opacity-50">
            <div className="absolute -left-[9px] top-1 w-4 h-4 rounded-full border-2 border-white bg-slate-300" />
            <div className="bg-slate-100 p-4 rounded-xl border border-slate-200 border-dashed">
              <h3 className="font-bold text-slate-600">Surgery</h3>
              <p className="text-xs text-slate-500">{patient ? format(new Date(patient.surgeryDate), 'MMM d, yyyy') : 'Unknown'}</p>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}
