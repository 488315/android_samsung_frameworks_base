package android.media.projection;

import android.Manifest;
import android.app.ActivityThread;
import android.graphics.Rect;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.ContentRecordingSession;

/* loaded from: classes3.dex */
public interface IMediaProjectionManager extends IInterface {
    public static final String EXTRA_PACKAGE_REUSING_GRANTED_CONSENT = "extra_media_projection_package_reusing_consent";
    public static final String EXTRA_USER_REVIEW_GRANTED_CONSENT = "extra_media_projection_user_consent_required";

    public static class Default implements IMediaProjectionManager {
        @Override // android.media.projection.IMediaProjectionManager
        public MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public IMediaProjection createProjection(int i, String str, int i2, boolean z, int i3) throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public IMediaProjection getProjection(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public boolean hasProjectionPermission(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public boolean isCurrentProjection(IMediaProjection iMediaProjection) throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyAppSelectorDisplayed(int i) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyCaptureBoundsChanged(int i, int i2, Rect rect) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestCancelled(int i) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestDisplayed(int i) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyPermissionRequestInitiated(int i, int i2) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void notifyWindowingModeChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void requestConsentForInvalidProjection(IMediaProjection iMediaProjection) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection) throws RemoteException {
            return false;
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionManager
        public void stopActiveProjection(int i) throws RemoteException {
        }
    }

    MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException;

    IMediaProjection createProjection(int i, String str, int i2, boolean z, int i3) throws RemoteException;

    MediaProjectionInfo getActiveProjectionInfo() throws RemoteException;

    IMediaProjection getProjection(int i, String str) throws RemoteException;

    boolean hasProjectionPermission(int i, String str) throws RemoteException;

    boolean isCurrentProjection(IMediaProjection iMediaProjection) throws RemoteException;

    void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws RemoteException;

    void notifyAppSelectorDisplayed(int i) throws RemoteException;

    void notifyCaptureBoundsChanged(int i, int i2, Rect rect) throws RemoteException;

    void notifyPermissionRequestCancelled(int i) throws RemoteException;

    void notifyPermissionRequestDisplayed(int i) throws RemoteException;

    void notifyPermissionRequestInitiated(int i, int i2) throws RemoteException;

    void notifyWindowingModeChanged(int i, int i2, int i3) throws RemoteException;

    void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException;

    void requestConsentForInvalidProjection(IMediaProjection iMediaProjection) throws RemoteException;

    boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection) throws RemoteException;

    void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) throws RemoteException;

    void stopActiveProjection(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaProjectionManager {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjectionManager";
        static final int TRANSACTION_addCallback = 9;
        static final int TRANSACTION_createProjection = 2;
        static final int TRANSACTION_getActiveProjectionInfo = 6;
        static final int TRANSACTION_getProjection = 3;
        static final int TRANSACTION_hasProjectionPermission = 1;
        static final int TRANSACTION_isCurrentProjection = 4;
        static final int TRANSACTION_notifyActiveProjectionCapturedContentVisibilityChanged = 8;
        static final int TRANSACTION_notifyAppSelectorDisplayed = 16;
        static final int TRANSACTION_notifyCaptureBoundsChanged = 18;
        static final int TRANSACTION_notifyPermissionRequestCancelled = 15;
        static final int TRANSACTION_notifyPermissionRequestDisplayed = 14;
        static final int TRANSACTION_notifyPermissionRequestInitiated = 13;
        static final int TRANSACTION_notifyWindowingModeChanged = 17;
        static final int TRANSACTION_removeCallback = 10;
        static final int TRANSACTION_requestConsentForInvalidProjection = 5;
        static final int TRANSACTION_setContentRecordingSession = 11;
        static final int TRANSACTION_setUserReviewGrantedConsentResult = 12;
        static final int TRANSACTION_stopActiveProjection = 7;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
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

        public static IMediaProjectionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaProjectionManager)) {
                return (IMediaProjectionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "notifyActiveProjectionCapturedContentVisibilityChanged";
                case 9:
                    return "addCallback";
                case 10:
                    return "removeCallback";
                case 11:
                    return "setContentRecordingSession";
                case 12:
                    return "setUserReviewGrantedConsentResult";
                case 13:
                    return "notifyPermissionRequestInitiated";
                case 14:
                    return "notifyPermissionRequestDisplayed";
                case 15:
                    return "notifyPermissionRequestCancelled";
                case 16:
                    return "notifyAppSelectorDisplayed";
                case 17:
                    return "notifyWindowingModeChanged";
                case 18:
                    return "notifyCaptureBoundsChanged";
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasProjectionPermission = hasProjectionPermission(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasProjectionPermission);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IMediaProjection createProjection = createProjection(readInt2, readString2, readInt3, readBoolean, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createProjection);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IMediaProjection projection = getProjection(readInt5, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(projection);
                    return true;
                case 4:
                    IMediaProjection asInterface = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isCurrentProjection = isCurrentProjection(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCurrentProjection);
                    return true;
                case 5:
                    IMediaProjection asInterface2 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestConsentForInvalidProjection(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    MediaProjectionInfo activeProjectionInfo = getActiveProjectionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeProjectionInfo, 1);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopActiveProjection(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyActiveProjectionCapturedContentVisibilityChanged(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IMediaProjectionWatcherCallback asInterface3 = IMediaProjectionWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    MediaProjectionInfo addCallback = addCallback(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addCallback, 1);
                    return true;
                case 10:
                    IMediaProjectionWatcherCallback asInterface4 = IMediaProjectionWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ContentRecordingSession contentRecordingSession = (ContentRecordingSession) parcel.readTypedObject(ContentRecordingSession.CREATOR);
                    IMediaProjection asInterface5 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean contentRecordingSession2 = setContentRecordingSession(contentRecordingSession, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contentRecordingSession2);
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    IMediaProjection asInterface6 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUserReviewGrantedConsentResult(readInt7, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestInitiated(readInt8, readInt9);
                    return true;
                case 14:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestDisplayed(readInt10);
                    return true;
                case 15:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestCancelled(readInt11);
                    return true;
                case 16:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppSelectorDisplayed(readInt12);
                    return true;
                case 17:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyWindowingModeChanged(readInt13, readInt14, readInt15);
                    return true;
                case 18:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCaptureBoundsChanged(readInt16, readInt17, rect);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaProjectionManager {
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

            @Override // android.media.projection.IMediaProjectionManager
            public boolean hasProjectionPermission(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public IMediaProjection createProjection(int i, String str, int i2, boolean z, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return IMediaProjection.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public IMediaProjection getProjection(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return IMediaProjection.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean isCurrentProjection(IMediaProjection iMediaProjection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void requestConsentForInvalidProjection(IMediaProjection iMediaProjection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaProjectionInfo) obtain2.readTypedObject(MediaProjectionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void stopActiveProjection(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionWatcherCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaProjectionInfo) obtain2.readTypedObject(MediaProjectionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaProjectionWatcherCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(contentRecordingSession, 0);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestInitiated(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestDisplayed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestCancelled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyAppSelectorDisplayed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyWindowingModeChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyCaptureBoundsChanged(int i, int i2, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void getProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void isCurrentProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void requestConsentForInvalidProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void stopActiveProjection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyActiveProjectionCapturedContentVisibilityChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void addCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setContentRecordingSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void setUserReviewGrantedConsentResult_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestInitiated_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestDisplayed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyPermissionRequestCancelled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyAppSelectorDisplayed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyWindowingModeChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }

        protected void notifyCaptureBoundsChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_MEDIA_PROJECTION, getCallingPid(), getCallingUid());
        }
    }
}
