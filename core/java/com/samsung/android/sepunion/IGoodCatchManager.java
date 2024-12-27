package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sepunion.IGoodCatchDispatcher;
import java.util.List;

/* loaded from: classes6.dex */
public interface IGoodCatchManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IGoodCatchManager";

    List<String> getSelectedSettingKey() throws RemoteException;

    void registerListener(String str, String[] strArr, IGoodCatchDispatcher iGoodCatchDispatcher, IBinder iBinder) throws RemoteException;

    void update(String[] strArr) throws RemoteException;

    public static class Default implements IGoodCatchManager {
        @Override // com.samsung.android.sepunion.IGoodCatchManager
        public void registerListener(String module, String[] function, IGoodCatchDispatcher sd, IBinder cb) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGoodCatchManager
        public void update(String[] data) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGoodCatchManager
        public List<String> getSelectedSettingKey() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGoodCatchManager {
        static final int TRANSACTION_getSelectedSettingKey = 3;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_update = 2;

        public Stub() {
            attachInterface(this, IGoodCatchManager.DESCRIPTOR);
        }

        public static IGoodCatchManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGoodCatchManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IGoodCatchManager)) {
                return (IGoodCatchManager) iin;
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
                    return "registerListener";
                case 2:
                    return "update";
                case 3:
                    return "getSelectedSettingKey";
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
                data.enforceInterface(IGoodCatchManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGoodCatchManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String[] _arg1 = data.createStringArray();
                    IGoodCatchDispatcher _arg2 = IGoodCatchDispatcher.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg3 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    registerListener(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    String[] _arg02 = data.createStringArray();
                    data.enforceNoDataAvail();
                    update(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    List<String> _result = getSelectedSettingKey();
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGoodCatchManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGoodCatchManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IGoodCatchManager
            public void registerListener(String module, String[] function, IGoodCatchDispatcher sd, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGoodCatchManager.DESCRIPTOR);
                    _data.writeString(module);
                    _data.writeStringArray(function);
                    _data.writeStrongInterface(sd);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGoodCatchManager
            public void update(String[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGoodCatchManager.DESCRIPTOR);
                    _data.writeStringArray(data);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGoodCatchManager
            public List<String> getSelectedSettingKey() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGoodCatchManager.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
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
            return 2;
        }
    }
}
