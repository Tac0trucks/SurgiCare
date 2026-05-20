import React from 'react';
import { useLocation, useNavigate } from 'react-router';
import { CheckCircle, AlertTriangle, AlertOctagon, CalendarPlus, Home } from 'lucide-react';
import { Assessment } from '../store';

export default function AssessmentResult() {
  const location = useLocation();
  const navigate = useNavigate();
  const assessment = location.state?.assessment as Assessment;

  if (!assessment) {
    return (
      <div className="flex items-center justify-center h-full">
        <p>No assessment data found.</p>
        <button onClick={() => navigate('/dashboard')} className="mt-4 text-teal-600">Go Home</button>
      </div>
    );
  }

  const { status } = assessment;

  const content = {
    Green: {
      title: "Normal Healing",
      desc: "Your wound appears to be healing normally.",
      icon: CheckCircle,
      color: "text-emerald-500",
      bg: "bg-emerald-500",
      bgLight: "bg-emerald-50",
      interventions: [
        "Continue prescribed medications",
        "Keep incision clean and dry",
        "Encourage light movement and walking",
        "Maintain proper nutrition and hydration",
        "Continue home monitoring"
      ]
    },
    Yellow: {
      title: "Needs Monitoring",
      desc: "Your symptoms show possible complications that should be monitored.",
      icon: AlertTriangle,
      color: "text-amber-500",
      bg: "bg-amber-500",
      bgLight: "bg-amber-50",
      interventions: [
        "Reassess symptoms regularly",
        "Monitor temperature and wound changes",
        "Advise rest and increased fluid intake",
        "Notify healthcare provider if symptoms worsen",
        "Schedule follow-up checkup"
      ]
    },
    Red: {
      title: "Possible Emergency",
      desc: "Serious warning signs needing immediate medical attention.",
      icon: AlertOctagon,
      color: "text-rose-500",
      bg: "bg-rose-500",
      bgLight: "bg-rose-50",
      interventions: [
        "Seek immediate medical attention",
        "Contact surgeon or emergency services",
        "Monitor vital signs",
        "Do not apply unprescribed medications to wound",
        "Prepare for possible hospital admission or emergency treatment"
      ]
    }
  }[status];

  const Icon = content.icon;

  return (
    <div className={`flex flex-col h-full ${content.bgLight}`}>
      <div className="flex-1 overflow-y-auto p-6 flex flex-col items-center">
        
        <div className="w-full flex justify-end pt-4">
          <button onClick={() => navigate('/dashboard')} className="text-slate-500 bg-white/50 p-2 rounded-full">
            <Home size={24} />
          </button>
        </div>

        <div className="flex flex-col items-center text-center mt-8 mb-8">
          <div className={`w-32 h-32 rounded-full flex items-center justify-center mb-6 shadow-lg ${content.bg} text-white`}>
            <Icon size={64} />
          </div>
          <h1 className={`text-3xl font-bold mb-2 ${content.color}`}>{content.title}</h1>
          <p className="text-slate-700 text-lg px-4">{content.desc}</p>
        </div>

        <div className="bg-white w-full rounded-3xl p-6 shadow-sm border border-slate-100">
          <h2 className="text-lg font-bold text-slate-800 mb-4">Recommended Actions:</h2>
          <ul className="space-y-3">
            {content.interventions.map((item, idx) => (
              <li key={idx} className="flex items-start space-x-3">
                <div className={`mt-1.5 w-2 h-2 rounded-full ${content.bg} shrink-0`} />
                <span className="text-slate-600 text-sm">{item}</span>
              </li>
            ))}
          </ul>
        </div>

      </div>

      <div className="p-6 bg-white border-t border-slate-100 space-y-3">
        {status !== 'Green' && (
          <button 
            onClick={() => navigate('/dashboard/appointments')}
            className={`w-full flex items-center justify-center space-x-2 py-4 rounded-2xl font-bold text-white transition-all shadow-md ${content.bg}`}
          >
            <CalendarPlus size={20} />
            <span>Book Appointment</span>
          </button>
        )}
        <button 
          onClick={() => navigate('/dashboard')}
          className="w-full py-4 rounded-2xl font-bold text-slate-600 bg-slate-100 hover:bg-slate-200 transition-all"
        >
          Back to Dashboard
        </button>
      </div>
    </div>
  );
}
