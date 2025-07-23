package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IUserInitializationCompleteCallback extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IUserInitializationCompleteCallback";

    public static class Default implements IUserInitializationCompleteCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IUserInitializationCompleteCallback
        public void onUserInitializationComplete(int i) throws RemoteException {
        }
    }

    void onUserInitializationComplete(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IUserInitializationCompleteCallback {
        static final int TRANSACTION_onUserInitializationComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUserInitializationCompleteCallback.DESCRIPTOR);
        }

        public static IUserInitializationCompleteCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUserInitializationCompleteCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUserInitializationCompleteCallback)) {
                return (IUserInitializationCompleteCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUserInitializationComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUserInitializationCompleteCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUserInitializationCompleteCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onUserInitializationComplete(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUserInitializationCompleteCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUserInitializationCompleteCallback.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IUserInitializationCompleteCallback
            public void onUserInitializationComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUserInitializationCompleteCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
