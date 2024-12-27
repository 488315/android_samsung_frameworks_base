package android.service.vr;

import android.app.Vr2dDisplayProperties;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVrManager extends IInterface {
    boolean getPersistentVrModeEnabled() throws RemoteException;

    int getVr2dDisplayId() throws RemoteException;

    boolean getVrModeState() throws RemoteException;

    void registerListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException;

    void registerPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks)
            throws RemoteException;

    void setAndBindCompositor(String str) throws RemoteException;

    void setPersistentVrModeEnabled(boolean z) throws RemoteException;

    void setStandbyEnabled(boolean z) throws RemoteException;

    void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties)
            throws RemoteException;

    void unregisterListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException;

    void unregisterPersistentVrStateListener(
            IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException;

    public static class Default implements IVrManager {
        @Override // android.service.vr.IVrManager
        public void registerListener(IVrStateCallbacks cb) throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public void unregisterListener(IVrStateCallbacks cb) throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public void registerPersistentVrStateListener(IPersistentVrStateCallbacks cb)
                throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public void unregisterPersistentVrStateListener(IPersistentVrStateCallbacks cb)
                throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public boolean getVrModeState() throws RemoteException {
            return false;
        }

        @Override // android.service.vr.IVrManager
        public boolean getPersistentVrModeEnabled() throws RemoteException {
            return false;
        }

        @Override // android.service.vr.IVrManager
        public void setPersistentVrModeEnabled(boolean enabled) throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties)
                throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public int getVr2dDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.service.vr.IVrManager
        public void setAndBindCompositor(String componentName) throws RemoteException {}

        @Override // android.service.vr.IVrManager
        public void setStandbyEnabled(boolean standby) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVrManager {
        public static final String DESCRIPTOR = "android.service.vr.IVrManager";
        static final int TRANSACTION_getPersistentVrModeEnabled = 6;
        static final int TRANSACTION_getVr2dDisplayId = 9;
        static final int TRANSACTION_getVrModeState = 5;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_registerPersistentVrStateListener = 3;
        static final int TRANSACTION_setAndBindCompositor = 10;
        static final int TRANSACTION_setPersistentVrModeEnabled = 7;
        static final int TRANSACTION_setStandbyEnabled = 11;
        static final int TRANSACTION_setVr2dDisplayProperties = 8;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_unregisterPersistentVrStateListener = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVrManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVrManager)) {
                return (IVrManager) iin;
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
                    return "unregisterListener";
                case 3:
                    return "registerPersistentVrStateListener";
                case 4:
                    return "unregisterPersistentVrStateListener";
                case 5:
                    return "getVrModeState";
                case 6:
                    return "getPersistentVrModeEnabled";
                case 7:
                    return "setPersistentVrModeEnabled";
                case 8:
                    return "setVr2dDisplayProperties";
                case 9:
                    return "getVr2dDisplayId";
                case 10:
                    return "setAndBindCompositor";
                case 11:
                    return "setStandbyEnabled";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IVrStateCallbacks _arg0 =
                            IVrStateCallbacks.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerListener(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    IVrStateCallbacks _arg02 =
                            IVrStateCallbacks.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    IPersistentVrStateCallbacks _arg03 =
                            IPersistentVrStateCallbacks.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerPersistentVrStateListener(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    IPersistentVrStateCallbacks _arg04 =
                            IPersistentVrStateCallbacks.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterPersistentVrStateListener(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _result = getVrModeState();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 6:
                    boolean _result2 = getPersistentVrModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 7:
                    boolean _arg05 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setPersistentVrModeEnabled(_arg05);
                    reply.writeNoException();
                    return true;
                case 8:
                    Vr2dDisplayProperties _arg06 =
                            (Vr2dDisplayProperties)
                                    data.readTypedObject(Vr2dDisplayProperties.CREATOR);
                    data.enforceNoDataAvail();
                    setVr2dDisplayProperties(_arg06);
                    reply.writeNoException();
                    return true;
                case 9:
                    int _result3 = getVr2dDisplayId();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 10:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    setAndBindCompositor(_arg07);
                    reply.writeNoException();
                    return true;
                case 11:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setStandbyEnabled(_arg08);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVrManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.vr.IVrManager
            public void registerListener(IVrStateCallbacks cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void unregisterListener(IVrStateCallbacks cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void registerPersistentVrStateListener(IPersistentVrStateCallbacks cb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void unregisterPersistentVrStateListener(IPersistentVrStateCallbacks cb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public boolean getVrModeState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public boolean getPersistentVrModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setPersistentVrModeEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(vr2dDisplayProperties, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public int getVr2dDisplayId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setAndBindCompositor(String componentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(componentName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setStandbyEnabled(boolean standby) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(standby);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
