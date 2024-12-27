package com.samsung.aasaservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAASA extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.aasaservice.IAASA";

    void getRemoveTargetList(int i) throws RemoteException;

    String getTrustedToday() throws RemoteException;

    void onReceivePolicyUpdateCompletion() throws RemoteException;

    String[] readInstallBlockedPKList() throws RemoteException;

    public static class Default implements IAASA {
        @Override // com.samsung.aasaservice.IAASA
        public void getRemoveTargetList(int type) throws RemoteException {
        }

        @Override // com.samsung.aasaservice.IAASA
        public String[] readInstallBlockedPKList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.aasaservice.IAASA
        public String getTrustedToday() throws RemoteException {
            return null;
        }

        @Override // com.samsung.aasaservice.IAASA
        public void onReceivePolicyUpdateCompletion() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAASA {
        static final int TRANSACTION_getRemoveTargetList = 1;
        static final int TRANSACTION_getTrustedToday = 3;
        static final int TRANSACTION_onReceivePolicyUpdateCompletion = 4;
        static final int TRANSACTION_readInstallBlockedPKList = 2;

        public Stub() {
            attachInterface(this, IAASA.DESCRIPTOR);
        }

        public static IAASA asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAASA.DESCRIPTOR);
            if (iin != null && (iin instanceof IAASA)) {
                return (IAASA) iin;
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
                    return "getRemoveTargetList";
                case 2:
                    return "readInstallBlockedPKList";
                case 3:
                    return "getTrustedToday";
                case 4:
                    return "onReceivePolicyUpdateCompletion";
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
                data.enforceInterface(IAASA.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAASA.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    getRemoveTargetList(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    String[] _result = readInstallBlockedPKList();
                    reply.writeNoException();
                    reply.writeStringArray(_result);
                    return true;
                case 3:
                    String _result2 = getTrustedToday();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 4:
                    onReceivePolicyUpdateCompletion();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAASA {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAASA.DESCRIPTOR;
            }

            @Override // com.samsung.aasaservice.IAASA
            public void getRemoveTargetList(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAASA.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.aasaservice.IAASA
            public String[] readInstallBlockedPKList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAASA.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.aasaservice.IAASA
            public String getTrustedToday() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAASA.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.aasaservice.IAASA
            public void onReceivePolicyUpdateCompletion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAASA.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
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
