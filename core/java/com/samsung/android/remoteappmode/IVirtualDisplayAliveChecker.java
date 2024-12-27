package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IVirtualDisplayAliveChecker extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker";

    void onVirtualDisplayCreated(int i) throws RemoteException;

    void onVirtualDisplayReleased(int i) throws RemoteException;

    public static class Default implements IVirtualDisplayAliveChecker {
        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayCreated(int displayId) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayReleased(int displayId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVirtualDisplayAliveChecker {
        static final int TRANSACTION_onVirtualDisplayCreated = 1;
        static final int TRANSACTION_onVirtualDisplayReleased = 2;

        public Stub() {
            attachInterface(this, IVirtualDisplayAliveChecker.DESCRIPTOR);
        }

        public static IVirtualDisplayAliveChecker asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualDisplayAliveChecker.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualDisplayAliveChecker)) {
                return (IVirtualDisplayAliveChecker) iin;
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
                    return "onVirtualDisplayCreated";
                case 2:
                    return "onVirtualDisplayReleased";
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
                data.enforceInterface(IVirtualDisplayAliveChecker.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualDisplayAliveChecker.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onVirtualDisplayCreated(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onVirtualDisplayReleased(_arg02);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualDisplayAliveChecker {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDisplayAliveChecker.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
            public void onVirtualDisplayCreated(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDisplayAliveChecker.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
            public void onVirtualDisplayReleased(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDisplayAliveChecker.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
