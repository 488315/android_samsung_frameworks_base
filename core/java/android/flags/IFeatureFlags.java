package android.flags;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IFeatureFlags extends IInterface {
    public static final String DESCRIPTOR = "android.flags.IFeatureFlags";

    void overrideFlag(SyncableFlag syncableFlag) throws RemoteException;

    List<SyncableFlag> queryFlags(List<SyncableFlag> list) throws RemoteException;

    void registerCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException;

    void resetFlag(SyncableFlag syncableFlag) throws RemoteException;

    List<SyncableFlag> syncFlags(List<SyncableFlag> list) throws RemoteException;

    void unregisterCallback(IFeatureFlagsCallback iFeatureFlagsCallback) throws RemoteException;

    public static class Default implements IFeatureFlags {
        @Override // android.flags.IFeatureFlags
        public List<SyncableFlag> syncFlags(List<SyncableFlag> flagList) throws RemoteException {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void registerCallback(IFeatureFlagsCallback callback) throws RemoteException {}

        @Override // android.flags.IFeatureFlags
        public void unregisterCallback(IFeatureFlagsCallback callback) throws RemoteException {}

        @Override // android.flags.IFeatureFlags
        public List<SyncableFlag> queryFlags(List<SyncableFlag> flagList) throws RemoteException {
            return null;
        }

        @Override // android.flags.IFeatureFlags
        public void overrideFlag(SyncableFlag flag) throws RemoteException {}

        @Override // android.flags.IFeatureFlags
        public void resetFlag(SyncableFlag flag) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IFeatureFlags {
        static final int TRANSACTION_overrideFlag = 5;
        static final int TRANSACTION_queryFlags = 4;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_resetFlag = 6;
        static final int TRANSACTION_syncFlags = 1;
        static final int TRANSACTION_unregisterCallback = 3;

        public Stub() {
            attachInterface(this, IFeatureFlags.DESCRIPTOR);
        }

        public static IFeatureFlags asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFeatureFlags.DESCRIPTOR);
            if (iin != null && (iin instanceof IFeatureFlags)) {
                return (IFeatureFlags) iin;
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
                    return "syncFlags";
                case 2:
                    return "registerCallback";
                case 3:
                    return "unregisterCallback";
                case 4:
                    return "queryFlags";
                case 5:
                    return "overrideFlag";
                case 6:
                    return "resetFlag";
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
                data.enforceInterface(IFeatureFlags.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFeatureFlags.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SyncableFlag> _arg0 = data.createTypedArrayList(SyncableFlag.CREATOR);
                    data.enforceNoDataAvail();
                    List<SyncableFlag> _result = syncFlags(_arg0);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    IFeatureFlagsCallback _arg02 =
                            IFeatureFlagsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    IFeatureFlagsCallback _arg03 =
                            IFeatureFlagsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    List<SyncableFlag> _arg04 = data.createTypedArrayList(SyncableFlag.CREATOR);
                    data.enforceNoDataAvail();
                    List<SyncableFlag> _result2 = queryFlags(_arg04);
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                case 5:
                    SyncableFlag _arg05 = (SyncableFlag) data.readTypedObject(SyncableFlag.CREATOR);
                    data.enforceNoDataAvail();
                    overrideFlag(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    SyncableFlag _arg06 = (SyncableFlag) data.readTypedObject(SyncableFlag.CREATOR);
                    data.enforceNoDataAvail();
                    resetFlag(_arg06);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFeatureFlags {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFeatureFlags.DESCRIPTOR;
            }

            @Override // android.flags.IFeatureFlags
            public List<SyncableFlag> syncFlags(List<SyncableFlag> flagList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    _data.writeTypedList(flagList, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<SyncableFlag> _result = _reply.createTypedArrayList(SyncableFlag.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void registerCallback(IFeatureFlagsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void unregisterCallback(IFeatureFlagsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public List<SyncableFlag> queryFlags(List<SyncableFlag> flagList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    _data.writeTypedList(flagList, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    List<SyncableFlag> _result = _reply.createTypedArrayList(SyncableFlag.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void overrideFlag(SyncableFlag flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    _data.writeTypedObject(flag, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.flags.IFeatureFlags
            public void resetFlag(SyncableFlag flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFeatureFlags.DESCRIPTOR);
                    _data.writeTypedObject(flag, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
