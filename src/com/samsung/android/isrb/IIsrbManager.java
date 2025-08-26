package com.samsung.android.isrb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIsrbManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.isrb.IIsrbManager";

    public static class Default implements IIsrbManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.isrb.IIsrbManager
        public boolean isBootCompleteState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.isrb.IIsrbManager
        public void setFakeTime() throws RemoteException {
        }

        @Override // com.samsung.android.isrb.IIsrbManager
        public void setIsrbEnable(boolean z) throws RemoteException {
        }
    }

    boolean isBootCompleteState() throws RemoteException;

    void setFakeTime() throws RemoteException;

    void setIsrbEnable(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IIsrbManager {
        static final int TRANSACTION_isBootCompleteState = 1;
        static final int TRANSACTION_setFakeTime = 3;
        static final int TRANSACTION_setIsrbEnable = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IIsrbManager.DESCRIPTOR);
        }

        public static IIsrbManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIsrbManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIsrbManager)) {
                return (IIsrbManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "isBootCompleteState";
            }
            if (i == 2) {
                return "setIsrbEnable";
            }
            if (i != 3) {
                return null;
            }
            return "setFakeTime";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIsrbManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIsrbManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean zIsBootCompleteState = isBootCompleteState();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsBootCompleteState);
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setIsrbEnable(z);
                parcel2.writeNoException();
            } else if (i == 3) {
                setFakeTime();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIsrbManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIsrbManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.isrb.IIsrbManager
            public boolean isBootCompleteState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIsrbManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.isrb.IIsrbManager
            public void setIsrbEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIsrbManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.isrb.IIsrbManager
            public void setFakeTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIsrbManager.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
