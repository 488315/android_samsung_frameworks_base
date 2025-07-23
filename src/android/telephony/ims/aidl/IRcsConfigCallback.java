package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IRcsConfigCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IRcsConfigCallback";

    public static class Default implements IRcsConfigCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IRcsConfigCallback
        public void onAutoConfigurationErrorReceived(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsConfigCallback
        public void onConfigurationChanged(byte[] bArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsConfigCallback
        public void onConfigurationReset() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsConfigCallback
        public void onPreProvisioningReceived(byte[] bArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsConfigCallback
        public void onRemoved() throws RemoteException {
        }
    }

    void onAutoConfigurationErrorReceived(int i, String str) throws RemoteException;

    void onConfigurationChanged(byte[] bArr) throws RemoteException;

    void onConfigurationReset() throws RemoteException;

    void onPreProvisioningReceived(byte[] bArr) throws RemoteException;

    void onRemoved() throws RemoteException;

    public static abstract class Stub extends Binder implements IRcsConfigCallback {
        static final int TRANSACTION_onAutoConfigurationErrorReceived = 2;
        static final int TRANSACTION_onConfigurationChanged = 1;
        static final int TRANSACTION_onConfigurationReset = 3;
        static final int TRANSACTION_onPreProvisioningReceived = 5;
        static final int TRANSACTION_onRemoved = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IRcsConfigCallback.DESCRIPTOR);
        }

        public static IRcsConfigCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRcsConfigCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRcsConfigCallback)) {
                return (IRcsConfigCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConfigurationChanged";
            }
            if (i == 2) {
                return "onAutoConfigurationErrorReceived";
            }
            if (i == 3) {
                return "onConfigurationReset";
            }
            if (i == 4) {
                return "onRemoved";
            }
            if (i != 5) {
                return null;
            }
            return "onPreProvisioningReceived";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRcsConfigCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRcsConfigCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onConfigurationChanged(createByteArray);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onAutoConfigurationErrorReceived(readInt, readString);
            } else if (i == 3) {
                onConfigurationReset();
            } else if (i == 4) {
                onRemoved();
            } else if (i == 5) {
                byte[] createByteArray2 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onPreProvisioningReceived(createByteArray2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRcsConfigCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRcsConfigCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onConfigurationChanged(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRcsConfigCallback.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onAutoConfigurationErrorReceived(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRcsConfigCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onConfigurationReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRcsConfigCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onRemoved() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRcsConfigCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsConfigCallback
            public void onPreProvisioningReceived(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRcsConfigCallback.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
