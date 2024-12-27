package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITestSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.ITestSessionCallback";

    void onCleanupFinished(int i) throws RemoteException;

    void onCleanupStarted(int i) throws RemoteException;

    public static class Default implements ITestSessionCallback {
        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupStarted(int userId) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupFinished(int userId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITestSessionCallback {
        static final int TRANSACTION_onCleanupFinished = 2;
        static final int TRANSACTION_onCleanupStarted = 1;

        public Stub() {
            attachInterface(this, ITestSessionCallback.DESCRIPTOR);
        }

        public static ITestSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITestSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITestSessionCallback)) {
                return (ITestSessionCallback) iin;
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
                    return "onCleanupStarted";
                case 2:
                    return "onCleanupFinished";
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
                data.enforceInterface(ITestSessionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITestSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onCleanupStarted(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onCleanupFinished(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITestSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITestSessionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.ITestSessionCallback
            public void onCleanupStarted(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITestSessionCallback.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSessionCallback
            public void onCleanupFinished(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITestSessionCallback.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
