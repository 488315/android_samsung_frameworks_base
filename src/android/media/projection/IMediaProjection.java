package android.media.projection;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.media.projection.IMediaProjectionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMediaProjection extends IInterface {

    public static class Default implements IMediaProjection {
        @Override // android.media.projection.IMediaProjection
        public int applyVirtualDisplayFlags(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectAudio() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectSecureVideo() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean canProjectVideo() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public int getDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.media.projection.IMediaProjection
        public ActivityOptions.LaunchCookie getLaunchCookie() throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjection
        public int getTaskId() throws RemoteException {
            return 0;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean isRecordingOverlay() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public boolean isValid() throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjection
        public void notifyVirtualDisplayCreated(int i) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void setRecordingOverlay(boolean z) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void setTaskId(int i) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void start(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void stop(int i) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjection
        public void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
        }
    }

    int applyVirtualDisplayFlags(int i) throws RemoteException;

    boolean canProjectAudio() throws RemoteException;

    boolean canProjectSecureVideo() throws RemoteException;

    boolean canProjectVideo() throws RemoteException;

    int getDisplayId() throws RemoteException;

    ActivityOptions.LaunchCookie getLaunchCookie() throws RemoteException;

    int getTaskId() throws RemoteException;

    boolean isRecordingOverlay() throws RemoteException;

    boolean isValid() throws RemoteException;

    void notifyVirtualDisplayCreated(int i) throws RemoteException;

    void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException;

    void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie) throws RemoteException;

    void setRecordingOverlay(boolean z) throws RemoteException;

    void setTaskId(int i) throws RemoteException;

    void start(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException;

    void stop(int i) throws RemoteException;

    void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaProjection {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjection";
        static final int TRANSACTION_applyVirtualDisplayFlags = 6;
        static final int TRANSACTION_canProjectAudio = 3;
        static final int TRANSACTION_canProjectSecureVideo = 5;
        static final int TRANSACTION_canProjectVideo = 4;
        static final int TRANSACTION_getDisplayId = 12;
        static final int TRANSACTION_getLaunchCookie = 9;
        static final int TRANSACTION_getTaskId = 10;
        static final int TRANSACTION_isRecordingOverlay = 11;
        static final int TRANSACTION_isValid = 16;
        static final int TRANSACTION_notifyVirtualDisplayCreated = 17;
        static final int TRANSACTION_registerCallback = 7;
        static final int TRANSACTION_setLaunchCookie = 13;
        static final int TRANSACTION_setRecordingOverlay = 15;
        static final int TRANSACTION_setTaskId = 14;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;
        static final int TRANSACTION_unregisterCallback = 8;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IMediaProjection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaProjection)) {
                return (IMediaProjection) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "isRecordingOverlay";
                case 12:
                    return "getDisplayId";
                case 13:
                    return "setLaunchCookie";
                case 14:
                    return "setTaskId";
                case 15:
                    return "setRecordingOverlay";
                case 16:
                    return "isValid";
                case 17:
                    return "notifyVirtualDisplayCreated";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IMediaProjectionCallback iMediaProjectionCallbackAsInterface = IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    start(iMediaProjectionCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stop(i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean zCanProjectAudio = canProjectAudio();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanProjectAudio);
                    return true;
                case 4:
                    boolean zCanProjectVideo = canProjectVideo();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanProjectVideo);
                    return true;
                case 5:
                    boolean zCanProjectSecureVideo = canProjectSecureVideo();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanProjectSecureVideo);
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iApplyVirtualDisplayFlags = applyVirtualDisplayFlags(i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iApplyVirtualDisplayFlags);
                    return true;
                case 7:
                    IMediaProjectionCallback iMediaProjectionCallbackAsInterface2 = IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(iMediaProjectionCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMediaProjectionCallback iMediaProjectionCallbackAsInterface3 = IMediaProjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iMediaProjectionCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ActivityOptions.LaunchCookie launchCookie = getLaunchCookie();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchCookie, 1);
                    return true;
                case 10:
                    int taskId = getTaskId();
                    parcel2.writeNoException();
                    parcel2.writeInt(taskId);
                    return true;
                case 11:
                    boolean zIsRecordingOverlay = isRecordingOverlay();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRecordingOverlay);
                    return true;
                case 12:
                    int displayId = getDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(displayId);
                    return true;
                case 13:
                    ActivityOptions.LaunchCookie launchCookie2 = (ActivityOptions.LaunchCookie) parcel.readTypedObject(ActivityOptions.LaunchCookie.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLaunchCookie(launchCookie2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskId(i5);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecordingOverlay(z);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean zIsValid = isValid();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsValid);
                    return true;
                case 17:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVirtualDisplayCreated(i6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaProjection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjection
            public void start(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjectionCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void stop(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectAudio() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectVideo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean canProjectSecureVideo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public int applyVirtualDisplayFlags(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjectionCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjectionCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public ActivityOptions.LaunchCookie getLaunchCookie() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityOptions.LaunchCookie) parcelObtain2.readTypedObject(ActivityOptions.LaunchCookie.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public int getTaskId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean isRecordingOverlay() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public int getDisplayId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(launchCookie, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void setTaskId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void setRecordingOverlay(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public boolean isValid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjection
            public void notifyVirtualDisplayCreated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void applyVirtualDisplayFlags_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void getLaunchCookie_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void getTaskId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isRecordingOverlay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setLaunchCookie_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setTaskId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setRecordingOverlay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isValid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyVirtualDisplayCreated_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }
    }
}
