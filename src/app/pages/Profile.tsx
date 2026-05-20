import React, { useState, useEffect } from 'react';
import { useAppContext } from '../store';
import { LogOut, Settings, Clock, User as UserIcon, Moon, Sun, Type, Bell, Eye } from 'lucide-react';
import { useNavigate } from 'react-router';

export default function Profile() {
  const { patient, currentDate, advanceDays, resetData } = useAppContext();
  const navigate = useNavigate();

  // Settings State
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [fontSize, setFontSize] = useState<'Normal' | 'Large' | 'Extra Large'>('Normal');
  const [highContrast, setHighContrast] = useState(false);
  const [notifications, setNotifications] = useState(true);

  // Example side effect for dark mode (applies a class to document, though Tailwind handles it via classes usually, we can simulate)
  useEffect(() => {
    if (isDarkMode) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  }, [isDarkMode]);

  const handleLogout = () => {
    resetData();
    navigate('/');
  };

  const handleTimeTravel = () => {
    advanceDays(1);
  };

  return (
    <div className={`flex flex-col h-full ${isDarkMode ? 'bg-slate-900' : 'bg-slate-50'}`}>
      <div className="p-6 pt-10 pb-6 bg-teal-600 text-white shadow-sm flex items-center justify-between">
        <h1 className="text-2xl font-bold">Profile</h1>
        <button onClick={handleLogout} className="p-2 bg-white/20 rounded-full hover:bg-white/30 transition-colors">
          <LogOut size={20} />
        </button>
      </div>

      <div className="flex-1 overflow-y-auto p-6 space-y-6">
        
        {/* User Info */}
        <div className={`rounded-2xl p-5 shadow-sm border flex items-center space-x-4 ${isDarkMode ? 'bg-slate-800 border-slate-700' : 'bg-white border-slate-100'}`}>
          <div className="w-16 h-16 bg-teal-100 rounded-full flex items-center justify-center text-teal-600">
            <UserIcon size={32} />
          </div>
          <div>
            <h2 className={`text-lg font-bold ${isDarkMode ? 'text-white' : 'text-slate-800'}`}>{patient?.name || 'Jane Doe'}</h2>
            <p className={`text-sm ${isDarkMode ? 'text-slate-400' : 'text-slate-500'}`}>{patient?.age} y/o • {patient?.sex}</p>
          </div>
        </div>

        {/* Quality of Life Settings */}
        <div className={`rounded-2xl p-5 shadow-sm border space-y-4 ${isDarkMode ? 'bg-slate-800 border-slate-700' : 'bg-white border-slate-100'}`}>
          <h3 className={`font-bold border-b pb-2 flex items-center space-x-2 ${isDarkMode ? 'text-white border-slate-700' : 'text-slate-800 border-slate-100'}`}>
            <Settings size={18} className="text-teal-500" />
            <span>App Preferences</span>
          </h3>

          {/* Text Size */}
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <Type size={18} className={isDarkMode ? 'text-slate-400' : 'text-slate-500'} />
              <span className={`text-sm font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-700'}`}>Text Size</span>
            </div>
            <select 
              value={fontSize}
              onChange={(e) => setFontSize(e.target.value as any)}
              className={`text-sm rounded-lg p-2 outline-none border ${
                isDarkMode 
                ? 'bg-slate-700 text-white border-slate-600' 
                : 'bg-slate-50 text-slate-700 border-slate-200'
              }`}
            >
              <option>Normal</option>
              <option>Large</option>
              <option>Extra Large</option>
            </select>
          </div>

          {/* Dark Mode */}
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              {isDarkMode ? <Moon size={18} className="text-slate-400" /> : <Sun size={18} className="text-slate-500" />}
              <span className={`text-sm font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-700'}`}>Dark Mode</span>
            </div>
            <button 
              onClick={() => setIsDarkMode(!isDarkMode)}
              className={`w-12 h-6 rounded-full relative transition-colors ${isDarkMode ? 'bg-teal-500' : 'bg-slate-300'}`}
            >
              <div className={`w-4 h-4 rounded-full bg-white absolute top-1 transition-transform ${isDarkMode ? 'left-7' : 'left-1'}`} />
            </button>
          </div>

          {/* High Contrast */}
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <Eye size={18} className={isDarkMode ? 'text-slate-400' : 'text-slate-500'} />
              <span className={`text-sm font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-700'}`}>High Contrast</span>
            </div>
            <button 
              onClick={() => setHighContrast(!highContrast)}
              className={`w-12 h-6 rounded-full relative transition-colors ${highContrast ? 'bg-teal-500' : 'bg-slate-300'}`}
            >
              <div className={`w-4 h-4 rounded-full bg-white absolute top-1 transition-transform ${highContrast ? 'left-7' : 'left-1'}`} />
            </button>
          </div>

          {/* Notifications */}
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <Bell size={18} className={isDarkMode ? 'text-slate-400' : 'text-slate-500'} />
              <span className={`text-sm font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-700'}`}>Daily Reminders</span>
            </div>
            <button 
              onClick={() => setNotifications(!notifications)}
              className={`w-12 h-6 rounded-full relative transition-colors ${notifications ? 'bg-teal-500' : 'bg-slate-300'}`}
            >
              <div className={`w-4 h-4 rounded-full bg-white absolute top-1 transition-transform ${notifications ? 'left-7' : 'left-1'}`} />
            </button>
          </div>

        </div>

        {/* Medical Info */}
        <div className={`rounded-2xl p-5 shadow-sm border space-y-3 ${isDarkMode ? 'bg-slate-800 border-slate-700' : 'bg-white border-slate-100'}`}>
          <h3 className={`font-bold border-b pb-2 ${isDarkMode ? 'text-white border-slate-700' : 'text-slate-800 border-slate-100'}`}>Medical Details</h3>
          
          <div>
            <p className="text-xs text-slate-500">Surgery</p>
            <p className={`font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-800'}`}>{patient?.surgeryType}</p>
          </div>
          <div>
            <p className="text-xs text-slate-500">Hospital</p>
            <p className={`font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-800'}`}>{patient?.hospital}</p>
          </div>
          <div>
            <p className="text-xs text-slate-500">Surgery Date</p>
            <p className={`font-medium ${isDarkMode ? 'text-slate-200' : 'text-slate-800'}`}>{patient?.surgeryDate}</p>
          </div>
        </div>

        {/* Settings / Developer tools */}
        <div className={`rounded-2xl p-5 shadow-sm border space-y-4 ${isDarkMode ? 'bg-slate-800 border-slate-700' : 'bg-white border-slate-100'}`}>
          <div className="flex items-center space-x-2 text-amber-600 mb-2">
            <Clock size={20} />
            <h3 className={`font-bold border-b pb-1 flex-1 ${isDarkMode ? 'border-amber-900' : 'border-amber-100'}`}>App Testing Tools</h3>
          </div>

          <div className={`p-3 rounded-xl border ${isDarkMode ? 'bg-slate-900 border-slate-700' : 'bg-slate-50 border-slate-200'}`}>
            <p className={`text-xs mb-2 ${isDarkMode ? 'text-slate-400' : 'text-slate-500'}`}>
              Current App Date: <span className={`font-bold ${isDarkMode ? 'text-slate-200' : 'text-slate-800'}`}>{currentDate.toDateString()}</span>
            </p>
            <button 
              onClick={handleTimeTravel}
              className="w-full flex items-center justify-center space-x-2 bg-amber-500 hover:bg-amber-600 text-white py-2 rounded-lg text-sm font-bold shadow-sm transition-colors"
            >
              <Clock size={16} />
              <span>Time Travel (+1 Day)</span>
            </button>
            <p className={`text-[10px] mt-2 text-center ${isDarkMode ? 'text-slate-500' : 'text-slate-400'}`}>Use this to test daily assessments and reminders.</p>
          </div>
        </div>

      </div>
    </div>
  );
}
