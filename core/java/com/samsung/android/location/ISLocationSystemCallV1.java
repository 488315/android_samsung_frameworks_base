package com.samsung.android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISLocationSystemCallV1 extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationSystemCallV1";

    boolean isProviderEnabledForUser(String str, int i) throws RemoteException;

    boolean isUidForeground(int i) throws RemoteException;

    public static class Default implements ISLocationSystemCallV1 {
        @Override // com.samsung.android.location.ISLocationSystemCallV1
        public boolean isProviderEnabledForUser(String provider, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.location.ISLocationSystemCallV1
        public boolean isUidForeground(int uid) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISLocationSystemCallV1 {
        static final int TRANSACTION_isProviderEnabledForUser = 1;
        static final int TRANSACTION_isUidForeground = 2;

        public Stub() {
            attachInterface(this, ISLocationSystemCallV1.DESCRIPTOR);
        }

        public static ISLocationSystemCallV1 asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISLocationSystemCallV1.DESCRIPTOR);
            if (iin != null && (iin instanceof ISLocationSystemCallV1)) {
                return (ISLocationSystemCallV1) iin;
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
                    return "isProviderEnabledForUser";
                case 2:
                    return "isUidForeground";
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
                data.enforceInterface(ISLocationSystemCallV1.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISLocationSystemCallV1.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = isProviderEnabledForUser(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = isUidForeground(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISLocationSystemCallV1 {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationSystemCallV1.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationSystemCallV1
            public boolean isProviderEnabledForUser(String provider, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationSystemCallV1.DESCRIPTOR);
                    _data.writeString(provider);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationSystemCallV1
            public boolean isUidForeground(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationSystemCallV1.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(2, _data, _reply, 0);
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
            return 1;
        }
    }
}
