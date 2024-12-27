package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface ISemMobileWipsPacketSender extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemMobileWipsPacketSender";

    boolean pingTcp(byte[] bArr, byte[] bArr2, int i, int i2, int i3) throws RemoteException;

    List<String> sendArp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    List<String> sendArpToSniffing(int i, byte[] bArr, byte[] bArr2, String str)
            throws RemoteException;

    int sendDhcp(int i, byte[] bArr, int i2, String str) throws RemoteException;

    byte[] sendDns(long[] jArr, byte[] bArr, byte[] bArr2, byte[] bArr3, String str, boolean z)
            throws RemoteException;

    boolean sendDnsQueries(
            long[] jArr, byte[] bArr, byte[] bArr2, String str, List<String> list, int i)
            throws RemoteException;

    List<String> sendIcmp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    boolean sendTcp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    public static class Default implements ISemMobileWipsPacketSender {
        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public List<String> sendArp(int timeoutMillis, byte[] gateway, byte[] myAddr, String myMac)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public List<String> sendArpToSniffing(
                int timeoutMillis, byte[] gateway, byte[] myAddr, String myMac)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public List<String> sendIcmp(
                int timeoutMillis, byte[] gateway, byte[] myAddr, String dstMac)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public int sendDhcp(int timeoutMillis, byte[] myAddr, int equalOption, String equalString)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public byte[] sendDns(
                long[] timeoutMillis,
                byte[] srcAddr,
                byte[] dstAddr,
                byte[] dnsMessage,
                String dstMac,
                boolean isUDP)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public boolean sendDnsQueries(
                long[] timeoutMillis,
                byte[] srcAddr,
                byte[] dstAddr,
                String dstMac,
                List<String> dnsMessages,
                int tcpIndex)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public boolean sendTcp(int timeoutMillis, byte[] gateway, byte[] myAddr, String myMac)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public boolean pingTcp(
                byte[] srcAddr, byte[] dstAddr, int dstPort, int ttl, int timeoutMillis)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemMobileWipsPacketSender {
        static final int TRANSACTION_pingTcp = 8;
        static final int TRANSACTION_sendArp = 1;
        static final int TRANSACTION_sendArpToSniffing = 2;
        static final int TRANSACTION_sendDhcp = 4;
        static final int TRANSACTION_sendDns = 5;
        static final int TRANSACTION_sendDnsQueries = 6;
        static final int TRANSACTION_sendIcmp = 3;
        static final int TRANSACTION_sendTcp = 7;

        public Stub() {
            attachInterface(this, ISemMobileWipsPacketSender.DESCRIPTOR);
        }

        public static ISemMobileWipsPacketSender asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemMobileWipsPacketSender.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemMobileWipsPacketSender)) {
                return (ISemMobileWipsPacketSender) iin;
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
                    return "sendArp";
                case 2:
                    return "sendArpToSniffing";
                case 3:
                    return "sendIcmp";
                case 4:
                    return "sendDhcp";
                case 5:
                    return "sendDns";
                case 6:
                    return "sendDnsQueries";
                case 7:
                    return "sendTcp";
                case 8:
                    return "pingTcp";
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
                data.enforceInterface(ISemMobileWipsPacketSender.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemMobileWipsPacketSender.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    byte[] _arg2 = data.createByteArray();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result = sendArp(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    byte[] _arg12 = data.createByteArray();
                    byte[] _arg22 = data.createByteArray();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result2 = sendArpToSniffing(_arg02, _arg12, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeStringList(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    byte[] _arg13 = data.createByteArray();
                    byte[] _arg23 = data.createByteArray();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result3 = sendIcmp(_arg03, _arg13, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeStringList(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    byte[] _arg14 = data.createByteArray();
                    int _arg24 = data.readInt();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = sendDhcp(_arg04, _arg14, _arg24, _arg34);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    long[] _arg05 = data.createLongArray();
                    byte[] _arg15 = data.createByteArray();
                    byte[] _arg25 = data.createByteArray();
                    byte[] _arg35 = data.createByteArray();
                    String _arg4 = data.readString();
                    boolean _arg5 = data.readBoolean();
                    data.enforceNoDataAvail();
                    byte[] _result5 = sendDns(_arg05, _arg15, _arg25, _arg35, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeByteArray(_result5);
                    return true;
                case 6:
                    long[] _arg06 = data.createLongArray();
                    byte[] _arg16 = data.createByteArray();
                    byte[] _arg26 = data.createByteArray();
                    String _arg36 = data.readString();
                    List<String> _arg42 = data.createStringArrayList();
                    int _arg52 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 =
                            sendDnsQueries(_arg06, _arg16, _arg26, _arg36, _arg42, _arg52);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    byte[] _arg17 = data.createByteArray();
                    byte[] _arg27 = data.createByteArray();
                    String _arg37 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result7 = sendTcp(_arg07, _arg17, _arg27, _arg37);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    byte[] _arg08 = data.createByteArray();
                    byte[] _arg18 = data.createByteArray();
                    int _arg28 = data.readInt();
                    int _arg38 = data.readInt();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = pingTcp(_arg08, _arg18, _arg28, _arg38, _arg43);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemMobileWipsPacketSender {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMobileWipsPacketSender.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendArp(
                    int timeoutMillis, byte[] gateway, byte[] myAddr, String myMac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeInt(timeoutMillis);
                    _data.writeByteArray(gateway);
                    _data.writeByteArray(myAddr);
                    _data.writeString(myMac);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendArpToSniffing(
                    int timeoutMillis, byte[] gateway, byte[] myAddr, String myMac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeInt(timeoutMillis);
                    _data.writeByteArray(gateway);
                    _data.writeByteArray(myAddr);
                    _data.writeString(myMac);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendIcmp(
                    int timeoutMillis, byte[] gateway, byte[] myAddr, String dstMac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeInt(timeoutMillis);
                    _data.writeByteArray(gateway);
                    _data.writeByteArray(myAddr);
                    _data.writeString(dstMac);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public int sendDhcp(
                    int timeoutMillis, byte[] myAddr, int equalOption, String equalString)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeInt(timeoutMillis);
                    _data.writeByteArray(myAddr);
                    _data.writeInt(equalOption);
                    _data.writeString(equalString);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public byte[] sendDns(
                    long[] timeoutMillis,
                    byte[] srcAddr,
                    byte[] dstAddr,
                    byte[] dnsMessage,
                    String dstMac,
                    boolean isUDP)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeLongArray(timeoutMillis);
                    _data.writeByteArray(srcAddr);
                    _data.writeByteArray(dstAddr);
                    _data.writeByteArray(dnsMessage);
                    _data.writeString(dstMac);
                    _data.writeBoolean(isUDP);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean sendDnsQueries(
                    long[] timeoutMillis,
                    byte[] srcAddr,
                    byte[] dstAddr,
                    String dstMac,
                    List<String> dnsMessages,
                    int tcpIndex)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeLongArray(timeoutMillis);
                    _data.writeByteArray(srcAddr);
                    _data.writeByteArray(dstAddr);
                    _data.writeString(dstMac);
                    _data.writeStringList(dnsMessages);
                    _data.writeInt(tcpIndex);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean sendTcp(int timeoutMillis, byte[] gateway, byte[] myAddr, String myMac)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeInt(timeoutMillis);
                    _data.writeByteArray(gateway);
                    _data.writeByteArray(myAddr);
                    _data.writeString(myMac);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean pingTcp(
                    byte[] srcAddr, byte[] dstAddr, int dstPort, int ttl, int timeoutMillis)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    _data.writeByteArray(srcAddr);
                    _data.writeByteArray(dstAddr);
                    _data.writeInt(dstPort);
                    _data.writeInt(ttl);
                    _data.writeInt(timeoutMillis);
                    this.mRemote.transact(8, _data, _reply, 0);
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
            return 7;
        }
    }
}
