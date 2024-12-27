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

    void setInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException;

    void setListenOffloading(int i, int i2, int i3, int i4) throws RemoteException;

    void setMsMiceInfo(int i, String str, String str2) throws RemoteException;

    void setPreparedAccountPin(int i, String str, String str2, String str3, String str4, ISemWifiP2pCallback iSemWifiP2pCallback) throws RemoteException;

    void setScreenSharing(boolean z) throws RemoteException;

    void unsetAllInUsePackage(String str) throws RemoteException;

    void unsetInUsePackage(String str, String str2, String str3, boolean z) throws RemoteException;

    public static class Default implements ISemWifiP2pManager {
        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public boolean isP2pConnected() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setMsMiceInfo(int capability, String name, String ipAddr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setScreenSharing(boolean set) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setPreparedAccountPin(int type, String pin, String hexEncData, String hexIv, String hashedAccount, ISemWifiP2pCallback callback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setListenOffloading(int channel, int period, int interval, int count) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void controlOpenWifiScanTimer(int control) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public List<String> getInUsePackageList(String type) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void setInUsePackage(String type, String ctxPkg, String pkg, boolean reqNextAction) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void unsetInUsePackage(String type, String ctxPkg, String pkg, boolean reqNextAction) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void unsetAllInUsePackage(String type) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void removeClient(String deviceAddress, ISemWifiP2pCallback callback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void discoverPeers(int channelNum, ISemWifiP2pCallback callback) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public MacAddress getP2pFactoryMacAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public SemWifiP2pDevice getSemWifiP2pDevice(String deviceAddress) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public int[] getChannelsMhzForBand(int band) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public boolean isP2pSoftApConcurrencySupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public boolean disconnectApBlockAutojoin(boolean block) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public long getP2pFeature() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
        public void factoryReset() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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
        static final int TRANSACTION_setInUsePackage = 8;
        static final int TRANSACTION_setListenOffloading = 5;
        static final int TRANSACTION_setMsMiceInfo = 2;
        static final int TRANSACTION_setPreparedAccountPin = 4;
        static final int TRANSACTION_setScreenSharing = 3;
        static final int TRANSACTION_unsetAllInUsePackage = 10;
        static final int TRANSACTION_unsetInUsePackage = 9;

        public Stub() {
            attachInterface(this, ISemWifiP2pManager.DESCRIPTOR);
        }

        public static ISemWifiP2pManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiP2pManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiP2pManager)) {
                return (ISemWifiP2pManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemWifiP2pManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemWifiP2pManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = isP2pConnected();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    setMsMiceInfo(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setScreenSharing(_arg02);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    String _arg12 = data.readString();
                    String _arg22 = data.readString();
                    String _arg3 = data.readString();
                    String _arg4 = data.readString();
                    ISemWifiP2pCallback _arg5 = ISemWifiP2pCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setPreparedAccountPin(_arg03, _arg12, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg04 = data.readInt();
                    int _arg13 = data.readInt();
                    int _arg23 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    setListenOffloading(_arg04, _arg13, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    controlOpenWifiScanTimer(_arg05);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result2 = getInUsePackageList(_arg06);
                    reply.writeNoException();
                    reply.writeStringList(_result2);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    String _arg14 = data.readString();
                    String _arg24 = data.readString();
                    boolean _arg33 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInUsePackage(_arg07, _arg14, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    String _arg15 = data.readString();
                    String _arg25 = data.readString();
                    boolean _arg34 = data.readBoolean();
                    data.enforceNoDataAvail();
                    unsetInUsePackage(_arg08, _arg15, _arg25, _arg34);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    unsetAllInUsePackage(_arg09);
                    reply.writeNoException();
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    ISemWifiP2pCallback _arg16 = ISemWifiP2pCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeClient(_arg010, _arg16);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    ISemWifiP2pCallback _arg17 = ISemWifiP2pCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    discoverPeers(_arg011, _arg17);
                    reply.writeNoException();
                    return true;
                case 13:
                    MacAddress _result3 = getP2pFactoryMacAddress();
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 14:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    SemWifiP2pDevice _result4 = getSemWifiP2pDevice(_arg012);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 15:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result5 = getChannelsMhzForBand(_arg013);
                    reply.writeNoException();
                    reply.writeIntArray(_result5);
                    return true;
                case 16:
                    boolean _result6 = isP2pSoftApConcurrencySupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 17:
                    boolean _arg014 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result7 = disconnectApBlockAutojoin(_arg014);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 18:
                    long _result8 = getP2pFeature();
                    reply.writeNoException();
                    reply.writeLong(_result8);
                    return true;
                case 19:
                    factoryReset();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemWifiP2pManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setMsMiceInfo(int capability, String name, String ipAddr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeInt(capability);
                    _data.writeString(name);
                    _data.writeString(ipAddr);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setScreenSharing(boolean set) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeBoolean(set);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setPreparedAccountPin(int type, String pin, String hexEncData, String hexIv, String hashedAccount, ISemWifiP2pCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(pin);
                    _data.writeString(hexEncData);
                    _data.writeString(hexIv);
                    _data.writeString(hashedAccount);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setListenOffloading(int channel, int period, int interval, int count) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeInt(channel);
                    _data.writeInt(period);
                    _data.writeInt(interval);
                    _data.writeInt(count);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void controlOpenWifiScanTimer(int control) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeInt(control);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public List<String> getInUsePackageList(String type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeString(type);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void setInUsePackage(String type, String ctxPkg, String pkg, boolean reqNextAction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(ctxPkg);
                    _data.writeString(pkg);
                    _data.writeBoolean(reqNextAction);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void unsetInUsePackage(String type, String ctxPkg, String pkg, boolean reqNextAction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(ctxPkg);
                    _data.writeString(pkg);
                    _data.writeBoolean(reqNextAction);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void unsetAllInUsePackage(String type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeString(type);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void removeClient(String deviceAddress, ISemWifiP2pCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeString(deviceAddress);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void discoverPeers(int channelNum, ISemWifiP2pCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeInt(channelNum);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public MacAddress getP2pFactoryMacAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    MacAddress _result = (MacAddress) _reply.readTypedObject(MacAddress.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public SemWifiP2pDevice getSemWifiP2pDevice(String deviceAddress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeString(deviceAddress);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    SemWifiP2pDevice _result = (SemWifiP2pDevice) _reply.readTypedObject(SemWifiP2pDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public int[] getChannelsMhzForBand(int band) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeInt(band);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean isP2pSoftApConcurrencySupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public boolean disconnectApBlockAutojoin(boolean block) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    _data.writeBoolean(block);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public long getP2pFeature() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pManager
            public void factoryReset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiP2pManager.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 18;
        }
    }
}
