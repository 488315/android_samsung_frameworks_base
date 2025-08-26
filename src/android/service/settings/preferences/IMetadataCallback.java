package android.service.settings.preferences;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMetadataCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.settings.preferences.IMetadataCallback";

    public static class Default implements IMetadataCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.settings.preferences.IMetadataCallback
        public void onFailure() throws RemoteException {
        }

        @Override // android.service.settings.preferences.IMetadataCallback
        public void onSuccess(MetadataResult metadataResult) throws RemoteException {
        }
    }

    void onFailure() throws RemoteException;

    void onSuccess(MetadataResult metadataResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IMetadataCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IMetadataCallback.DESCRIPTOR);
        }

        public static IMetadataCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMetadataCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMetadataCallback)) {
                return (IMetadataCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMetadataCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMetadataCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MetadataResult metadataResult = (MetadataResult) parcel.readTypedObject(MetadataResult.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(metadataResult);
            } else if (i == 2) {
                onFailure();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMetadataCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMetadataCallback.DESCRIPTOR;
            }

            @Override // android.service.settings.preferences.IMetadataCallback
            public void onSuccess(MetadataResult metadataResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMetadataCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(metadataResult, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.settings.preferences.IMetadataCallback
            public void onFailure() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMetadataCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
