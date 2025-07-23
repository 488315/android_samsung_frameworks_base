package android.debug;

import android.debug.IAdbCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAdbManager extends IInterface {
    public static final String DESCRIPTOR = "android.debug.IAdbManager";

    public static class Default implements IAdbManager {
        @Override // android.debug.IAdbManager
        public void allowDebugging(boolean z, String str) throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void allowWirelessDebugging(boolean z, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.debug.IAdbManager
        public void clearDebuggingKeys() throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void denyDebugging() throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void denyWirelessDebugging() throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void disablePairing() throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void enablePairingByPairingCode() throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void enablePairingByQrCode(String str, String str2) throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public int getAdbWirelessPort() throws RemoteException {
            return 0;
        }

        @Override // android.debug.IAdbManager
        public FingerprintAndPairDevice[] getPairedDevices() throws RemoteException {
            return null;
        }

        @Override // android.debug.IAdbManager
        public boolean isAdbWifiQrSupported() throws RemoteException {
            return false;
        }

        @Override // android.debug.IAdbManager
        public boolean isAdbWifiSupported() throws RemoteException {
            return false;
        }

        @Override // android.debug.IAdbManager
        public void registerCallback(IAdbCallback iAdbCallback) throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void unpairDevice(String str) throws RemoteException {
        }

        @Override // android.debug.IAdbManager
        public void unregisterCallback(IAdbCallback iAdbCallback) throws RemoteException {
        }
    }

    void allowDebugging(boolean z, String str) throws RemoteException;

    void allowWirelessDebugging(boolean z, String str) throws RemoteException;

    void clearDebuggingKeys() throws RemoteException;

    void denyDebugging() throws RemoteException;

    void denyWirelessDebugging() throws RemoteException;

    void disablePairing() throws RemoteException;

    void enablePairingByPairingCode() throws RemoteException;

    void enablePairingByQrCode(String str, String str2) throws RemoteException;

    int getAdbWirelessPort() throws RemoteException;

    FingerprintAndPairDevice[] getPairedDevices() throws RemoteException;

    boolean isAdbWifiQrSupported() throws RemoteException;

    boolean isAdbWifiSupported() throws RemoteException;

    void registerCallback(IAdbCallback iAdbCallback) throws RemoteException;

    void unpairDevice(String str) throws RemoteException;

    void unregisterCallback(IAdbCallback iAdbCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAdbManager {
        static final int TRANSACTION_allowDebugging = 1;
        static final int TRANSACTION_allowWirelessDebugging = 4;
        static final int TRANSACTION_clearDebuggingKeys = 3;
        static final int TRANSACTION_denyDebugging = 2;
        static final int TRANSACTION_denyWirelessDebugging = 5;
        static final int TRANSACTION_disablePairing = 11;
        static final int TRANSACTION_enablePairingByPairingCode = 8;
        static final int TRANSACTION_enablePairingByQrCode = 9;
        static final int TRANSACTION_getAdbWirelessPort = 10;
        static final int TRANSACTION_getPairedDevices = 6;
        static final int TRANSACTION_isAdbWifiQrSupported = 13;
        static final int TRANSACTION_isAdbWifiSupported = 12;
        static final int TRANSACTION_registerCallback = 14;
        static final int TRANSACTION_unpairDevice = 7;
        static final int TRANSACTION_unregisterCallback = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, IAdbManager.DESCRIPTOR);
        }

        public static IAdbManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAdbManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAdbManager)) {
                return (IAdbManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "allowDebugging";
                case 2:
                    return "denyDebugging";
                case 3:
                    return "clearDebuggingKeys";
                case 4:
                    return "allowWirelessDebugging";
                case 5:
                    return "denyWirelessDebugging";
                case 6:
                    return "getPairedDevices";
                case 7:
                    return "unpairDevice";
                case 8:
                    return "enablePairingByPairingCode";
                case 9:
                    return "enablePairingByQrCode";
                case 10:
                    return "getAdbWirelessPort";
                case 11:
                    return "disablePairing";
                case 12:
                    return "isAdbWifiSupported";
                case 13:
                    return "isAdbWifiQrSupported";
                case 14:
                    return "registerCallback";
                case 15:
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
                parcel.enforceInterface(IAdbManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdbManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allowDebugging(readBoolean, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    denyDebugging();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    clearDebuggingKeys();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allowWirelessDebugging(readBoolean2, readString2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    denyWirelessDebugging();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    FingerprintAndPairDevice[] pairedDevices = getPairedDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pairedDevices, 1);
                    return true;
                case 7:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unpairDevice(readString3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    enablePairingByPairingCode();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enablePairingByQrCode(readString4, readString5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int adbWirelessPort = getAdbWirelessPort();
                    parcel2.writeNoException();
                    parcel2.writeInt(adbWirelessPort);
                    return true;
                case 11:
                    disablePairing();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean isAdbWifiSupported = isAdbWifiSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdbWifiSupported);
                    return true;
                case 13:
                    boolean isAdbWifiQrSupported = isAdbWifiQrSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdbWifiQrSupported);
                    return true;
                case 14:
                    IAdbCallback asInterface = IAdbCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IAdbCallback asInterface2 = IAdbCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAdbManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdbManager.DESCRIPTOR;
            }

            @Override // android.debug.IAdbManager
            public void allowDebugging(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void denyDebugging() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void clearDebuggingKeys() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void allowWirelessDebugging(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void denyWirelessDebugging() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public FingerprintAndPairDevice[] getPairedDevices() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FingerprintAndPairDevice[]) obtain2.createTypedArray(FingerprintAndPairDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void unpairDevice(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void enablePairingByPairingCode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void enablePairingByQrCode(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public int getAdbWirelessPort() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void disablePairing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public boolean isAdbWifiSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public boolean isAdbWifiQrSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void registerCallback(IAdbCallback iAdbCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAdbCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.debug.IAdbManager
            public void unregisterCallback(IAdbCallback iAdbCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdbManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAdbCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
