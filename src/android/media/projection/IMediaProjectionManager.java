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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaProjectionManager)) {
                return (IMediaProjectionManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasProjectionPermission = hasProjectionPermission(i3, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasProjectionPermission);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IMediaProjection iMediaProjectionCreateProjection = createProjection(i4, string2, i5, z, i6);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iMediaProjectionCreateProjection);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IMediaProjection projection = getProjection(i7, string3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(projection);
                    return true;
                case 4:
                    IMediaProjection iMediaProjectionAsInterface = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zIsCurrentProjection = isCurrentProjection(iMediaProjectionAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCurrentProjection);
                    return true;
                case 5:
                    IMediaProjection iMediaProjectionAsInterface2 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestConsentForInvalidProjection(iMediaProjectionAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    MediaProjectionInfo activeProjectionInfo = getActiveProjectionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeProjectionInfo, 1);
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopActiveProjection(i8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyActiveProjectionCapturedContentVisibilityChanged(z2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IMediaProjectionWatcherCallback iMediaProjectionWatcherCallbackAsInterface = IMediaProjectionWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    MediaProjectionInfo mediaProjectionInfoAddCallback = addCallback(iMediaProjectionWatcherCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mediaProjectionInfoAddCallback, 1);
                    return true;
                case 10:
                    IMediaProjectionWatcherCallback iMediaProjectionWatcherCallbackAsInterface2 = IMediaProjectionWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCallback(iMediaProjectionWatcherCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ContentRecordingSession contentRecordingSession = (ContentRecordingSession) parcel.readTypedObject(ContentRecordingSession.CREATOR);
                    IMediaProjection iMediaProjectionAsInterface3 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean contentRecordingSession2 = setContentRecordingSession(contentRecordingSession, iMediaProjectionAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contentRecordingSession2);
                    return true;
                case 12:
                    int i9 = parcel.readInt();
                    IMediaProjection iMediaProjectionAsInterface4 = IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUserReviewGrantedConsentResult(i9, iMediaProjectionAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestInitiated(i10, i11);
                    return true;
                case 14:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestDisplayed(i12);
                    return true;
                case 15:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPermissionRequestCancelled(i13);
                    return true;
                case 16:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppSelectorDisplayed(i14);
                    return true;
                case 17:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyWindowingModeChanged(i15, i16, i17);
                    return true;
                case 18:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCaptureBoundsChanged(i18, i19, rect);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public IMediaProjection createProjection(int i, String str, int i2, boolean z, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IMediaProjection.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public IMediaProjection getProjection(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IMediaProjection.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean isCurrentProjection(IMediaProjection iMediaProjection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void requestConsentForInvalidProjection(IMediaProjection iMediaProjection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public MediaProjectionInfo getActiveProjectionInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MediaProjectionInfo) parcelObtain2.readTypedObject(MediaProjectionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void stopActiveProjection(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjectionWatcherCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MediaProjectionInfo) parcelObtain2.readTypedObject(MediaProjectionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaProjectionWatcherCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contentRecordingSession, 0);
                    parcelObtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestInitiated(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestDisplayed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyPermissionRequestCancelled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyAppSelectorDisplayed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyWindowingModeChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionManager
            public void notifyCaptureBoundsChanged(int i, int i2, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
