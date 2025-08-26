package com.samsung.android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemMdnieManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.display.ISemMdnieManager";

    public static class Default implements ISemMdnieManager {
        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean afpcDataApply() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean afpcDataOff() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean afpcDataVerify() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean afpcDataWrite() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean afpcWorkOff() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean disableNightMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean enableNightMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getContentMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean getNightModeBlock() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getNightModeCct() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getNightModeStep() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getScreenMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int[] getSupportedContentMode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int[] getSupportedScreenMode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean isContentModeSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean isNightModeSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean isScreenModeSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setColorFadeNightDim(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setColorVision(boolean z, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setContentMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setEadMode(int i, int i2, int[] iArr) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setEadModeSub(int i, int i2, int[] iArr) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setExtraDimMode(int i) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setHighBrightnessMode(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setHighDynamicRangeMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setLightNotificationMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightMode(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightModeBlock(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightModeCct(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightModeStep(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setScreenMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeAccessibilityMode(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeColorBlind(boolean z, int[] iArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeEmergencyMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeNegative(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeScreenCurtain(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void updateAlwaysOnDisplay(boolean z, int i) throws RemoteException {
        }
    }

    boolean afpcDataApply() throws RemoteException;

    boolean afpcDataOff() throws RemoteException;

    boolean afpcDataVerify() throws RemoteException;

    boolean afpcDataWrite() throws RemoteException;

    boolean afpcWorkOff() throws RemoteException;

    boolean disableNightMode() throws RemoteException;

    boolean enableNightMode(int i) throws RemoteException;

    int getContentMode() throws RemoteException;

    boolean getNightModeBlock() throws RemoteException;

    int getNightModeCct() throws RemoteException;

    int getNightModeStep() throws RemoteException;

    int getScreenMode() throws RemoteException;

    int[] getSupportedContentMode() throws RemoteException;

    int[] getSupportedScreenMode() throws RemoteException;

    boolean isContentModeSupported() throws RemoteException;

    boolean isNightModeSupported() throws RemoteException;

    boolean isScreenModeSupported() throws RemoteException;

    boolean setColorFadeNightDim(boolean z) throws RemoteException;

    boolean setColorVision(boolean z, int i, int i2) throws RemoteException;

    boolean setContentMode(int i) throws RemoteException;

    void setEadMode(int i, int i2, int[] iArr) throws RemoteException;

    void setEadModeSub(int i, int i2, int[] iArr) throws RemoteException;

    void setExtraDimMode(int i) throws RemoteException;

    void setHighBrightnessMode(int i, int i2, int i3) throws RemoteException;

    boolean setHighDynamicRangeMode(boolean z) throws RemoteException;

    boolean setLightNotificationMode(boolean z) throws RemoteException;

    boolean setNightMode(boolean z, int i) throws RemoteException;

    boolean setNightModeBlock(boolean z) throws RemoteException;

    boolean setNightModeCct(int i) throws RemoteException;

    boolean setNightModeStep(int i) throws RemoteException;

    boolean setScreenMode(int i) throws RemoteException;

    boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    boolean setmDNIeAccessibilityMode(int i, boolean z) throws RemoteException;

    boolean setmDNIeColorBlind(boolean z, int[] iArr) throws RemoteException;

    boolean setmDNIeEmergencyMode(boolean z) throws RemoteException;

    boolean setmDNIeNegative(boolean z) throws RemoteException;

    boolean setmDNIeScreenCurtain(boolean z) throws RemoteException;

    void updateAlwaysOnDisplay(boolean z, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemMdnieManager {
        static final int TRANSACTION_afpcDataApply = 36;
        static final int TRANSACTION_afpcDataOff = 37;
        static final int TRANSACTION_afpcDataVerify = 34;
        static final int TRANSACTION_afpcDataWrite = 35;
        static final int TRANSACTION_afpcWorkOff = 38;
        static final int TRANSACTION_disableNightMode = 14;
        static final int TRANSACTION_enableNightMode = 13;
        static final int TRANSACTION_getContentMode = 2;
        static final int TRANSACTION_getNightModeBlock = 16;
        static final int TRANSACTION_getNightModeCct = 20;
        static final int TRANSACTION_getNightModeStep = 19;
        static final int TRANSACTION_getScreenMode = 1;
        static final int TRANSACTION_getSupportedContentMode = 6;
        static final int TRANSACTION_getSupportedScreenMode = 4;
        static final int TRANSACTION_isContentModeSupported = 5;
        static final int TRANSACTION_isNightModeSupported = 12;
        static final int TRANSACTION_isScreenModeSupported = 3;
        static final int TRANSACTION_setColorFadeNightDim = 25;
        static final int TRANSACTION_setColorVision = 26;
        static final int TRANSACTION_setContentMode = 8;
        static final int TRANSACTION_setEadMode = 10;
        static final int TRANSACTION_setEadModeSub = 11;
        static final int TRANSACTION_setExtraDimMode = 22;
        static final int TRANSACTION_setHighBrightnessMode = 23;
        static final int TRANSACTION_setHighDynamicRangeMode = 24;
        static final int TRANSACTION_setLightNotificationMode = 32;
        static final int TRANSACTION_setNightMode = 21;
        static final int TRANSACTION_setNightModeBlock = 15;
        static final int TRANSACTION_setNightModeCct = 18;
        static final int TRANSACTION_setNightModeStep = 17;
        static final int TRANSACTION_setScreenMode = 7;
        static final int TRANSACTION_setWhiteRGB = 9;
        static final int TRANSACTION_setmDNIeAccessibilityMode = 31;
        static final int TRANSACTION_setmDNIeColorBlind = 27;
        static final int TRANSACTION_setmDNIeEmergencyMode = 30;
        static final int TRANSACTION_setmDNIeNegative = 28;
        static final int TRANSACTION_setmDNIeScreenCurtain = 29;
        static final int TRANSACTION_updateAlwaysOnDisplay = 33;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 37;
        }

        public Stub() {
            attachInterface(this, ISemMdnieManager.DESCRIPTOR);
        }

        public static ISemMdnieManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemMdnieManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemMdnieManager)) {
                return (ISemMdnieManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getScreenMode";
                case 2:
                    return "getContentMode";
                case 3:
                    return "isScreenModeSupported";
                case 4:
                    return "getSupportedScreenMode";
                case 5:
                    return "isContentModeSupported";
                case 6:
                    return "getSupportedContentMode";
                case 7:
                    return "setScreenMode";
                case 8:
                    return "setContentMode";
                case 9:
                    return "setWhiteRGB";
                case 10:
                    return "setEadMode";
                case 11:
                    return "setEadModeSub";
                case 12:
                    return "isNightModeSupported";
                case 13:
                    return "enableNightMode";
                case 14:
                    return "disableNightMode";
                case 15:
                    return "setNightModeBlock";
                case 16:
                    return "getNightModeBlock";
                case 17:
                    return "setNightModeStep";
                case 18:
                    return "setNightModeCct";
                case 19:
                    return "getNightModeStep";
                case 20:
                    return "getNightModeCct";
                case 21:
                    return "setNightMode";
                case 22:
                    return "setExtraDimMode";
                case 23:
                    return "setHighBrightnessMode";
                case 24:
                    return "setHighDynamicRangeMode";
                case 25:
                    return "setColorFadeNightDim";
                case 26:
                    return "setColorVision";
                case 27:
                    return "setmDNIeColorBlind";
                case 28:
                    return "setmDNIeNegative";
                case 29:
                    return "setmDNIeScreenCurtain";
                case 30:
                    return "setmDNIeEmergencyMode";
                case 31:
                    return "setmDNIeAccessibilityMode";
                case 32:
                    return "setLightNotificationMode";
                case 33:
                    return "updateAlwaysOnDisplay";
                case 34:
                    return "afpcDataVerify";
                case 35:
                    return "afpcDataWrite";
                case 36:
                    return "afpcDataApply";
                case 37:
                    return "afpcDataOff";
                case 38:
                    return "afpcWorkOff";
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
                parcel.enforceInterface(ISemMdnieManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemMdnieManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int screenMode = getScreenMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(screenMode);
                    return true;
                case 2:
                    int contentMode = getContentMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(contentMode);
                    return true;
                case 3:
                    boolean zIsScreenModeSupported = isScreenModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenModeSupported);
                    return true;
                case 4:
                    int[] supportedScreenMode = getSupportedScreenMode();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedScreenMode);
                    return true;
                case 5:
                    boolean zIsContentModeSupported = isContentModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsContentModeSupported);
                    return true;
                case 6:
                    int[] supportedContentMode = getSupportedContentMode();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedContentMode);
                    return true;
                case 7:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean screenMode2 = setScreenMode(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenMode2);
                    return true;
                case 8:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean contentMode2 = setContentMode(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contentMode2);
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean whiteRGB = setWhiteRGB(i5, i6, i7, i8, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(whiteRGB);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEadMode(i11, i12, iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEadModeSub(i13, i14, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean zIsNightModeSupported = isNightModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNightModeSupported);
                    return true;
                case 13:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnableNightMode = enableNightMode(i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableNightMode);
                    return true;
                case 14:
                    boolean zDisableNightMode = disableNightMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableNightMode);
                    return true;
                case 15:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeBlock = setNightModeBlock(z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeBlock);
                    return true;
                case 16:
                    boolean nightModeBlock2 = getNightModeBlock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeBlock2);
                    return true;
                case 17:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightModeStep = setNightModeStep(i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeStep);
                    return true;
                case 18:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightModeCct = setNightModeCct(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeCct);
                    return true;
                case 19:
                    int nightModeStep2 = getNightModeStep();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightModeStep2);
                    return true;
                case 20:
                    int nightModeCct2 = getNightModeCct();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightModeCct2);
                    return true;
                case 21:
                    boolean z2 = parcel.readBoolean();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightMode = setNightMode(z2, i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightMode);
                    return true;
                case 22:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setExtraDimMode(i19);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHighBrightnessMode(i20, i21, i22);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean highDynamicRangeMode = setHighDynamicRangeMode(z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highDynamicRangeMode);
                    return true;
                case 25:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean colorFadeNightDim = setColorFadeNightDim(z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(colorFadeNightDim);
                    return true;
                case 26:
                    boolean z5 = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean colorVision = setColorVision(z5, i23, i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(colorVision);
                    return true;
                case 27:
                    boolean z6 = parcel.readBoolean();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean z7 = setmDNIeColorBlind(z6, iArrCreateIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z7);
                    return true;
                case 28:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z9 = setmDNIeNegative(z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z9);
                    return true;
                case 29:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z11 = setmDNIeScreenCurtain(z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z11);
                    return true;
                case 30:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z13 = setmDNIeEmergencyMode(z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z13);
                    return true;
                case 31:
                    int i25 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z15 = setmDNIeAccessibilityMode(i25, z14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z15);
                    return true;
                case 32:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean lightNotificationMode = setLightNotificationMode(z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lightNotificationMode);
                    return true;
                case 33:
                    boolean z17 = parcel.readBoolean();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAlwaysOnDisplay(z17, i26);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    boolean zAfpcDataVerify = afpcDataVerify();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAfpcDataVerify);
                    return true;
                case 35:
                    boolean zAfpcDataWrite = afpcDataWrite();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAfpcDataWrite);
                    return true;
                case 36:
                    boolean zAfpcDataApply = afpcDataApply();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAfpcDataApply);
                    return true;
                case 37:
                    boolean zAfpcDataOff = afpcDataOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAfpcDataOff);
                    return true;
                case 38:
                    boolean zAfpcWorkOff = afpcWorkOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAfpcWorkOff);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemMdnieManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMdnieManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getScreenMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getContentMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isScreenModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int[] getSupportedScreenMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isContentModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int[] getSupportedContentMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setScreenMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setContentMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setEadMode(int i, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setEadModeSub(int i, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isNightModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean enableNightMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean disableNightMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeBlock(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean getNightModeBlock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeStep(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeCct(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getNightModeStep() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getNightModeCct() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightMode(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setExtraDimMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setHighBrightnessMode(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setHighDynamicRangeMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setColorFadeNightDim(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setColorVision(boolean z, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeColorBlind(boolean z, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeNegative(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeScreenCurtain(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeEmergencyMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeAccessibilityMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setLightNotificationMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void updateAlwaysOnDisplay(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataVerify() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataWrite() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataApply() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataOff() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcWorkOff() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
