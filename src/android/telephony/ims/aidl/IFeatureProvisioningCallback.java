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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFeatureProvisioningCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFeatureProvisioningCallback)) {
                return (IFeatureProvisioningCallback) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onFeatureProvisioningChanged(i3, i4, z);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onRcsFeatureProvisioningChanged(i5, i6, z2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFeatureProvisioningCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
            public void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFeatureProvisioningCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
