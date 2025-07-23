package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsConfigCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsConfigCallback";

    public static class Default implements IImsConfigCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfigCallback
        public void onIntConfigChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfigCallback
        public void onStringConfigChanged(int i, String str) throws RemoteException {
        }
    }

    void onIntConfigChanged(int i, int i2) throws RemoteException;

    void onStringConfigChanged(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsConfigCallback {
        static final int TRANSACTION_onIntConfigChanged = 1;
        static final int TRANSACTION_onStringConfigChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IImsConfigCallback.DESCRIPTOR);
        }

        public static IImsConfigCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsConfigCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsConfigCallback)) {
                return (IImsConfigCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onIntConfigChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onStringConfigChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsConfigCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsConfigCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIntConfigChanged(readInt, readInt2);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onStringConfigChanged(readInt3, readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IImsConfigCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsConfigCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsConfigCallback
            public void onIntConfigChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsConfigCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfigCallback
            public void onStringConfigChanged(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsConfigCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
