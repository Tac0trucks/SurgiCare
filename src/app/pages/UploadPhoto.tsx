import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router';
import { Camera, Image as ImageIcon, ArrowRight } from 'lucide-react';
import { useAppContext } from '../store';

export default function UploadPhoto() {
  const navigate = useNavigate();
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [preview, setPreview] = useState<string | null>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result as string);
        // We temporarily store it in session or pass via state to the next screen.
        // For simplicity, we can pass it via React Router state
      };
      reader.readAsDataURL(file);
    }
  };

  const handleNext = () => {
    if (preview) {
      navigate('/dashboard/assessment', { state: { image: preview } });
    }
  };

  return (
    <div className="flex flex-col h-full bg-white">
      <div className="p-6 pt-10 pb-4 border-b border-slate-100 flex items-center shadow-sm z-10 bg-white">
        <button onClick={() => navigate(-1)} className="text-teal-600 font-medium">Cancel</button>
        <h1 className="flex-1 text-center font-bold text-slate-800 text-lg">Daily Assessment</h1>
        <div className="w-12"></div> {/* Spacer for centering */}
      </div>

      <div className="flex-1 overflow-y-auto p-6 flex flex-col">
        <div className="mb-6">
          <p className="text-xs font-bold text-teal-600 tracking-wider mb-1">STEP 1 OF 2</p>
          <h2 className="text-2xl font-bold text-slate-800">Upload Photo</h2>
          <p className="text-slate-500 mt-2 text-sm">Please take a clear picture of your abdominal incision in good lighting.</p>
        </div>

        <div className="flex-1 flex flex-col items-center justify-center">
          {preview ? (
            <div className="relative w-full aspect-square bg-slate-100 rounded-3xl overflow-hidden border-4 border-slate-200">
              <img src={preview} alt="Wound preview" className="w-full h-full object-cover" />
              <button 
                onClick={() => setPreview(null)}
                className="absolute top-4 right-4 bg-black/50 text-white rounded-full p-2 hover:bg-black/70"
              >
                Retake
              </button>
            </div>
          ) : (
            <div 
              onClick={() => fileInputRef.current?.click()}
              className="w-full aspect-square bg-slate-50 border-2 border-dashed border-slate-300 rounded-3xl flex flex-col items-center justify-center cursor-pointer hover:bg-slate-100 transition-colors"
            >
              <div className="w-16 h-16 bg-white rounded-full shadow-sm flex items-center justify-center text-teal-500 mb-4">
                <Camera size={32} />
              </div>
              <p className="font-semibold text-slate-700">Tap to Take Photo</p>
              <p className="text-xs text-slate-400 mt-1">or select from gallery</p>
            </div>
          )}
          
          <input 
            type="file" 
            accept="image/*" 
            className="hidden" 
            ref={fileInputRef}
            onChange={handleFileChange}
          />
        </div>

        <div className="mt-8">
          <button 
            disabled={!preview}
            onClick={handleNext}
            className={`w-full flex items-center justify-center space-x-2 py-4 rounded-2xl font-bold text-white transition-all shadow-md ${
              preview ? 'bg-teal-600 hover:bg-teal-700' : 'bg-slate-300 cursor-not-allowed'
            }`}
          >
            <span>Continue to Symptoms</span>
            <ArrowRight size={20} />
          </button>
        </div>
      </div>
    </div>
  );
}
