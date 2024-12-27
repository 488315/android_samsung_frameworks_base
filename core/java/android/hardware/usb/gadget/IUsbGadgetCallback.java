package android.hardware.usb.gadget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUsbGadgetCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android$hardware$usb$gadget$IUsbGadgetCallback".replace('$', '.');
    public static final String HASH = "cb628c69682659911bca5c1d04042adba7f0de4b";
    public static final int VERSION = 1;

    void getCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getUsbSpeedCb(int i, long j) throws RemoteException;

    void resetCb(int i, long j) throws RemoteException;

    void setCurrentUsbFunctionsCb(long j, int i, long j2) throws RemoteException;

    public static class Default implements IUsbGadgetCallback {
        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void setCurrentUsbFunctionsCb(long functions, int status, long transactionId)
                throws RemoteException {}

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void getCurrentUsbFunctionsCb(long functions, int status, long transactionId)
                throws RemoteException {}

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void getUsbSpeedCb(int speed, long transactionId) throws RemoteException {}

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public void resetCb(int status, long transactionId) throws RemoteException {}

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.usb.gadget.IUsbGadgetCallback
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUsbGadgetCallback {
        static final int TRANSACTION_getCurrentUsbFunctionsCb = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getUsbSpeedCb = 3;
        static final int TRANSACTION_resetCb = 4;
        static final int TRANSACTION_setCurrentUsbFunctionsCb = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsbGadgetCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IUsbGadgetCallback)) {
                return (IUsbGadgetCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    int _arg1 = data.readInt();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    setCurrentUsbFunctionsCb(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    long _arg02 = data.readLong();
                    int _arg12 = data.readInt();
                    long _arg22 = data.readLong();
                    data.enforceNoDataAvail();
                    getCurrentUsbFunctionsCb(_arg02, _arg12, _arg22);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    long _arg13 = data.readLong();
                    data.enforceNoDataAvail();
                    getUsbSpeedCb(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    long _arg14 = data.readLong();
                    data.enforceNoDataAvail();
                    resetCb(_arg04, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUsbGadgetCallback {
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

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void setCurrentUsbFunctionsCb(long functions, int status, long transactionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(functions);
                    _data.writeInt(status);
                    _data.writeLong(transactionId);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setCurrentUsbFunctionsCb is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void getCurrentUsbFunctionsCb(long functions, int status, long transactionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(functions);
                    _data.writeInt(status);
                    _data.writeLong(transactionId);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getCurrentUsbFunctionsCb is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void getUsbSpeedCb(int speed, long transactionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(speed);
                    _data.writeLong(transactionId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getUsbSpeedCb is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public void resetCb(int status, long transactionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeLong(transactionId);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method resetCb is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.usb.gadget.IUsbGadgetCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
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
    }
}
