package android.media.tv.extension.scan;

import android.media.tv.extension.scan.ILcnConflictListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILcnConflict extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.ILcnConflict";

    public static class Default implements ILcnConflict {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.ILcnConflict
        public Bundle[] getLcnConflictGroups() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.ILcnConflict
        public int resolveLcnConflict(Bundle[] bundleArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.ILcnConflict
        public int setListener(ILcnConflictListener iLcnConflictListener) throws RemoteException {
            return 0;
        }
    }

    Bundle[] getLcnConflictGroups() throws RemoteException;

    int resolveLcnConflict(Bundle[] bundleArr) throws RemoteException;

    int setListener(ILcnConflictListener iLcnConflictListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ILcnConflict {
        static final int TRANSACTION_getLcnConflictGroups = 1;
        static final int TRANSACTION_resolveLcnConflict = 2;
        static final int TRANSACTION_setListener = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.ILcnConflict");
        }

        public static ILcnConflict asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.ILcnConflict");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILcnConflict)) {
                return (ILcnConflict) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getLcnConflictGroups";
            }
            if (i == 2) {
                return "resolveLcnConflict";
            }
            if (i != 3) {
                return null;
            }
            return "setListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.ILcnConflict");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.ILcnConflict");
                return true;
            }
            if (i == 1) {
                Bundle[] lcnConflictGroups = getLcnConflictGroups();
                parcel2.writeNoException();
                parcel2.writeTypedArray(lcnConflictGroups, 1);
            } else if (i == 2) {
                Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int iResolveLcnConflict = resolveLcnConflict(bundleArr);
                parcel2.writeNoException();
                parcel2.writeInt(iResolveLcnConflict);
            } else if (i == 3) {
                ILcnConflictListener iLcnConflictListenerAsInterface = ILcnConflictListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(iLcnConflictListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(listener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILcnConflict {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.ILcnConflict";
            }

            @Override // android.media.tv.extension.scan.ILcnConflict
            public Bundle[] getLcnConflictGroups() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.ILcnConflict");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle[]) parcelObtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ILcnConflict
            public int resolveLcnConflict(Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.ILcnConflict");
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ILcnConflict
            public int setListener(ILcnConflictListener iLcnConflictListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.ILcnConflict");
                    parcelObtain.writeStrongInterface(iLcnConflictListener);
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
