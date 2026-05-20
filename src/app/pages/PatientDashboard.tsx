import React from 'react';
import { useNavigate } from 'react-router';
import { useAppContext } from '../store';
import { Camera, ClipboardCheck, Calendar, Pill, BookOpen, Activity, AlertCircle, CheckCircle2 } from 'lucide-react';
import { ImageWithFallback } from '../components/figma/ImageWithFallback';

export default function PatientDashboard() {
  const navigate = useNavigate();
  const { patient, assessments, currentDate } = useAppContext();

  // Find if today's assessment is done
  const todayDateString = currentDate.toISOString().split('T')[0];
  const todayAssessment = assessments.find(a => a.date.startsWith(todayDateString));

  const menuItems = [
    { 
      title: 'Daily Assessment', 
      desc: 'Photo & Symptoms', 
      icon: Camera, 
      color: 'bg-blue-100 text-blue-600',
      path: '/dashboard/upload',
      highlight: !todayAssessment
    },
    { 
      title: 'Healing Progress', 
      desc: 'View timeline', 
      icon: Activity, 
      color: 'bg-emerald-100 text-emerald-600',
      path: '/dashboard/progress'
    },
    { 
      title: 'Medications', 
      desc: 'Reminders & Streaks', 
      icon: Pill, 
      color: 'bg-purple-100 text-purple-600',
      path: '/dashboard/medications'
    },
    { 
      title: 'Appointments', 
      desc: 'Schedule visit', 
      icon: Calendar, 
      color: 'bg-amber-100 text-amber-600',
      path: '/dashboard/appointments'
    },
    { 
      title: 'Educational Tips', 
      desc: 'Care guides', 
      icon: BookOpen, 
      color: 'bg-rose-100 text-rose-600',
      path: '#' // Placeholder
    },
  ];

  return (
    <div className="flex flex-col h-full bg-slate-50">
      {/* Header */}
      <div className="bg-teal-600 text-white pt-10 pb-6 px-6 rounded-b-[32px] shadow-sm">
        <div className="flex justify-between items-center mb-6">
          <div>
            <p className="text-teal-100 text-sm">Welcome back,</p>
            <h1 className="text-2xl font-bold">{patient?.name || 'Patient'}</h1>
          </div>
          <button onClick={() => navigate('/dashboard/profile')} className="w-12 h-12 bg-white rounded-full flex items-center justify-center p-1 shadow-inner hover:bg-slate-50 transition-colors">
             <ImageWithFallback src="figma:asset/image-1.png" alt="Logo" className="w-full h-full object-contain" />
          </button>
        </div>

        {/* Status Card */}
        <div className="bg-white text-slate-800 rounded-2xl p-4 shadow-md flex items-center justify-between">
          <div>
            <p className="text-xs font-semibold text-slate-500 mb-1">TODAY'S STATUS</p>
            {todayAssessment ? (
              <div className="flex items-center space-x-2">
                <CheckCircle2 size={20} className="text-emerald-500" />
                <span className="font-bold text-emerald-600">Assessment Complete</span>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                <AlertCircle size={20} className="text-amber-500" />
                <span className="font-bold text-amber-600">Pending Assessment</span>
              </div>
            )}
          </div>
          {!todayAssessment && (
            <button 
              onClick={() => navigate('/dashboard/upload')}
              className="bg-teal-600 text-white text-sm font-semibold py-2 px-4 rounded-lg hover:bg-teal-700"
            >
              Start
            </button>
          )}
        </div>
      </div>

      {/* Reminders / Notifications */}
      <div className="px-6 mt-6">
        <h2 className="text-lg font-bold text-slate-800 mb-3">Daily Reminders</h2>
        <div className="space-y-3">
          {!todayAssessment && (
            <div className="bg-blue-50 border border-blue-100 p-3 rounded-xl flex items-start space-x-3">
              <Camera size={20} className="text-blue-500 mt-0.5" />
              <div>
                <p className="text-sm font-medium text-slate-800">Upload today's wound photo.</p>
                <p className="text-xs text-slate-500 mt-0.5">Helps track your healing progress.</p>
              </div>
            </div>
          )}
          <div className="bg-purple-50 border border-purple-100 p-3 rounded-xl flex items-start space-x-3">
            <Pill size={20} className="text-purple-500 mt-0.5" />
            <div>
              <p className="text-sm font-medium text-slate-800">Take your prescribed medications.</p>
              <p className="text-xs text-slate-500 mt-0.5">Don't break your streak!</p>
            </div>
          </div>
        </div>
      </div>

      {/* Main Menu Grid */}
      <div className="px-6 mt-8">
        <h2 className="text-lg font-bold text-slate-800 mb-4">Quick Access</h2>
        <div className="grid grid-cols-2 gap-4">
          {menuItems.map((item, idx) => (
            <button 
              key={idx}
              onClick={() => navigate(item.path)}
              className={`p-4 rounded-2xl bg-white border border-slate-100 shadow-sm flex flex-col items-start text-left transition-transform active:scale-95 ${item.highlight ? 'ring-2 ring-teal-500' : ''}`}
            >
              <div className={`w-10 h-10 rounded-full flex items-center justify-center mb-3 ${item.color}`}>
                <item.icon size={20} />
              </div>
              <h3 className="font-semibold text-slate-800 text-sm">{item.title}</h3>
              <p className="text-xs text-slate-500 mt-1">{item.desc}</p>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
