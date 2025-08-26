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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemWifiP2pManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemWifiP2pManager)) {
                return (ISemWifiP2pManager) iInterfaceQueryLocalInterface;
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
                    boolean zIsP2pConnected = isP2pConnected();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsP2pConnected);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMsMiceInfo(i3, string, string2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreenSharing(z);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    ISemWifiP2pCallback iSemWifiP2pCallbackAsInterface = ISemWifiP2pCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPreparedAccountPin(i4, string3, string4, string5, string6, iSemWifiP2pCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setListenOffloading(i5, i6, i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    controlOpenWifiScanTimer(i9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> inUsePackageList = getInUsePackageList(string7);
                    parcel2.writeNoException();
                    parcel2.writeStringList(inUsePackageList);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInUsePackage(string8, string9, string10, z2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    unsetInUsePackage(string11, string12, string13, z3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsetAllInUsePackage(string14);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string15 = parcel.readString();
                    ISemWifiP2pCallback iSemWifiP2pCallbackAsInterface2 = ISemWifiP2pCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClient(string15, iSemWifiP2pCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i10 = parcel.readInt();
                    ISemWifiP2pCallback iSemWifiP2pCallbackAsInterface3 = ISemWifiP2pCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    discoverPeers(i10, iSemWifiP2pCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    MacAddress p2pFactoryMacAddress = getP2pFactoryMacAddress();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(p2pFactoryMacAddress, 1);
                    return true;
                case 14:
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemWifiP2pDevice semWifiP2pDevice = getSemWifiP2pDevice(string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semWifiP2pDevice, 1);
                    return true;
                case 15:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] channelsMhzForBand = getChannelsMhzForBand(i11);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(channelsMhzForBand);
                    return true;
                case 16:
                    boolean zIsP2pSoftApConcurrencySupported = isP2pSoftApConcurrencySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsP2pSoftApConcurrencySupported);
                    return true;
                case 17:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zDisconnectApBlockAutojoin = disconnectApBlockAutojoin(z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisconnectApBlockAutojoin);
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
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAwareEnabled(z5);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setMsMiceInfo(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setScreenSharing(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setPreparedAccountPin(int i, String str, String str2, String str3, String str4, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeStrongInterface(iSemWifiP2pCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setListenOffloading(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void controlOpenWifiScanTimer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public List<String> getInUsePackageList(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void unsetInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void unsetAllInUsePackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void removeClient(String str, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSemWifiP2pCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void discoverPeers(int i, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSemWifiP2pCallback);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public MacAddress getP2pFactoryMacAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MacAddress) parcelObtain2.readTypedObject(MacAddress.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public SemWifiP2pDevice getSemWifiP2pDevice(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemWifiP2pDevice) parcelObtain2.readTypedObject(SemWifiP2pDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public int[] getChannelsMhzForBand(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean isP2pSoftApConcurrencySupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean disconnectApBlockAutojoin(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public long getP2pFeature() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void factoryReset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setAwareEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
