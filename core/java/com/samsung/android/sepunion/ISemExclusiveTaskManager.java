package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISemExclusiveTaskManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.ISemExclusiveTaskManager";

    List<String> getExclusiveTaskList(String str) throws RemoteException;

    public static class Default implements ISemExclusiveTaskManager {
        @Override // com.samsung.android.sepunion.ISemExclusiveTaskManager
        public List<String> getExclusiveTaskList(String taskName) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemExclusiveTaskManager {
        static final int TRANSACTION_getExclusiveTaskList = 1;

        public Stub() {
            attachInterface(this, ISemExclusiveTaskManager.DESCRIPTOR);
        }

        public static ISemExclusiveTaskManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemExclusiveTaskManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemExclusiveTaskManager)) {
                return (ISemExclusiveTaskManager) iin;
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
                    return "getExclusiveTaskList";
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
                data.enforceInterface(ISemExclusiveTaskManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemExclusiveTaskManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result = getExclusiveTaskList(_arg0);
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemExclusiveTaskManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemExclusiveTaskManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.ISemExclusiveTaskManager
            public List<String> getExclusiveTaskList(String taskName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExclusiveTaskManager.DESCRIPTOR);
                    _data.writeString(taskName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
