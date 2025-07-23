package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamProfileInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamProfileInterface";

    public static class Default implements ICamProfileInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamProfileInterface
        public Bundle getCamServiceUpdateInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamProfileInterface
        public void requestResendProfileInfoBroadcastACON() throws RemoteException {
        }
    }

    Bundle getCamServiceUpdateInfo(int i) throws RemoteException;

    void requestResendProfileInfoBroadcastACON() throws RemoteException;

    public static abstract class Stub extends Binder implements ICamProfileInterface {
        static final int TRANSACTION_getCamServiceUpdateInfo = 1;
        static final int TRANSACTION_requestResendProfileInfoBroadcastACON = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamProfileInterface");
        }

        public static ICamProfileInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamProfileInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamProfileInterface)) {
                return (ICamProfileInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getCamServiceUpdateInfo";
            }
            if (i != 2) {
                return null;
            }
            return "requestResendProfileInfoBroadcastACON";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamProfileInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamProfileInterface");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                Bundle camServiceUpdateInfo = getCamServiceUpdateInfo(readInt);
                parcel2.writeNoException();
                parcel2.writeTypedObject(camServiceUpdateInfo, 1);
            } else if (i == 2) {
                requestResendProfileInfoBroadcastACON();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICamProfileInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamProfileInterface";
            }

            @Override // android.media.tv.extension.cam.ICamProfileInterface
            public Bundle getCamServiceUpdateInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamProfileInterface");
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamProfileInterface
            public void requestResendProfileInfoBroadcastACON() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamProfileInterface");
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
