package vendor.samsung.hardware.sysinput;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISehSysInputDev extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$sysinput$ISehSysInputDev".replace('$', '.');
    public static final String HASH = "ebc882a8076245906ae71306e8e0706f50e728ae";
    public static final int VERSION = 2;

    int activate(int i, int i2, boolean z) throws RemoteException;

    int[] getDeviceList(boolean z) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getKeyState(int i, SehIntStringParcel sehIntStringParcel) throws RemoteException;

    String getProperty(int i, int i2) throws RemoteException;

    int injectRawdata(int i, int[] iArr, int i2) throws RemoteException;

    void readTaas(SehIntStringParcel sehIntStringParcel) throws RemoteException;

    int registerCallback(ISehSysInputCallback iSehSysInputCallback) throws RemoteException;

    void runCommand(int i, String str, SehIntStringParcel sehIntStringParcel)
            throws RemoteException;

    int setProperty(int i, int i2, String str) throws RemoteException;

    int streamRawdata(int i, int i2) throws RemoteException;

    int writeTaas(String str) throws RemoteException;

    public static class Default implements ISehSysInputDev {
        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int[] getDeviceList(boolean force) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int registerCallback(ISehSysInputCallback callback) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int streamRawdata(int type, int mode) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int injectRawdata(int type, int[] inputData, int inputSize) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int activate(int type, int enable, boolean isBefore) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public void runCommand(int type, String cmdname, SehIntStringParcel outbuf)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int setProperty(int type, int property, String vaule) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public String getProperty(int type, int property) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public void getKeyState(int keycode, SehIntStringParcel outbuf) throws RemoteException {}

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public void readTaas(SehIntStringParcel outbuf) throws RemoteException {}

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int writeTaas(String buf) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISehSysInputDev {
        static final int TRANSACTION_activate = 5;
        static final int TRANSACTION_getDeviceList = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getKeyState = 9;
        static final int TRANSACTION_getProperty = 8;
        static final int TRANSACTION_injectRawdata = 4;
        static final int TRANSACTION_readTaas = 10;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_runCommand = 6;
        static final int TRANSACTION_setProperty = 7;
        static final int TRANSACTION_streamRawdata = 3;
        static final int TRANSACTION_writeTaas = 11;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehSysInputDev asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehSysInputDev)) {
                return (ISehSysInputDev) iin;
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
                    return "getDeviceList";
                case 2:
                    return "registerCallback";
                case 3:
                    return "streamRawdata";
                case 4:
                    return "injectRawdata";
                case 5:
                    return "activate";
                case 6:
                    return "runCommand";
                case 7:
                    return "setProperty";
                case 8:
                    return "getProperty";
                case 9:
                    return "getKeyState";
                case 10:
                    return "readTaas";
                case 11:
                    return "writeTaas";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case TRANSACTION_getInterfaceVersion /* 16777215 */:
                    return "getInterfaceVersion";
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
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= TRANSACTION_getInterfaceVersion) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == TRANSACTION_getInterfaceVersion) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == TRANSACTION_getInterfaceHash) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int[] _result = getDeviceList(_arg0);
                    reply.writeNoException();
                    reply.writeIntArray(_result);
                    return true;
                case 2:
                    ISehSysInputCallback _arg02 =
                            ISehSysInputCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result2 = registerCallback(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result3 = streamRawdata(_arg03, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    int[] _arg12 = data.createIntArray();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = injectRawdata(_arg04, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg13 = data.readInt();
                    boolean _arg22 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result5 = activate(_arg05, _arg13, _arg22);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    String _arg14 = data.readString();
                    SehIntStringParcel _arg23 = new SehIntStringParcel();
                    data.enforceNoDataAvail();
                    runCommand(_arg06, _arg14, _arg23);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg23, 1);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg15 = data.readInt();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    int _result6 = setProperty(_arg07, _arg15, _arg24);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result7 = getProperty(_arg08, _arg16);
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    SehIntStringParcel _arg17 = new SehIntStringParcel();
                    data.enforceNoDataAvail();
                    getKeyState(_arg09, _arg17);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg17, 1);
                    return true;
                case 10:
                    SehIntStringParcel _arg010 = new SehIntStringParcel();
                    data.enforceNoDataAvail();
                    readTaas(_arg010);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg010, 1);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    int _result8 = writeTaas(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehSysInputDev {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int[] getDeviceList(boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(force);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getDeviceList is unimplemented.");
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int registerCallback(ISehSysInputCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method registerCallback is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int streamRawdata(int type, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method streamRawdata is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int injectRawdata(int type, int[] inputData, int inputSize)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeIntArray(inputData);
                    _data.writeInt(inputSize);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method injectRawdata is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int activate(int type, int enable, boolean isBefore) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(enable);
                    _data.writeBoolean(isBefore);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method activate is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public void runCommand(int type, String cmdname, SehIntStringParcel outbuf)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(cmdname);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method runCommand is unimplemented.");
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outbuf.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int setProperty(int type, int property, String vaule) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(property);
                    _data.writeString(vaule);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setProperty is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public String getProperty(int type, int property) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(property);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getProperty is unimplemented.");
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public void getKeyState(int keycode, SehIntStringParcel outbuf) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(keycode);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getKeyState is unimplemented.");
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outbuf.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public void readTaas(SehIntStringParcel outbuf) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method readTaas is unimplemented.");
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outbuf.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int writeTaas(String buf) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(buf);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method writeTaas is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.samsung.hardware.sysinput.ISehSysInputDev
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
