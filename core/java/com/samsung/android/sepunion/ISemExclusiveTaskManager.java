package com.samsung.android.sepunion;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISemExclusiveTaskManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.ISemExclusiveTaskManager";

    List<String> getExclusiveTaskList(String str) throws RemoteException;

    public static class Default implements ISemExclusiveTaskManager {
        @Override // com.samsung.android.sepunion.ISemExclusiveTaskManager
        public List<String> getExclusiveTaskList(String taskName) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemExclusiveTaskManager {
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

        @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemExclusiveTaskManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemExclusiveTaskManager.DESCRIPTOR);
                    return true;
                default:
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
        }

        private static class Proxy implements ISemExclusiveTaskManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
