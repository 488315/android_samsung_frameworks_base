package android.media.tv.extension.scan;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IFavoriteNetworkListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IFavoriteNetworkListener";

    public static class Default implements IFavoriteNetworkListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IFavoriteNetworkListener
        public void onDetectFavoriteNetwork(Bundle bundle) throws RemoteException {
        }
    }

    void onDetectFavoriteNetwork(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IFavoriteNetworkListener {
        static final int TRANSACTION_onDetectFavoriteNetwork = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IFavoriteNetworkListener");
        }

        public static IFavoriteNetworkListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IFavoriteNetworkListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFavoriteNetworkListener)) {
                return (IFavoriteNetworkListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDetectFavoriteNetwork";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.IFavoriteNetworkListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IFavoriteNetworkListener");
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onDetectFavoriteNetwork(bundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IFavoriteNetworkListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IFavoriteNetworkListener";
            }

            @Override // android.media.tv.extension.scan.IFavoriteNetworkListener
            public void onDetectFavoriteNetwork(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IFavoriteNetworkListener");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
