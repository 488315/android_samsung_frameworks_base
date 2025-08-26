package android.media.tv.extension.servicedb;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IServiceListImportSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IServiceListImportSession";

    public static class Default implements IServiceListImportSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListImportSession
        public int importServiceList(ParcelFileDescriptor parcelFileDescriptor, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListImportSession
        public int preload(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListImportSession
        public int release() throws RemoteException {
            return 0;
        }
    }

    int importServiceList(ParcelFileDescriptor parcelFileDescriptor, Bundle bundle) throws RemoteException;

    int preload(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int release() throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceListImportSession {
        static final int TRANSACTION_importServiceList = 1;
        static final int TRANSACTION_preload = 2;
        static final int TRANSACTION_release = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IServiceListImportSession");
        }

        public static IServiceListImportSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListImportSession");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IServiceListImportSession)) {
                return (IServiceListImportSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "importServiceList";
            }
            if (i == 2) {
                return "preload";
            }
            if (i != 3) {
                return null;
            }
            return "release";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.servicedb.IServiceListImportSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IServiceListImportSession");
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int iImportServiceList = importServiceList(parcelFileDescriptor, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(iImportServiceList);
            } else if (i == 2) {
                ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                int iPreload = preload(parcelFileDescriptor2);
                parcel2.writeNoException();
                parcel2.writeInt(iPreload);
            } else if (i == 3) {
                int iRelease = release();
                parcel2.writeNoException();
                parcel2.writeInt(iRelease);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IServiceListImportSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IServiceListImportSession";
            }

            @Override // android.media.tv.extension.servicedb.IServiceListImportSession
            public int importServiceList(ParcelFileDescriptor parcelFileDescriptor, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListImportSession");
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListImportSession
            public int preload(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListImportSession");
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListImportSession
            public int release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListImportSession");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
