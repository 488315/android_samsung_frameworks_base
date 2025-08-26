package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRemoteLockMonitorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IRemoteLockMonitorCallback";

    public static class Default implements IRemoteLockMonitorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.IRemoteLockMonitorCallback
        public void changeRemoteLockState(RemoteLockInfo remoteLockInfo) throws RemoteException {
        }

        @Override // com.android.internal.widget.IRemoteLockMonitorCallback
        public int checkRemoteLockPassword(byte[] bArr) throws RemoteException {
            return 0;
        }
    }

    void changeRemoteLockState(RemoteLockInfo remoteLockInfo) throws RemoteException;

    int checkRemoteLockPassword(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteLockMonitorCallback {
        static final int TRANSACTION_changeRemoteLockState = 1;
        static final int TRANSACTION_checkRemoteLockPassword = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IRemoteLockMonitorCallback.DESCRIPTOR);
        }

        public static IRemoteLockMonitorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteLockMonitorCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteLockMonitorCallback)) {
                return (IRemoteLockMonitorCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "changeRemoteLockState";
            }
            if (i != 2) {
                return null;
            }
            return "checkRemoteLockPassword";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteLockMonitorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteLockMonitorCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RemoteLockInfo remoteLockInfo = (RemoteLockInfo) parcel.readTypedObject(RemoteLockInfo.CREATOR);
                parcel.enforceNoDataAvail();
                changeRemoteLockState(remoteLockInfo);
            } else if (i == 2) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                int iCheckRemoteLockPassword = checkRemoteLockPassword(bArrCreateByteArray);
                parcel2.writeNoException();
                parcel2.writeInt(iCheckRemoteLockPassword);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteLockMonitorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLockMonitorCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IRemoteLockMonitorCallback
            public void changeRemoteLockState(RemoteLockInfo remoteLockInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLockMonitorCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteLockInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteLockMonitorCallback
            public int checkRemoteLockPassword(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLockMonitorCallback.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
