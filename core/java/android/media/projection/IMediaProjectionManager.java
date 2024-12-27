package android.media.projection;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.ContentRecordingSession;

/* loaded from: classes2.dex */
public interface IMediaProjectionManager extends IInterface {
    public static final String EXTRA_PACKAGE_REUSING_GRANTED_CONSENT =
            "extra_media_projection_package_reusing_consent";
    public static final String EXTRA_USER_REVIEW_GRANTED_CONSENT =
            "extra_media_projection_user_consent_required";

    MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback)
            throws RemoteException;

    IMediaProjection createProjection(int i, String str, int i2, boolean z) throws RemoteException;

    MediaProjectionInfo getActiveProjectionInfo() throws RemoteException;

    IMediaProjection getProjection(int i, String str) throws RemoteException;

    boolean hasProjectionPermission(int i, String str) throws RemoteException;

    boolean isCurrentProjection(IMediaProjection iMediaProjection) throws RemoteException;

    void notifyActiveProjectionCapturedContentResized(int i, int i2) throws RemoteException;

    void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws RemoteException;

    void notifyAppSelectorDisplayed(int i) throws RemoteException;

    void notifyPermissionRequestCancelled(int i) throws RemoteException;

    void notifyPermissionRequestDisplayed(int i) throws RemoteException;

    void notifyPermissionRequestInitiated(int i, int i2) throws RemoteException;

    void notifyWindowingModeChanged(int i, int i2, int i3) throws RemoteException;

    void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback)
            throws RemoteException;

    void requestConsentForInvalidProjection(IMediaProjection iMediaProjection)
            throws RemoteException;

    boolean setContentRecordingSession(
            ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection)
            throws RemoteException;

    void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection)
            throws RemoteException;

    void stopActiveProjection() throws RemoteException;

    public static class Default implements IMediaProjectionManager {
        @Override // android.media.projection.IMediaProjectionManager
        public boolean hasProjectionPermission(int processUid, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public IMediaProjection createProjection(
                int processUid, String packageName, int type, boolean permanentGrant)
                throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public IMediaProjection getProjection(int processUid, String packageName)
                throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public boolean isCurrentProjection(IMediaProjection projection) throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void requestConsentForInvalidProjection(IMediaProjection projection)
                throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void stopActiveProjection() throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyActiveProjectionCapturedContentResized(int width, int height)
                throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean isVisible)
                throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback callback)
                throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void removeCallback(IMediaProjectionWatcherCallback callback)
                throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public boolean setContentRecordingSession(
                ContentRecordingSession incomingSession, IMediaProjection projection)
                throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void setUserReviewGrantedConsentResult(
                int consentResult, IMediaProjection projection) throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestInitiated(int hostProcessUid, int sessionCreationSource)
                throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestDisplayed(int hostProcessUid) throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestCancelled(int hostProcessUid) throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyAppSelectorDisplayed(int hostProcessUid) throws RemoteException {}

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyWindowingModeChanged(
                int contentToRecord, int targetProcessUid, int windowingMode)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMediaProjectionManager {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjectionManager";
        static final int TRANSACTION_addCallback = 10;
        static final int TRANSACTION_createProjection = 2;
        static final int TRANSACTION_getActiveProjectionInfo = 6;
        static final int TRANSACTION_getProjection = 3;
        static final int TRANSACTION_hasProjectionPermission = 1;
        static final int TRANSACTION_isCurrentProjection = 4;
        static final int TRANSACTION_notifyActiveProjectionCapturedContentResized = 8;
        static final int TRANSACTION_notifyActiveProjectionCapturedContentVisibilityChanged = 9;
        static final int TRANSACTION_notifyAppSelectorDisplayed = 17;
        static final int TRANSACTION_notifyPermissionRequestCancelled = 16;
        static final int TRANSACTION_notifyPermissionRequestDisplayed = 15;
        static final int TRANSACTION_notifyPermissionRequestInitiated = 14;
        static final int TRANSACTION_notifyWindowingModeChanged = 18;
        static final int TRANSACTION_removeCallback = 11;
        static final int TRANSACTION_requestConsentForInvalidProjection = 5;
        static final int TRANSACTION_setContentRecordingSession = 12;
        static final int TRANSACTION_setUserReviewGrantedConsentResult = 13;
        static final int TRANSACTION_stopActiveProjection = 7;
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

        public static IMediaProjectionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaProjectionManager)) {
                return (IMediaProjectionManager) iin;
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
                    return "hasProjectionPermission";
                case 2:
                    return "createProjection";
                case 3:
                    return "getProjection";
                case 4:
                    return "isCurrentProjection";
                case 5:
                    return "requestConsentForInvalidProjection";
                case 6:
                    return "getActiveProjectionInfo";
                case 7:
                    return "stopActiveProjection";
                case 8:
                    return "notifyActiveProjectionCapturedContentResized";
                case 9:
                    return "notifyActiveProjectionCapturedContentVisibilityChanged";
                case 10:
                    return "addCallback";
                case 11:
                    return "removeCallback";
                case 12:
                    return "setContentRecordingSession";
                case 13:
                    return "setUserReviewGrantedConsentResult";
                case 14:
                    return "notifyPermissionRequestInitiated";
                case 15:
                    return "notifyPermissionRequestDisplayed";
                case 16:
                    return "notifyPermissionRequestCancelled";
                case 17:
                    return "notifyAppSelectorDisplayed";
                case 18:
                    return "notifyWindowingModeChanged";
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
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = hasProjectionPermission(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    boolean _arg3 = data.readBoolean();
                    data.enforceNoDataAvail();
                    IMediaProjection _result2 = createProjection(_arg02, _arg12, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    IMediaProjection _result3 = getProjection(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 4:
                    IMediaProjection _arg04 =
                            IMediaProjection.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result4 = isCurrentProjection(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    IMediaProjection _arg05 =
                            IMediaProjection.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestConsentForInvalidProjection(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    MediaProjectionInfo _result5 = getActiveProjectionInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 7:
                    stopActiveProjection();
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyActiveProjectionCapturedContentResized(_arg06, _arg14);
                    reply.writeNoException();
                    return true;
                case 9:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyActiveProjectionCapturedContentVisibilityChanged(_arg07);
                    reply.writeNoException();
                    return true;
                case 10:
                    IMediaProjectionWatcherCallback _arg08 =
                            IMediaProjectionWatcherCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    MediaProjectionInfo _result6 = addCallback(_arg08);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 11:
                    IMediaProjectionWatcherCallback _arg09 =
                            IMediaProjectionWatcherCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeCallback(_arg09);
                    reply.writeNoException();
                    return true;
                case 12:
                    ContentRecordingSession _arg010 =
                            (ContentRecordingSession)
                                    data.readTypedObject(ContentRecordingSession.CREATOR);
                    IMediaProjection _arg15 =
                            IMediaProjection.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result7 = setContentRecordingSession(_arg010, _arg15);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 13:
                    int _arg011 = data.readInt();
                    IMediaProjection _arg16 =
                            IMediaProjection.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setUserReviewGrantedConsentResult(_arg011, _arg16);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg012 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyPermissionRequestInitiated(_arg012, _arg17);
                    return true;
                case 15:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyPermissionRequestDisplayed(_arg013);
                    return true;
                case 16:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyPermissionRequestCancelled(_arg014);
                    return true;
                case 17:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyAppSelectorDisplayed(_arg015);
                    return true;
                case 18:
                    int _arg016 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyWindowingModeChanged(_arg016, _arg18, _arg22);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMediaProjectionManager {
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

            @Override // android.media.projection.IMediaProjectionManager
            public boolean hasProjectionPermission(int processUid, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(processUid);
                    _data.writeString(packageName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public IMediaProjection createProjection(
                    int processUid, String packageName, int type, boolean permanentGrant)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(processUid);
                    _data.writeString(packageName);
                    _data.writeInt(type);
                    _data.writeBoolean(permanentGrant);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    IMediaProjection _result =
                            IMediaProjection.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public IMediaProjection getProjection(int processUid, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(processUid);
                    _data.writeString(packageName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    IMediaProjection _result =
                            IMediaProjection.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean isCurrentProjection(IMediaProjection projection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(projection);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void requestConsentForInvalidProjection(IMediaProjection projection)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(projection);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    MediaProjectionInfo _result =
                            (MediaProjectionInfo)
                                    _reply.readTypedObject(MediaProjectionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void stopActiveProjection() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyActiveProjectionCapturedContentResized(int width, int height)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean isVisible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isVisible);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    MediaProjectionInfo _result =
                            (MediaProjectionInfo)
                                    _reply.readTypedObject(MediaProjectionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void removeCallback(IMediaProjectionWatcherCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean setContentRecordingSession(
                    ContentRecordingSession incomingSession, IMediaProjection projection)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(incomingSession, 0);
                    _data.writeStrongInterface(projection);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void setUserReviewGrantedConsentResult(
                    int consentResult, IMediaProjection projection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(consentResult);
                    _data.writeStrongInterface(projection);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestInitiated(
                    int hostProcessUid, int sessionCreationSource) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hostProcessUid);
                    _data.writeInt(sessionCreationSource);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestDisplayed(int hostProcessUid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hostProcessUid);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestCancelled(int hostProcessUid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hostProcessUid);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyAppSelectorDisplayed(int hostProcessUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hostProcessUid);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyWindowingModeChanged(
                    int contentToRecord, int targetProcessUid, int windowingMode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(contentToRecord);
                    _data.writeInt(targetProcessUid);
                    _data.writeInt(windowingMode);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void getProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isCurrentProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void requestConsentForInvalidProjection_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void stopActiveProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyActiveProjectionCapturedContentResized_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyActiveProjectionCapturedContentVisibilityChanged_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void addCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setContentRecordingSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setUserReviewGrantedConsentResult_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestInitiated_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestDisplayed_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestCancelled_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyAppSelectorDisplayed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyWindowingModeChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
