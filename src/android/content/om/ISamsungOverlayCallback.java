package android.content.om;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISamsungOverlayCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.om.ISamsungOverlayCallback";

    public static class Default implements ISamsungOverlayCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.om.ISamsungOverlayCallback
        public void onOverlayStateChanged(String str, String str2, int i) throws RemoteException {
        }
    }

    void onOverlayStateChanged(String str, String str2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISamsungOverlayCallback {
        static final int TRANSACTION_onOverlayStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISamsungOverlayCallback.DESCRIPTOR);
        }

        public static ISamsungOverlayCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISamsungOverlayCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISamsungOverlayCallback)) {
                return (ISamsungOverlayCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onOverlayStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISamsungOverlayCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISamsungOverlayCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onOverlayStateChanged(readString, readString2, readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISamsungOverlayCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISamsungOverlayCallback.DESCRIPTOR;
            }

            @Override // android.content.om.ISamsungOverlayCallback
            public void onOverlayStateChanged(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISamsungOverlayCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
