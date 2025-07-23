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
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListImportSession");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceListImportSession)) {
                return (IServiceListImportSession) queryLocalInterface;
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
                int importServiceList = importServiceList(parcelFileDescriptor, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(importServiceList);
            } else if (i == 2) {
                ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                int preload = preload(parcelFileDescriptor2);
                parcel2.writeNoException();
                parcel2.writeInt(preload);
            } else if (i == 3) {
                int release = release();
                parcel2.writeNoException();
                parcel2.writeInt(release);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListImportSession");
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListImportSession
            public int preload(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListImportSession");
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListImportSession
            public int release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListImportSession");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
