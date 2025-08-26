package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IMemorySaverPackageMoveObserver extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IMemorySaverPackageMoveObserver";

    public static class Default implements IMemorySaverPackageMoveObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IMemorySaverPackageMoveObserver
        public void onCreated(String str) throws RemoteException {
        }

        @Override // android.content.pm.IMemorySaverPackageMoveObserver
        public void onStatusChanged(int i, int i2, long j) throws RemoteException {
        }
    }

    void onCreated(String str) throws RemoteException;

    void onStatusChanged(int i, int i2, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IMemorySaverPackageMoveObserver {
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onStatusChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IMemorySaverPackageMoveObserver.DESCRIPTOR);
        }

        public static IMemorySaverPackageMoveObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMemorySaverPackageMoveObserver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMemorySaverPackageMoveObserver)) {
                return (IMemorySaverPackageMoveObserver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCreated";
            }
            if (i != 2) {
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
                parcel.enforceInterface(IMemorySaverPackageMoveObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMemorySaverPackageMoveObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onCreated(string);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                long j = parcel.readLong();
                parcel.enforceNoDataAvail();
                onStatusChanged(i3, i4, j);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMemorySaverPackageMoveObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMemorySaverPackageMoveObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.IMemorySaverPackageMoveObserver
            public void onCreated(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverPackageMoveObserver.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IMemorySaverPackageMoveObserver
            public void onStatusChanged(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverPackageMoveObserver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
