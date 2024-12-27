package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IMemorySaverPackageMoveObserver extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IMemorySaverPackageMoveObserver";

    void onCreated(String str) throws RemoteException;

    void onStatusChanged(int i, int i2, long j) throws RemoteException;

    public static class Default implements IMemorySaverPackageMoveObserver {
        @Override // android.content.pm.IMemorySaverPackageMoveObserver
        public void onCreated(String packageName) throws RemoteException {
        }

        @Override // android.content.pm.IMemorySaverPackageMoveObserver
        public void onStatusChanged(int moveId, int status, long estMillis) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMemorySaverPackageMoveObserver {
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onStatusChanged = 2;

        public Stub() {
            attachInterface(this, IMemorySaverPackageMoveObserver.DESCRIPTOR);
        }

        public static IMemorySaverPackageMoveObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMemorySaverPackageMoveObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IMemorySaverPackageMoveObserver)) {
                return (IMemorySaverPackageMoveObserver) iin;
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
                    return "onCreated";
                case 2:
                    return "onStatusChanged";
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
                data.enforceInterface(IMemorySaverPackageMoveObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMemorySaverPackageMoveObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onCreated(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    onStatusChanged(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMemorySaverPackageMoveObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMemorySaverPackageMoveObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.IMemorySaverPackageMoveObserver
            public void onCreated(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMemorySaverPackageMoveObserver.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IMemorySaverPackageMoveObserver
            public void onStatusChanged(int moveId, int status, long estMillis) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMemorySaverPackageMoveObserver.DESCRIPTOR);
                    _data.writeInt(moveId);
                    _data.writeInt(status);
                    _data.writeLong(estMillis);
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
