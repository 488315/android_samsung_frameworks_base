package android.media.tv.extension.scan;

import android.media.tv.extension.scan.IFavoriteNetworkListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IFavoriteNetwork extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IFavoriteNetwork";

    public static class Default implements IFavoriteNetwork {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IFavoriteNetwork
        public Bundle[] getFavoriteNetworks() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IFavoriteNetwork
        public int setFavoriteNetwork(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IFavoriteNetwork
        public int setListener(IFavoriteNetworkListener iFavoriteNetworkListener) throws RemoteException {
            return 0;
        }
    }

    Bundle[] getFavoriteNetworks() throws RemoteException;

    int setFavoriteNetwork(Bundle bundle) throws RemoteException;

    int setListener(IFavoriteNetworkListener iFavoriteNetworkListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IFavoriteNetwork {
        static final int TRANSACTION_getFavoriteNetworks = 1;
        static final int TRANSACTION_setFavoriteNetwork = 2;
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
            attachInterface(this, "android.media.tv.extension.scan.IFavoriteNetwork");
        }

        public static IFavoriteNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IFavoriteNetwork");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFavoriteNetwork)) {
                return (IFavoriteNetwork) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getFavoriteNetworks";
            }
            if (i == 2) {
                return "setFavoriteNetwork";
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
                parcel.enforceInterface("android.media.tv.extension.scan.IFavoriteNetwork");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IFavoriteNetwork");
                return true;
            }
            if (i == 1) {
                Bundle[] favoriteNetworks = getFavoriteNetworks();
                parcel2.writeNoException();
                parcel2.writeTypedArray(favoriteNetworks, 1);
            } else if (i == 2) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int favoriteNetwork = setFavoriteNetwork(bundle);
                parcel2.writeNoException();
                parcel2.writeInt(favoriteNetwork);
            } else if (i == 3) {
                IFavoriteNetworkListener iFavoriteNetworkListenerAsInterface = IFavoriteNetworkListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(iFavoriteNetworkListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(listener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFavoriteNetwork {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IFavoriteNetwork";
            }

            @Override // android.media.tv.extension.scan.IFavoriteNetwork
            public Bundle[] getFavoriteNetworks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IFavoriteNetwork");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle[]) parcelObtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IFavoriteNetwork
            public int setFavoriteNetwork(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IFavoriteNetwork");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IFavoriteNetwork
            public int setListener(IFavoriteNetworkListener iFavoriteNetworkListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IFavoriteNetwork");
                    parcelObtain.writeStrongInterface(iFavoriteNetworkListener);
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
