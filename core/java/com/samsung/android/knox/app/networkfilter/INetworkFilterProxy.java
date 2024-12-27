package com.samsung.android.knox.app.networkfilter;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface INetworkFilterProxy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.app.networkfilter.INetworkFilterProxy";

    String[] getBrowserAppList() throws RemoteException;

    String getHttpLocalProxyAddress() throws RemoteException;

    int getHttpLocalProxyPort() throws RemoteException;

    String getHttpProxythreadStatus() throws RemoteException;

    List<String> getListener(String str) throws RemoteException;

    String getLocalProxyAddress() throws RemoteException;

    int getLocalProxyPort() throws RemoteException;

    String getProxythreadStatus() throws RemoteException;

    String getV6LocalProxyAddress() throws RemoteException;

    int getV6LocalProxyPort() throws RemoteException;

    String getV6ProxythreadStatus() throws RemoteException;

    boolean isHttpProxyThreadAlive() throws RemoteException;

    boolean isHttpProxyThreadRunning() throws RemoteException;

    boolean isProxyThreadAlive() throws RemoteException;

    boolean isProxyThreadRunning() throws RemoteException;

    boolean isV6ProxyThreadAlive() throws RemoteException;

    boolean isV6ProxyThreadRunning() throws RemoteException;

    int registerRemoteProxyAddr(String str, String str2) throws RemoteException;

    int setConfig(String str, String str2) throws RemoteException;

    int startHttpProxyServer() throws RemoteException;

    int startProxyServer() throws RemoteException;

    int startV6ProxyServer() throws RemoteException;

    int stopHttpProxyServer() throws RemoteException;

    int stopProxyServer() throws RemoteException;

    int stopV6ProxyServer() throws RemoteException;

    void updateApplicationInfo(String str, int i, String str2, String str3, int i2) throws RemoteException;

    void vpnMessengerForDnsQuery(IBinder iBinder) throws RemoteException;

    public static class Default implements INetworkFilterProxy {
        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int setConfig(String profileName, String jsonConfig) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int registerRemoteProxyAddr(String profileName, String ipConfig) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public List<String> getListener(String profileName) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int startProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int stopProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isProxyThreadRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String getProxythreadStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isProxyThreadAlive() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String getLocalProxyAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int getLocalProxyPort() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int startV6ProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int stopV6ProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isV6ProxyThreadRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String getV6ProxythreadStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isV6ProxyThreadAlive() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String getV6LocalProxyAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int getV6LocalProxyPort() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int startHttpProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int stopHttpProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isHttpProxyThreadRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String getHttpProxythreadStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isHttpProxyThreadAlive() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String getHttpLocalProxyAddress() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int getHttpLocalProxyPort() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String[] getBrowserAppList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public void vpnMessengerForDnsQuery(IBinder binder) throws RemoteException {
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public void updateApplicationInfo(String packageName, int uid, String signature, String versionName, int versionCode) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkFilterProxy {
        static final int TRANSACTION_getBrowserAppList = 25;
        static final int TRANSACTION_getHttpLocalProxyAddress = 23;
        static final int TRANSACTION_getHttpLocalProxyPort = 24;
        static final int TRANSACTION_getHttpProxythreadStatus = 21;
        static final int TRANSACTION_getListener = 3;
        static final int TRANSACTION_getLocalProxyAddress = 9;
        static final int TRANSACTION_getLocalProxyPort = 10;
        static final int TRANSACTION_getProxythreadStatus = 7;
        static final int TRANSACTION_getV6LocalProxyAddress = 16;
        static final int TRANSACTION_getV6LocalProxyPort = 17;
        static final int TRANSACTION_getV6ProxythreadStatus = 14;
        static final int TRANSACTION_isHttpProxyThreadAlive = 22;
        static final int TRANSACTION_isHttpProxyThreadRunning = 20;
        static final int TRANSACTION_isProxyThreadAlive = 8;
        static final int TRANSACTION_isProxyThreadRunning = 6;
        static final int TRANSACTION_isV6ProxyThreadAlive = 15;
        static final int TRANSACTION_isV6ProxyThreadRunning = 13;
        static final int TRANSACTION_registerRemoteProxyAddr = 2;
        static final int TRANSACTION_setConfig = 1;
        static final int TRANSACTION_startHttpProxyServer = 18;
        static final int TRANSACTION_startProxyServer = 4;
        static final int TRANSACTION_startV6ProxyServer = 11;
        static final int TRANSACTION_stopHttpProxyServer = 19;
        static final int TRANSACTION_stopProxyServer = 5;
        static final int TRANSACTION_stopV6ProxyServer = 12;
        static final int TRANSACTION_updateApplicationInfo = 27;
        static final int TRANSACTION_vpnMessengerForDnsQuery = 26;

        public Stub() {
            attachInterface(this, INetworkFilterProxy.DESCRIPTOR);
        }

        public static INetworkFilterProxy asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INetworkFilterProxy.DESCRIPTOR);
            if (iin != null && (iin instanceof INetworkFilterProxy)) {
                return (INetworkFilterProxy) iin;
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
                    return "setConfig";
                case 2:
                    return "registerRemoteProxyAddr";
                case 3:
                    return "getListener";
                case 4:
                    return "startProxyServer";
                case 5:
                    return "stopProxyServer";
                case 6:
                    return "isProxyThreadRunning";
                case 7:
                    return "getProxythreadStatus";
                case 8:
                    return "isProxyThreadAlive";
                case 9:
                    return "getLocalProxyAddress";
                case 10:
                    return "getLocalProxyPort";
                case 11:
                    return "startV6ProxyServer";
                case 12:
                    return "stopV6ProxyServer";
                case 13:
                    return "isV6ProxyThreadRunning";
                case 14:
                    return "getV6ProxythreadStatus";
                case 15:
                    return "isV6ProxyThreadAlive";
                case 16:
                    return "getV6LocalProxyAddress";
                case 17:
                    return "getV6LocalProxyPort";
                case 18:
                    return "startHttpProxyServer";
                case 19:
                    return "stopHttpProxyServer";
                case 20:
                    return "isHttpProxyThreadRunning";
                case 21:
                    return "getHttpProxythreadStatus";
                case 22:
                    return "isHttpProxyThreadAlive";
                case 23:
                    return "getHttpLocalProxyAddress";
                case 24:
                    return "getHttpLocalProxyPort";
                case 25:
                    return "getBrowserAppList";
                case 26:
                    return "vpnMessengerForDnsQuery";
                case 27:
                    return "updateApplicationInfo";
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
                data.enforceInterface(INetworkFilterProxy.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(INetworkFilterProxy.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    int _result = setConfig(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    int _result2 = registerRemoteProxyAddr(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result3 = getListener(_arg03);
                    reply.writeNoException();
                    reply.writeStringList(_result3);
                    return true;
                case 4:
                    int _result4 = startProxyServer();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    int _result5 = stopProxyServer();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    boolean _result6 = isProxyThreadRunning();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    String _result7 = getProxythreadStatus();
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 8:
                    boolean _result8 = isProxyThreadAlive();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    String _result9 = getLocalProxyAddress();
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 10:
                    int _result10 = getLocalProxyPort();
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 11:
                    int _result11 = startV6ProxyServer();
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 12:
                    int _result12 = stopV6ProxyServer();
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 13:
                    boolean _result13 = isV6ProxyThreadRunning();
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 14:
                    String _result14 = getV6ProxythreadStatus();
                    reply.writeNoException();
                    reply.writeString(_result14);
                    return true;
                case 15:
                    boolean _result15 = isV6ProxyThreadAlive();
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 16:
                    String _result16 = getV6LocalProxyAddress();
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 17:
                    int _result17 = getV6LocalProxyPort();
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 18:
                    int _result18 = startHttpProxyServer();
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 19:
                    int _result19 = stopHttpProxyServer();
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 20:
                    boolean _result20 = isHttpProxyThreadRunning();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 21:
                    String _result21 = getHttpProxythreadStatus();
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 22:
                    boolean _result22 = isHttpProxyThreadAlive();
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 23:
                    String _result23 = getHttpLocalProxyAddress();
                    reply.writeNoException();
                    reply.writeString(_result23);
                    return true;
                case 24:
                    int _result24 = getHttpLocalProxyPort();
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case 25:
                    String[] _result25 = getBrowserAppList();
                    reply.writeNoException();
                    reply.writeStringArray(_result25);
                    return true;
                case 26:
                    IBinder _arg04 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    vpnMessengerForDnsQuery(_arg04);
                    return true;
                case 27:
                    String _arg05 = data.readString();
                    int _arg13 = data.readInt();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    updateApplicationInfo(_arg05, _arg13, _arg2, _arg3, _arg4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INetworkFilterProxy {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INetworkFilterProxy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int setConfig(String profileName, String jsonConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    _data.writeString(profileName);
                    _data.writeString(jsonConfig);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int registerRemoteProxyAddr(String profileName, String ipConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    _data.writeString(profileName);
                    _data.writeString(ipConfig);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public List<String> getListener(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    _data.writeString(profileName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int startProxyServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int stopProxyServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isProxyThreadRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getProxythreadStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isProxyThreadAlive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getLocalProxyAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int getLocalProxyPort() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int startV6ProxyServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int stopV6ProxyServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isV6ProxyThreadRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getV6ProxythreadStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isV6ProxyThreadAlive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getV6LocalProxyAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int getV6LocalProxyPort() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int startHttpProxyServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int stopHttpProxyServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isHttpProxyThreadRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getHttpProxythreadStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isHttpProxyThreadAlive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getHttpLocalProxyAddress() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int getHttpLocalProxyPort() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String[] getBrowserAppList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public void vpnMessengerForDnsQuery(IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public void updateApplicationInfo(String packageName, int uid, String signature, String versionName, int versionCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeString(signature);
                    _data.writeString(versionName);
                    _data.writeInt(versionCode);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 26;
        }
    }
}
