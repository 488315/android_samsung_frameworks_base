package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.EmergencyRegistrationResult;

/* loaded from: classes4.dex */
public interface IWwanSelectorResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IWwanSelectorResultCallback";

    public static class Default implements IWwanSelectorResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IWwanSelectorResultCallback
        public void onComplete(EmergencyRegistrationResult emergencyRegistrationResult) throws RemoteException {
        }
    }

    void onComplete(EmergencyRegistrationResult emergencyRegistrationResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IWwanSelectorResultCallback {
        static final int TRANSACTION_onComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWwanSelectorResultCallback.DESCRIPTOR);
        }

        public static IWwanSelectorResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWwanSelectorResultCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWwanSelectorResultCallback)) {
                return (IWwanSelectorResultCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWwanSelectorResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWwanSelectorResultCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                EmergencyRegistrationResult emergencyRegistrationResult = (EmergencyRegistrationResult) parcel.readTypedObject(EmergencyRegistrationResult.CREATOR);
                parcel.enforceNoDataAvail();
                onComplete(emergencyRegistrationResult);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWwanSelectorResultCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWwanSelectorResultCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IWwanSelectorResultCallback
            public void onComplete(EmergencyRegistrationResult emergencyRegistrationResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWwanSelectorResultCallback.DESCRIPTOR);
                    obtain.writeTypedObject(emergencyRegistrationResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
