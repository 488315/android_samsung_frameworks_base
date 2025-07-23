package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDreamOverlayCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlayCallback";

    public static class Default implements IDreamOverlayCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onExitRequested() throws RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onRedirectWake(boolean z) throws RemoteException {
        }
    }

    void onExitRequested() throws RemoteException;

    void onRedirectWake(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IDreamOverlayCallback {
        static final int TRANSACTION_onExitRequested = 1;
        static final int TRANSACTION_onRedirectWake = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDreamOverlayCallback.DESCRIPTOR);
        }

        public static IDreamOverlayCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDreamOverlayCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDreamOverlayCallback)) {
                return (IDreamOverlayCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onExitRequested";
            }
            if (i != 2) {
                return null;
            }
            return "onRedirectWake";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDreamOverlayCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDreamOverlayCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onExitRequested();
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onRedirectWake(readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDreamOverlayCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlayCallback.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayCallback
            public void onExitRequested() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDreamOverlayCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayCallback
            public void onRedirectWake(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDreamOverlayCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
