package android.os.incremental;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IStorageHealthListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.incremental.IStorageHealthListener";
    public static final int HEALTH_STATUS_BLOCKED = 2;
    public static final int HEALTH_STATUS_OK = 0;
    public static final int HEALTH_STATUS_READS_PENDING = 1;
    public static final int HEALTH_STATUS_UNHEALTHY = 3;

    public static class Default implements IStorageHealthListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.incremental.IStorageHealthListener
        public void onHealthStatus(int i, int i2) throws RemoteException {
        }
    }

    void onHealthStatus(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IStorageHealthListener {
        static final int TRANSACTION_onHealthStatus = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStorageHealthListener.DESCRIPTOR);
        }

        public static IStorageHealthListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IStorageHealthListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStorageHealthListener)) {
                return (IStorageHealthListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onHealthStatus";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStorageHealthListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStorageHealthListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onHealthStatus(i3, i4);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStorageHealthListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStorageHealthListener.DESCRIPTOR;
            }

            @Override // android.os.incremental.IStorageHealthListener
            public void onHealthStatus(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IStorageHealthListener.DESCRIPTOR);
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
