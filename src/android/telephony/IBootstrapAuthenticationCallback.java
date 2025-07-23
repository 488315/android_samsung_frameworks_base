package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IBootstrapAuthenticationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.IBootstrapAuthenticationCallback";

    public static class Default implements IBootstrapAuthenticationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onAuthenticationFailure(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onKeysAvailable(int i, byte[] bArr, String str) throws RemoteException {
        }
    }

    void onAuthenticationFailure(int i, int i2) throws RemoteException;

    void onKeysAvailable(int i, byte[] bArr, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IBootstrapAuthenticationCallback {
        static final int TRANSACTION_onAuthenticationFailure = 2;
        static final int TRANSACTION_onKeysAvailable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IBootstrapAuthenticationCallback.DESCRIPTOR);
        }

        public static IBootstrapAuthenticationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBootstrapAuthenticationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBootstrapAuthenticationCallback)) {
                return (IBootstrapAuthenticationCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onKeysAvailable";
            }
            if (i != 2) {
                return null;
            }
            return "onAuthenticationFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBootstrapAuthenticationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBootstrapAuthenticationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onKeysAvailable(readInt, createByteArray, readString);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAuthenticationFailure(readInt2, readInt3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBootstrapAuthenticationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBootstrapAuthenticationCallback.DESCRIPTOR;
            }

            @Override // android.telephony.IBootstrapAuthenticationCallback
            public void onKeysAvailable(int i, byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBootstrapAuthenticationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.IBootstrapAuthenticationCallback
            public void onAuthenticationFailure(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBootstrapAuthenticationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
