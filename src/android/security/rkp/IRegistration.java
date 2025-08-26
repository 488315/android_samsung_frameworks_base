package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.rkp.IGetKeyCallback;
import android.security.rkp.IStoreUpgradedKeyCallback;

/* loaded from: classes3.dex */
public interface IRegistration extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IRegistration";

    public static class Default implements IRegistration {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.rkp.IRegistration
        public void cancelGetKey(IGetKeyCallback iGetKeyCallback) throws RemoteException {
        }

        @Override // android.security.rkp.IRegistration
        public void getKey(int i, IGetKeyCallback iGetKeyCallback) throws RemoteException {
        }

        @Override // android.security.rkp.IRegistration
        public void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) throws RemoteException {
        }
    }

    void cancelGetKey(IGetKeyCallback iGetKeyCallback) throws RemoteException;

    void getKey(int i, IGetKeyCallback iGetKeyCallback) throws RemoteException;

    void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IRegistration {
        static final int TRANSACTION_cancelGetKey = 2;
        static final int TRANSACTION_getKey = 1;
        static final int TRANSACTION_storeUpgradedKeyAsync = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IRegistration.DESCRIPTOR);
        }

        public static IRegistration asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRegistration.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRegistration)) {
                return (IRegistration) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getKey";
            }
            if (i == 2) {
                return "cancelGetKey";
            }
            if (i != 3) {
                return null;
            }
            return "storeUpgradedKeyAsync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRegistration.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRegistration.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                IGetKeyCallback iGetKeyCallbackAsInterface = IGetKeyCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getKey(i3, iGetKeyCallbackAsInterface);
            } else if (i == 2) {
                IGetKeyCallback iGetKeyCallbackAsInterface2 = IGetKeyCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                cancelGetKey(iGetKeyCallbackAsInterface2);
            } else if (i == 3) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                IStoreUpgradedKeyCallback iStoreUpgradedKeyCallbackAsInterface = IStoreUpgradedKeyCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                storeUpgradedKeyAsync(bArrCreateByteArray, bArrCreateByteArray2, iStoreUpgradedKeyCallbackAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRegistration {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRegistration.DESCRIPTOR;
            }

            @Override // android.security.rkp.IRegistration
            public void getKey(int i, IGetKeyCallback iGetKeyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRegistration.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetKeyCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.rkp.IRegistration
            public void cancelGetKey(IGetKeyCallback iGetKeyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRegistration.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGetKeyCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.rkp.IRegistration
            public void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRegistration.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeStrongInterface(iStoreUpgradedKeyCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
