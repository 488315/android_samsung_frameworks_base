package com.android.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IRealTimeTokenService extends IInterface {
    public static final String DESCRIPTOR = "com.android.server.IRealTimeTokenService";

    int checkTokenInfoExpiry(long j) throws RemoteException;

    int registerTokenInfo(long j, long j2) throws RemoteException;

    int unregisterAllTokenInfo() throws RemoteException;

    int unregisterTokenInfo(long j) throws RemoteException;

    public static class Default implements IRealTimeTokenService {
        @Override // com.android.server.IRealTimeTokenService
        public int registerTokenInfo(long tag, long expiry) throws RemoteException {
            return 0;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int checkTokenInfoExpiry(long tag) throws RemoteException {
            return 0;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int unregisterTokenInfo(long tag) throws RemoteException {
            return 0;
        }

        @Override // com.android.server.IRealTimeTokenService
        public int unregisterAllTokenInfo() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRealTimeTokenService {
        static final int TRANSACTION_checkTokenInfoExpiry = 2;
        static final int TRANSACTION_registerTokenInfo = 1;
        static final int TRANSACTION_unregisterAllTokenInfo = 4;
        static final int TRANSACTION_unregisterTokenInfo = 3;

        public Stub() {
            attachInterface(this, IRealTimeTokenService.DESCRIPTOR);
        }

        public static IRealTimeTokenService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRealTimeTokenService.DESCRIPTOR);
            if (iin != null && (iin instanceof IRealTimeTokenService)) {
                return (IRealTimeTokenService) iin;
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
                    return "registerTokenInfo";
                case 2:
                    return "checkTokenInfoExpiry";
                case 3:
                    return "unregisterTokenInfo";
                case 4:
                    return "unregisterAllTokenInfo";
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
                data.enforceInterface(IRealTimeTokenService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRealTimeTokenService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    long _arg1 = data.readLong();
                    data.enforceNoDataAvail();
                    int _result = registerTokenInfo(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    long _arg02 = data.readLong();
                    data.enforceNoDataAvail();
                    int _result2 = checkTokenInfoExpiry(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    long _arg03 = data.readLong();
                    data.enforceNoDataAvail();
                    int _result3 = unregisterTokenInfo(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    int _result4 = unregisterAllTokenInfo();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRealTimeTokenService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRealTimeTokenService.DESCRIPTOR;
            }

            @Override // com.android.server.IRealTimeTokenService
            public int registerTokenInfo(long tag, long expiry) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    _data.writeLong(tag);
                    _data.writeLong(expiry);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.server.IRealTimeTokenService
            public int checkTokenInfoExpiry(long tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    _data.writeLong(tag);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.server.IRealTimeTokenService
            public int unregisterTokenInfo(long tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    _data.writeLong(tag);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.server.IRealTimeTokenService
            public int unregisterAllTokenInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRealTimeTokenService.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
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
            return 3;
        }
    }
}
