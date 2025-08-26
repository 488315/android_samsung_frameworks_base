package android.content.pm;

import android.content.pm.IDataLoader;
import android.content.pm.IDataLoaderStatusListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDataLoaderManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IDataLoaderManager";

    public static class Default implements IDataLoaderManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IDataLoaderManager
        public boolean bindToDataLoader(int i, DataLoaderParamsParcel dataLoaderParamsParcel, long j, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IDataLoaderManager
        public IDataLoader getDataLoader(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IDataLoaderManager
        public void unbindFromDataLoader(int i) throws RemoteException {
        }
    }

    boolean bindToDataLoader(int i, DataLoaderParamsParcel dataLoaderParamsParcel, long j, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException;

    IDataLoader getDataLoader(int i) throws RemoteException;

    void unbindFromDataLoader(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataLoaderManager {
        static final int TRANSACTION_bindToDataLoader = 1;
        static final int TRANSACTION_getDataLoader = 2;
        static final int TRANSACTION_unbindFromDataLoader = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IDataLoaderManager.DESCRIPTOR);
        }

        public static IDataLoaderManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDataLoaderManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDataLoaderManager)) {
                return (IDataLoaderManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "bindToDataLoader";
            }
            if (i == 2) {
                return "getDataLoader";
            }
            if (i != 3) {
                return null;
            }
            return "unbindFromDataLoader";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDataLoaderManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDataLoaderManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                DataLoaderParamsParcel dataLoaderParamsParcel = (DataLoaderParamsParcel) parcel.readTypedObject(DataLoaderParamsParcel.CREATOR);
                long j = parcel.readLong();
                IDataLoaderStatusListener iDataLoaderStatusListenerAsInterface = IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                boolean zBindToDataLoader = bindToDataLoader(i3, dataLoaderParamsParcel, j, iDataLoaderStatusListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeBoolean(zBindToDataLoader);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                IDataLoader dataLoader = getDataLoader(i4);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(dataLoader);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                unbindFromDataLoader(i5);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDataLoaderManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataLoaderManager.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoaderManager
            public boolean bindToDataLoader(int i, DataLoaderParamsParcel dataLoaderParamsParcel, long j, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDataLoaderManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iDataLoaderStatusListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoaderManager
            public IDataLoader getDataLoader(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDataLoaderManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IDataLoader.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoaderManager
            public void unbindFromDataLoader(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDataLoaderManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
