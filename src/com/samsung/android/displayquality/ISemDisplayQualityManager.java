package com.samsung.android.displayquality;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemDisplayQualityManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.displayquality.ISemDisplayQualityManager";

    public static class Default implements ISemDisplayQualityManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
        public void enhanceDisplayOutdoorVisibilityByLux(int i) throws RemoteException {
        }

        @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
        public void setAdaptiveSync(boolean z) throws RemoteException {
        }
    }

    void enhanceDisplayOutdoorVisibilityByLux(int i) throws RemoteException;

    void setAdaptiveSync(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemDisplayQualityManager {
        static final int TRANSACTION_enhanceDisplayOutdoorVisibilityByLux = 1;
        static final int TRANSACTION_setAdaptiveSync = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemDisplayQualityManager.DESCRIPTOR);
        }

        public static ISemDisplayQualityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemDisplayQualityManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemDisplayQualityManager)) {
                return (ISemDisplayQualityManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "enhanceDisplayOutdoorVisibilityByLux";
            }
            if (i != 2) {
                return null;
            }
            return "setAdaptiveSync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemDisplayQualityManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemDisplayQualityManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                enhanceDisplayOutdoorVisibilityByLux(i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setAdaptiveSync(z);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemDisplayQualityManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemDisplayQualityManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
            public void enhanceDisplayOutdoorVisibilityByLux(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplayQualityManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.displayquality.ISemDisplayQualityManager
            public void setAdaptiveSync(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemDisplayQualityManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
