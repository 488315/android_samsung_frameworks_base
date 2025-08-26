package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.euicc.EuiccProfileInfo;

/* loaded from: classes4.dex */
public interface IGetAllProfilesCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetAllProfilesCallback";

    public static class Default implements IGetAllProfilesCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IGetAllProfilesCallback
        public void onComplete(int i, EuiccProfileInfo[] euiccProfileInfoArr) throws RemoteException {
        }
    }

    void onComplete(int i, EuiccProfileInfo[] euiccProfileInfoArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetAllProfilesCallback {
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
            attachInterface(this, IGetAllProfilesCallback.DESCRIPTOR);
        }

        public static IGetAllProfilesCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGetAllProfilesCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGetAllProfilesCallback)) {
                return (IGetAllProfilesCallback) iInterfaceQueryLocalInterface;
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
                parcel.enforceInterface(IGetAllProfilesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetAllProfilesCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                EuiccProfileInfo[] euiccProfileInfoArr = (EuiccProfileInfo[]) parcel.createTypedArray(EuiccProfileInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onComplete(i3, euiccProfileInfoArr);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGetAllProfilesCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetAllProfilesCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetAllProfilesCallback
            public void onComplete(int i, EuiccProfileInfo[] euiccProfileInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetAllProfilesCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(euiccProfileInfoArr, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
