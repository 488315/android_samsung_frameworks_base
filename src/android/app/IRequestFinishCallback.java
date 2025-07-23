package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRequestFinishCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IRequestFinishCallback";

    public static class Default implements IRequestFinishCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IRequestFinishCallback
        public void requestFinish() throws RemoteException {
        }
    }

    void requestFinish() throws RemoteException;

    public static abstract class Stub extends Binder implements IRequestFinishCallback {
        static final int TRANSACTION_requestFinish = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRequestFinishCallback.DESCRIPTOR);
        }

        public static IRequestFinishCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRequestFinishCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRequestFinishCallback)) {
                return (IRequestFinishCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "requestFinish";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRequestFinishCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRequestFinishCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                requestFinish();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRequestFinishCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestFinishCallback.DESCRIPTOR;
            }

            @Override // android.app.IRequestFinishCallback
            public void requestFinish() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRequestFinishCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
