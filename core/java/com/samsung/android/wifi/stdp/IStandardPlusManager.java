package com.samsung.android.wifi.stdp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IStandardPlusManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.stdp.IStandardPlusManager";

    void disableUsdNearby(int i) throws RemoteException;

    void enableUsdNearby(int i) throws RemoteException;

    boolean registerCallback(int i, IStandardPlusCallback iStandardPlusCallback)
            throws RemoteException;

    void startBleScan() throws RemoteException;

    void stopBleAdvertising() throws RemoteException;

    void stopBleScan() throws RemoteException;

    boolean unregisterCallback(int i) throws RemoteException;

    public static class Default implements IStandardPlusManager {
        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void startBleScan() throws RemoteException {}

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void stopBleScan() throws RemoteException {}

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void stopBleAdvertising() throws RemoteException {}

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void enableUsdNearby(int duration) throws RemoteException {}

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void disableUsdNearby(int duration) throws RemoteException {}

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public boolean registerCallback(int hash, IStandardPlusCallback callback)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public boolean unregisterCallback(int hash) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IStandardPlusManager {
        static final int TRANSACTION_disableUsdNearby = 5;
        static final int TRANSACTION_enableUsdNearby = 4;
        static final int TRANSACTION_registerCallback = 6;
        static final int TRANSACTION_startBleScan = 1;
        static final int TRANSACTION_stopBleAdvertising = 3;
        static final int TRANSACTION_stopBleScan = 2;
        static final int TRANSACTION_unregisterCallback = 7;

        public Stub() {
            attachInterface(this, IStandardPlusManager.DESCRIPTOR);
        }

        public static IStandardPlusManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStandardPlusManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IStandardPlusManager)) {
                return (IStandardPlusManager) iin;
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
                    return "startBleScan";
                case 2:
                    return "stopBleScan";
                case 3:
                    return "stopBleAdvertising";
                case 4:
                    return "enableUsdNearby";
                case 5:
                    return "disableUsdNearby";
                case 6:
                    return "registerCallback";
                case 7:
                    return "unregisterCallback";
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
                data.enforceInterface(IStandardPlusManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStandardPlusManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    startBleScan();
                    reply.writeNoException();
                    return true;
                case 2:
                    stopBleScan();
                    reply.writeNoException();
                    return true;
                case 3:
                    stopBleAdvertising();
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    enableUsdNearby(_arg0);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    disableUsdNearby(_arg02);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg03 = data.readInt();
                    IStandardPlusCallback _arg1 =
                            IStandardPlusCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result = registerCallback(_arg03, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 7:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = unregisterCallback(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStandardPlusManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStandardPlusManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void startBleScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void stopBleScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void stopBleAdvertising() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void enableUsdNearby(int duration) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    _data.writeInt(duration);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void disableUsdNearby(int duration) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    _data.writeInt(duration);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public boolean registerCallback(int hash, IStandardPlusCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    _data.writeInt(hash);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public boolean unregisterCallback(int hash) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    _data.writeInt(hash);
                    this.mRemote.transact(7, _data, _reply, 0);
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
            return 6;
        }
    }
}
