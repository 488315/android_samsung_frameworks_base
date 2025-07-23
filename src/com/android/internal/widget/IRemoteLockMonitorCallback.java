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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteLockMonitorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteLockMonitorCallback)) {
                return (IRemoteLockMonitorCallback) queryLocalInterface;
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
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                int checkRemoteLockPassword = checkRemoteLockPassword(createByteArray);
                parcel2.writeNoException();
                parcel2.writeInt(checkRemoteLockPassword);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteLockMonitorCallback.DESCRIPTOR);
                    obtain.writeTypedObject(remoteLockInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IRemoteLockMonitorCallback
            public int checkRemoteLockPassword(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteLockMonitorCallback.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
