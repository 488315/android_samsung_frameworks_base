package android.os;


public interface IUpdateEngineStable extends IInterface {
    public static final String DESCRIPTOR = "android$os$IUpdateEngineStable".replace('$', '.');
    public static final String HASH = "ee2e6f0bd51391955f79f4d5eeeafc37c668cd40";
    public static final int VERSION = 2;

    void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr)
            throws RemoteException;

    boolean bind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean unbind(IUpdateEngineStableCallback iUpdateEngineStableCallback) throws RemoteException;

    public static class Default implements IUpdateEngineStable {
        @Override // android.os.IUpdateEngineStable
        public void applyPayloadFd(
                ParcelFileDescriptor pfd,
                long payload_offset,
                long payload_size,
                String[] headerKeyValuePairs)
                throws RemoteException {}

        @Override // android.os.IUpdateEngineStable
        public boolean bind(IUpdateEngineStableCallback callback) throws RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngineStable
        public boolean unbind(IUpdateEngineStableCallback callback) throws RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngineStable
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.os.IUpdateEngineStable
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUpdateEngineStable {
        static final int TRANSACTION_applyPayloadFd = 1;
        static final int TRANSACTION_bind = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_unbind = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateEngineStable asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IUpdateEngineStable)) {
                return (IUpdateEngineStable) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    String[] _arg3 = data.createStringArray();
                    data.enforceNoDataAvail();
                    applyPayloadFd(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    IUpdateEngineStableCallback _arg02 =
                            IUpdateEngineStableCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result = bind(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 3:
                    IUpdateEngineStableCallback _arg03 =
                            IUpdateEngineStableCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result2 = unbind(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUpdateEngineStable {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.os.IUpdateEngineStable
            public void applyPayloadFd(
                    ParcelFileDescriptor pfd,
                    long payload_offset,
                    long payload_size,
                    String[] headerKeyValuePairs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(pfd, 0);
                    _data.writeLong(payload_offset);
                    _data.writeLong(payload_size);
                    _data.writeStringArray(headerKeyValuePairs);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method applyPayloadFd is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public boolean bind(IUpdateEngineStableCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method bind is unimplemented.");
                    }
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public boolean unbind(IUpdateEngineStableCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method unbind is unimplemented.");
                    }
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.os.IUpdateEngineStable
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
