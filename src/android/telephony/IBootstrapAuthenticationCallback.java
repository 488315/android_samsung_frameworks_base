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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBootstrapAuthenticationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBootstrapAuthenticationCallback)) {
                return (IBootstrapAuthenticationCallback) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onKeysAvailable(i3, bArrCreateByteArray, string);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAuthenticationFailure(i4, i5);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBootstrapAuthenticationCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.IBootstrapAuthenticationCallback
            public void onAuthenticationFailure(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBootstrapAuthenticationCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
