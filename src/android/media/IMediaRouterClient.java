package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaRouterClient extends IInterface {

    public static class Default implements IMediaRouterClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaRouterClient
        public void onGroupRouteSelected(String str) throws RemoteException {
        }

        @Override // android.media.IMediaRouterClient
        public void onRestoreRoute() throws RemoteException {
        }

        @Override // android.media.IMediaRouterClient
        public void onStateChanged() throws RemoteException {
        }
    }

    void onGroupRouteSelected(String str) throws RemoteException;

    void onRestoreRoute() throws RemoteException;

    void onStateChanged() throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaRouterClient {
        public static final String DESCRIPTOR = "android.media.IMediaRouterClient";
        static final int TRANSACTION_onGroupRouteSelected = 3;
        static final int TRANSACTION_onRestoreRoute = 2;
        static final int TRANSACTION_onStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaRouterClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaRouterClient)) {
                return (IMediaRouterClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStateChanged";
            }
            if (i == 2) {
                return "onRestoreRoute";
            }
            if (i != 3) {
                return null;
            }
            return "onGroupRouteSelected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onStateChanged();
            } else if (i == 2) {
                onRestoreRoute();
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onGroupRouteSelected(readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaRouterClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouterClient
            public void onStateChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onRestoreRoute() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onGroupRouteSelected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
