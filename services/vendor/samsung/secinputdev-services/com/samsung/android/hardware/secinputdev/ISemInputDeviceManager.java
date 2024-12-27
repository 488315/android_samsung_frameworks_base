package com.samsung.android.hardware.secinputdev;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISemInputDeviceManager extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.hardware.secinputdev.ISemInputDeviceManager";

    int activate(
            SemInputConstants.Device device, SemInputConstants.DisplayState displayState, boolean z)
            throws RemoteException;

    int enableMotion(String str, boolean z, String str2) throws RemoteException;

    String getCommandList(SemInputConstants.Device device) throws RemoteException;

    int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException;

    String getKeyPressStateAll() throws RemoteException;

    int getMotionControl(String str, String str2) throws RemoteException;

    String getProperty(SemInputConstants.Device device, SemInputConstants.Property property)
            throws RemoteException;

    int getSupportDevice(SemInputConstants.Device device) throws RemoteException;

    int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException;

    int isEnableMotion(String str, String str2) throws RemoteException;

    boolean isKeyPressedByKeycode(int i) throws RemoteException;

    boolean isSupportMotion(String str) throws RemoteException;

    boolean registerListener(IBinder iBinder, int i, String str) throws RemoteException;

    String runCommand(SemInputConstants.Device device, String str) throws RemoteException;

    int sendRawdataTsp(SemInputConstants.Device device, int[] iArr) throws RemoteException;

    int setAodEnable(int i) throws RemoteException;

    int setAodRect(int i, int i2, int i3, int i4) throws RemoteException;

    int setAotEnable(int i) throws RemoteException;

    int setCommand(SemInputConstants.Device device, SemInputConstants.Command command, String str)
            throws RemoteException;

    int setFodEnable(int i, int i2, int i3, int i4) throws RemoteException;

    int setFodLpMode(int i) throws RemoteException;

    int setFodRect(int i, int i2, int i3, int i4) throws RemoteException;

    int setMotionControl(String str, int i, String str2) throws RemoteException;

    int setProperty(
            SemInputConstants.Device device, SemInputConstants.Property property, String str)
            throws RemoteException;

    int setSingletapEnable(int i) throws RemoteException;

    int setSpenEnabled(int i, int i2, boolean z) throws RemoteException;

    int setSyncChanged(int i) throws RemoteException;

    int setTemperature(int i) throws RemoteException;

    int setTspEnabled(int i, int i2, boolean z) throws RemoteException;

    boolean unregisterListener(IBinder iBinder, int i, String str) throws RemoteException;

    public static class Default implements ISemInputDeviceManager {
        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String getKeyPressStateAll() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean isKeyPressedByKeycode(int keycode) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean registerListener(IBinder binder, int type, String client)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean unregisterListener(IBinder binder, int type, String client)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int sendRawdataTsp(SemInputConstants.Device device, int[] data)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean isSupportMotion(String type) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int enableMotion(String type, boolean enable, String client) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int isEnableMotion(String type, String client) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setMotionControl(String subtype, int control, String client)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getMotionControl(String subtype, String client) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getSupportDevice(SemInputConstants.Device device) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String getCommandList(SemInputConstants.Device device) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setTspEnabled(int devid, int mode, boolean state) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setTemperature(int value) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setAodRect(int w, int h, int x, int y) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setAodEnable(int mode) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setAotEnable(int mode) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setFodEnable(int mode, int pressFast, int strictMode, int control)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setFodRect(int left, int top, int right, int bottom) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setFodLpMode(int mode) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setSingletapEnable(int mode) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setSyncChanged(int mode) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setSpenEnabled(int devid, int mode, boolean state) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int activate(
                SemInputConstants.Device device, SemInputConstants.DisplayState mode, boolean state)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setCommand(
                SemInputConstants.Device device, SemInputConstants.Command command, String mode)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setProperty(
                SemInputConstants.Device device, SemInputConstants.Property property, String mode)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String getProperty(
                SemInputConstants.Device device, SemInputConstants.Property property)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String runCommand(SemInputConstants.Device device, String cmd)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemInputDeviceManager {
        static final int TRANSACTION_activate = 26;
        static final int TRANSACTION_enableMotion = 7;
        static final int TRANSACTION_getCommandList = 14;
        static final int TRANSACTION_getDeviceEnabled = 13;
        static final int TRANSACTION_getKeyPressStateAll = 1;
        static final int TRANSACTION_getMotionControl = 10;
        static final int TRANSACTION_getProperty = 29;
        static final int TRANSACTION_getSupportDevice = 11;
        static final int TRANSACTION_getTspSupportFeature = 12;
        static final int TRANSACTION_isEnableMotion = 8;
        static final int TRANSACTION_isKeyPressedByKeycode = 2;
        static final int TRANSACTION_isSupportMotion = 6;
        static final int TRANSACTION_registerListener = 3;
        static final int TRANSACTION_runCommand = 30;
        static final int TRANSACTION_sendRawdataTsp = 5;
        static final int TRANSACTION_setAodEnable = 18;
        static final int TRANSACTION_setAodRect = 17;
        static final int TRANSACTION_setAotEnable = 19;
        static final int TRANSACTION_setCommand = 27;
        static final int TRANSACTION_setFodEnable = 20;
        static final int TRANSACTION_setFodLpMode = 22;
        static final int TRANSACTION_setFodRect = 21;
        static final int TRANSACTION_setMotionControl = 9;
        static final int TRANSACTION_setProperty = 28;
        static final int TRANSACTION_setSingletapEnable = 23;
        static final int TRANSACTION_setSpenEnabled = 25;
        static final int TRANSACTION_setSyncChanged = 24;
        static final int TRANSACTION_setTemperature = 16;
        static final int TRANSACTION_setTspEnabled = 15;
        static final int TRANSACTION_unregisterListener = 4;

        public Stub() {
            attachInterface(this, ISemInputDeviceManager.DESCRIPTOR);
        }

        public static ISemInputDeviceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemInputDeviceManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemInputDeviceManager)) {
                return (ISemInputDeviceManager) iin;
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
                    return "getKeyPressStateAll";
                case 2:
                    return "isKeyPressedByKeycode";
                case 3:
                    return "registerListener";
                case 4:
                    return "unregisterListener";
                case 5:
                    return "sendRawdataTsp";
                case 6:
                    return "isSupportMotion";
                case 7:
                    return "enableMotion";
                case 8:
                    return "isEnableMotion";
                case 9:
                    return "setMotionControl";
                case 10:
                    return "getMotionControl";
                case 11:
                    return "getSupportDevice";
                case 12:
                    return "getTspSupportFeature";
                case 13:
                    return "getDeviceEnabled";
                case 14:
                    return "getCommandList";
                case 15:
                    return "setTspEnabled";
                case 16:
                    return "setTemperature";
                case 17:
                    return "setAodRect";
                case 18:
                    return "setAodEnable";
                case TRANSACTION_setAotEnable /* 19 */:
                    return "setAotEnable";
                case 20:
                    return "setFodEnable";
                case 21:
                    return "setFodRect";
                case 22:
                    return "setFodLpMode";
                case 23:
                    return "setSingletapEnable";
                case TRANSACTION_setSyncChanged /* 24 */:
                    return "setSyncChanged";
                case TRANSACTION_setSpenEnabled /* 25 */:
                    return "setSpenEnabled";
                case TRANSACTION_activate /* 26 */:
                    return "activate";
                case TRANSACTION_setCommand /* 27 */:
                    return "setCommand";
                case TRANSACTION_setProperty /* 28 */:
                    return "setProperty";
                case TRANSACTION_getProperty /* 29 */:
                    return "getProperty";
                case 30:
                    return "runCommand";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemInputDeviceManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemInputDeviceManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _result = getKeyPressStateAll();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = isKeyPressedByKeycode(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    IBinder _arg02 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result3 = registerListener(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    IBinder _arg03 = data.readStrongBinder();
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result4 = unregisterListener(_arg03, _arg12, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    SemInputConstants.Device _arg04 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    int[] _arg13 = data.createIntArray();
                    data.enforceNoDataAvail();
                    int _result5 = sendRawdataTsp(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result6 = isSupportMotion(_arg05);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    boolean _arg14 = data.readBoolean();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    int _result7 = enableMotion(_arg06, _arg14, _arg23);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    int _result8 = isEnableMotion(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    int _arg16 = data.readInt();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = setMotionControl(_arg08, _arg16, _arg24);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    int _result10 = getMotionControl(_arg09, _arg17);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 11:
                    SemInputConstants.Device _arg010 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    data.enforceNoDataAvail();
                    int _result11 = getSupportDevice(_arg010);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 12:
                    SemInputConstants.Device _arg011 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    data.enforceNoDataAvail();
                    int _result12 = getTspSupportFeature(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 13:
                    SemInputConstants.Device _arg012 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    data.enforceNoDataAvail();
                    int _result13 = getDeviceEnabled(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 14:
                    SemInputConstants.Device _arg013 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    data.enforceNoDataAvail();
                    String _result14 = getCommandList(_arg013);
                    reply.writeNoException();
                    reply.writeString(_result14);
                    return true;
                case 15:
                    int _arg014 = data.readInt();
                    int _arg18 = data.readInt();
                    boolean _arg25 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result15 = setTspEnabled(_arg014, _arg18, _arg25);
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 16:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result16 = setTemperature(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 17:
                    int _arg016 = data.readInt();
                    int _arg19 = data.readInt();
                    int _arg26 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result17 = setAodRect(_arg016, _arg19, _arg26, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 18:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result18 = setAodEnable(_arg017);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case TRANSACTION_setAotEnable /* 19 */:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result19 = setAotEnable(_arg018);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 20:
                    int _arg019 = data.readInt();
                    int _arg110 = data.readInt();
                    int _arg27 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result20 = setFodEnable(_arg019, _arg110, _arg27, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 21:
                    int _arg020 = data.readInt();
                    int _arg111 = data.readInt();
                    int _arg28 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result21 = setFodRect(_arg020, _arg111, _arg28, _arg33);
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 22:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result22 = setFodLpMode(_arg021);
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 23:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result23 = setSingletapEnable(_arg022);
                    reply.writeNoException();
                    reply.writeInt(_result23);
                    return true;
                case TRANSACTION_setSyncChanged /* 24 */:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result24 = setSyncChanged(_arg023);
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case TRANSACTION_setSpenEnabled /* 25 */:
                    int _arg024 = data.readInt();
                    int _arg112 = data.readInt();
                    boolean _arg29 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result25 = setSpenEnabled(_arg024, _arg112, _arg29);
                    reply.writeNoException();
                    reply.writeInt(_result25);
                    return true;
                case TRANSACTION_activate /* 26 */:
                    SemInputConstants.Device _arg025 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.DisplayState _arg113 =
                            (SemInputConstants.DisplayState)
                                    data.readTypedObject(SemInputConstants.DisplayState.CREATOR);
                    boolean _arg210 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result26 = activate(_arg025, _arg113, _arg210);
                    reply.writeNoException();
                    reply.writeInt(_result26);
                    return true;
                case TRANSACTION_setCommand /* 27 */:
                    SemInputConstants.Device _arg026 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Command _arg114 =
                            (SemInputConstants.Command)
                                    data.readTypedObject(SemInputConstants.Command.CREATOR);
                    String _arg211 = data.readString();
                    data.enforceNoDataAvail();
                    int _result27 = setCommand(_arg026, _arg114, _arg211);
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case TRANSACTION_setProperty /* 28 */:
                    SemInputConstants.Device _arg027 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Property _arg115 =
                            (SemInputConstants.Property)
                                    data.readTypedObject(SemInputConstants.Property.CREATOR);
                    String _arg212 = data.readString();
                    data.enforceNoDataAvail();
                    int _result28 = setProperty(_arg027, _arg115, _arg212);
                    reply.writeNoException();
                    reply.writeInt(_result28);
                    return true;
                case TRANSACTION_getProperty /* 29 */:
                    SemInputConstants.Device _arg028 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Property _arg116 =
                            (SemInputConstants.Property)
                                    data.readTypedObject(SemInputConstants.Property.CREATOR);
                    data.enforceNoDataAvail();
                    String _result29 = getProperty(_arg028, _arg116);
                    reply.writeNoException();
                    reply.writeString(_result29);
                    return true;
                case 30:
                    SemInputConstants.Device _arg029 =
                            (SemInputConstants.Device)
                                    data.readTypedObject(SemInputConstants.Device.CREATOR);
                    String _arg117 = data.readString();
                    data.enforceNoDataAvail();
                    String _result30 = runCommand(_arg029, _arg117);
                    reply.writeNoException();
                    reply.writeString(_result30);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemInputDeviceManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInputDeviceManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getKeyPressStateAll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean isKeyPressedByKeycode(int keycode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(keycode);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean registerListener(IBinder binder, int type, String client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(type);
                    _data.writeString(client);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean unregisterListener(IBinder binder, int type, String client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(type);
                    _data.writeString(client);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int sendRawdataTsp(SemInputConstants.Device device, int[] data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeIntArray(data);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean isSupportMotion(String type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeString(type);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int enableMotion(String type, boolean enable, String client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeBoolean(enable);
                    _data.writeString(client);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int isEnableMotion(String type, String client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(client);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setMotionControl(String subtype, int control, String client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeString(subtype);
                    _data.writeInt(control);
                    _data.writeString(client);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getMotionControl(String subtype, String client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeString(subtype);
                    _data.writeString(client);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getSupportDevice(SemInputConstants.Device device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getTspSupportFeature(SemInputConstants.Device device)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getCommandList(SemInputConstants.Device device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setTspEnabled(int devid, int mode, boolean state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(devid);
                    _data.writeInt(mode);
                    _data.writeBoolean(state);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setTemperature(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAodRect(int w, int h, int x, int y) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(w);
                    _data.writeInt(h);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAodEnable(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAotEnable(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(Stub.TRANSACTION_setAotEnable, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodEnable(int mode, int pressFast, int strictMode, int control)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeInt(pressFast);
                    _data.writeInt(strictMode);
                    _data.writeInt(control);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodRect(int left, int top, int right, int bottom) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodLpMode(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSingletapEnable(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSyncChanged(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(Stub.TRANSACTION_setSyncChanged, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSpenEnabled(int devid, int mode, boolean state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeInt(devid);
                    _data.writeInt(mode);
                    _data.writeBoolean(state);
                    this.mRemote.transact(Stub.TRANSACTION_setSpenEnabled, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int activate(
                    SemInputConstants.Device device,
                    SemInputConstants.DisplayState mode,
                    boolean state)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeTypedObject(mode, 0);
                    _data.writeBoolean(state);
                    this.mRemote.transact(Stub.TRANSACTION_activate, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setCommand(
                    SemInputConstants.Device device, SemInputConstants.Command command, String mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeTypedObject(command, 0);
                    _data.writeString(mode);
                    this.mRemote.transact(Stub.TRANSACTION_setCommand, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setProperty(
                    SemInputConstants.Device device,
                    SemInputConstants.Property property,
                    String mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeTypedObject(property, 0);
                    _data.writeString(mode);
                    this.mRemote.transact(Stub.TRANSACTION_setProperty, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getProperty(
                    SemInputConstants.Device device, SemInputConstants.Property property)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeTypedObject(property, 0);
                    this.mRemote.transact(Stub.TRANSACTION_getProperty, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String runCommand(SemInputConstants.Device device, String cmd)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeString(cmd);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getProperty;
        }
    }
}
