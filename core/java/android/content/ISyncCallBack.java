package android.content;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface ISyncCallBack extends IInterface {
    public static final String DESCRIPTOR = "android.content.ISyncCallBack";

    void deletePersonaData(int i) throws RemoteException;

    void doSync(String str, int i) throws RemoteException;

    public static class Default implements ISyncCallBack {
        @Override // android.content.ISyncCallBack
        public void doSync(String syncerName, int userId) throws RemoteException {
        }

        @Override // android.content.ISyncCallBack
        public void deletePersonaData(int personaId) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISyncCallBack {
        static final int TRANSACTION_deletePersonaData = 2;
        static final int TRANSACTION_doSync = 1;

        public Stub() {
            attachInterface(this, ISyncCallBack.DESCRIPTOR);
        }

        public static ISyncCallBack asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISyncCallBack.DESCRIPTOR);
            if (iin != null && (iin instanceof ISyncCallBack)) {
                return (ISyncCallBack) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "doSync";
                case 2:
                    return "deletePersonaData";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISyncCallBack.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISyncCallBack.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            doSync(_arg0, _arg1);
                            reply.writeNoException();
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            deletePersonaData(_arg02);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISyncCallBack {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISyncCallBack.DESCRIPTOR;
            }

            @Override // android.content.ISyncCallBack
            public void doSync(String syncerName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISyncCallBack.DESCRIPTOR);
                    _data.writeString(syncerName);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.ISyncCallBack
            public void deletePersonaData(int personaId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISyncCallBack.DESCRIPTOR);
                    _data.writeInt(personaId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
