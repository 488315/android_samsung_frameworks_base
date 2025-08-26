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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemDisplaySolutionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemDisplaySolutionManager)) {
                return (ISemDisplaySolutionManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int videoEnhancerSettingState = getVideoEnhancerSettingState(string);
                    parcel2.writeNoException();
                    parcel2.writeInt(videoEnhancerSettingState);
                    return true;
                case 8:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float fingerPrintBacklightValue = getFingerPrintBacklightValue(i3);
                    parcel2.writeNoException();
                    parcel2.writeFloat(fingerPrintBacklightValue);
                    return true;
                case 9:
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    float f3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    float alphaMaskLevel = getAlphaMaskLevel(f, f2, f3);
                    parcel2.writeNoException();
                    parcel2.writeFloat(alphaMaskLevel);
                    return true;
                case 10:
                    boolean zIsMdnieScenarioControlServiceEnabled = isMdnieScenarioControlServiceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMdnieScenarioControlServiceEnabled);
                    return true;
                case 11:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHighDynamicRangeMode(z);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAutoBrightnessLux(i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIRCompensationMode(z2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDetailVeiwStateChanged(z3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitStateChanged(z4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitStateChangedWithBrightness(z5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitStateChangedInt(i6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAutoCurrentLimitOffMode(z6);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBurnInPreventionDisabled(z7);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoModeEnable(z8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setGalleryModeEnable(z9);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCameraModeEnable(z10);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDouAppModeEnable(z11);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoCurrentLimitOffModeEnabled(z12);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMdnieScenarioControlServiceEnable(z13);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenBrightnessForPreview(i7);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMultipleScreenBrightness(string2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOnPixelRatioValueForPMS(string3);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    float f4 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setMultipleScreenBrightnessValueForHDR(f4);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    float f5 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setEyeComfortWeightingFactor(f5);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String string4 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVideoEnhancerSettingState(string4, i8);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String string5 = parcel.readString();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    float f6 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setSleepPatternBLF(string5, j, j2, f6);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean z14 = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBlfEnableTimeBySchedule(z14, i9);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateGlutMode(string6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateQdcmMode(string7);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    boolean zIsBlueLightFilterScheduledTime = isBlueLightFilterScheduledTime();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBlueLightFilterScheduledTime);
                    return true;
                case 37:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEadIndexOffset(i10);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRenderIntentValue(i11);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getGalleryModeEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getCameraModeEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getDouAppModeEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean getAutoCurrentLimitOffModeEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public String getOnPixelRatioValueForPMS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public int getVideoEnhancerSettingState(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public float getFingerPrintBacklightValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public float getAlphaMaskLevel(float f, float f2, float f3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean isMdnieScenarioControlServiceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setHighDynamicRangeMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateAutoBrightnessLux(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setIRCompensationMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onDetailVeiwStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChangedWithBrightness(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitStateChangedInt(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onAutoCurrentLimitOffMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void onBurnInPreventionDisabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setVideoModeEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setGalleryModeEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setCameraModeEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setDouAppModeEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setAutoCurrentLimitOffModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMdnieScenarioControlServiceEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setScreenBrightnessForPreview(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMultipleScreenBrightness(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setOnPixelRatioValueForPMS(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setMultipleScreenBrightnessValueForHDR(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setEyeComfortWeightingFactor(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setVideoEnhancerSettingState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setSleepPatternBLF(String str, long j, long j2, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setBlfEnableTimeBySchedule(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateGlutMode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void updateQdcmMode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public boolean isBlueLightFilterScheduledTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setEadIndexOffset(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public void setRenderIntentValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displaysolution.ISemDisplaySolutionManager
            public int getBlfAdaptiveCurrentIndex() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
