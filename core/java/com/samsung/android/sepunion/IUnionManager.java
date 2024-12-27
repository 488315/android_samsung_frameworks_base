package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IUnionManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IUnionManager";

    IBinder getSemSystemService(String str, Bundle bundle) throws RemoteException;

    void setDumpEnabled(String str, String str2) throws RemoteException;

    public static class Default implements IUnionManager {
        @Override // com.samsung.android.sepunion.IUnionManager
        public IBinder getSemSystemService(String name, Bundle opt) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.sepunion.IUnionManager
        public void setDumpEnabled(String label, String path) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUnionManager {
        static final int TRANSACTION_getSemSystemService = 1;
        static final int TRANSACTION_setDumpEnabled = 2;

        public Stub() {
            attachInterface(this, IUnionManager.DESCRIPTOR);
        }

        public static IUnionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUnionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IUnionManager)) {
                return (IUnionManager) iin;
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
                    return "getSemSystemService";
                case 2:
                    return "setDumpEnabled";
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
                data.enforceInterface(IUnionManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUnionManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    IBinder _result = getSemSystemService(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    setDumpEnabled(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUnionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnionManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IUnionManager
            public IBinder getSemSystemService(String name, Bundle opt) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUnionManager.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeTypedObject(opt, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IUnionManager
            public void setDumpEnabled(String label, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUnionManager.DESCRIPTOR);
                    _data.writeString(label);
                    _data.writeString(path);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
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
