package android.hardware.power;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPower extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$power$IPower".replace('$', '.');
    public static final String HASH = "d111735ed2b89b6c32443aac9b162b1afbbea3f2";
    public static final int VERSION = 5;

    void closeSessionChannel(int i, int i2) throws RemoteException;

    IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws RemoteException;

    IPowerHintSession createHintSessionWithConfig(
            int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig)
            throws RemoteException;

    long getHintSessionPreferredRate() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    ChannelConfig getSessionChannel(int i, int i2) throws RemoteException;

    boolean isBoostSupported(int i) throws RemoteException;

    boolean isModeSupported(int i) throws RemoteException;

    void setBoost(int i, int i2) throws RemoteException;

    void setMode(int i, boolean z) throws RemoteException;

    public static class Default implements IPower {
        @Override // android.hardware.power.IPower
        public void setMode(int type, boolean enabled) throws RemoteException {}

        @Override // android.hardware.power.IPower
        public boolean isModeSupported(int type) throws RemoteException {
            return false;
        }

        @Override // android.hardware.power.IPower
        public void setBoost(int type, int durationMs) throws RemoteException {}

        @Override // android.hardware.power.IPower
        public boolean isBoostSupported(int type) throws RemoteException {
            return false;
        }

        @Override // android.hardware.power.IPower
        public IPowerHintSession createHintSession(
                int tgid, int uid, int[] threadIds, long durationNanos) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public long getHintSessionPreferredRate() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.power.IPower
        public IPowerHintSession createHintSessionWithConfig(
                int tgid,
                int uid,
                int[] threadIds,
                long durationNanos,
                int tag,
                SessionConfig config)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public ChannelConfig getSessionChannel(int tgid, int uid) throws RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public void closeSessionChannel(int tgid, int uid) throws RemoteException {}

        @Override // android.hardware.power.IPower
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.power.IPower
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPower {
        static final int TRANSACTION_closeSessionChannel = 9;
        static final int TRANSACTION_createHintSession = 5;
        static final int TRANSACTION_createHintSessionWithConfig = 7;
        static final int TRANSACTION_getHintSessionPreferredRate = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSessionChannel = 8;
        static final int TRANSACTION_isBoostSupported = 4;
        static final int TRANSACTION_isModeSupported = 2;
        static final int TRANSACTION_setBoost = 3;
        static final int TRANSACTION_setMode = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IPower asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPower)) {
                return (IPower) iin;
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
                    return "setMode";
                case 2:
                    return "isModeSupported";
                case 3:
                    return "setBoost";
                case 4:
                    return "isBoostSupported";
                case 5:
                    return "createHintSession";
                case 6:
                    return "getHintSessionPreferredRate";
                case 7:
                    return "createHintSessionWithConfig";
                case 8:
                    return "getSessionChannel";
                case 9:
                    return "closeSessionChannel";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setMode(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = isModeSupported(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    setBoost(_arg03, _arg12);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = isBoostSupported(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg13 = data.readInt();
                    int[] _arg2 = data.createIntArray();
                    long _arg3 = data.readLong();
                    data.enforceNoDataAvail();
                    IPowerHintSession _result3 = createHintSession(_arg05, _arg13, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 6:
                    long _result4 = getHintSessionPreferredRate();
                    reply.writeNoException();
                    reply.writeLong(_result4);
                    return true;
                case 7:
                    int _arg06 = data.readInt();
                    int _arg14 = data.readInt();
                    int[] _arg22 = data.createIntArray();
                    long _arg32 = data.readLong();
                    int _arg4 = data.readInt();
                    SessionConfig _arg5 = new SessionConfig();
                    data.enforceNoDataAvail();
                    IPowerHintSession _result5 =
                            createHintSessionWithConfig(
                                    _arg06, _arg14, _arg22, _arg32, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    reply.writeTypedObject(_arg5, 1);
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    ChannelConfig _result6 = getSessionChannel(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 9:
                    int _arg08 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    closeSessionChannel(_arg08, _arg16);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPower {
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

            @Override // android.hardware.power.IPower
            public void setMode(int type, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeBoolean(enabled);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setMode is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isModeSupported(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method isModeSupported is unimplemented.");
                    }
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void setBoost(int type, int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(durationMs);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setBoost is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isBoostSupported(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method isBoostSupported is unimplemented.");
                    }
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public IPowerHintSession createHintSession(
                    int tgid, int uid, int[] threadIds, long durationNanos) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(tgid);
                    _data.writeInt(uid);
                    _data.writeIntArray(threadIds);
                    _data.writeLong(durationNanos);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method createHintSession is unimplemented.");
                    }
                    _reply.readException();
                    IPowerHintSession _result =
                            IPowerHintSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public long getHintSessionPreferredRate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getHintSessionPreferredRate is unimplemented.");
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public IPowerHintSession createHintSessionWithConfig(
                    int tgid,
                    int uid,
                    int[] threadIds,
                    long durationNanos,
                    int tag,
                    SessionConfig config)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(tgid);
                    _data.writeInt(uid);
                    _data.writeIntArray(threadIds);
                    _data.writeLong(durationNanos);
                    _data.writeInt(tag);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException(
                                "Method createHintSessionWithConfig is unimplemented.");
                    }
                    _reply.readException();
                    IPowerHintSession _result =
                            IPowerHintSession.Stub.asInterface(_reply.readStrongBinder());
                    if (_reply.readInt() != 0) {
                        config.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public ChannelConfig getSessionChannel(int tgid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(tgid);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getSessionChannel is unimplemented.");
                    }
                    _reply.readException();
                    ChannelConfig _result =
                            (ChannelConfig) _reply.readTypedObject(ChannelConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void closeSessionChannel(int tgid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(tgid);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method closeSessionChannel is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.power.IPower
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

            @Override // android.hardware.power.IPower
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

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
