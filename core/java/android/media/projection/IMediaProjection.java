package android.media.projection;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaProjection extends IInterface {
    int applyVirtualDisplayFlags(int i) throws RemoteException;

    boolean canProjectAudio() throws RemoteException;

    boolean canProjectSecureVideo() throws RemoteException;

    boolean canProjectVideo() throws RemoteException;

    ActivityOptions.LaunchCookie getLaunchCookie() throws RemoteException;

    int getTaskId() throws RemoteException;

    boolean isValid() throws RemoteException;

    void notifyVirtualDisplayCreated(int i) throws RemoteException;

    void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException;

    void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie) throws RemoteException;

    void setTaskId(int i) throws RemoteException;

    void start(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException;

    void stop() throws RemoteException;

    void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback)
            throws RemoteException;

    public static class Default implements IMediaProjection {
        @Override // android.media.projection.IMediaProjection
        public void start(IMediaProjectionCallback callback) throws RemoteException {}

        @Override // android.media.projection.IMediaProjection
        public void stop() throws RemoteException {}

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectAudio() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectVideo() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectSecureVideo() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public int applyVirtualDisplayFlags(int flags) throws RemoteException {
            return 0;
        }

        @Override // android.media.projection.IMediaProjection
        public void registerCallback(IMediaProjectionCallback callback) throws RemoteException {}

        @Override // android.media.projection.IMediaProjection
        public void unregisterCallback(IMediaProjectionCallback callback) throws RemoteException {}

        @Override // android.media.projection.IMediaProjection
        public ActivityOptions.LaunchCookie getLaunchCookie() throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjection
        public int getTaskId() throws RemoteException {
            return 0;
        }

        @Override // android.media.projection.IMediaProjection
        public void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie)
                throws RemoteException {}

        @Override // android.media.projection.IMediaProjection
        public void setTaskId(int taskId) throws RemoteException {}

        @Override // android.media.projection.IMediaProjection
        public boolean isValid() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public void notifyVirtualDisplayCreated(int displayId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMediaProjection {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjection";
        static final int TRANSACTION_applyVirtualDisplayFlags = 6;
        static final int TRANSACTION_canProjectAudio = 3;
        static final int TRANSACTION_canProjectSecureVideo = 5;
        static final int TRANSACTION_canProjectVideo = 4;
        static final int TRANSACTION_getLaunchCookie = 9;
        static final int TRANSACTION_getTaskId = 10;
        static final int TRANSACTION_isValid = 13;
        static final int TRANSACTION_notifyVirtualDisplayCreated = 14;
        static final int TRANSACTION_registerCallback = 7;
        static final int TRANSACTION_setLaunchCookie = 11;
        static final int TRANSACTION_setTaskId = 12;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;
        static final int TRANSACTION_unregisterCallback = 8;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IMediaProjection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaProjection)) {
                return (IMediaProjection) iin;
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
                    return "start";
                case 2:
                    return "stop";
                case 3:
                    return "canProjectAudio";
                case 4:
                    return "canProjectVideo";
                case 5:
                    return "canProjectSecureVideo";
                case 6:
                    return "applyVirtualDisplayFlags";
                case 7:
                    return "registerCallback";
                case 8:
                    return "unregisterCallback";
                case 9:
                    return "getLaunchCookie";
                case 10:
                    return "getTaskId";
                case 11:
                    return "setLaunchCookie";
                case 12:
                    return "setTaskId";
                case 13:
                    return "isValid";
                case 14:
                    return "notifyVirtualDisplayCreated";
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
                    IMediaProjectionCallback _arg0 =
                            IMediaProjectionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    start(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    stop();
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _result = canProjectAudio();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 4:
                    boolean _result2 = canProjectVideo();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 5:
                    boolean _result3 = canProjectSecureVideo();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 6:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = applyVirtualDisplayFlags(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 7:
                    IMediaProjectionCallback _arg03 =
                            IMediaProjectionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg03);
                    reply.writeNoException();
                    return true;
                case 8:
                    IMediaProjectionCallback _arg04 =
                            IMediaProjectionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg04);
                    reply.writeNoException();
                    return true;
                case 9:
                    ActivityOptions.LaunchCookie _result5 = getLaunchCookie();
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 10:
                    int _result6 = getTaskId();
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 11:
                    ActivityOptions.LaunchCookie _arg05 =
                            (ActivityOptions.LaunchCookie)
                                    data.readTypedObject(ActivityOptions.LaunchCookie.CREATOR);
                    data.enforceNoDataAvail();
                    setLaunchCookie(_arg05);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    setTaskId(_arg06);
                    reply.writeNoException();
                    return true;
                case 13:
                    boolean _result7 = isValid();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 14:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyVirtualDisplayCreated(_arg07);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMediaProjection {
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

            @Override // android.media.projection.IMediaProjection
            public void start(IMediaProjectionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void stop() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectAudio() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectVideo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectSecureVideo() throws RemoteException {
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

            @Override // android.media.projection.IMediaProjection
            public int applyVirtualDisplayFlags(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void registerCallback(IMediaProjectionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void unregisterCallback(IMediaProjectionCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public ActivityOptions.LaunchCookie getLaunchCookie() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    ActivityOptions.LaunchCookie _result =
                            (ActivityOptions.LaunchCookie)
                                    _reply.readTypedObject(ActivityOptions.LaunchCookie.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public int getTaskId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(launchCookie, 0);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void setTaskId(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean isValid() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void notifyVirtualDisplayCreated(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void applyVirtualDisplayFlags_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void getLaunchCookie_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void getTaskId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setLaunchCookie_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setTaskId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isValid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyVirtualDisplayCreated_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
