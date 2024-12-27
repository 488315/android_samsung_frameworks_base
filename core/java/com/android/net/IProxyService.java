package com.android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IProxyService extends IInterface {
    public static final String DESCRIPTOR = "com.android.net.IProxyService";

    int getProxyPortForProfile(String str) throws RemoteException;

    String getProxythreadStatus(String str) throws RemoteException;

    void handleScreenunlock() throws RemoteException;

    boolean isProxyThreadAlive(String str) throws RemoteException;

    boolean isProxyThreadRunning(String str) throws RemoteException;

    void resetInterface(String str) throws RemoteException;

    String resolvePacFile(String str, String str2) throws RemoteException;

    boolean setMiscValueForPacProfile(int i, String str, String str2, int i2)
            throws RemoteException;

    void setPacFile(String str) throws RemoteException;

    boolean setPacFileForKnoxProfile(String str, String str2) throws RemoteException;

    boolean startPacSystemForKnoxProfile(String str) throws RemoteException;

    int startProxyServerForKnoxProfile(
            String str, int i, String str2, String str3, boolean z, String str4, int i2)
            throws RemoteException;

    boolean stopPacSystemForKnoxProfile(String str) throws RemoteException;

    void stopProxyServerForKnoxProfile(String str) throws RemoteException;

    public static class Default implements IProxyService {
        @Override // com.android.net.IProxyService
        public String resolvePacFile(String host, String url) throws RemoteException {
            return null;
        }

        @Override // com.android.net.IProxyService
        public void setPacFile(String scriptContents) throws RemoteException {}

        @Override // com.android.net.IProxyService
        public boolean startPacSystemForKnoxProfile(String profileName) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public boolean stopPacSystemForKnoxProfile(String profileName) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public int startProxyServerForKnoxProfile(
                String profileName,
                int proxyAuthType,
                String userName,
                String password,
                boolean isCredentialPredfined,
                String staticProxyServer,
                int staticProxyPort)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.net.IProxyService
        public void stopProxyServerForKnoxProfile(String profileName) throws RemoteException {}

        @Override // com.android.net.IProxyService
        public boolean setPacFileForKnoxProfile(String profileName, String scriptContents)
                throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public boolean setMiscValueForPacProfile(
                int netId, String profileName, String interfacename, int showScreen)
                throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public int getProxyPortForProfile(String profileName) throws RemoteException {
            return 0;
        }

        @Override // com.android.net.IProxyService
        public void handleScreenunlock() throws RemoteException {}

        @Override // com.android.net.IProxyService
        public void resetInterface(String profileName) throws RemoteException {}

        @Override // com.android.net.IProxyService
        public boolean isProxyThreadRunning(String profileName) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public String getProxythreadStatus(String profileName) throws RemoteException {
            return null;
        }

        @Override // com.android.net.IProxyService
        public boolean isProxyThreadAlive(String profileName) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IProxyService {
        static final int TRANSACTION_getProxyPortForProfile = 9;
        static final int TRANSACTION_getProxythreadStatus = 13;
        static final int TRANSACTION_handleScreenunlock = 10;
        static final int TRANSACTION_isProxyThreadAlive = 14;
        static final int TRANSACTION_isProxyThreadRunning = 12;
        static final int TRANSACTION_resetInterface = 11;
        static final int TRANSACTION_resolvePacFile = 1;
        static final int TRANSACTION_setMiscValueForPacProfile = 8;
        static final int TRANSACTION_setPacFile = 2;
        static final int TRANSACTION_setPacFileForKnoxProfile = 7;
        static final int TRANSACTION_startPacSystemForKnoxProfile = 3;
        static final int TRANSACTION_startProxyServerForKnoxProfile = 5;
        static final int TRANSACTION_stopPacSystemForKnoxProfile = 4;
        static final int TRANSACTION_stopProxyServerForKnoxProfile = 6;

        public Stub() {
            attachInterface(this, "com.android.net.IProxyService");
        }

        public static IProxyService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.android.net.IProxyService");
            if (iin != null && (iin instanceof IProxyService)) {
                return (IProxyService) iin;
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
                    return "resolvePacFile";
                case 2:
                    return "setPacFile";
                case 3:
                    return "startPacSystemForKnoxProfile";
                case 4:
                    return "stopPacSystemForKnoxProfile";
                case 5:
                    return "startProxyServerForKnoxProfile";
                case 6:
                    return "stopProxyServerForKnoxProfile";
                case 7:
                    return "setPacFileForKnoxProfile";
                case 8:
                    return "setMiscValueForPacProfile";
                case 9:
                    return "getProxyPortForProfile";
                case 10:
                    return "handleScreenunlock";
                case 11:
                    return "resetInterface";
                case 12:
                    return "isProxyThreadRunning";
                case 13:
                    return "getProxythreadStatus";
                case 14:
                    return "isProxyThreadAlive";
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
                data.enforceInterface("com.android.net.IProxyService");
            }
            if (code == 1598968902) {
                reply.writeString("com.android.net.IProxyService");
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    String _result = resolvePacFile(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    setPacFile(_arg02);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = startPacSystemForKnoxProfile(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result3 = stopPacSystemForKnoxProfile(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg12 = data.readInt();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    boolean _arg4 = data.readBoolean();
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 =
                            startProxyServerForKnoxProfile(
                                    _arg05, _arg12, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    stopProxyServerForKnoxProfile(_arg06);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result5 = setPacFileForKnoxProfile(_arg07, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    String _arg14 = data.readString();
                    String _arg22 = data.readString();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = setMiscValueForPacProfile(_arg08, _arg14, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    int _result7 = getProxyPortForProfile(_arg09);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 10:
                    handleScreenunlock();
                    reply.writeNoException();
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    data.enforceNoDataAvail();
                    resetInterface(_arg010);
                    reply.writeNoException();
                    return true;
                case 12:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result8 = isProxyThreadRunning(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    String _result9 = getProxythreadStatus(_arg012);
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 14:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result10 = isProxyThreadAlive(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProxyService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.android.net.IProxyService";
            }

            @Override // com.android.net.IProxyService
            public String resolvePacFile(String host, String url) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(host);
                    _data.writeString(url);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void setPacFile(String scriptContents) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(scriptContents);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean startPacSystemForKnoxProfile(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean stopPacSystemForKnoxProfile(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public int startProxyServerForKnoxProfile(
                    String profileName,
                    int proxyAuthType,
                    String userName,
                    String password,
                    boolean isCredentialPredfined,
                    String staticProxyServer,
                    int staticProxyPort)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    _data.writeInt(proxyAuthType);
                    _data.writeString(userName);
                    _data.writeString(password);
                    _data.writeBoolean(isCredentialPredfined);
                    _data.writeString(staticProxyServer);
                    _data.writeInt(staticProxyPort);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void stopProxyServerForKnoxProfile(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean setPacFileForKnoxProfile(String profileName, String scriptContents)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    _data.writeString(scriptContents);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean setMiscValueForPacProfile(
                    int netId, String profileName, String interfacename, int showScreen)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeInt(netId);
                    _data.writeString(profileName);
                    _data.writeString(interfacename);
                    _data.writeInt(showScreen);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public int getProxyPortForProfile(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void handleScreenunlock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void resetInterface(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean isProxyThreadRunning(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public String getProxythreadStatus(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean isProxyThreadAlive(String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.android.net.IProxyService");
                    _data.writeString(profileName);
                    this.mRemote.transact(14, _data, _reply, 0);
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
            return 13;
        }
    }
}
