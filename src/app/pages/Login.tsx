import React, { useState } from 'react';
import { useNavigate } from 'react-router';
import { User, Activity } from 'lucide-react';

export default function Login() {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col h-full bg-white items-center justify-center p-6 font-sans">
      <div className="w-[80px] h-[80px] bg-[#CBFBF1] rounded-[16px] flex items-center justify-center mb-6 shadow-sm">
        <Activity size={40} color="#009689" strokeWidth={3.33} />
      </div>
      
      <h1 className="text-[30px] leading-[36px] font-bold text-[#1D293D] mb-2 text-center">SurgiCare</h1>
      <p className="text-[#62748E] text-[16px] leading-[24px] text-center mb-10">Select your role to continue</p>
      
      <div className="w-full max-w-sm space-y-4">
        <button 
          onClick={() => navigate('/home')}
          className="w-full bg-white border-[1.78px] border-slate-200 rounded-[16px] p-6 flex items-center space-x-4 transition-all"
        >
          <div className="w-12 h-12 bg-[#F0FDFA] rounded-full flex items-center justify-center">
            <User size={24} color="#009689" />
          </div>
          <div className="text-left flex-1">
            <h2 className="font-bold text-[#1D293D] text-[16px] leading-[24px]">I am a Patient</h2>
            <p className="text-[12px] leading-[18px] text-[#62748E] mt-1">Monitor recovery & track symptoms</p>
          </div>
        </button>

        <button 
          onClick={() => navigate('/doctor')}
          className="w-full bg-white border-[1.78px] border-slate-200 rounded-[16px] p-6 flex items-center space-x-4 transition-all"
        >
          <div className="w-12 h-12 bg-[#EFF6FF] rounded-full flex items-center justify-center">
            <Activity size={24} color="#2563EB" />
          </div>
          <div className="text-left flex-1">
            <h2 className="font-bold text-[#1D293D] text-[16px] leading-[24px]">I am a Doctor</h2>
            <p className="text-[12px] leading-[18px] text-[#62748E] mt-1">Review patients & manage care</p>
          </div>
        </button>
      </div>
    </div>
  );
}
