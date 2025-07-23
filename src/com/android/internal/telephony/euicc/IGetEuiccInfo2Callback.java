package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IGetEuiccInfo2Callback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetEuiccInfo2Callback";

    public static class Default implements IGetEuiccInfo2Callback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IGetEuiccInfo2Callback
        public void onComplete(int i, byte[] bArr) throws RemoteException {
        }
    }

    void onComplete(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetEuiccInfo2Callback {
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
            attachInterface(this, IGetEuiccInfo2Callback.DESCRIPTOR);
        }

        public static IGetEuiccInfo2Callback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGetEuiccInfo2Callback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGetEuiccInfo2Callback)) {
                return (IGetEuiccInfo2Callback) queryLocalInterface;
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
                parcel.enforceInterface(IGetEuiccInfo2Callback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetEuiccInfo2Callback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onComplete(readInt, createByteArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGetEuiccInfo2Callback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetEuiccInfo2Callback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetEuiccInfo2Callback
            public void onComplete(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetEuiccInfo2Callback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
