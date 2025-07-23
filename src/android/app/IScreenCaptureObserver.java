package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IScreenCaptureObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.IScreenCaptureObserver";

    public static class Default implements IScreenCaptureObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IScreenCaptureObserver
        public void onScreenCaptured() throws RemoteException {
        }
    }

    void onScreenCaptured() throws RemoteException;

    public static abstract class Stub extends Binder implements IScreenCaptureObserver {
        static final int TRANSACTION_onScreenCaptured = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IScreenCaptureObserver.DESCRIPTOR);
        }

        public static IScreenCaptureObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IScreenCaptureObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScreenCaptureObserver)) {
                return (IScreenCaptureObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onScreenCaptured";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IScreenCaptureObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IScreenCaptureObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onScreenCaptured();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IScreenCaptureObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScreenCaptureObserver.DESCRIPTOR;
            }

            @Override // android.app.IScreenCaptureObserver
            public void onScreenCaptured() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IScreenCaptureObserver.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
