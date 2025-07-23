package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBackgroundActivityLaunchCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IBackgroundActivityLaunchCallback";

    public static class Default implements IBackgroundActivityLaunchCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IBackgroundActivityLaunchCallback
        public void onBackgroundActivityLaunchAborted(String str) throws RemoteException {
        }
    }

    void onBackgroundActivityLaunchAborted(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackgroundActivityLaunchCallback {
        static final int TRANSACTION_onBackgroundActivityLaunchAborted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBackgroundActivityLaunchCallback.DESCRIPTOR);
        }

        public static IBackgroundActivityLaunchCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBackgroundActivityLaunchCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackgroundActivityLaunchCallback)) {
                return (IBackgroundActivityLaunchCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onBackgroundActivityLaunchAborted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBackgroundActivityLaunchCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBackgroundActivityLaunchCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onBackgroundActivityLaunchAborted(readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBackgroundActivityLaunchCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBackgroundActivityLaunchCallback.DESCRIPTOR;
            }

            @Override // android.app.IBackgroundActivityLaunchCallback
            public void onBackgroundActivityLaunchAborted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBackgroundActivityLaunchCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
