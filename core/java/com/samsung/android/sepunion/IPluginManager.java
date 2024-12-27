package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.cover.CoverState;

/* loaded from: classes6.dex */
public interface IPluginManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IPluginManager";

    CoverState getCoverState() throws RemoteException;

    public static class Default implements IPluginManager {
        @Override // com.samsung.android.sepunion.IPluginManager
        public CoverState getCoverState() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPluginManager {
        static final int TRANSACTION_getCoverState = 1;

        public Stub() {
            attachInterface(this, IPluginManager.DESCRIPTOR);
        }

        public static IPluginManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPluginManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IPluginManager)) {
                return (IPluginManager) iin;
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
                    return "getCoverState";
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
                data.enforceInterface(IPluginManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPluginManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CoverState _result = getCoverState();
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPluginManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPluginManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IPluginManager
            public CoverState getCoverState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPluginManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    CoverState _result = (CoverState) _reply.readTypedObject(CoverState.CREATOR);
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
