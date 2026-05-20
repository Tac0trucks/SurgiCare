import React, { useState } from 'react';
import { useAppContext } from '../store';
import { Search, AlertTriangle, CheckCircle, Clock, ChevronRight, Check, MessageSquare, Calendar, X } from 'lucide-react';
import { useNavigate } from 'react-router';

// Extended type for doctor dashboard patients
interface DoctorPatient {
  id: string;
  name: string;
  status: 'Red' | 'Yellow' | 'Green';
  issue: string;
  time: string;
  actionNeeded: boolean;
  appointmentRequested: boolean;
  appointmentStatus: 'Pending' | 'Approved' | 'None';
  recommendations: string[];
}

export default function DoctorDashboard() {
  const { assessments, patient } = useAppContext();
  const navigate = useNavigate();
  
  // Combine app state assessments (for the registered patient) with some mock data
  const currentUserStatus = assessments.length > 0 ? assessments[assessments.length-1].status : 'Green';
  const currentUserIssue = assessments.length > 0 ? assessments[assessments.length-1].symptoms.join(', ') || 'Post-op Recovery' : 'Post-op Day 3';
  
  const [patientsList, setPatientsList] = useState<DoctorPatient[]>([
    { 
      id: '1', 
      name: patient?.name || 'Jane Doe', 
      status: currentUserStatus, 
      issue: currentUserIssue, 
      time: '10 mins ago', 
      actionNeeded: currentUserStatus !== 'Green',
      appointmentRequested: currentUserStatus === 'Red',
      appointmentStatus: currentUserStatus === 'Red' ? 'Pending' : 'None',
      recommendations: []
    },
    { id: '2', name: 'Mark Smith', status: 'Red', issue: 'High fever, severe pain', time: '1 hour ago', actionNeeded: true, appointmentRequested: true, appointmentStatus: 'Pending', recommendations: [] },
    { id: '3', name: 'Sarah Connor', status: 'Yellow', issue: 'Increased redness around incision', time: '3 hours ago', actionNeeded: true, appointmentRequested: false, appointmentStatus: 'None', recommendations: [] },
    { id: '4', name: 'John Wick', status: 'Green', issue: 'Normal healing', time: '5 hours ago', actionNeeded: false, appointmentRequested: false, appointmentStatus: 'None', recommendations: [] },
  ]);

  const [selectedPatientId, setSelectedPatientId] = useState<string | null>(null);
  const [recommendationText, setRecommendationText] = useState('');
  const [showRecInput, setShowRecInput] = useState(false);

  const selectedPatient = patientsList.find(p => p.id === selectedPatientId);

  const handleApproveAppointment = () => {
    if (!selectedPatientId) return;
    setPatientsList(prev => prev.map(p => {
      if (p.id === selectedPatientId) {
        return { ...p, appointmentStatus: 'Approved', actionNeeded: p.status === 'Yellow' };
      }
      return p;
    }));
  };

  const handleSendRecommendation = () => {
    if (!selectedPatientId || !recommendationText.trim()) return;
    setPatientsList(prev => prev.map(p => {
      if (p.id === selectedPatientId) {
        return { 
          ...p, 
          recommendations: [...p.recommendations, recommendationText],
          actionNeeded: p.appointmentStatus === 'Pending' // Only keep action needed if appointment is still pending
        };
      }
      return p;
    }));
    setRecommendationText('');
    setShowRecInput(false);
  };

  const handlePromptAppointment = () => {
    if (!selectedPatientId) return;
    setPatientsList(prev => prev.map(p => {
      if (p.id === selectedPatientId) {
        return { ...p, appointmentRequested: true, appointmentStatus: 'Pending' };
      }
      return p;
    }));
  };

  const handleLogout = () => {
    navigate('/');
  };

  if (selectedPatient) {
    return (
      <div className="flex flex-col h-full bg-slate-50">
        <div className="p-4 bg-slate-800 text-white flex items-center justify-between">
          <div className="flex items-center">
            <button onClick={() => setSelectedPatientId(null)} className="mr-4 p-2 bg-slate-700 rounded-lg">
              <ChevronRight className="rotate-180" size={20} />
            </button>
            <h1 className="text-lg font-bold">Patient Details</h1>
          </div>
        </div>
        
        <div className="flex-1 overflow-y-auto p-6 space-y-6">
          <div className="flex justify-between items-start">
            <h2 className="text-2xl font-bold text-slate-800">{selectedPatient.name}</h2>
            <div className={`px-4 py-1.5 rounded-full text-white font-bold text-sm ${
              selectedPatient.status === 'Red' ? 'bg-rose-500' : selectedPatient.status === 'Yellow' ? 'bg-amber-500' : 'bg-emerald-500'
            }`}>
              {selectedPatient.status}
            </div>
          </div>

          <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
            <h3 className="font-bold text-slate-800 mb-2 text-sm uppercase tracking-wider">Reported Issue</h3>
            <p className="text-slate-600">{selectedPatient.issue}</p>
          </div>

          {selectedPatient.appointmentStatus !== 'None' && (
            <div className={`p-5 rounded-2xl border ${selectedPatient.appointmentStatus === 'Approved' ? 'bg-emerald-50 border-emerald-200' : 'bg-blue-50 border-blue-200'}`}>
              <div className="flex items-center space-x-3 mb-2">
                <Calendar size={20} className={selectedPatient.appointmentStatus === 'Approved' ? 'text-emerald-500' : 'text-blue-500'} />
                <h3 className={`font-bold ${selectedPatient.appointmentStatus === 'Approved' ? 'text-emerald-800' : 'text-blue-800'}`}>Appointment Request</h3>
              </div>
              <p className={`text-sm ${selectedPatient.appointmentStatus === 'Approved' ? 'text-emerald-600' : 'text-blue-600'}`}>
                Status: <strong>{selectedPatient.appointmentStatus}</strong>
              </p>
            </div>
          )}

          {selectedPatient.recommendations.length > 0 && (
            <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
              <h3 className="font-bold text-slate-800 mb-3 text-sm uppercase tracking-wider">Sent Recommendations</h3>
              <ul className="space-y-3">
                {selectedPatient.recommendations.map((rec, idx) => (
                  <li key={idx} className="bg-slate-50 p-3 rounded-lg text-sm text-slate-700 border border-slate-100 flex items-start space-x-2">
                    <CheckCircle size={16} className="text-teal-500 mt-0.5 shrink-0" />
                    <span>{rec}</span>
                  </li>
                ))}
              </ul>
            </div>
          )}

          <div className="pt-4 border-t border-slate-200 space-y-3">
            <h3 className="font-bold text-slate-800 mb-2">Doctor Actions</h3>
            
            {selectedPatient.appointmentStatus === 'Pending' && (
              <button 
                onClick={handleApproveAppointment}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3.5 rounded-xl font-bold flex items-center justify-center space-x-2 transition-colors shadow-sm"
              >
                <Check size={20} />
                <span>Approve Appointment</span>
              </button>
            )}

            {selectedPatient.appointmentStatus === 'None' && selectedPatient.status !== 'Green' && (
              <button 
                onClick={handlePromptAppointment}
                className="w-full bg-amber-500 hover:bg-amber-600 text-white py-3.5 rounded-xl font-bold flex items-center justify-center space-x-2 transition-colors shadow-sm"
              >
                <Calendar size={20} />
                <span>Prompt to Schedule Appointment</span>
              </button>
            )}

            {!showRecInput ? (
              <button 
                onClick={() => setShowRecInput(true)}
                className="w-full bg-white hover:bg-slate-50 border-2 border-slate-200 text-slate-700 py-3.5 rounded-xl font-bold flex items-center justify-center space-x-2 transition-colors"
              >
                <MessageSquare size={20} />
                <span>Send Recommendation</span>
              </button>
            ) : (
              <div className="bg-slate-100 p-4 rounded-xl border border-slate-200 space-y-3">
                <textarea 
                  value={recommendationText}
                  onChange={(e) => setRecommendationText(e.target.value)}
                  placeholder="Type your recommendation here..."
                  className="w-full rounded-lg border-none p-3 text-sm resize-none h-24 focus:ring-2 focus:ring-blue-500"
                />
                <div className="flex space-x-2">
                  <button 
                    onClick={handleSendRecommendation}
                    className="flex-1 bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-bold text-sm"
                  >
                    Send
                  </button>
                  <button 
                    onClick={() => { setShowRecInput(false); setRecommendationText(''); }}
                    className="p-2 bg-white rounded-lg text-slate-500 hover:bg-slate-200 border border-slate-200"
                  >
                    <X size={20} />
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    );
  }

  const urgentCount = patientsList.filter(p => p.status === 'Red').length;
  const reviewCount = patientsList.filter(p => p.status === 'Yellow').length;
  const normalCount = patientsList.filter(p => p.status === 'Green').length;

  return (
    <div className="flex flex-col h-full bg-slate-100">
      {/* Header */}
      <div className="bg-slate-800 text-white pt-10 pb-6 px-6 shadow-md rounded-b-3xl">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h1 className="text-2xl font-bold">Provider Dashboard</h1>
            <p className="text-slate-300 text-sm mt-1">Review flagged patients</p>
          </div>
          <button onClick={handleLogout} className="p-2 bg-white/10 rounded-full hover:bg-white/20 transition-colors">
            <X size={20} />
          </button>
        </div>
        
        <div className="mt-2 relative">
          <Search className="absolute left-3 top-2.5 text-slate-400" size={20} />
          <input 
            type="text" 
            placeholder="Search patients..." 
            className="w-full bg-slate-700/50 border border-slate-600 rounded-xl py-2.5 pl-10 pr-4 text-white placeholder-slate-400 focus:ring-2 focus:ring-blue-500 focus:outline-none"
          />
        </div>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-3 gap-3 p-6 pb-2">
        <div className="bg-white rounded-2xl p-4 flex flex-col items-center justify-center shadow-sm">
          <span className="text-2xl font-black text-rose-500">{urgentCount}</span>
          <span className="text-[10px] font-bold text-slate-400 uppercase tracking-widest mt-1">Urgent</span>
        </div>
        <div className="bg-white rounded-2xl p-4 flex flex-col items-center justify-center shadow-sm">
          <span className="text-2xl font-black text-amber-500">{reviewCount}</span>
          <span className="text-[10px] font-bold text-slate-400 uppercase tracking-widest mt-1">Review</span>
        </div>
        <div className="bg-white rounded-2xl p-4 flex flex-col items-center justify-center shadow-sm">
          <span className="text-2xl font-black text-emerald-500">{normalCount}</span>
          <span className="text-[10px] font-bold text-slate-400 uppercase tracking-widest mt-1">Normal</span>
        </div>
      </div>

      {/* Patient List */}
      <div className="flex-1 overflow-y-auto px-6 pb-6 space-y-4 pt-4">
        <h2 className="font-bold text-slate-800 text-sm uppercase tracking-wider">Patients</h2>
        
        <div className="space-y-3">
          {patientsList.sort((a, b) => {
            const priority = { 'Red': 3, 'Yellow': 2, 'Green': 1 };
            return priority[b.status] - priority[a.status];
          }).map((p) => (
            <button 
              key={p.id}
              onClick={() => setSelectedPatientId(p.id)}
              className="w-full bg-white rounded-2xl p-4 flex items-center shadow-sm hover:ring-2 hover:ring-blue-400 transition-all text-left relative overflow-hidden"
            >
              {p.actionNeeded && (
                <div className="absolute top-0 left-0 w-1 h-full bg-blue-500" />
              )}
              
              <div className="flex-1 pl-2">
                <div className="flex items-center space-x-2">
                  <h3 className="font-bold text-slate-800">{p.name}</h3>
                  {p.status === 'Red' && <AlertTriangle size={14} className="text-rose-500" />}
                  {p.status === 'Yellow' && <Clock size={14} className="text-amber-500" />}
                  {p.status === 'Green' && <CheckCircle size={14} className="text-emerald-500" />}
                </div>
                <p className="text-sm text-slate-600 truncate mt-1">{p.issue}</p>
                <div className="flex items-center space-x-3 mt-2">
                  <span className="text-[10px] text-slate-400 bg-slate-50 px-2 py-0.5 rounded-md border border-slate-100">{p.time}</span>
                  {p.appointmentStatus === 'Pending' && (
                    <span className="text-[10px] font-bold text-blue-600 bg-blue-50 px-2 py-0.5 rounded-md">Appt Pending</span>
                  )}
                  {p.recommendations.length > 0 && (
                    <span className="text-[10px] font-bold text-teal-600 bg-teal-50 px-2 py-0.5 rounded-md">{p.recommendations.length} Msg(s)</span>
                  )}
                </div>
              </div>
              
              <ChevronRight size={20} className="text-slate-300 ml-2 shrink-0" />
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
