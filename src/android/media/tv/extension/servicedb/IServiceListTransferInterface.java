package android.media.tv.extension.servicedb;

import android.media.tv.extension.servicedb.IServiceListExportListener;
import android.media.tv.extension.servicedb.IServiceListImportListener;
import android.media.tv.extension.servicedb.IServiceListSetChannelListListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IServiceListTransferInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IServiceListTransferInterface";

    public static class Default implements IServiceListTransferInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListTransferInterface
        public IBinder createExportSession(IServiceListExportListener iServiceListExportListener) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListTransferInterface
        public IBinder createImportSession(IServiceListImportListener iServiceListImportListener) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListTransferInterface
        public IBinder createSetChannelListSession(IServiceListSetChannelListListener iServiceListSetChannelListListener) throws RemoteException {
            return null;
        }
    }

    IBinder createExportSession(IServiceListExportListener iServiceListExportListener) throws RemoteException;

    IBinder createImportSession(IServiceListImportListener iServiceListImportListener) throws RemoteException;

    IBinder createSetChannelListSession(IServiceListSetChannelListListener iServiceListSetChannelListListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceListTransferInterface {
        static final int TRANSACTION_createExportSession = 1;
        static final int TRANSACTION_createImportSession = 2;
        static final int TRANSACTION_createSetChannelListSession = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IServiceListTransferInterface");
        }

        public static IServiceListTransferInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListTransferInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceListTransferInterface)) {
                return (IServiceListTransferInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createExportSession";
            }
            if (i == 2) {
                return "createImportSession";
            }
            if (i != 3) {
                return null;
            }
            return "createSetChannelListSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.servicedb.IServiceListTransferInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IServiceListTransferInterface");
                return true;
            }
            if (i == 1) {
                IServiceListExportListener asInterface = IServiceListExportListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                IBinder createExportSession = createExportSession(asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(createExportSession);
            } else if (i == 2) {
                IServiceListImportListener asInterface2 = IServiceListImportListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                IBinder createImportSession = createImportSession(asInterface2);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(createImportSession);
            } else if (i == 3) {
                IServiceListSetChannelListListener asInterface3 = IServiceListSetChannelListListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                IBinder createSetChannelListSession = createSetChannelListSession(asInterface3);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(createSetChannelListSession);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IServiceListTransferInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IServiceListTransferInterface";
            }

            @Override // android.media.tv.extension.servicedb.IServiceListTransferInterface
            public IBinder createExportSession(IServiceListExportListener iServiceListExportListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListTransferInterface");
                    obtain.writeStrongInterface(iServiceListExportListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListTransferInterface
            public IBinder createImportSession(IServiceListImportListener iServiceListImportListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListTransferInterface");
                    obtain.writeStrongInterface(iServiceListImportListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListTransferInterface
            public IBinder createSetChannelListSession(IServiceListSetChannelListListener iServiceListSetChannelListListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListTransferInterface");
                    obtain.writeStrongInterface(iServiceListSetChannelListListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
