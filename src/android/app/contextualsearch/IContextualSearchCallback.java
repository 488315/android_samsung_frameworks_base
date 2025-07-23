package android.app.contextualsearch;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelableException;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IContextualSearchCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.contextualsearch.IContextualSearchCallback";

    public static class Default implements IContextualSearchCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onError(ParcelableException parcelableException) throws RemoteException {
        }

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onResult(ContextualSearchState contextualSearchState) throws RemoteException {
        }
    }

    void onError(ParcelableException parcelableException) throws RemoteException;

    void onResult(ContextualSearchState contextualSearchState) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextualSearchCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IContextualSearchCallback.DESCRIPTOR);
        }

        public static IContextualSearchCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContextualSearchCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextualSearchCallback)) {
                return (IContextualSearchCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onResult";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContextualSearchCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContextualSearchCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextualSearchState contextualSearchState = (ContextualSearchState) parcel.readTypedObject(ContextualSearchState.CREATOR);
                parcel.enforceNoDataAvail();
                onResult(contextualSearchState);
            } else if (i == 2) {
                ParcelableException parcelableException = (ParcelableException) parcel.readTypedObject(ParcelableException.CREATOR);
                parcel.enforceNoDataAvail();
                onError(parcelableException);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContextualSearchCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextualSearchCallback.DESCRIPTOR;
            }

            @Override // android.app.contextualsearch.IContextualSearchCallback
            public void onResult(ContextualSearchState contextualSearchState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextualSearchCallback.DESCRIPTOR);
                    obtain.writeTypedObject(contextualSearchState, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contextualsearch.IContextualSearchCallback
            public void onError(ParcelableException parcelableException) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextualSearchCallback.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableException, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
