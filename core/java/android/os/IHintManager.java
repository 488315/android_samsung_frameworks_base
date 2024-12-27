package android.os;

import android.hardware.power.ChannelConfig;
import android.hardware.power.SessionConfig;
import android.os.IHintSession;

/* loaded from: classes3.dex */
public interface IHintManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.IHintManager";

    void closeSessionChannel() throws RemoteException;

    IHintSession createHintSessionWithConfig(IBinder iBinder, int[] iArr, long j, int i, SessionConfig sessionConfig) throws RemoteException;

    long getHintSessionPreferredRate() throws RemoteException;

    int[] getHintSessionThreadIds(IHintSession iHintSession) throws RemoteException;

    ChannelConfig getSessionChannel(IBinder iBinder) throws RemoteException;

    void setHintSessionThreads(IHintSession iHintSession, int[] iArr) throws RemoteException;

    public static class Default implements IHintManager {
        @Override // android.os.IHintManager
        public IHintSession createHintSessionWithConfig(IBinder token, int[] threadIds, long durationNanos, int tag, SessionConfig config) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public long getHintSessionPreferredRate() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IHintManager
        public void setHintSessionThreads(IHintSession hintSession, int[] tids) throws RemoteException {
        }

        @Override // android.os.IHintManager
        public int[] getHintSessionThreadIds(IHintSession hintSession) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public ChannelConfig getSessionChannel(IBinder token) throws RemoteException {
            return null;
        }

        @Override // android.os.IHintManager
        public void closeSessionChannel() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IHintManager {
        static final int TRANSACTION_closeSessionChannel = 6;
        static final int TRANSACTION_createHintSessionWithConfig = 1;
        static final int TRANSACTION_getHintSessionPreferredRate = 2;
        static final int TRANSACTION_getHintSessionThreadIds = 4;
        static final int TRANSACTION_getSessionChannel = 5;
        static final int TRANSACTION_setHintSessionThreads = 3;

        public Stub() {
            attachInterface(this, IHintManager.DESCRIPTOR);
        }

        public static IHintManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHintManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IHintManager)) {
                return (IHintManager) iin;
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
                    return "createHintSessionWithConfig";
                case 2:
                    return "getHintSessionPreferredRate";
                case 3:
                    return "setHintSessionThreads";
                case 4:
                    return "getHintSessionThreadIds";
                case 5:
                    return "getSessionChannel";
                case 6:
                    return "closeSessionChannel";
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
                data.enforceInterface(IHintManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IHintManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int[] _arg1 = data.createIntArray();
                    long _arg2 = data.readLong();
                    int _arg3 = data.readInt();
                    SessionConfig _arg4 = new SessionConfig();
                    data.enforceNoDataAvail();
                    IHintSession _result = createHintSessionWithConfig(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    reply.writeTypedObject(_arg4, 1);
                    return true;
                case 2:
                    long _result2 = getHintSessionPreferredRate();
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 3:
                    IHintSession _arg02 = IHintSession.Stub.asInterface(data.readStrongBinder());
                    int[] _arg12 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setHintSessionThreads(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg03 = data.readStrongBinder();
                    IHintSession _arg04 = IHintSession.Stub.asInterface(_arg03);
                    data.enforceNoDataAvail();
                    int[] _result3 = getHintSessionThreadIds(_arg04);
                    reply.writeNoException();
                    reply.writeIntArray(_result3);
                    return true;
                case 5:
                    IBinder _arg05 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    ChannelConfig _result4 = getSessionChannel(_arg05);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 6:
                    closeSessionChannel();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IHintManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHintManager.DESCRIPTOR;
            }

            @Override // android.os.IHintManager
            public IHintSession createHintSessionWithConfig(IBinder token, int[] threadIds, long durationNanos, int tag, SessionConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeIntArray(threadIds);
                    _data.writeLong(durationNanos);
                    _data.writeInt(tag);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IHintSession _result = IHintSession.Stub.asInterface(_reply.readStrongBinder());
                    if (_reply.readInt() != 0) {
                        config.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IHintManager
            public long getHintSessionPreferredRate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IHintManager
            public void setHintSessionThreads(IHintSession hintSession, int[] tids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    _data.writeStrongInterface(hintSession);
                    _data.writeIntArray(tids);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IHintManager
            public int[] getHintSessionThreadIds(IHintSession hintSession) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    _data.writeStrongInterface(hintSession);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IHintManager
            public ChannelConfig getSessionChannel(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ChannelConfig _result = (ChannelConfig) _reply.readTypedObject(ChannelConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IHintManager
            public void closeSessionChannel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
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
