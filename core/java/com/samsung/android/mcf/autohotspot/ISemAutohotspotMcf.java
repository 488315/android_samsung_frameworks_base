package com.samsung.android.mcf.autohotspot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISemAutohotspotMcf extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf";

    int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4)
            throws RemoteException;

    int genericCommand(String str) throws RemoteException;

    int getMcfConnectedStatus(String str) throws RemoteException;

    int getMcfConnectedStatusFromScanResult(String str) throws RemoteException;

    List<String> getMcfScanDetail() throws RemoteException;

    int startMcfClientMHSDiscovery(boolean z) throws RemoteException;

    public static class Default implements ISemAutohotspotMcf {
        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public List<String> getMcfScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int startMcfClientMHSDiscovery(boolean enable) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int connectToMcfMHS(
                String deviceId,
                int type,
                int mmhidden,
                int mmSecurity,
                String mhs_wifi_mac,
                String Username,
                int ver)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int getMcfConnectedStatus(String mhs_mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int getMcfConnectedStatusFromScanResult(String mac) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int genericCommand(String cmd) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemAutohotspotMcf {
        static final int TRANSACTION_connectToMcfMHS = 3;
        static final int TRANSACTION_genericCommand = 6;
        static final int TRANSACTION_getMcfConnectedStatus = 4;
        static final int TRANSACTION_getMcfConnectedStatusFromScanResult = 5;
        static final int TRANSACTION_getMcfScanDetail = 1;
        static final int TRANSACTION_startMcfClientMHSDiscovery = 2;

        public Stub() {
            attachInterface(this, ISemAutohotspotMcf.DESCRIPTOR);
        }

        public static ISemAutohotspotMcf asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemAutohotspotMcf.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemAutohotspotMcf)) {
                return (ISemAutohotspotMcf) iin;
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
                    return "getMcfScanDetail";
                case 2:
                    return "startMcfClientMHSDiscovery";
                case 3:
                    return "connectToMcfMHS";
                case 4:
                    return "getMcfConnectedStatus";
                case 5:
                    return "getMcfConnectedStatusFromScanResult";
                case 6:
                    return "genericCommand";
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
                data.enforceInterface(ISemAutohotspotMcf.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemAutohotspotMcf.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<String> _result = getMcfScanDetail();
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                case 2:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result2 = startMcfClientMHSDiscovery(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    String _arg02 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    String _arg4 = data.readString();
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result3 =
                            connectToMcfMHS(_arg02, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = getMcfConnectedStatus(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    int _result5 = getMcfConnectedStatusFromScanResult(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    int _result6 = genericCommand(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemAutohotspotMcf {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemAutohotspotMcf.DESCRIPTOR;
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public List<String> getMcfScanDetail() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int startMcfClientMHSDiscovery(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int connectToMcfMHS(
                    String deviceId,
                    int type,
                    int mmhidden,
                    int mmSecurity,
                    String mhs_wifi_mac,
                    String Username,
                    int ver)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeInt(type);
                    _data.writeInt(mmhidden);
                    _data.writeInt(mmSecurity);
                    _data.writeString(mhs_wifi_mac);
                    _data.writeString(Username);
                    _data.writeInt(ver);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int getMcfConnectedStatus(String mhs_mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    _data.writeString(mhs_mac);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int getMcfConnectedStatusFromScanResult(String mac) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    _data.writeString(mac);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int genericCommand(String cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    _data.writeString(cmd);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
