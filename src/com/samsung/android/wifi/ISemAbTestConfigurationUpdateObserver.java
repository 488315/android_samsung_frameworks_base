package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemAbTestConfigurationUpdateObserver extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver";

    public static class Default implements ISemAbTestConfigurationUpdateObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver
        public void notifyAbTestConfigUpdate(SemAbTestConfiguration semAbTestConfiguration) throws RemoteException {
        }
    }

    void notifyAbTestConfigUpdate(SemAbTestConfiguration semAbTestConfiguration) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemAbTestConfigurationUpdateObserver {
        static final int TRANSACTION_notifyAbTestConfigUpdate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
        }

        public static ISemAbTestConfigurationUpdateObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemAbTestConfigurationUpdateObserver)) {
                return (ISemAbTestConfigurationUpdateObserver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "notifyAbTestConfigUpdate";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemAbTestConfiguration semAbTestConfiguration = (SemAbTestConfiguration) parcel.readTypedObject(SemAbTestConfiguration.CREATOR);
                parcel.enforceNoDataAvail();
                notifyAbTestConfigUpdate(semAbTestConfiguration);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemAbTestConfigurationUpdateObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemAbTestConfigurationUpdateObserver.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver
            public void notifyAbTestConfigUpdate(SemAbTestConfiguration semAbTestConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semAbTestConfiguration, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
