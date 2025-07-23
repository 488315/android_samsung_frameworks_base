package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamAppInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamAppInfoListener";

    public static class Default implements ICamAppInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamAppInfoListener
        public void onCamAppInfoChanged(int i, Bundle bundle) throws RemoteException {
        }
    }

    void onCamAppInfoChanged(int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamAppInfoListener {
        static final int TRANSACTION_onCamAppInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamAppInfoListener");
        }

        public static ICamAppInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamAppInfoListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamAppInfoListener)) {
                return (ICamAppInfoListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCamAppInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamAppInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamAppInfoListener");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onCamAppInfoChanged(readInt, bundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICamAppInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamAppInfoListener";
            }

            @Override // android.media.tv.extension.cam.ICamAppInfoListener
            public void onCamAppInfoChanged(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamAppInfoListener");
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
