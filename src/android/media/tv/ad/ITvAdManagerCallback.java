package android.media.tv.ad;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvAdManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdManagerCallback";

    public static class Default implements ITvAdManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceAdded(String str) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceRemoved(String str) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceUpdated(String str) throws RemoteException {
        }
    }

    void onAdServiceAdded(String str) throws RemoteException;

    void onAdServiceRemoved(String str) throws RemoteException;

    void onAdServiceUpdated(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvAdManagerCallback {
        static final int TRANSACTION_onAdServiceAdded = 1;
        static final int TRANSACTION_onAdServiceRemoved = 2;
        static final int TRANSACTION_onAdServiceUpdated = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ITvAdManagerCallback.DESCRIPTOR);
        }

        public static ITvAdManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvAdManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvAdManagerCallback)) {
                return (ITvAdManagerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAdServiceAdded";
            }
            if (i == 2) {
                return "onAdServiceRemoved";
            }
            if (i != 3) {
                return null;
            }
            return "onAdServiceUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITvAdManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvAdManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onAdServiceAdded(readString);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onAdServiceRemoved(readString2);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onAdServiceUpdated(readString3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITvAdManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceAdded(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceRemoved(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceUpdated(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
