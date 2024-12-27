package com.samsung.android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemMdnieManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.display.ISemMdnieManager";

    boolean afpcDataApply() throws RemoteException;

    boolean afpcDataOff() throws RemoteException;

    boolean afpcDataVerify() throws RemoteException;

    boolean afpcDataWrite() throws RemoteException;

    boolean afpcWorkOff() throws RemoteException;

    boolean disableNightMode() throws RemoteException;

    boolean enableNightMode(int i) throws RemoteException;

    int getContentMode() throws RemoteException;

    boolean getNightModeBlock() throws RemoteException;

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

    void setEadMode(int i, int[] iArr) throws RemoteException;

    void setEadModeSub(int i, int[] iArr) throws RemoteException;

    void setExtraDimMode(int i) throws RemoteException;

    void setHighBrightnessMode(int i, int i2, int i3) throws RemoteException;

    boolean setHighDynamicRangeMode(boolean z) throws RemoteException;

    boolean setLightNotificationMode(boolean z) throws RemoteException;

    boolean setNightMode(boolean z, int i) throws RemoteException;

    boolean setNightModeBlock(boolean z) throws RemoteException;

    boolean setNightModeStep(int i) throws RemoteException;

    boolean setScreenMode(int i) throws RemoteException;

    boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    boolean setmDNIeAccessibilityMode(int i, boolean z) throws RemoteException;

    boolean setmDNIeColorBlind(boolean z, int[] iArr) throws RemoteException;

    boolean setmDNIeEmergencyMode(boolean z) throws RemoteException;

    boolean setmDNIeNegative(boolean z) throws RemoteException;

    boolean setmDNIeScreenCurtain(boolean z) throws RemoteException;

    void updateAlwaysOnDisplay(boolean z, int i) throws RemoteException;

    public static class Default implements ISemMdnieManager {
        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getScreenMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getContentMode() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean isScreenModeSupported() throws RemoteException {
            return false;
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
        public int[] getSupportedContentMode() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setScreenMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setContentMode(int mode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setWhiteRGB(int red, int green, int blue, int red_sub, int green_sub, int blue_sub) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setEadMode(int index, int[] arr) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setEadModeSub(int index, int[] arr) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean isNightModeSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean enableNightMode(int opacityIndex) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean disableNightMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightModeBlock(boolean support) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean getNightModeBlock() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightModeStep(int index) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public int getNightModeStep() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setNightMode(boolean enable, int index) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setExtraDimMode(int opacity) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void setHighBrightnessMode(int id, int lux, int index) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setHighDynamicRangeMode(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setColorFadeNightDim(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setColorVision(boolean enable, int color, int level) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeColorBlind(boolean enable, int[] result) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeNegative(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeScreenCurtain(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeEmergencyMode(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setmDNIeAccessibilityMode(int mode, boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean setLightNotificationMode(boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public void updateAlwaysOnDisplay(boolean enable, int aodbrightness) throws RemoteException {
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
        public boolean afpcDataApply() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.display.ISemMdnieManager
        public boolean afpcDataOff() throws RemoteException {
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
    }

    public static abstract class Stub extends Binder implements ISemMdnieManager {
        static final int TRANSACTION_afpcDataApply = 34;
        static final int TRANSACTION_afpcDataOff = 35;
        static final int TRANSACTION_afpcDataVerify = 32;
        static final int TRANSACTION_afpcDataWrite = 33;
        static final int TRANSACTION_afpcWorkOff = 36;
        static final int TRANSACTION_disableNightMode = 14;
        static final int TRANSACTION_enableNightMode = 13;
        static final int TRANSACTION_getContentMode = 2;
        static final int TRANSACTION_getNightModeBlock = 16;
        static final int TRANSACTION_getNightModeStep = 18;
        static final int TRANSACTION_getScreenMode = 1;
        static final int TRANSACTION_getSupportedContentMode = 6;
        static final int TRANSACTION_getSupportedScreenMode = 4;
        static final int TRANSACTION_isContentModeSupported = 5;
        static final int TRANSACTION_isNightModeSupported = 12;
        static final int TRANSACTION_isScreenModeSupported = 3;
        static final int TRANSACTION_setColorFadeNightDim = 23;
        static final int TRANSACTION_setColorVision = 24;
        static final int TRANSACTION_setContentMode = 8;
        static final int TRANSACTION_setEadMode = 10;
        static final int TRANSACTION_setEadModeSub = 11;
        static final int TRANSACTION_setExtraDimMode = 20;
        static final int TRANSACTION_setHighBrightnessMode = 21;
        static final int TRANSACTION_setHighDynamicRangeMode = 22;
        static final int TRANSACTION_setLightNotificationMode = 30;
        static final int TRANSACTION_setNightMode = 19;
        static final int TRANSACTION_setNightModeBlock = 15;
        static final int TRANSACTION_setNightModeStep = 17;
        static final int TRANSACTION_setScreenMode = 7;
        static final int TRANSACTION_setWhiteRGB = 9;
        static final int TRANSACTION_setmDNIeAccessibilityMode = 29;
        static final int TRANSACTION_setmDNIeColorBlind = 25;
        static final int TRANSACTION_setmDNIeEmergencyMode = 28;
        static final int TRANSACTION_setmDNIeNegative = 26;
        static final int TRANSACTION_setmDNIeScreenCurtain = 27;
        static final int TRANSACTION_updateAlwaysOnDisplay = 31;

        public Stub() {
            attachInterface(this, ISemMdnieManager.DESCRIPTOR);
        }

        public static ISemMdnieManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemMdnieManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemMdnieManager)) {
                return (ISemMdnieManager) iin;
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
                    return "getNightModeStep";
                case 19:
                    return "setNightMode";
                case 20:
                    return "setExtraDimMode";
                case 21:
                    return "setHighBrightnessMode";
                case 22:
                    return "setHighDynamicRangeMode";
                case 23:
                    return "setColorFadeNightDim";
                case 24:
                    return "setColorVision";
                case 25:
                    return "setmDNIeColorBlind";
                case 26:
                    return "setmDNIeNegative";
                case 27:
                    return "setmDNIeScreenCurtain";
                case 28:
                    return "setmDNIeEmergencyMode";
                case 29:
                    return "setmDNIeAccessibilityMode";
                case 30:
                    return "setLightNotificationMode";
                case 31:
                    return "updateAlwaysOnDisplay";
                case 32:
                    return "afpcDataVerify";
                case 33:
                    return "afpcDataWrite";
                case 34:
                    return "afpcDataApply";
                case 35:
                    return "afpcDataOff";
                case 36:
                    return "afpcWorkOff";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemMdnieManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemMdnieManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _result = getScreenMode();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _result2 = getContentMode();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    boolean _result3 = isScreenModeSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    int[] _result4 = getSupportedScreenMode();
                    reply.writeNoException();
                    reply.writeIntArray(_result4);
                    return true;
                case 5:
                    boolean _result5 = isContentModeSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    int[] _result6 = getSupportedContentMode();
                    reply.writeNoException();
                    reply.writeIntArray(_result6);
                    return true;
                case 7:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = setScreenMode(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = setContentMode(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    int _arg03 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = setWhiteRGB(_arg03, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 10:
                    int _arg04 = data.readInt();
                    int[] _arg12 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setEadMode(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg05 = data.readInt();
                    int[] _arg13 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setEadModeSub(_arg05, _arg13);
                    reply.writeNoException();
                    return true;
                case 12:
                    boolean _result10 = isNightModeSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 13:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = enableNightMode(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 14:
                    boolean _result12 = disableNightMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 15:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result13 = setNightModeBlock(_arg07);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 16:
                    boolean _result14 = getNightModeBlock();
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 17:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = setNightModeStep(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 18:
                    int _result16 = getNightModeStep();
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 19:
                    boolean _arg09 = data.readBoolean();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result17 = setNightMode(_arg09, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 20:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    setExtraDimMode(_arg010);
                    reply.writeNoException();
                    return true;
                case 21:
                    int _arg011 = data.readInt();
                    int _arg15 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    setHighBrightnessMode(_arg011, _arg15, _arg22);
                    reply.writeNoException();
                    return true;
                case 22:
                    boolean _arg012 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result18 = setHighDynamicRangeMode(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 23:
                    boolean _arg013 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result19 = setColorFadeNightDim(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 24:
                    boolean _arg014 = data.readBoolean();
                    int _arg16 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result20 = setColorVision(_arg014, _arg16, _arg23);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 25:
                    boolean _arg015 = data.readBoolean();
                    int[] _arg17 = data.createIntArray();
                    data.enforceNoDataAvail();
                    boolean _result21 = setmDNIeColorBlind(_arg015, _arg17);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 26:
                    boolean _arg016 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result22 = setmDNIeNegative(_arg016);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 27:
                    boolean _arg017 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result23 = setmDNIeScreenCurtain(_arg017);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 28:
                    boolean _arg018 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result24 = setmDNIeEmergencyMode(_arg018);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 29:
                    int _arg019 = data.readInt();
                    boolean _arg18 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result25 = setmDNIeAccessibilityMode(_arg019, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 30:
                    boolean _arg020 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result26 = setLightNotificationMode(_arg020);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 31:
                    boolean _arg021 = data.readBoolean();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    updateAlwaysOnDisplay(_arg021, _arg19);
                    reply.writeNoException();
                    return true;
                case 32:
                    boolean _result27 = afpcDataVerify();
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    return true;
                case 33:
                    boolean _result28 = afpcDataWrite();
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 34:
                    boolean _result29 = afpcDataApply();
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 35:
                    boolean _result30 = afpcDataOff();
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 36:
                    boolean _result31 = afpcWorkOff();
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemMdnieManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getContentMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isScreenModeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int[] getSupportedScreenMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isContentModeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int[] getSupportedContentMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setScreenMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setContentMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setWhiteRGB(int red, int green, int blue, int red_sub, int green_sub, int blue_sub) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(red);
                    _data.writeInt(green);
                    _data.writeInt(blue);
                    _data.writeInt(red_sub);
                    _data.writeInt(green_sub);
                    _data.writeInt(blue_sub);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setEadMode(int index, int[] arr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(index);
                    _data.writeIntArray(arr);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setEadModeSub(int index, int[] arr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(index);
                    _data.writeIntArray(arr);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean isNightModeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean enableNightMode(int opacityIndex) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(opacityIndex);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean disableNightMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeBlock(boolean support) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(support);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean getNightModeBlock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightModeStep(int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(index);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public int getNightModeStep() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setNightMode(boolean enable, int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(index);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setExtraDimMode(int opacity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(opacity);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void setHighBrightnessMode(int id, int lux, int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(lux);
                    _data.writeInt(index);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setHighDynamicRangeMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setColorFadeNightDim(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setColorVision(boolean enable, int color, int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(color);
                    _data.writeInt(level);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeColorBlind(boolean enable, int[] result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeIntArray(result);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeNegative(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeScreenCurtain(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeEmergencyMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setmDNIeAccessibilityMode(int mode, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean setLightNotificationMode(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public void updateAlwaysOnDisplay(boolean enable, int aodbrightness) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(aodbrightness);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataVerify() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataWrite() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataApply() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcDataOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.display.ISemMdnieManager
            public boolean afpcWorkOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMdnieManager.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }
    }
}
