package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDataLoaderStatusListener extends IInterface {
    public static final int DATA_LOADER_BINDING = 1;
    public static final int DATA_LOADER_BOUND = 2;
    public static final int DATA_LOADER_CREATED = 3;
    public static final int DATA_LOADER_DESTROYED = 0;
    public static final int DATA_LOADER_IMAGE_NOT_READY = 7;
    public static final int DATA_LOADER_IMAGE_READY = 6;
    public static final int DATA_LOADER_STARTED = 4;
    public static final int DATA_LOADER_STOPPED = 5;
    public static final int DATA_LOADER_UNAVAILABLE = 8;
    public static final int DATA_LOADER_UNRECOVERABLE = 9;
    public static final String DESCRIPTOR = "android.content.pm.IDataLoaderStatusListener";

    public static class Default implements IDataLoaderStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IDataLoaderStatusListener
        public void onStatusChanged(int i, int i2) throws RemoteException {
        }
    }

    void onStatusChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataLoaderStatusListener {
        static final int TRANSACTION_onStatusChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDataLoaderStatusListener.DESCRIPTOR);
        }

        public static IDataLoaderStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDataLoaderStatusListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDataLoaderStatusListener)) {
                return (IDataLoaderStatusListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStatusChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDataLoaderStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDataLoaderStatusListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStatusChanged(i3, i4);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDataLoaderStatusListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataLoaderStatusListener.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoaderStatusListener
            public void onStatusChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDataLoaderStatusListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
