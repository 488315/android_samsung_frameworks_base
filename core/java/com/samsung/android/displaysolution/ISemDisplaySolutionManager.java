package com.samsung.android.displaysolution;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemDisplaySolutionManager extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.displaysolution.ISemDisplaySolutionManager";

    float getAlphaMaskLevel(float f, float f2, float f3) throws RemoteException;

    boolean getAutoCurrentLimitOffModeEnabled() throws RemoteException;

    int getBlfAdaptiveCurrentIndex() throws RemoteException;

    boolean getCameraModeEnable() throws RemoteException;

    boolean getDouAppModeEnable() throws RemoteException;

    float getFingerPrintBacklightValue(int i) throws RemoteException;

    boolean getGalleryModeEnable() throws RemoteException;

    String getOnPixelRatioValueForPMS() throws RemoteException;

    int getVideoEnhancerSettingState(String str) throws RemoteException;

    boolean getVideoModeEnable() throws RemoteException;

    boolean isBlueLightFilterScheduledTime() throws RemoteException;

    boolean isMdnieScenarioControlServiceEnabled() throws RemoteException;

    void onAutoCurrentLimitOffMode(boolean z) throws RemoteException;

    void onAutoCurrentLimitStateChanged(boolean z) throws RemoteException;

    void onAutoCurrentLimitStateChangedInt(int i) throws RemoteException;

    void onAutoCurrentLimitStateChangedWithBrightness(boolean z) throws RemoteException;

    void onBurnInPreventionDisabled(boolean z) throws RemoteException;

    void onDetailVeiwStateChanged(boolean z) throws RemoteException;

    void setAutoCurrentLimitOffModeEnabled(boolean z) throws RemoteException;

    void setBlfEnableTimeBySchedule(boolean z, int i) throws RemoteException;

    void setCameraModeEnable(boolean z) throws RemoteException;

    void setDouAppModeEnable(boolean z) throws RemoteException;

    void setEadIndexOffset(int i) throws RemoteException;

    void setEyeComfortWeightingFactor(float f) throws RemoteException;

    void setGalleryModeEnable(boolean z) throws RemoteException;

    void setHighDynamicRangeMode(boolean z) throws RemoteException;

    void setIRCompensationMode(boolean z) throws RemoteException;

    void setMdnieScenarioControlServiceEnable(boolean z) throws RemoteException;

    void setMultipleScreenBrightness(String str) throws RemoteException;

    void setMultipleScreenBrightnessValueForHDR(float f) throws RemoteException;

    void setOnPixelRatioValueForPMS(String str) throws RemoteException;

    void setScreenBrightnessForPreview(int i) throws RemoteException;

    void setSleepPatternBLF(String str, long j, long j2, float f) throws RemoteException;

    void setVideoEnhancerSettingState(String str, int i) throws RemoteException;

    void setVideoModeEnable(boolean z) throws RemoteException;

    void setmDNIeModeState(String str) throws RemoteException;

    void updateAutoBrightnessLux(int i, int i2) throws RemoteException;

    public static class Default implements ISemDisplaySolutionManager {
        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getVideoModeEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getGalleryModeEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getCameraModeEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getDouAppModeEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getAutoCurrentLimitOffModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public String getOnPixelRatioValueForPMS() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public int getVideoEnhancerSettingState(String name) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public float getFingerPrintBacklightValue(int brightnessNits) throws RemoteException {
            return 0.0f;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public float getAlphaMaskLevel(
                float CurrentPlatformBrightnessValue, float FingerPrintPlatformValue, float br_ctrl)
                throws RemoteException {
            return 0.0f;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean isMdnieScenarioControlServiceEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setHighDynamicRangeMode(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void updateAutoBrightnessLux(int id, int lux) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setIRCompensationMode(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onDetailVeiwStateChanged(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitStateChanged(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitStateChangedWithBrightness(boolean enable)
                throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitStateChangedInt(int value) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitOffMode(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onBurnInPreventionDisabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setVideoModeEnable(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setGalleryModeEnable(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setCameraModeEnable(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setDouAppModeEnable(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setAutoCurrentLimitOffModeEnabled(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setMdnieScenarioControlServiceEnable(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setScreenBrightnessForPreview(int settingValue) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setMultipleScreenBrightness(String name) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setOnPixelRatioValueForPMS(String value) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setMultipleScreenBrightnessValueForHDR(float scalefactorValueHDR)
                throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setEyeComfortWeightingFactor(float scaleValue) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setVideoEnhancerSettingState(String name, int state) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setSleepPatternBLF(
                String mWeekType, long mBedtime, long mWakeupTime, float mConfidence)
                throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setBlfEnableTimeBySchedule(boolean enable, int index) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setmDNIeModeState(String mode) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean isBlueLightFilterScheduledTime() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setEadIndexOffset(int offset) throws RemoteException {}

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public int getBlfAdaptiveCurrentIndex() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemDisplaySolutionManager {
        static final int TRANSACTION_getAlphaMaskLevel = 9;
        static final int TRANSACTION_getAutoCurrentLimitOffModeEnabled = 5;
        static final int TRANSACTION_getBlfAdaptiveCurrentIndex = 37;
        static final int TRANSACTION_getCameraModeEnable = 3;
        static final int TRANSACTION_getDouAppModeEnable = 4;
        static final int TRANSACTION_getFingerPrintBacklightValue = 8;
        static final int TRANSACTION_getGalleryModeEnable = 2;
        static final int TRANSACTION_getOnPixelRatioValueForPMS = 6;
        static final int TRANSACTION_getVideoEnhancerSettingState = 7;
        static final int TRANSACTION_getVideoModeEnable = 1;
        static final int TRANSACTION_isBlueLightFilterScheduledTime = 35;
        static final int TRANSACTION_isMdnieScenarioControlServiceEnabled = 10;
        static final int TRANSACTION_onAutoCurrentLimitOffMode = 18;
        static final int TRANSACTION_onAutoCurrentLimitStateChanged = 15;
        static final int TRANSACTION_onAutoCurrentLimitStateChangedInt = 17;
        static final int TRANSACTION_onAutoCurrentLimitStateChangedWithBrightness = 16;
        static final int TRANSACTION_onBurnInPreventionDisabled = 19;
        static final int TRANSACTION_onDetailVeiwStateChanged = 14;
        static final int TRANSACTION_setAutoCurrentLimitOffModeEnabled = 24;
        static final int TRANSACTION_setBlfEnableTimeBySchedule = 33;
        static final int TRANSACTION_setCameraModeEnable = 22;
        static final int TRANSACTION_setDouAppModeEnable = 23;
        static final int TRANSACTION_setEadIndexOffset = 36;
        static final int TRANSACTION_setEyeComfortWeightingFactor = 30;
        static final int TRANSACTION_setGalleryModeEnable = 21;
        static final int TRANSACTION_setHighDynamicRangeMode = 11;
        static final int TRANSACTION_setIRCompensationMode = 13;
        static final int TRANSACTION_setMdnieScenarioControlServiceEnable = 25;
        static final int TRANSACTION_setMultipleScreenBrightness = 27;
        static final int TRANSACTION_setMultipleScreenBrightnessValueForHDR = 29;
        static final int TRANSACTION_setOnPixelRatioValueForPMS = 28;
        static final int TRANSACTION_setScreenBrightnessForPreview = 26;
        static final int TRANSACTION_setSleepPatternBLF = 32;
        static final int TRANSACTION_setVideoEnhancerSettingState = 31;
        static final int TRANSACTION_setVideoModeEnable = 20;
        static final int TRANSACTION_setmDNIeModeState = 34;
        static final int TRANSACTION_updateAutoBrightnessLux = 12;

        public Stub() {
            attachInterface(this, ISemDisplaySolutionManager.DESCRIPTOR);
        }

        public static ISemDisplaySolutionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemDisplaySolutionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemDisplaySolutionManager)) {
                return (ISemDisplaySolutionManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getVideoModeEnable";
                case 2:
                    return "getGalleryModeEnable";
                case 3:
                    return "getCameraModeEnable";
                case 4:
                    return "getDouAppModeEnable";
                case 5:
                    return "getAutoCurrentLimitOffModeEnabled";
                case 6:
                    return "getOnPixelRatioValueForPMS";
                case 7:
                    return "getVideoEnhancerSettingState";
                case 8:
                    return "getFingerPrintBacklightValue";
                case 9:
                    return "getAlphaMaskLevel";
                case 10:
                    return "isMdnieScenarioControlServiceEnabled";
                case 11:
                    return "setHighDynamicRangeMode";
                case 12:
                    return "updateAutoBrightnessLux";
                case 13:
                    return "setIRCompensationMode";
                case 14:
                    return "onDetailVeiwStateChanged";
                case 15:
                    return "onAutoCurrentLimitStateChanged";
                case 16:
                    return "onAutoCurrentLimitStateChangedWithBrightness";
                case 17:
                    return "onAutoCurrentLimitStateChangedInt";
                case 18:
                    return "onAutoCurrentLimitOffMode";
                case 19:
                    return "onBurnInPreventionDisabled";
                case 20:
                    return "setVideoModeEnable";
                case 21:
                    return "setGalleryModeEnable";
                case 22:
                    return "setCameraModeEnable";
                case 23:
                    return "setDouAppModeEnable";
                case 24:
                    return "setAutoCurrentLimitOffModeEnabled";
                case 25:
                    return "setMdnieScenarioControlServiceEnable";
                case 26:
                    return "setScreenBrightnessForPreview";
                case 27:
                    return "setMultipleScreenBrightness";
                case 28:
                    return "setOnPixelRatioValueForPMS";
                case 29:
                    return "setMultipleScreenBrightnessValueForHDR";
                case 30:
                    return "setEyeComfortWeightingFactor";
                case 31:
                    return "setVideoEnhancerSettingState";
                case 32:
                    return "setSleepPatternBLF";
                case 33:
                    return "setBlfEnableTimeBySchedule";
                case 34:
                    return "setmDNIeModeState";
                case 35:
                    return "isBlueLightFilterScheduledTime";
                case 36:
                    return "setEadIndexOffset";
                case 37:
                    return "getBlfAdaptiveCurrentIndex";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemDisplaySolutionManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemDisplaySolutionManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = getVideoModeEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    boolean _result2 = getGalleryModeEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    boolean _result3 = getCameraModeEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    boolean _result4 = getDouAppModeEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    boolean _result5 = getAutoCurrentLimitOffModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    String _result6 = getOnPixelRatioValueForPMS();
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 7:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    int _result7 = getVideoEnhancerSettingState(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 8:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    float _result8 = getFingerPrintBacklightValue(_arg02);
                    reply.writeNoException();
                    reply.writeFloat(_result8);
                    return true;
                case 9:
                    float _arg03 = data.readFloat();
                    float _arg1 = data.readFloat();
                    float _arg2 = data.readFloat();
                    data.enforceNoDataAvail();
                    float _result9 = getAlphaMaskLevel(_arg03, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeFloat(_result9);
                    return true;
                case 10:
                    boolean _result10 = isMdnieScenarioControlServiceEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setHighDynamicRangeMode(_arg04);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg05 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    updateAutoBrightnessLux(_arg05, _arg12);
                    reply.writeNoException();
                    return true;
                case 13:
                    boolean _arg06 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setIRCompensationMode(_arg06);
                    reply.writeNoException();
                    return true;
                case 14:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onDetailVeiwStateChanged(_arg07);
                    reply.writeNoException();
                    return true;
                case 15:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onAutoCurrentLimitStateChanged(_arg08);
                    reply.writeNoException();
                    return true;
                case 16:
                    boolean _arg09 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onAutoCurrentLimitStateChangedWithBrightness(_arg09);
                    reply.writeNoException();
                    return true;
                case 17:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    onAutoCurrentLimitStateChangedInt(_arg010);
                    reply.writeNoException();
                    return true;
                case 18:
                    boolean _arg011 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onAutoCurrentLimitOffMode(_arg011);
                    reply.writeNoException();
                    return true;
                case 19:
                    boolean _arg012 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onBurnInPreventionDisabled(_arg012);
                    reply.writeNoException();
                    return true;
                case 20:
                    boolean _arg013 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setVideoModeEnable(_arg013);
                    reply.writeNoException();
                    return true;
                case 21:
                    boolean _arg014 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setGalleryModeEnable(_arg014);
                    reply.writeNoException();
                    return true;
                case 22:
                    boolean _arg015 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCameraModeEnable(_arg015);
                    reply.writeNoException();
                    return true;
                case 23:
                    boolean _arg016 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDouAppModeEnable(_arg016);
                    reply.writeNoException();
                    return true;
                case 24:
                    boolean _arg017 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAutoCurrentLimitOffModeEnabled(_arg017);
                    reply.writeNoException();
                    return true;
                case 25:
                    boolean _arg018 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMdnieScenarioControlServiceEnable(_arg018);
                    reply.writeNoException();
                    return true;
                case 26:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    setScreenBrightnessForPreview(_arg019);
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg020 = data.readString();
                    data.enforceNoDataAvail();
                    setMultipleScreenBrightness(_arg020);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg021 = data.readString();
                    data.enforceNoDataAvail();
                    setOnPixelRatioValueForPMS(_arg021);
                    reply.writeNoException();
                    return true;
                case 29:
                    float _arg022 = data.readFloat();
                    data.enforceNoDataAvail();
                    setMultipleScreenBrightnessValueForHDR(_arg022);
                    reply.writeNoException();
                    return true;
                case 30:
                    float _arg023 = data.readFloat();
                    data.enforceNoDataAvail();
                    setEyeComfortWeightingFactor(_arg023);
                    reply.writeNoException();
                    return true;
                case 31:
                    String _arg024 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    setVideoEnhancerSettingState(_arg024, _arg13);
                    reply.writeNoException();
                    return true;
                case 32:
                    String _arg025 = data.readString();
                    long _arg14 = data.readLong();
                    long _arg22 = data.readLong();
                    float _arg3 = data.readFloat();
                    data.enforceNoDataAvail();
                    setSleepPatternBLF(_arg025, _arg14, _arg22, _arg3);
                    reply.writeNoException();
                    return true;
                case 33:
                    boolean _arg026 = data.readBoolean();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    setBlfEnableTimeBySchedule(_arg026, _arg15);
                    reply.writeNoException();
                    return true;
                case 34:
                    String _arg027 = data.readString();
                    data.enforceNoDataAvail();
                    setmDNIeModeState(_arg027);
                    reply.writeNoException();
                    return true;
                case 35:
                    boolean _result11 = isBlueLightFilterScheduledTime();
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 36:
                    int _arg028 = data.readInt();
                    data.enforceNoDataAvail();
                    setEadIndexOffset(_arg028);
                    reply.writeNoException();
                    return true;
                case 37:
                    int _result12 = getBlfAdaptiveCurrentIndex();
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemDisplaySolutionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemDisplaySolutionManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getVideoModeEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getGalleryModeEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getCameraModeEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getDouAppModeEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getAutoCurrentLimitOffModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public String getOnPixelRatioValueForPMS() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public int getVideoEnhancerSettingState(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public float getFingerPrintBacklightValue(int brightnessNits) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(brightnessNits);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public float getAlphaMaskLevel(
                    float CurrentPlatformBrightnessValue,
                    float FingerPrintPlatformValue,
                    float br_ctrl)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeFloat(CurrentPlatformBrightnessValue);
                    _data.writeFloat(FingerPrintPlatformValue);
                    _data.writeFloat(br_ctrl);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean isMdnieScenarioControlServiceEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setHighDynamicRangeMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateAutoBrightnessLux(int id, int lux) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(lux);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setIRCompensationMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onDetailVeiwStateChanged(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChanged(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChangedWithBrightness(boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChangedInt(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitOffMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onBurnInPreventionDisabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setVideoModeEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setGalleryModeEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setCameraModeEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setDouAppModeEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setAutoCurrentLimitOffModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMdnieScenarioControlServiceEnable(boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setScreenBrightnessForPreview(int settingValue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(settingValue);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMultipleScreenBrightness(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setOnPixelRatioValueForPMS(String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(value);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMultipleScreenBrightnessValueForHDR(float scalefactorValueHDR)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeFloat(scalefactorValueHDR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setEyeComfortWeightingFactor(float scaleValue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeFloat(scaleValue);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setVideoEnhancerSettingState(String name, int state)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(state);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setSleepPatternBLF(
                    String mWeekType, long mBedtime, long mWakeupTime, float mConfidence)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(mWeekType);
                    _data.writeLong(mBedtime);
                    _data.writeLong(mWakeupTime);
                    _data.writeFloat(mConfidence);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setBlfEnableTimeBySchedule(boolean enable, int index)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(index);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setmDNIeModeState(String mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeString(mode);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean isBlueLightFilterScheduledTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setEadIndexOffset(int offset) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    _data.writeInt(offset);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public int getBlfAdaptiveCurrentIndex() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 36;
        }
    }
}
