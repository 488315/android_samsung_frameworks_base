package com.samsung.android.wifi.p2p;

import android.net.MacAddress;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.p2p.ISemWifiP2pCallback;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemWifiP2pManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.p2p.ISemWifiP2pManager";

    public static class Default implements ISemWifiP2pManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void controlOpenWifiScanTimer(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public boolean disconnectApBlockAutojoin(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void discoverPeers(int i, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void factoryReset() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public int[] getChannelsMhzForBand(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public List<String> getInUsePackageList(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public MacAddress getP2pFactoryMacAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public long getP2pFeature() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public SemWifiP2pDevice getSemWifiP2pDevice(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public boolean isP2pConnected() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public boolean isP2pSoftApConcurrencySupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void removeClient(String str, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setAwareEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setListenOffloading(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setMsMiceInfo(int i, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setPreparedAccountPin(int i, String str, String str2, String str3, String str4, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setScreenSharing(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void unsetAllInUsePackage(String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void unsetInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException {
        }
    }

    void controlOpenWifiScanTimer(int i) throws RemoteException;

    boolean disconnectApBlockAutojoin(boolean z) throws RemoteException;

    void discoverPeers(int i, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException;

    void factoryReset() throws RemoteException;

    int[] getChannelsMhzForBand(int i) throws RemoteException;

    List<String> getInUsePackageList(String str) throws RemoteException;

    MacAddress getP2pFactoryMacAddress() throws RemoteException;

    long getP2pFeature() throws RemoteException;

    SemWifiP2pDevice getSemWifiP2pDevice(String str) throws RemoteException;

    boolean isP2pConnected() throws RemoteException;

    boolean isP2pSoftApConcurrencySupported() throws RemoteException;

    void removeClient(String str, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException;

    void setAwareEnabled(boolean z) throws RemoteException;

    void setInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException;

    void setListenOffloading(int i, int i2, int i3, int i4) throws RemoteException;

    void setMsMiceInfo(int i, String str, String str2) throws RemoteException;

    void setPreparedAccountPin(int i, String str, String str2, String str3, String str4, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException;

    void setScreenSharing(boolean z) throws RemoteException;

    void unsetAllInUsePackage(String str) throws RemoteException;

    void unsetInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiP2pManager {
        static final int TRANSACTION_controlOpenWifiScanTimer = 6;
        static final int TRANSACTION_disconnectApBlockAutojoin = 17;
        static final int TRANSACTION_discoverPeers = 12;
        static final int TRANSACTION_factoryReset = 19;
        static final int TRANSACTION_getChannelsMhzForBand = 15;
        static final int TRANSACTION_getInUsePackageList = 7;
        static final int TRANSACTION_getP2pFactoryMacAddress = 13;
        static final int TRANSACTION_getP2pFeature = 18;
        static final int TRANSACTION_getSemWifiP2pDevice = 14;
        static final int TRANSACTION_isP2pConnected = 1;
        static final int TRANSACTION_isP2pSoftApConcurrencySupported = 16;
        static final int TRANSACTION_removeClient = 11;
        static final int TRANSACTION_setAwareEnabled = 20;
        static final int TRANSACTION_setInUsePackage = 8;
        static final int TRANSACTION_setListenOffloading = 5;
        static final int TRANSACTION_setMsMiceInfo = 2;
        static final int TRANSACTION_setPreparedAccountPin = 4;
        static final int TRANSACTION_setScreenSharing = 3;
        static final int TRANSACTION_unsetAllInUsePackage = 10;
        static final int TRANSACTION_unsetInUsePackage = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }

        public Stub() {
            attachInterface(this, ISemWifiP2pManager.DESCRIPTOR);
        }

        public static ISemWifiP2pManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemWifiP2pManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemWifiP2pManager)) {
                return (ISemWifiP2pManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isP2pConnected";
                case 2:
                    return "setMsMiceInfo";
                case 3:
                    return "setScreenSharing";
                case 4:
                    return "setPreparedAccountPin";
                case 5:
                    return "setListenOffloading";
                case 6:
                    return "controlOpenWifiScanTimer";
                case 7:
                    return "getInUsePackageList";
                case 8:
                    return "setInUsePackage";
                case 9:
                    return "unsetInUsePackage";
                case 10:
                    return "unsetAllInUsePackage";
                case 11:
                    return "removeClient";
                case 12:
                    return "discoverPeers";
                case 13:
                    return "getP2pFactoryMacAddress";
                case 14:
                    return "getSemWifiP2pDevice";
                case 15:
                    return "getChannelsMhzForBand";
                case 16:
                    return "isP2pSoftApConcurrencySupported";
                case 17:
                    return "disconnectApBlockAutojoin";
                case 18:
                    return "getP2pFeature";
                case 19:
                    return "factoryReset";
                case 20:
                    return "setAwareEnabled";
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
                parcel.enforceInterface(ISemWifiP2pManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiP2pManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isP2pConnected = isP2pConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isP2pConnected);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMsMiceInfo(readInt, readString, readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreenSharing(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    ISemWifiP2pCallback asInterface = ISemWifiP2pCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPreparedAccountPin(readInt2, readString3, readString4, readString5, readString6, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setListenOffloading(readInt3, readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    controlOpenWifiScanTimer(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> inUsePackageList = getInUsePackageList(readString7);
                    parcel2.writeNoException();
                    parcel2.writeStringList(inUsePackageList);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInUsePackage(readString8, readString9, readString10, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    unsetInUsePackage(readString11, readString12, readString13, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsetAllInUsePackage(readString14);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString15 = parcel.readString();
                    ISemWifiP2pCallback asInterface2 = ISemWifiP2pCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClient(readString15, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    ISemWifiP2pCallback asInterface3 = ISemWifiP2pCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    discoverPeers(readInt8, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    MacAddress p2pFactoryMacAddress = getP2pFactoryMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(p2pFactoryMacAddress, 1);
                    return true;
                case 14:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemWifiP2pDevice semWifiP2pDevice = getSemWifiP2pDevice(readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semWifiP2pDevice, 1);
                    return true;
                case 15:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] channelsMhzForBand = getChannelsMhzForBand(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(channelsMhzForBand);
                    return true;
                case 16:
                    boolean isP2pSoftApConcurrencySupported = isP2pSoftApConcurrencySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isP2pSoftApConcurrencySupported);
                    return true;
                case 17:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean disconnectApBlockAutojoin = disconnectApBlockAutojoin(readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disconnectApBlockAutojoin);
                    return true;
                case 18:
                    long p2pFeature = getP2pFeature();
                    parcel2.writeNoException();
                    parcel2.writeLong(p2pFeature);
                    return true;
                case 19:
                    factoryReset();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAwareEnabled(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemWifiP2pManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiP2pManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean isP2pConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setMsMiceInfo(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setScreenSharing(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setPreparedAccountPin(int i, String str, String str2, String str3, String str4, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStrongInterface(iSemWifiP2pCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setListenOffloading(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void controlOpenWifiScanTimer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public List<String> getInUsePackageList(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void unsetInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void unsetAllInUsePackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void removeClient(String str, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSemWifiP2pCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void discoverPeers(int i, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSemWifiP2pCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public MacAddress getP2pFactoryMacAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MacAddress) obtain2.readTypedObject(MacAddress.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public SemWifiP2pDevice getSemWifiP2pDevice(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemWifiP2pDevice) obtain2.readTypedObject(SemWifiP2pDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public int[] getChannelsMhzForBand(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean isP2pSoftApConcurrencySupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean disconnectApBlockAutojoin(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public long getP2pFeature() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void factoryReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setAwareEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
