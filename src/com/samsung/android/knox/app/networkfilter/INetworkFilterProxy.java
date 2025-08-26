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

    public static class Default implements INetworkFilterProxy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public String[] getBrowserAppList() throws RemoteException {
            return null;
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
        public String getHttpProxythreadStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public List<String> getListener(String str) throws RemoteException {
            return null;
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
        public String getProxythreadStatus() throws RemoteException {
            return null;
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
        public String getV6ProxythreadStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isHttpProxyThreadAlive() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isHttpProxyThreadRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isProxyThreadAlive() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isProxyThreadRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isV6ProxyThreadAlive() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public boolean isV6ProxyThreadRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int registerRemoteProxyAddr(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int setConfig(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int startHttpProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int startProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int startV6ProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int stopHttpProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int stopProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public int stopV6ProxyServer() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
        public void updateApplicationInfo(String str, int i, String str2, String str3, int i2) throws RemoteException {
        }
    }

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
        static final int TRANSACTION_updateApplicationInfo = 26;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 25;
        }

        public Stub() {
            attachInterface(this, INetworkFilterProxy.DESCRIPTOR);
        }

        public static INetworkFilterProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INetworkFilterProxy.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetworkFilterProxy)) {
                return (INetworkFilterProxy) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "updateApplicationInfo";
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
                parcel.enforceInterface(INetworkFilterProxy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INetworkFilterProxy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int config = setConfig(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(config);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRegisterRemoteProxyAddr = registerRemoteProxyAddr(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterRemoteProxyAddr);
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> listener = getListener(string5);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listener);
                    return true;
                case 4:
                    int iStartProxyServer = startProxyServer();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartProxyServer);
                    return true;
                case 5:
                    int iStopProxyServer = stopProxyServer();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopProxyServer);
                    return true;
                case 6:
                    boolean zIsProxyThreadRunning = isProxyThreadRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxyThreadRunning);
                    return true;
                case 7:
                    String proxythreadStatus = getProxythreadStatus();
                    parcel2.writeNoException();
                    parcel2.writeString(proxythreadStatus);
                    return true;
                case 8:
                    boolean zIsProxyThreadAlive = isProxyThreadAlive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxyThreadAlive);
                    return true;
                case 9:
                    String localProxyAddress = getLocalProxyAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(localProxyAddress);
                    return true;
                case 10:
                    int localProxyPort = getLocalProxyPort();
                    parcel2.writeNoException();
                    parcel2.writeInt(localProxyPort);
                    return true;
                case 11:
                    int iStartV6ProxyServer = startV6ProxyServer();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartV6ProxyServer);
                    return true;
                case 12:
                    int iStopV6ProxyServer = stopV6ProxyServer();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopV6ProxyServer);
                    return true;
                case 13:
                    boolean zIsV6ProxyThreadRunning = isV6ProxyThreadRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsV6ProxyThreadRunning);
                    return true;
                case 14:
                    String v6ProxythreadStatus = getV6ProxythreadStatus();
                    parcel2.writeNoException();
                    parcel2.writeString(v6ProxythreadStatus);
                    return true;
                case 15:
                    boolean zIsV6ProxyThreadAlive = isV6ProxyThreadAlive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsV6ProxyThreadAlive);
                    return true;
                case 16:
                    String v6LocalProxyAddress = getV6LocalProxyAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(v6LocalProxyAddress);
                    return true;
                case 17:
                    int v6LocalProxyPort = getV6LocalProxyPort();
                    parcel2.writeNoException();
                    parcel2.writeInt(v6LocalProxyPort);
                    return true;
                case 18:
                    int iStartHttpProxyServer = startHttpProxyServer();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartHttpProxyServer);
                    return true;
                case 19:
                    int iStopHttpProxyServer = stopHttpProxyServer();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopHttpProxyServer);
                    return true;
                case 20:
                    boolean zIsHttpProxyThreadRunning = isHttpProxyThreadRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHttpProxyThreadRunning);
                    return true;
                case 21:
                    String httpProxythreadStatus = getHttpProxythreadStatus();
                    parcel2.writeNoException();
                    parcel2.writeString(httpProxythreadStatus);
                    return true;
                case 22:
                    boolean zIsHttpProxyThreadAlive = isHttpProxyThreadAlive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHttpProxyThreadAlive);
                    return true;
                case 23:
                    String httpLocalProxyAddress = getHttpLocalProxyAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(httpLocalProxyAddress);
                    return true;
                case 24:
                    int httpLocalProxyPort = getHttpLocalProxyPort();
                    parcel2.writeNoException();
                    parcel2.writeInt(httpLocalProxyPort);
                    return true;
                case 25:
                    String[] browserAppList = getBrowserAppList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(browserAppList);
                    return true;
                case 26:
                    String string6 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateApplicationInfo(string6, i3, string7, string8, i4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INetworkFilterProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INetworkFilterProxy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int setConfig(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int registerRemoteProxyAddr(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public List<String> getListener(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int startProxyServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int stopProxyServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isProxyThreadRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getProxythreadStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isProxyThreadAlive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getLocalProxyAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int getLocalProxyPort() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int startV6ProxyServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int stopV6ProxyServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isV6ProxyThreadRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getV6ProxythreadStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isV6ProxyThreadAlive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getV6LocalProxyAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int getV6LocalProxyPort() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int startHttpProxyServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int stopHttpProxyServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isHttpProxyThreadRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getHttpProxythreadStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public boolean isHttpProxyThreadAlive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String getHttpLocalProxyAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public int getHttpLocalProxyPort() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public String[] getBrowserAppList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.app.networkfilter.INetworkFilterProxy
            public void updateApplicationInfo(String str, int i, String str2, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(INetworkFilterProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
