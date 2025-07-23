package com.samsung.android.displaysolution;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemDisplaySolutionManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.displaysolution.ISemDisplaySolutionManager";

    public static class Default implements ISemDisplaySolutionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public float getAlphaMaskLevel(float f, float f2, float f3) throws RemoteException {
            return 0.0f;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getAutoCurrentLimitOffModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public int getBlfAdaptiveCurrentIndex() throws RemoteException {
            return 0;
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
        public float getFingerPrintBacklightValue(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getGalleryModeEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public String getOnPixelRatioValueForPMS() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public int getVideoEnhancerSettingState(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean getVideoModeEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean isBlueLightFilterScheduledTime() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public boolean isMdnieScenarioControlServiceEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitOffMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitStateChangedInt(int i) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onAutoCurrentLimitStateChangedWithBrightness(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onBurnInPreventionDisabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void onDetailVeiwStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setAutoCurrentLimitOffModeEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setBlfEnableTimeBySchedule(boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setCameraModeEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setDouAppModeEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setEadIndexOffset(int i) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setEyeComfortWeightingFactor(float f) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setGalleryModeEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setHighDynamicRangeMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setIRCompensationMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setMdnieScenarioControlServiceEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setMultipleScreenBrightness(String str) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setMultipleScreenBrightnessValueForHDR(float f) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setOnPixelRatioValueForPMS(String str) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setRenderIntentValue(int i) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setScreenBrightnessForPreview(int i) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setSleepPatternBLF(String str, long j, long j2, float f) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setVideoEnhancerSettingState(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void setVideoModeEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void updateAutoBrightnessLux(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void updateGlutMode(String str) throws RemoteException {
        }

        @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
        public void updateQdcmMode(String str) throws RemoteException {
        }
    }

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

    void setRenderIntentValue(int i) throws RemoteException;

    void setScreenBrightnessForPreview(int i) throws RemoteException;

    void setSleepPatternBLF(String str, long j, long j2, float f) throws RemoteException;

    void setVideoEnhancerSettingState(String str, int i) throws RemoteException;

    void setVideoModeEnable(boolean z) throws RemoteException;

    void updateAutoBrightnessLux(int i, int i2) throws RemoteException;

    void updateGlutMode(String str) throws RemoteException;

    void updateQdcmMode(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemDisplaySolutionManager {
        static final int TRANSACTION_getAlphaMaskLevel = 9;
        static final int TRANSACTION_getAutoCurrentLimitOffModeEnabled = 5;
        static final int TRANSACTION_getBlfAdaptiveCurrentIndex = 39;
        static final int TRANSACTION_getCameraModeEnable = 3;
        static final int TRANSACTION_getDouAppModeEnable = 4;
        static final int TRANSACTION_getFingerPrintBacklightValue = 8;
        static final int TRANSACTION_getGalleryModeEnable = 2;
        static final int TRANSACTION_getOnPixelRatioValueForPMS = 6;
        static final int TRANSACTION_getVideoEnhancerSettingState = 7;
        static final int TRANSACTION_getVideoModeEnable = 1;
        static final int TRANSACTION_isBlueLightFilterScheduledTime = 36;
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
        static final int TRANSACTION_setEadIndexOffset = 37;
        static final int TRANSACTION_setEyeComfortWeightingFactor = 30;
        static final int TRANSACTION_setGalleryModeEnable = 21;
        static final int TRANSACTION_setHighDynamicRangeMode = 11;
        static final int TRANSACTION_setIRCompensationMode = 13;
        static final int TRANSACTION_setMdnieScenarioControlServiceEnable = 25;
        static final int TRANSACTION_setMultipleScreenBrightness = 27;
        static final int TRANSACTION_setMultipleScreenBrightnessValueForHDR = 29;
        static final int TRANSACTION_setOnPixelRatioValueForPMS = 28;
        static final int TRANSACTION_setRenderIntentValue = 38;
        static final int TRANSACTION_setScreenBrightnessForPreview = 26;
        static final int TRANSACTION_setSleepPatternBLF = 32;
        static final int TRANSACTION_setVideoEnhancerSettingState = 31;
        static final int TRANSACTION_setVideoModeEnable = 20;
        static final int TRANSACTION_updateAutoBrightnessLux = 12;
        static final int TRANSACTION_updateGlutMode = 34;
        static final int TRANSACTION_updateQdcmMode = 35;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }

        public Stub() {
            attachInterface(this, ISemDisplaySolutionManager.DESCRIPTOR);
        }

        public static ISemDisplaySolutionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemDisplaySolutionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemDisplaySolutionManager)) {
                return (ISemDisplaySolutionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "updateGlutMode";
                case 35:
                    return "updateQdcmMode";
                case 36:
                    return "isBlueLightFilterScheduledTime";
                case 37:
                    return "setEadIndexOffset";
                case 38:
                    return "setRenderIntentValue";
                case 39:
                    return "getBlfAdaptiveCurrentIndex";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemDisplaySolutionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemDisplaySolutionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean videoModeEnable = getVideoModeEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(videoModeEnable);
                    return true;
                case 2:
                    boolean galleryModeEnable = getGalleryModeEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(galleryModeEnable);
                    return true;
                case 3:
                    boolean cameraModeEnable = getCameraModeEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cameraModeEnable);
                    return true;
                case 4:
                    boolean douAppModeEnable = getDouAppModeEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(douAppModeEnable);
                    return true;
                case 5:
                    boolean autoCurrentLimitOffModeEnabled = getAutoCurrentLimitOffModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoCurrentLimitOffModeEnabled);
                    return true;
                case 6:
                    String onPixelRatioValueForPMS = getOnPixelRatioValueForPMS();
                    parcel2.writeNoException();
                    parcel2.writeString(onPixelRatioValueForPMS);
                    return true;
                case 7:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int videoEnhancerSettingState = getVideoEnhancerSettingState(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(videoEnhancerSettingState);
                    return true;
                case 8:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float fingerPrintBacklightValue = getFingerPrintBacklightValue(readInt);
                    parcel2.writeNoException();
                    parcel2.writeFloat(fingerPrintBacklightValue);
                    return true;
                case 9:
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    float alphaMaskLevel = getAlphaMaskLevel(readFloat, readFloat2, readFloat3);
                    parcel2.writeNoException();
                    parcel2.writeFloat(alphaMaskLevel);
                    return true;
                case 10:
                    boolean isMdnieScenarioControlServiceEnabled = isMdnieScenarioControlServiceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMdnieScenarioControlServiceEnabled);
                    return true;
                case 11:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHighDynamicRangeMode(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAutoBrightnessLux(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIRCompensationMode(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDetailVeiwStateChanged(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitStateChanged(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitStateChangedWithBrightness(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitStateChangedInt(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitOffMode(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBurnInPreventionDisabled(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoModeEnable(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setGalleryModeEnable(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCameraModeEnable(readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDouAppModeEnable(readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoCurrentLimitOffModeEnabled(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMdnieScenarioControlServiceEnable(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenBrightnessForPreview(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMultipleScreenBrightness(readString2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOnPixelRatioValueForPMS(readString3);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    float readFloat4 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setMultipleScreenBrightnessValueForHDR(readFloat4);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    float readFloat5 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setEyeComfortWeightingFactor(readFloat5);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVideoEnhancerSettingState(readString4, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String readString5 = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    float readFloat6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setSleepPatternBLF(readString5, readLong, readLong2, readFloat6);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean readBoolean14 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBlfEnableTimeBySchedule(readBoolean14, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateGlutMode(readString6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateQdcmMode(readString7);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    boolean isBlueLightFilterScheduledTime = isBlueLightFilterScheduledTime();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBlueLightFilterScheduledTime);
                    return true;
                case 37:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEadIndexOffset(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRenderIntentValue(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int blfAdaptiveCurrentIndex = getBlfAdaptiveCurrentIndex();
                    parcel2.writeNoException();
                    parcel2.writeInt(blfAdaptiveCurrentIndex);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemDisplaySolutionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getGalleryModeEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getCameraModeEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getDouAppModeEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getAutoCurrentLimitOffModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public String getOnPixelRatioValueForPMS() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public int getVideoEnhancerSettingState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public float getFingerPrintBacklightValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public float getAlphaMaskLevel(float f, float f2, float f3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean isMdnieScenarioControlServiceEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setHighDynamicRangeMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateAutoBrightnessLux(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setIRCompensationMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onDetailVeiwStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChangedWithBrightness(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChangedInt(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitOffMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onBurnInPreventionDisabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setVideoModeEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setGalleryModeEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setCameraModeEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setDouAppModeEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setAutoCurrentLimitOffModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMdnieScenarioControlServiceEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setScreenBrightnessForPreview(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMultipleScreenBrightness(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setOnPixelRatioValueForPMS(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMultipleScreenBrightnessValueForHDR(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setEyeComfortWeightingFactor(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setVideoEnhancerSettingState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setSleepPatternBLF(String str, long j, long j2, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeFloat(f);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setBlfEnableTimeBySchedule(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateGlutMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateQdcmMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean isBlueLightFilterScheduledTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setEadIndexOffset(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setRenderIntentValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public int getBlfAdaptiveCurrentIndex() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
