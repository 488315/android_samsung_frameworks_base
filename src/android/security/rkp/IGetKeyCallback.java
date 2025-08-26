package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGetKeyCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IGetKeyCallback";

    public static class Default implements IGetKeyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.rkp.IGetKeyCallback
        public void onCancel() throws RemoteException {
        }

        @Override // android.security.rkp.IGetKeyCallback
        public void onError(byte b, String str) throws RemoteException {
        }

        @Override // android.security.rkp.IGetKeyCallback
        public void onSuccess(RemotelyProvisionedKey remotelyProvisionedKey) throws RemoteException {
        }
    }

    public @interface ErrorCode {
        public static final byte ERROR_PENDING_INTERNET_CONNECTIVITY = 3;
        public static final byte ERROR_PERMANENT = 5;
        public static final byte ERROR_REQUIRES_SECURITY_PATCH = 2;
        public static final byte ERROR_UNKNOWN = 1;
    }

    void onCancel() throws RemoteException;

    void onError(byte b, String str) throws RemoteException;

    void onSuccess(RemotelyProvisionedKey remotelyProvisionedKey) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetKeyCallback {
        static final int TRANSACTION_onCancel = 2;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGetKeyCallback.DESCRIPTOR);
        }

        public static IGetKeyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGetKeyCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGetKeyCallback)) {
                return (IGetKeyCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i == 2) {
                return "onCancel";
            }
            if (i != 3) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGetKeyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetKeyCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RemotelyProvisionedKey remotelyProvisionedKey = (RemotelyProvisionedKey) parcel.readTypedObject(RemotelyProvisionedKey.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(remotelyProvisionedKey);
            } else if (i == 2) {
                onCancel();
            } else if (i == 3) {
                byte b = parcel.readByte();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(b, string);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGetKeyCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetKeyCallback.DESCRIPTOR;
            }

            @Override // android.security.rkp.IGetKeyCallback
            public void onSuccess(RemotelyProvisionedKey remotelyProvisionedKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetKeyCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remotelyProvisionedKey, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.rkp.IGetKeyCallback
            public void onCancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetKeyCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.rkp.IGetKeyCallback
            public void onError(byte b, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetKeyCallback.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
