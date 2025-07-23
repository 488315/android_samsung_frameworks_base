package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IFeatureProvisioningCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IFeatureProvisioningCallback";

    public static class Default implements IFeatureProvisioningCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
        public void onFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
        public void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException {
        }
    }

    void onFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException;

    void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IFeatureProvisioningCallback {
        static final int TRANSACTION_onFeatureProvisioningChanged = 1;
        static final int TRANSACTION_onRcsFeatureProvisioningChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IFeatureProvisioningCallback.DESCRIPTOR);
        }

        public static IFeatureProvisioningCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFeatureProvisioningCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFeatureProvisioningCallback)) {
                return (IFeatureProvisioningCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onFeatureProvisioningChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onRcsFeatureProvisioningChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFeatureProvisioningCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFeatureProvisioningCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onFeatureProvisioningChanged(readInt, readInt2, readBoolean);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onRcsFeatureProvisioningChanged(readInt3, readInt4, readBoolean2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFeatureProvisioningCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFeatureProvisioningCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public void onFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFeatureProvisioningCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFeatureProvisioningCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
