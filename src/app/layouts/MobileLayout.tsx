import React from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router';
import { Home, ClipboardList, Pill, User, Stethoscope } from 'lucide-react';

export default function MobileLayout() {
  const location = useLocation();
  const navigate = useNavigate();

  // Hide nav on login, doctor, and some subpages if needed
  const isAuthPage = location.pathname === '/';
  const isDoctorPage = location.pathname.startsWith('/doctor');
  
  const showBottomNav = !isAuthPage && !isDoctorPage;

  const navItems = [
    { label: 'Home', path: '/dashboard', icon: Home },
    { label: 'Progress', path: '/dashboard/progress', icon: ClipboardList },
    { label: 'Meds', path: '/dashboard/medications', icon: Pill },
    { label: 'Profile', path: '/dashboard/profile', icon: User },
  ];

  return (
    <div className="flex justify-center items-center min-h-screen bg-slate-200">
      <div className="w-full max-w-md h-[100dvh] sm:h-[850px] sm:rounded-[40px] sm:border-[8px] border-slate-900 bg-white overflow-hidden shadow-2xl relative flex flex-col font-sans">
        
        {/* Main Content Area */}
        <div className={`flex-1 overflow-y-auto ${showBottomNav ? 'pb-20' : ''}`}>
          <Outlet />
        </div>

        {/* Bottom Navigation */}
        {showBottomNav && (
          <div className="absolute bottom-0 w-full bg-white border-t border-slate-200 px-6 py-3 flex justify-between items-center z-50 rounded-b-[32px]">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive = location.pathname === item.path;
              return (
                <button
                  key={item.path}
                  onClick={() => navigate(item.path)}
                  className={`flex flex-col items-center justify-center space-y-1 w-16 ${
                    isActive ? 'text-teal-600' : 'text-slate-400 hover:text-slate-600'
                  }`}
                >
                  <Icon size={24} strokeWidth={isActive ? 2.5 : 2} />
                  <span className="text-[10px] font-medium">{item.label}</span>
                </button>
              );
            })}
          </div>
        )}
      </div>
      
      {/* Doctor Mode Toggle (Outside the phone shell for testing) */}
      <button 
        onClick={() => navigate(isDoctorPage ? '/dashboard' : '/doctor')}
        className="fixed bottom-4 right-4 bg-slate-800 text-white p-3 rounded-full shadow-lg flex items-center space-x-2 z-50"
      >
        <Stethoscope size={20} />
        <span className="text-sm font-semibold">{isDoctorPage ? 'Patient View' : 'Doctor View'}</span>
      </button>
    </div>
  );
}
