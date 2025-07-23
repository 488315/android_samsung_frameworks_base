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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemMdnieManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemMdnieManager)) {
                return (ISemMdnieManager) queryLocalInterface;
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
                    boolean isScreenModeSupported = isScreenModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenModeSupported);
                    return true;
                case 4:
                    int[] supportedScreenMode = getSupportedScreenMode();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedScreenMode);
                    return true;
                case 5:
                    boolean isContentModeSupported = isContentModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isContentModeSupported);
                    return true;
                case 6:
                    int[] supportedContentMode = getSupportedContentMode();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedContentMode);
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean screenMode2 = setScreenMode(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenMode2);
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean contentMode2 = setContentMode(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contentMode2);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean whiteRGB = setWhiteRGB(readInt3, readInt4, readInt5, readInt6, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(whiteRGB);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEadMode(readInt9, readInt10, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEadModeSub(readInt11, readInt12, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean isNightModeSupported = isNightModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNightModeSupported);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableNightMode = enableNightMode(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableNightMode);
                    return true;
                case 14:
                    boolean disableNightMode = disableNightMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableNightMode);
                    return true;
                case 15:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeBlock = setNightModeBlock(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeBlock);
                    return true;
                case 16:
                    boolean nightModeBlock2 = getNightModeBlock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeBlock2);
                    return true;
                case 17:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightModeStep = setNightModeStep(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeStep);
                    return true;
                case 18:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightModeCct = setNightModeCct(readInt15);
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
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightMode = setNightMode(readBoolean2, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightMode);
                    return true;
                case 22:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setExtraDimMode(readInt17);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHighBrightnessMode(readInt18, readInt19, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean highDynamicRangeMode = setHighDynamicRangeMode(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highDynamicRangeMode);
                    return true;
                case 25:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean colorFadeNightDim = setColorFadeNightDim(readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(colorFadeNightDim);
                    return true;
                case 26:
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean colorVision = setColorVision(readBoolean5, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(colorVision);
                    return true;
                case 27:
                    boolean readBoolean6 = parcel.readBoolean();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean z = setmDNIeColorBlind(readBoolean6, createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 28:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z2 = setmDNIeNegative(readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z2);
                    return true;
                case 29:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z3 = setmDNIeScreenCurtain(readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z3);
                    return true;
                case 30:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z4 = setmDNIeEmergencyMode(readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z4);
                    return true;
                case 31:
                    int readInt23 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean z5 = setmDNIeAccessibilityMode(readInt23, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z5);
                    return true;
                case 32:
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean lightNotificationMode = setLightNotificationMode(readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lightNotificationMode);
                    return true;
                case 33:
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAlwaysOnDisplay(readBoolean12, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    boolean afpcDataVerify = afpcDataVerify();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(afpcDataVerify);
                    return true;
                case 35:
                    boolean afpcDataWrite = afpcDataWrite();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(afpcDataWrite);
                    return true;
                case 36:
                    boolean afpcDataApply = afpcDataApply();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(afpcDataApply);
                    return true;
                case 37:
                    boolean afpcDataOff = afpcDataOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(afpcDataOff);
                    return true;
                case 38:
                    boolean afpcWorkOff = afpcWorkOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(afpcWorkOff);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getContentMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isScreenModeSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int[] getSupportedScreenMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isContentModeSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int[] getSupportedContentMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setScreenMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setContentMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setEadMode(int i, int i2, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setEadModeSub(int i, int i2, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isNightModeSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean enableNightMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean disableNightMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeBlock(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean getNightModeBlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeStep(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeCct(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getNightModeStep() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getNightModeCct() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightMode(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setExtraDimMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setHighBrightnessMode(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setHighDynamicRangeMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setColorFadeNightDim(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setColorVision(boolean z, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeColorBlind(boolean z, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeNegative(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeScreenCurtain(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeEmergencyMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeAccessibilityMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setLightNotificationMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void updateAlwaysOnDisplay(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataVerify() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataWrite() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataApply() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcWorkOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
