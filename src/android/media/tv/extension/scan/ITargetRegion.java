package android.media.tv.extension.scan;

import android.media.tv.extension.scan.ITargetRegionListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITargetRegion extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.ITargetRegion";

    public static class Default implements ITargetRegion {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.ITargetRegion
        public Bundle[] getTargetRegions() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.ITargetRegion
        public int setListener(ITargetRegionListener iTargetRegionListener) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.ITargetRegion
        public int setTargetRegion(Bundle bundle) throws RemoteException {
            return 0;
        }
    }

    Bundle[] getTargetRegions() throws RemoteException;

    int setListener(ITargetRegionListener iTargetRegionListener) throws RemoteException;

    int setTargetRegion(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ITargetRegion {
        static final int TRANSACTION_getTargetRegions = 1;
        static final int TRANSACTION_setListener = 3;
        static final int TRANSACTION_setTargetRegion = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.ITargetRegion");
        }

        public static ITargetRegion asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.ITargetRegion");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITargetRegion)) {
                return (ITargetRegion) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getTargetRegions";
            }
            if (i == 2) {
                return "setTargetRegion";
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
                parcel.enforceInterface("android.media.tv.extension.scan.ITargetRegion");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.ITargetRegion");
                return true;
            }
            if (i == 1) {
                Bundle[] targetRegions = getTargetRegions();
                parcel2.writeNoException();
                parcel2.writeTypedArray(targetRegions, 1);
            } else if (i == 2) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int targetRegion = setTargetRegion(bundle);
                parcel2.writeNoException();
                parcel2.writeInt(targetRegion);
            } else if (i == 3) {
                ITargetRegionListener iTargetRegionListenerAsInterface = ITargetRegionListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(iTargetRegionListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(listener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITargetRegion {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.ITargetRegion";
            }

            @Override // android.media.tv.extension.scan.ITargetRegion
            public Bundle[] getTargetRegions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.ITargetRegion");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle[]) parcelObtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ITargetRegion
            public int setTargetRegion(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.ITargetRegion");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.ITargetRegion
            public int setListener(ITargetRegionListener iTargetRegionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.ITargetRegion");
                    parcelObtain.writeStrongInterface(iTargetRegionListener);
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
