package com.samsung.android.wifi.stdp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.stdp.IStandardPlusCallback;

/* loaded from: classes6.dex */
public interface IStandardPlusManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.stdp.IStandardPlusManager";

    public static class Default implements IStandardPlusManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void disableUsdNearby(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void enableUsdNearby(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public boolean registerCallback(int i, IStandardPlusCallback iStandardPlusCallback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void startBleScan() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void stopBleAdvertising() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public void stopBleScan() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
        public boolean unregisterCallback(int i) throws RemoteException {
            return false;
        }
    }

    void disableUsdNearby(int i) throws RemoteException;

    void enableUsdNearby(int i) throws RemoteException;

    boolean registerCallback(int i, IStandardPlusCallback iStandardPlusCallback) throws RemoteException;

    void startBleScan() throws RemoteException;

    void stopBleAdvertising() throws RemoteException;

    void stopBleScan() throws RemoteException;

    boolean unregisterCallback(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IStandardPlusManager {
        static final int TRANSACTION_disableUsdNearby = 5;
        static final int TRANSACTION_enableUsdNearby = 4;
        static final int TRANSACTION_registerCallback = 6;
        static final int TRANSACTION_startBleScan = 1;
        static final int TRANSACTION_stopBleAdvertising = 3;
        static final int TRANSACTION_stopBleScan = 2;
        static final int TRANSACTION_unregisterCallback = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IStandardPlusManager.DESCRIPTOR);
        }

        public static IStandardPlusManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStandardPlusManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStandardPlusManager)) {
                return (IStandardPlusManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startBleScan";
                case 2:
                    return "stopBleScan";
                case 3:
                    return "stopBleAdvertising";
                case 4:
                    return "enableUsdNearby";
                case 5:
                    return "disableUsdNearby";
                case 6:
                    return "registerCallback";
                case 7:
                    return "unregisterCallback";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStandardPlusManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStandardPlusManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    startBleScan();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    stopBleScan();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    stopBleAdvertising();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableUsdNearby(readInt);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableUsdNearby(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    IStandardPlusCallback asInterface = IStandardPlusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerCallback = registerCallback(readInt3, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCallback);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean unregisterCallback = unregisterCallback(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterCallback);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IStandardPlusManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStandardPlusManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void startBleScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void stopBleScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void stopBleAdvertising() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void enableUsdNearby(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public void disableUsdNearby(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public boolean registerCallback(int i, IStandardPlusCallback iStandardPlusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iStandardPlusCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusManager
            public boolean unregisterCallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStandardPlusManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
