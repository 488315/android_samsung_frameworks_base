package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface ISemMobileWipsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemMobileWipsService";

    void broadcastBcnEventAbort(String str, int i) throws RemoteException;

    void broadcastBcnIntervalEvent(
            String str, String str2, String str3, int i, int i2, long j, long j2)
            throws RemoteException;

    boolean checkMWIPS(String str, int i) throws RemoteException;

    void onDnsResponses(List<String> list, String str) throws RemoteException;

    void onScanResults(List<SemMobileWipsScanResult> list) throws RemoteException;

    boolean registerCallback(ISemMobileWipsFramework iSemMobileWipsFramework)
            throws RemoteException;

    boolean registerPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender)
            throws RemoteException;

    void sendMessage(Message message) throws RemoteException;

    boolean setCurrentBss(String str, String str2, String str3, int i, byte[] bArr)
            throws RemoteException;

    boolean unregisterCallback(ISemMobileWipsFramework iSemMobileWipsFramework)
            throws RemoteException;

    boolean unregisterPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender)
            throws RemoteException;

    void updateWifiChipInfo(String str, String str2) throws RemoteException;

    public static class Default implements ISemMobileWipsService {
        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean registerCallback(ISemMobileWipsFramework callback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean unregisterCallback(ISemMobileWipsFramework callback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void broadcastBcnIntervalEvent(
                String iface,
                String ssid,
                String bssid,
                int channel,
                int beaconInterval,
                long timestamp,
                long systemtime)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void broadcastBcnEventAbort(String iface, int abortReason) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean checkMWIPS(String bssid, int freq) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void sendMessage(Message msg) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void updateWifiChipInfo(String id, String value) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean setCurrentBss(
                String bssid, String ssid, String macAddress, int frequency, byte[] ies)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void onScanResults(List<SemMobileWipsScanResult> scanResults)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void onDnsResponses(List<String> dnsResponses, String dstMac)
                throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean registerPacketSender(ISemMobileWipsPacketSender packetSender)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean unregisterPacketSender(ISemMobileWipsPacketSender packetSender)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemMobileWipsService {
        static final int TRANSACTION_broadcastBcnEventAbort = 4;
        static final int TRANSACTION_broadcastBcnIntervalEvent = 3;
        static final int TRANSACTION_checkMWIPS = 5;
        static final int TRANSACTION_onDnsResponses = 10;
        static final int TRANSACTION_onScanResults = 9;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_registerPacketSender = 11;
        static final int TRANSACTION_sendMessage = 6;
        static final int TRANSACTION_setCurrentBss = 8;
        static final int TRANSACTION_unregisterCallback = 2;
        static final int TRANSACTION_unregisterPacketSender = 12;
        static final int TRANSACTION_updateWifiChipInfo = 7;

        public Stub() {
            attachInterface(this, ISemMobileWipsService.DESCRIPTOR);
        }

        public static ISemMobileWipsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemMobileWipsService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemMobileWipsService)) {
                return (ISemMobileWipsService) iin;
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
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "broadcastBcnIntervalEvent";
                case 4:
                    return "broadcastBcnEventAbort";
                case 5:
                    return "checkMWIPS";
                case 6:
                    return "sendMessage";
                case 7:
                    return "updateWifiChipInfo";
                case 8:
                    return "setCurrentBss";
                case 9:
                    return "onScanResults";
                case 10:
                    return "onDnsResponses";
                case 11:
                    return "registerPacketSender";
                case 12:
                    return "unregisterPacketSender";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemMobileWipsService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemMobileWipsService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ISemMobileWipsFramework _arg0 =
                            ISemMobileWipsFramework.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result = registerCallback(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    ISemMobileWipsFramework _arg02 =
                            ISemMobileWipsFramework.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result2 = unregisterCallback(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    long _arg5 = data.readLong();
                    long _arg6 = data.readLong();
                    data.enforceNoDataAvail();
                    broadcastBcnIntervalEvent(_arg03, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    broadcastBcnEventAbort(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = checkMWIPS(_arg05, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 6:
                    Message _arg06 = (Message) data.readTypedObject(Message.CREATOR);
                    data.enforceNoDataAvail();
                    sendMessage(_arg06);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    updateWifiChipInfo(_arg07, _arg14);
                    reply.writeNoException();
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg15 = data.readString();
                    String _arg22 = data.readString();
                    int _arg32 = data.readInt();
                    byte[] _arg42 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result4 = setCurrentBss(_arg08, _arg15, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 9:
                    List<SemMobileWipsScanResult> _arg09 =
                            data.createTypedArrayList(SemMobileWipsScanResult.CREATOR);
                    data.enforceNoDataAvail();
                    onScanResults(_arg09);
                    reply.writeNoException();
                    return true;
                case 10:
                    List<String> _arg010 = data.createStringArrayList();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    onDnsResponses(_arg010, _arg16);
                    reply.writeNoException();
                    return true;
                case 11:
                    ISemMobileWipsPacketSender _arg011 =
                            ISemMobileWipsPacketSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result5 = registerPacketSender(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 12:
                    ISemMobileWipsPacketSender _arg012 =
                            ISemMobileWipsPacketSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result6 = unregisterPacketSender(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemMobileWipsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMobileWipsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean registerCallback(ISemMobileWipsFramework callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean unregisterCallback(ISemMobileWipsFramework callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void broadcastBcnIntervalEvent(
                    String iface,
                    String ssid,
                    String bssid,
                    int channel,
                    int beaconInterval,
                    long timestamp,
                    long systemtime)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(ssid);
                    _data.writeString(bssid);
                    _data.writeInt(channel);
                    _data.writeInt(beaconInterval);
                    _data.writeLong(timestamp);
                    _data.writeLong(systemtime);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void broadcastBcnEventAbort(String iface, int abortReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(abortReason);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean checkMWIPS(String bssid, int freq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeString(bssid);
                    _data.writeInt(freq);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void sendMessage(Message msg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeTypedObject(msg, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void updateWifiChipInfo(String id, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(value);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean setCurrentBss(
                    String bssid, String ssid, String macAddress, int frequency, byte[] ies)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeString(bssid);
                    _data.writeString(ssid);
                    _data.writeString(macAddress);
                    _data.writeInt(frequency);
                    _data.writeByteArray(ies);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void onScanResults(List<SemMobileWipsScanResult> scanResults)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeTypedList(scanResults, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void onDnsResponses(List<String> dnsResponses, String dstMac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeStringList(dnsResponses);
                    _data.writeString(dstMac);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean registerPacketSender(ISemMobileWipsPacketSender packetSender)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeStrongInterface(packetSender);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean unregisterPacketSender(ISemMobileWipsPacketSender packetSender)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    _data.writeStrongInterface(packetSender);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
