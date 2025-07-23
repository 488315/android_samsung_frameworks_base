package android.content.pm;

import android.content.pm.IDataLoaderStatusListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDataLoader extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IDataLoader";

    public static class Default implements IDataLoader {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IDataLoader
        public void create(int i, DataLoaderParamsParcel dataLoaderParamsParcel, FileSystemControlParcel fileSystemControlParcel, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void destroy(int i) throws RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void prepareImage(int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr) throws RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void start(int i) throws RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void stop(int i) throws RemoteException {
        }
    }

    void create(int i, DataLoaderParamsParcel dataLoaderParamsParcel, FileSystemControlParcel fileSystemControlParcel, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException;

    void destroy(int i) throws RemoteException;

    void prepareImage(int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr) throws RemoteException;

    void start(int i) throws RemoteException;

    void stop(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataLoader {
        static final int TRANSACTION_create = 1;
        static final int TRANSACTION_destroy = 4;
        static final int TRANSACTION_prepareImage = 5;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_stop = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IDataLoader.DESCRIPTOR);
        }

        public static IDataLoader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDataLoader.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataLoader)) {
                return (IDataLoader) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "create";
            }
            if (i == 2) {
                return "start";
            }
            if (i == 3) {
                return "stop";
            }
            if (i == 4) {
                return "destroy";
            }
            if (i != 5) {
                return null;
            }
            return "prepareImage";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDataLoader.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDataLoader.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                DataLoaderParamsParcel dataLoaderParamsParcel = (DataLoaderParamsParcel) parcel.readTypedObject(DataLoaderParamsParcel.CREATOR);
                FileSystemControlParcel fileSystemControlParcel = (FileSystemControlParcel) parcel.readTypedObject(FileSystemControlParcel.CREATOR);
                IDataLoaderStatusListener asInterface = IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                create(readInt, dataLoaderParamsParcel, fileSystemControlParcel, asInterface);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                start(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                stop(readInt3);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                destroy(readInt4);
            } else if (i == 5) {
                int readInt5 = parcel.readInt();
                InstallationFileParcel[] installationFileParcelArr = (InstallationFileParcel[]) parcel.createTypedArray(InstallationFileParcel.CREATOR);
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                prepareImage(readInt5, installationFileParcelArr, createStringArray);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDataLoader {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataLoader.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoader
            public void create(int i, DataLoaderParamsParcel dataLoaderParamsParcel, FileSystemControlParcel fileSystemControlParcel, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeTypedObject(fileSystemControlParcel, 0);
                    obtain.writeStrongInterface(iDataLoaderStatusListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void start(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void stop(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void destroy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void prepareImage(int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(installationFileParcelArr, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
