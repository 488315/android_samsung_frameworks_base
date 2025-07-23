package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMmiStatusCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.IMmiStatusCallback";

    public static class Default implements IMmiStatusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.IMmiStatusCallback
        public void onMmiClose() throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.IMmiStatusCallback
        public void onMmiEnq(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.IMmiStatusCallback
        public void onMmiListMenu(Bundle bundle) throws RemoteException {
        }
    }

    void onMmiClose() throws RemoteException;

    void onMmiEnq(Bundle bundle) throws RemoteException;

    void onMmiListMenu(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IMmiStatusCallback {
        static final int TRANSACTION_onMmiClose = 3;
        static final int TRANSACTION_onMmiEnq = 1;
        static final int TRANSACTION_onMmiListMenu = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.IMmiStatusCallback");
        }

        public static IMmiStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.IMmiStatusCallback");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMmiStatusCallback)) {
                return (IMmiStatusCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onMmiEnq";
            }
            if (i == 2) {
                return "onMmiListMenu";
            }
            if (i != 3) {
                return null;
            }
            return "onMmiClose";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.IMmiStatusCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.IMmiStatusCallback");
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onMmiEnq(bundle);
            } else if (i == 2) {
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onMmiListMenu(bundle2);
            } else if (i == 3) {
                onMmiClose();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMmiStatusCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.IMmiStatusCallback";
            }

            @Override // android.media.tv.extension.cam.IMmiStatusCallback
            public void onMmiEnq(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiStatusCallback");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IMmiStatusCallback
            public void onMmiListMenu(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiStatusCallback");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IMmiStatusCallback
            public void onMmiClose() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiStatusCallback");
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
