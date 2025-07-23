package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.euicc.EuiccProfileInfo;

/* loaded from: classes4.dex */
public interface IGetProfileCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetProfileCallback";

    public static class Default implements IGetProfileCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IGetProfileCallback
        public void onComplete(int i, EuiccProfileInfo euiccProfileInfo) throws RemoteException {
        }
    }

    void onComplete(int i, EuiccProfileInfo euiccProfileInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetProfileCallback {
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
            attachInterface(this, IGetProfileCallback.DESCRIPTOR);
        }

        public static IGetProfileCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGetProfileCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGetProfileCallback)) {
                return (IGetProfileCallback) queryLocalInterface;
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
                parcel.enforceInterface(IGetProfileCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetProfileCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                EuiccProfileInfo euiccProfileInfo = (EuiccProfileInfo) parcel.readTypedObject(EuiccProfileInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onComplete(readInt, euiccProfileInfo);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGetProfileCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetProfileCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetProfileCallback
            public void onComplete(int i, EuiccProfileInfo euiccProfileInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetProfileCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(euiccProfileInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
