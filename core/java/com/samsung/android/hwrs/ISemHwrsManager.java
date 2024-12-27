package com.samsung.android.hwrs;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISemHwrsManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hwrs.ISemHwrsManager";

    boolean addShare(String str, String str2, String str3, String str4, String str5)
            throws RemoteException;

    boolean addUser(String str, String str2) throws RemoteException;

    boolean deleteUser(String str) throws RemoteException;

    String getKsmbdServerStatus() throws RemoteException;

    boolean ksmbdServerCleanup() throws RemoteException;

    boolean reloadKmbdServerConfiguration() throws RemoteException;

    boolean restartKsmbdServer() throws RemoteException;

    boolean startKsmbdServer() throws RemoteException;

    boolean stopKsmbdServer() throws RemoteException;

    public static class Default implements ISemHwrsManager {
        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean addShare(
                String aResName,
                String aResPath,
                String aUserName,
                String aGroupName,
                String aMaxConnections)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean addUser(String aUserName, String aUserPassword) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean deleteUser(String aUserName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean startKsmbdServer() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean stopKsmbdServer() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean restartKsmbdServer() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean reloadKmbdServerConfiguration() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean ksmbdServerCleanup() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public String getKsmbdServerStatus() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemHwrsManager {
        static final int TRANSACTION_addShare = 1;
        static final int TRANSACTION_addUser = 2;
        static final int TRANSACTION_deleteUser = 3;
        static final int TRANSACTION_getKsmbdServerStatus = 9;
        static final int TRANSACTION_ksmbdServerCleanup = 8;
        static final int TRANSACTION_reloadKmbdServerConfiguration = 7;
        static final int TRANSACTION_restartKsmbdServer = 6;
        static final int TRANSACTION_startKsmbdServer = 4;
        static final int TRANSACTION_stopKsmbdServer = 5;

        public Stub() {
            attachInterface(this, ISemHwrsManager.DESCRIPTOR);
        }

        public static ISemHwrsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemHwrsManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemHwrsManager)) {
                return (ISemHwrsManager) iin;
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
                    return "addShare";
                case 2:
                    return "addUser";
                case 3:
                    return "deleteUser";
                case 4:
                    return "startKsmbdServer";
                case 5:
                    return "stopKsmbdServer";
                case 6:
                    return "restartKsmbdServer";
                case 7:
                    return "reloadKmbdServerConfiguration";
                case 8:
                    return "ksmbdServerCleanup";
                case 9:
                    return "getKsmbdServerStatus";
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
                data.enforceInterface(ISemHwrsManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemHwrsManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    String _arg4 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = addShare(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = addUser(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result3 = deleteUser(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    boolean _result4 = startKsmbdServer();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    boolean _result5 = stopKsmbdServer();
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    boolean _result6 = restartKsmbdServer();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    boolean _result7 = reloadKmbdServerConfiguration();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    boolean _result8 = ksmbdServerCleanup();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    String _result9 = getKsmbdServerStatus();
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemHwrsManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemHwrsManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean addShare(
                    String aResName,
                    String aResPath,
                    String aUserName,
                    String aGroupName,
                    String aMaxConnections)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    _data.writeString(aResName);
                    _data.writeString(aResPath);
                    _data.writeString(aUserName);
                    _data.writeString(aGroupName);
                    _data.writeString(aMaxConnections);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean addUser(String aUserName, String aUserPassword) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    _data.writeString(aUserName);
                    _data.writeString(aUserPassword);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean deleteUser(String aUserName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    _data.writeString(aUserName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean startKsmbdServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean stopKsmbdServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean restartKsmbdServer() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean reloadKmbdServerConfiguration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean ksmbdServerCleanup() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public String getKsmbdServerStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
