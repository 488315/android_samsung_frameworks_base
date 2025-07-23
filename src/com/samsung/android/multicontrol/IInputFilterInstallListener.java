package com.samsung.android.multicontrol;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IInputFilterInstallListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multicontrol.IInputFilterInstallListener";

    public static class Default implements IInputFilterInstallListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onInstalled() throws RemoteException {
        }

        @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
        public void onUninstalled() throws RemoteException {
        }
    }

    void onInstalled() throws RemoteException;

    void onUninstalled() throws RemoteException;

    public static abstract class Stub extends Binder implements IInputFilterInstallListener {
        static final int TRANSACTION_onInstalled = 1;
        static final int TRANSACTION_onUninstalled = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IInputFilterInstallListener.DESCRIPTOR);
        }

        public static IInputFilterInstallListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputFilterInstallListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputFilterInstallListener)) {
                return (IInputFilterInstallListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onInstalled";
            }
            if (i != 2) {
                return null;
            }
            return "onUninstalled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputFilterInstallListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputFilterInstallListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onInstalled();
            } else if (i == 2) {
                onUninstalled();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputFilterInstallListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputFilterInstallListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
            public void onInstalled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputFilterInstallListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multicontrol.IInputFilterInstallListener
            public void onUninstalled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputFilterInstallListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
