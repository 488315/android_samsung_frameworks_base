package com.android.server.media.projection;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AppOpsManager;
import android.app.IProcessObserver;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplay;
import android.media.MediaRouter;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionCallback;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.media.projection.MediaProjectionInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.ContentRecordingSession;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.internal.app.IAppOpsService;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.media.projection.MediaProjectionManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.time.InstantSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class MediaProjectionManagerService extends SystemService implements Watchdog.Monitor {
    static final long MEDIA_PROJECTION_PREVENTS_REUSING_CONSENT = 266201607;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final AppOpsManager mAppOps;
    public final CallbackDelegate mCallbackDelegate;
    public final MediaProjectionManagerService$Injector$$ExternalSyntheticLambda0 mClock;
    public final Context mContext;
    public final Map mDeathEaters;
    public final Handler mHandler;
    public final Injector mInjector;
    public final Object mLock;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public MediaRouter.RouteInfo mMediaRouteInfo;
    public final MediaRouter mMediaRouter;
    public final MediaRouterCallback mMediaRouterCallback;
    public final PackageManager mPackageManager;
    public MediaProjection mProjectionGrant;
    public IBinder mProjectionToken;
    public final WindowManagerInternal mWmInternal;

    /* renamed from: com.android.server.media.projection.MediaProjectionManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public final /* synthetic */ int $r8$classId = 1;
        public Object this$0;

        public AnonymousClass3(MediaProjectionManagerService mediaProjectionManagerService) {
            this.this$0 = mediaProjectionManagerService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            SemWifiDisplay activeDisplay;
            switch (this.$r8$classId) {
                case 0:
                    DisplayManager displayManager = (DisplayManager) ((MediaProjectionManagerService) this.this$0).mContext.getSystemService("display");
                    if (displayManager.semGetWifiDisplayStatus() != null && displayManager.semGetWifiDisplayStatus().getActiveDisplayState() == 2 && (activeDisplay = displayManager.semGetWifiDisplayStatus().getActiveDisplay()) != null) {
                        if (!SystemProperties.get("ro.build.characteristics", "phone").contains("tablet")) {
                            Toast.makeText(new ContextThemeWrapper(((MediaProjectionManagerService) this.this$0).mContext, R.style.Theme.DeviceDefault.Light), ((MediaProjectionManagerService) this.this$0).mContext.getString(17043509, activeDisplay.getDeviceName()), 1).show();
                            break;
                        } else {
                            Toast.makeText(new ContextThemeWrapper(((MediaProjectionManagerService) this.this$0).mContext, R.style.Theme.DeviceDefault.Light), ((MediaProjectionManagerService) this.this$0).mContext.getString(17043510, activeDisplay.getDeviceName()), 1).show();
                            break;
                        }
                    }
                    break;
                default:
                    try {
                        ((IMediaProjectionCallback) this.this$0).onStop();
                        break;
                    } catch (RemoteException e) {
                        Slog.w("MediaProjectionManagerService", "Failed to notify media projection has stopped", e);
                        return;
                    }
            }
        }
    }

    public final class BinderService extends IMediaProjectionManager.Stub {
        public BinderService(Context context) {
            super(PermissionEnforcer.fromContext(context));
        }

        public final MediaProjectionInfo addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            addCallback_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return MediaProjectionManagerService.this.addCallback(iMediaProjectionWatcherCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final IMediaProjection createProjection(int i, String str, int i2, boolean z) {
            if (MediaProjectionManagerService.this.mContext.checkCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION") != 0) {
                throw new SecurityException("Requires MANAGE_MEDIA_PROJECTION in order to grant projection permission");
            }
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("package name must not be empty");
            }
            return MediaProjectionManagerService.this.createProjectionInternal(i, str, i2, z, Binder.getCallingUserHandle());
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(MediaProjectionManagerService.this.mContext, "MediaProjectionManagerService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
                    mediaProjectionManagerService.getClass();
                    printWriter.println("MEDIA PROJECTION MANAGER (dumpsys media_projection)");
                    synchronized (mediaProjectionManagerService.mLock) {
                        try {
                            printWriter.println("Media Projection: ");
                            MediaProjection mediaProjection = mediaProjectionManagerService.mProjectionGrant;
                            if (mediaProjection != null) {
                                mediaProjection.dump(printWriter);
                            } else {
                                printWriter.println("null");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final MediaProjectionInfo getActiveProjectionInfo() {
            if (MediaProjectionManagerService.this.mContext.checkCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION") != 0) {
                throw new SecurityException("Requires MANAGE_MEDIA_PROJECTION in order to get active projection info");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return MediaProjectionManagerService.this.getActiveProjectionInfo();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final IMediaProjection getProjection(int i, String str) {
            getProjection_enforcePermission();
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("package name must not be empty");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return MediaProjectionManagerService.this.getProjectionInternal(i, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean hasProjectionPermission(int i, String str) {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (MediaProjectionManagerService.this.mContext.getPackageManager().checkPermission("android.permission.CAPTURE_VIDEO_OUTPUT", str) != 0) {
                    if (MediaProjectionManagerService.this.mAppOps.noteOpNoThrow(46, i, str) != 0) {
                        z = false;
                        return z;
                    }
                }
                z = true;
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isCurrentProjection(IMediaProjection iMediaProjection) {
            isCurrentProjection_enforcePermission();
            return MediaProjectionManagerService.this.isCurrentProjection(iMediaProjection == null ? null : iMediaProjection.asBinder());
        }

        public final void notifyActiveProjectionCapturedContentResized(int i, int i2) {
            CallbackDelegate callbackDelegate;
            notifyActiveProjectionCapturedContentResized_enforcePermission();
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    if (isCurrentProjection(MediaProjectionManagerService.this.mProjectionGrant)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            synchronized (MediaProjectionManagerService.this.mLock) {
                                try {
                                    MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
                                    MediaProjection mediaProjection = mediaProjectionManagerService.mProjectionGrant;
                                    if (mediaProjection != null && (callbackDelegate = mediaProjectionManagerService.mCallbackDelegate) != null) {
                                        callbackDelegate.dispatchResize(mediaProjection, i, i2);
                                    }
                                } finally {
                                }
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } finally {
                }
            }
        }

        public final void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) {
            CallbackDelegate callbackDelegate;
            notifyActiveProjectionCapturedContentVisibilityChanged_enforcePermission();
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    if (isCurrentProjection(MediaProjectionManagerService.this.mProjectionGrant)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            synchronized (MediaProjectionManagerService.this.mLock) {
                                try {
                                    MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
                                    MediaProjection mediaProjection = mediaProjectionManagerService.mProjectionGrant;
                                    if (mediaProjection != null && (callbackDelegate = mediaProjectionManagerService.mCallbackDelegate) != null) {
                                        callbackDelegate.dispatchVisibilityChanged(mediaProjection, z);
                                    }
                                } finally {
                                }
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } finally {
                }
            }
        }

        public final void notifyAppSelectorDisplayed(int i) {
            notifyAppSelectorDisplayed_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.this.notifyAppSelectorDisplayed(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyPermissionRequestCancelled(int i) {
            notifyPermissionRequestCancelled_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.this.notifyPermissionRequestCancelled(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyPermissionRequestDisplayed(int i) {
            notifyPermissionRequestDisplayed_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.this.notifyPermissionRequestDisplayed(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyPermissionRequestInitiated(int i, int i2) {
            notifyPermissionRequestInitiated_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.this.notifyPermissionRequestInitiated(i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyWindowingModeChanged(int i, int i2, int i3) {
            notifyWindowingModeChanged_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.this.notifyWindowingModeChanged(i, i2, i3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
            if (MediaProjectionManagerService.this.mContext.checkCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION") != 0) {
                throw new SecurityException("Requires MANAGE_MEDIA_PROJECTION in order to remove projection callbacks");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.m677$$Nest$mremoveCallback(MediaProjectionManagerService.this, iMediaProjectionWatcherCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestConsentForInvalidProjection(IMediaProjection iMediaProjection) {
            requestConsentForInvalidProjection_enforcePermission();
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    if (!isCurrentProjection(iMediaProjection)) {
                        Slog.v("MediaProjectionManagerService", "Reusing token: Won't request consent again for a token that isn't current");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        MediaProjectionManagerService.this.requestConsentForInvalidProjection();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection) {
            setContentRecordingSession_enforcePermission();
            synchronized (MediaProjectionManagerService.this.mLock) {
                if (!isCurrentProjection(iMediaProjection)) {
                    throw new SecurityException("Unable to set ContentRecordingSession on non-current MediaProjection");
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return MediaProjectionManagerService.this.setContentRecordingSession(contentRecordingSession);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) {
            setUserReviewGrantedConsentResult_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjectionManagerService.this.setUserReviewGrantedConsentResult(i, iMediaProjection);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopActiveProjection() {
            stopActiveProjection_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (MediaProjectionManagerService.this.mLock) {
                    try {
                        if (MediaProjectionManagerService.this.mProjectionGrant != null) {
                            Slog.d("MediaProjectionManagerService", "Content Recording: Stopping active projection");
                            MediaProjectionManagerService.this.mProjectionGrant.stop();
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final class CallbackDelegate {
        public final Handler mHandler;
        public final Object mLock = new Object();
        public final Map mClientCallbacks = new ArrayMap();
        public final Map mWatcherCallbacks = new ArrayMap();

        public CallbackDelegate(Looper looper) {
            this.mHandler = new Handler(looper, null, true);
        }

        public final void dispatchResize(MediaProjection mediaProjection, final int i, final int i2) {
            if (mediaProjection == null) {
                Slog.e("MediaProjectionManagerService", "Tried to dispatch resize notification for a null media projection. Ignoring!");
                return;
            }
            synchronized (this.mLock) {
                try {
                    for (final IMediaProjectionCallback iMediaProjectionCallback : ((ArrayMap) this.mClientCallbacks).values()) {
                        this.mHandler.post(new Runnable() { // from class: com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                IMediaProjectionCallback iMediaProjectionCallback2 = iMediaProjectionCallback;
                                int i3 = i;
                                int i4 = i2;
                                try {
                                    iMediaProjectionCallback2.onCapturedContentResize(i3, i4);
                                } catch (RemoteException e) {
                                    Slog.w("MediaProjectionManagerService", ArrayUtils$$ExternalSyntheticOutline0.m(i3, i4, "Failed to notify media projection has resized to ", " x "), e);
                                }
                            }
                        });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dispatchVisibilityChanged(MediaProjection mediaProjection, final boolean z) {
            if (mediaProjection == null) {
                Slog.e("MediaProjectionManagerService", "Tried to dispatch visibility changed notification for a null media projection. Ignoring!");
                return;
            }
            synchronized (this.mLock) {
                try {
                    for (final IMediaProjectionCallback iMediaProjectionCallback : ((ArrayMap) this.mClientCallbacks).values()) {
                        this.mHandler.post(new Runnable() { // from class: com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                IMediaProjectionCallback iMediaProjectionCallback2 = iMediaProjectionCallback;
                                boolean z2 = z;
                                try {
                                    iMediaProjectionCallback2.onCapturedContentVisibilityChanged(z2);
                                } catch (RemoteException e) {
                                    Slog.w("MediaProjectionManagerService", "Failed to notify media projection has captured content visibility change to " + z2, e);
                                }
                            }
                        });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void remove(IMediaProjectionCallback iMediaProjectionCallback) {
            synchronized (this.mLock) {
                ((ArrayMap) this.mClientCallbacks).remove(iMediaProjectionCallback.asBinder());
            }
        }
    }

    interface Clock {
    }

    class Injector {
    }

    final class MediaProjection extends IMediaProjection.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public IMediaProjectionCallback mCallback;
        public int mCountStarts;
        public final long mCreateTimeMs;
        public MediaProjectionManagerService$MediaProjection$$ExternalSyntheticLambda1 mDeathEater;
        public final boolean mIsPrivileged;
        public ActivityOptions.LaunchCookie mLaunchCookie;
        public boolean mRestoreSystemAlertWindow;
        public ContentRecordingSession mSession;
        public final int mTargetSdkVersion;
        public int mTaskId;
        public final long mTimeoutMs;
        public IBinder mToken;
        public final int mType;
        public int mVirtualDisplayId;
        public final String packageName;
        public final int uid;
        public final UserHandle userHandle;

        public MediaProjection(int i, int i2, String str, int i3, boolean z) {
            long millis = Duration.ofMinutes(5L).toMillis();
            this.mTaskId = -1;
            this.mLaunchCookie = null;
            this.mTimeoutMs = millis;
            this.mCountStarts = 0;
            this.mVirtualDisplayId = -1;
            this.mType = i;
            this.uid = i2;
            this.packageName = str;
            this.userHandle = new UserHandle(UserHandle.getUserId(i2));
            this.mTargetSdkVersion = i3;
            this.mIsPrivileged = z;
            getClass();
            this.mCreateTimeMs = SystemClock.uptimeMillis();
            MediaProjectionManagerService.this.mActivityManagerInternal.notifyMediaProjectionEvent(i2, asBinder(), 0);
        }

        public final int applyVirtualDisplayFlags(int i) {
            applyVirtualDisplayFlags_enforcePermission();
            int i2 = this.mType;
            if (i2 == 0) {
                return (i & (-9)) | 18;
            }
            if (i2 == 1) {
                return (i & (-18)) | 10;
            }
            if (i2 == 2) {
                return (i & (-9)) | 19;
            }
            throw new RuntimeException("Unknown MediaProjection type");
        }

        public final boolean canProjectAudio() {
            int i = this.mType;
            return i == 1 || i == 2 || i == 0;
        }

        public final boolean canProjectSecureVideo() {
            return false;
        }

        public final boolean canProjectVideo() {
            int i = this.mType;
            return i == 1 || i == 0;
        }

        public final void dump(PrintWriter printWriter) {
            StringBuilder sb = new StringBuilder("(");
            sb.append(this.packageName);
            sb.append(", uid=");
            sb.append(this.uid);
            sb.append("): ");
            int i = this.mType;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "TYPE_PRESENTATION" : "TYPE_MIRRORING" : "TYPE_SCREEN_CAPTURE", printWriter);
        }

        public final ActivityOptions.LaunchCookie getLaunchCookie() {
            getLaunchCookie_enforcePermission();
            return this.mLaunchCookie;
        }

        public final MediaProjectionInfo getProjectionInfo() {
            return new MediaProjectionInfo(this.packageName, this.userHandle, this.mLaunchCookie);
        }

        public final int getTaskId() {
            getTaskId_enforcePermission();
            return this.mTaskId;
        }

        public final boolean isValid() {
            isValid_enforcePermission();
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    getClass();
                    boolean z = SystemClock.uptimeMillis() - this.mCreateTimeMs > this.mTimeoutMs;
                    boolean z2 = this.mVirtualDisplayId != -1;
                    if (!z && this.mCountStarts <= 1 && !z2) {
                        return true;
                    }
                    MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
                    Injector injector = mediaProjectionManagerService.mInjector;
                    MediaProjection mediaProjection = mediaProjectionManagerService.mProjectionGrant;
                    injector.getClass();
                    if (!CompatChanges.isChangeEnabled(MediaProjectionManagerService.MEDIA_PROJECTION_PREVENTS_REUSING_CONSENT, mediaProjection.packageName, UserHandle.getUserHandleForUid(mediaProjection.uid))) {
                        return false;
                    }
                    Slog.v("MediaProjectionManagerService", "Reusing token: Throw exception due to invalid projection.");
                    MediaProjectionManagerService.this.mProjectionGrant.stop();
                    throw new SecurityException("Don't re-use the resultData to retrieve the same projection instance, and don't use a token that has timed out. Don't take multiple captures by invoking MediaProjection#createVirtualDisplay multiple times on the same instance.");
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void notifyVirtualDisplayCreated(int i) {
            notifyVirtualDisplayCreated_enforcePermission();
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    this.mVirtualDisplayId = i;
                    ContentRecordingSession contentRecordingSession = this.mSession;
                    if (contentRecordingSession != null && contentRecordingSession.getVirtualDisplayId() == -1) {
                        Slog.v("MediaProjectionManagerService", "Virtual display now created, so update session with the virtual display id");
                        this.mSession.setVirtualDisplayId(this.mVirtualDisplayId);
                        if (!MediaProjectionManagerService.this.setContentRecordingSession(this.mSession)) {
                            Slog.e("MediaProjectionManagerService", "Failed to set session for virtual display id");
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) {
            if (iMediaProjectionCallback == null) {
                throw new IllegalArgumentException("callback must not be null");
            }
            CallbackDelegate callbackDelegate = MediaProjectionManagerService.this.mCallbackDelegate;
            synchronized (callbackDelegate.mLock) {
                ((ArrayMap) callbackDelegate.mClientCallbacks).put(iMediaProjectionCallback.asBinder(), iMediaProjectionCallback);
            }
        }

        public final void setLaunchCookie(ActivityOptions.LaunchCookie launchCookie) {
            setLaunchCookie_enforcePermission();
            this.mLaunchCookie = launchCookie;
        }

        public final void setTaskId(int i) {
            setTaskId_enforcePermission();
            this.mTaskId = i;
        }

        public final void start(final IMediaProjectionCallback iMediaProjectionCallback) {
            if (iMediaProjectionCallback == null) {
                throw new IllegalArgumentException("callback must not be null");
            }
            Slog.v("MediaProjectionManagerService", "Start the token instance " + this);
            boolean hasRunningForegroundService = MediaProjectionManagerService.this.mActivityManagerInternal.hasRunningForegroundService(this.uid, 32);
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    if (MediaProjectionManagerService.this.isCurrentProjection(asBinder())) {
                        Slog.w("MediaProjectionManagerService", "UID " + Binder.getCallingUid() + " attempted to start already started MediaProjection");
                        this.mCountStarts = this.mCountStarts + 1;
                        return;
                    }
                    if (this.mTargetSdkVersion >= 29 && !this.mIsPrivileged && !hasRunningForegroundService) {
                        throw new SecurityException("Media projections require a foreground service of type ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION");
                    }
                    try {
                        IBinder asBinder = iMediaProjectionCallback.asBinder();
                        this.mToken = asBinder;
                        ?? r1 = new IBinder.DeathRecipient() { // from class: com.android.server.media.projection.MediaProjectionManagerService$MediaProjection$$ExternalSyntheticLambda1
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                MediaProjectionManagerService.MediaProjection mediaProjection = MediaProjectionManagerService.MediaProjection.this;
                                IMediaProjectionCallback iMediaProjectionCallback2 = iMediaProjectionCallback;
                                int i = MediaProjectionManagerService.MediaProjection.$r8$clinit;
                                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Content Recording: MediaProjection stopped by Binder death - id= "), mediaProjection.mVirtualDisplayId, "MediaProjectionManagerService");
                                MediaProjectionManagerService.this.mCallbackDelegate.remove(iMediaProjectionCallback2);
                                mediaProjection.stop();
                            }
                        };
                        this.mDeathEater = r1;
                        asBinder.linkToDeath(r1, 0);
                        if (this.mType == 0) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (ArrayUtils.contains(MediaProjectionManagerService.this.mPackageManager.getPackageInfoAsUser(this.packageName, 4096, UserHandle.getUserId(this.uid)).requestedPermissions, "android.permission.SYSTEM_ALERT_WINDOW") && MediaProjectionManagerService.this.mAppOps.unsafeCheckOpRawNoThrow(24, this.uid, this.packageName) == 3) {
                                    MediaProjectionManagerService.this.mAppOps.setMode(24, this.uid, this.packageName, 0);
                                    this.mRestoreSystemAlertWindow = true;
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Slog.w("MediaProjectionManagerService", "Package not found, aborting MediaProjection", e);
                                return;
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        MediaProjectionManagerService.m678$$Nest$mstartProjectionLocked(MediaProjectionManagerService.this, this);
                        this.mCallback = iMediaProjectionCallback;
                        registerCallback(iMediaProjectionCallback);
                        this.mCountStarts++;
                    } catch (RemoteException e2) {
                        Slog.w("MediaProjectionManagerService", "MediaProjectionCallbacks must be valid, aborting MediaProjection", e2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void stop() {
            synchronized (MediaProjectionManagerService.this.mLock) {
                try {
                    if (!MediaProjectionManagerService.this.isCurrentProjection(asBinder())) {
                        Slog.w("MediaProjectionManagerService", "Attempted to stop inactive MediaProjection (uid=" + Binder.getCallingUid() + ", pid=" + Binder.getCallingPid() + ")");
                        return;
                    }
                    if (this.mRestoreSystemAlertWindow) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (MediaProjectionManagerService.this.mAppOps.unsafeCheckOpRawNoThrow(24, this.uid, this.packageName) == 0) {
                                MediaProjectionManagerService.this.mAppOps.setMode(24, this.uid, this.packageName, 3);
                            }
                            this.mRestoreSystemAlertWindow = false;
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    Slog.d("MediaProjectionManagerService", "Content Recording: handling stopping this projection token createTime= " + this.mCreateTimeMs + " countStarts= " + this.mCountStarts);
                    MediaProjectionManagerService.m679$$Nest$mstopProjectionLocked(MediaProjectionManagerService.this, this);
                    this.mToken.unlinkToDeath(this.mDeathEater, 0);
                    this.mToken = null;
                    unregisterCallback(this.mCallback);
                    this.mCallback = null;
                    MediaProjectionManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.media.projection.MediaProjectionManagerService$MediaProjection$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaProjectionManagerService.MediaProjection mediaProjection = MediaProjectionManagerService.MediaProjection.this;
                            MediaProjectionManagerService.this.mActivityManagerInternal.notifyMediaProjectionEvent(mediaProjection.uid, mediaProjection.asBinder(), 1);
                        }
                    });
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) {
            if (iMediaProjectionCallback == null) {
                throw new IllegalArgumentException("callback must not be null");
            }
            MediaProjectionManagerService.this.mCallbackDelegate.remove(iMediaProjectionCallback);
        }
    }

    public final class MediaRouterCallback extends MediaRouter.SimpleCallback {
        public MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            synchronized (MediaProjectionManagerService.this.mLock) {
                if ((i & 4) != 0) {
                    try {
                        MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
                        mediaProjectionManagerService.mMediaRouteInfo = routeInfo;
                        if (mediaProjectionManagerService.mProjectionGrant != null) {
                            Slog.d("MediaProjectionManagerService", "Content Recording: Stopped MediaProjection due to route type of REMOTE_DISPLAY not selected");
                            MediaProjectionManagerService.this.mProjectionGrant.stop();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
            if (mediaProjectionManagerService.mMediaRouteInfo == routeInfo) {
                mediaProjectionManagerService.mMediaRouteInfo = null;
            }
        }
    }

    public final class WatcherSessionCallback implements Runnable {
        public final IMediaProjectionWatcherCallback mCallback;
        public final MediaProjectionInfo mProjectionInfo;
        public final ContentRecordingSession mSession;

        public WatcherSessionCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
            this.mCallback = iMediaProjectionWatcherCallback;
            this.mProjectionInfo = mediaProjectionInfo;
            this.mSession = contentRecordingSession;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                this.mCallback.onRecordingSessionSet(this.mProjectionInfo, this.mSession);
            } catch (RemoteException e) {
                Slog.w("MediaProjectionManagerService", "Failed to notify content recording session changed", e);
            }
        }
    }

    public final class WatcherStopCallback implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public IMediaProjectionWatcherCallback mCallback;
        public MediaProjectionInfo mInfo;

        public /* synthetic */ WatcherStopCallback(int i) {
            this.$r8$classId = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        this.mCallback.onStop(this.mInfo);
                        break;
                    } catch (RemoteException e) {
                        Slog.w("MediaProjectionManagerService", "Failed to notify media projection has stopped", e);
                        return;
                    }
                default:
                    try {
                        this.mCallback.onStart(this.mInfo);
                        break;
                    } catch (RemoteException e2) {
                        Slog.w("MediaProjectionManagerService", "Failed to notify media projection has started", e2);
                    }
            }
        }
    }

    /* renamed from: -$$Nest$mremoveCallback, reason: not valid java name */
    public static void m677$$Nest$mremoveCallback(MediaProjectionManagerService mediaProjectionManagerService, IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
        synchronized (mediaProjectionManagerService.mLock) {
            IBinder asBinder = iMediaProjectionWatcherCallback.asBinder();
            IBinder.DeathRecipient deathRecipient = (IBinder.DeathRecipient) ((ArrayMap) mediaProjectionManagerService.mDeathEaters).remove(asBinder);
            if (deathRecipient != null) {
                asBinder.unlinkToDeath(deathRecipient, 0);
            }
            CallbackDelegate callbackDelegate = mediaProjectionManagerService.mCallbackDelegate;
            synchronized (callbackDelegate.mLock) {
                ((ArrayMap) callbackDelegate.mWatcherCallbacks).remove(iMediaProjectionWatcherCallback.asBinder());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0055, code lost:
    
        if (r0 != null) goto L21;
     */
    /* renamed from: -$$Nest$mstartProjectionLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m678$$Nest$mstartProjectionLocked(final com.android.server.media.projection.MediaProjectionManagerService r7, com.android.server.media.projection.MediaProjectionManagerService.MediaProjection r8) {
        /*
            com.android.server.media.projection.MediaProjectionManagerService$MediaProjection r0 = r7.mProjectionGrant
            if (r0 == 0) goto L10
            java.lang.String r0 = "MediaProjectionManagerService"
            java.lang.String r1 = "Content Recording: Stopped MediaProjection to start new incoming projection"
            android.util.Slog.d(r0, r1)
            com.android.server.media.projection.MediaProjectionManagerService$MediaProjection r0 = r7.mProjectionGrant
            r0.stop()
        L10:
            android.media.MediaRouter$RouteInfo r0 = r7.mMediaRouteInfo
            if (r0 == 0) goto L87
            int r0 = r0.getStatusCode()
            r1 = 6
            if (r0 != r1) goto L87
            android.content.Context r0 = r7.mContext
            java.lang.String r1 = "desktopmode"
            java.lang.Object r0 = r0.getSystemService(r1)
            com.samsung.android.desktopmode.SemDesktopModeManager r0 = (com.samsung.android.desktopmode.SemDesktopModeManager) r0
            if (r0 == 0) goto L58
            com.samsung.android.desktopmode.SemDesktopModeState r0 = r0.getDesktopModeState()
            int r0 = r0.enabled
            r1 = 4
            if (r0 != r1) goto L58
            android.content.Context r0 = r7.mContext
            java.lang.String r1 = "display"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.hardware.display.DisplayManager r0 = (android.hardware.display.DisplayManager) r0
            android.hardware.display.SemWifiDisplayStatus r1 = r0.semGetWifiDisplayStatus()
            if (r1 == 0) goto L58
            android.hardware.display.SemWifiDisplayStatus r1 = r0.semGetWifiDisplayStatus()
            int r1 = r1.getActiveDisplayState()
            r2 = 2
            if (r1 != r2) goto L58
            android.hardware.display.SemWifiDisplayStatus r0 = r0.semGetWifiDisplayStatus()
            android.hardware.display.SemWifiDisplay r0 = r0.getActiveDisplay()
            if (r0 == 0) goto L58
            goto L59
        L58:
            r0 = 0
        L59:
            if (r0 == 0) goto L6d
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            r1.<init>(r2)
            com.android.server.media.projection.MediaProjectionManagerService$$ExternalSyntheticLambda0 r2 = new com.android.server.media.projection.MediaProjectionManagerService$$ExternalSyntheticLambda0
            r2.<init>()
            r1.post(r2)
            goto L7e
        L6d:
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            r0.<init>(r1)
            com.android.server.media.projection.MediaProjectionManagerService$3 r1 = new com.android.server.media.projection.MediaProjectionManagerService$3
            r1.<init>(r7)
            r0.post(r1)
        L7e:
            android.media.MediaRouter r0 = r7.mMediaRouter
            android.media.MediaRouter$RouteInfo r0 = r0.getFallbackRoute()
            r0.select()
        L87:
            android.os.IBinder r0 = r8.asBinder()
            r7.mProjectionToken = r0
            r7.mProjectionGrant = r8
            com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate r7 = r7.mCallbackDelegate
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            java.util.Map r1 = r7.mWatcherCallbacks     // Catch: java.lang.Throwable -> Lc0
            android.util.ArrayMap r1 = (android.util.ArrayMap) r1     // Catch: java.lang.Throwable -> Lc0
            java.util.Collection r1 = r1.values()     // Catch: java.lang.Throwable -> Lc0
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> Lc0
        La0:
            boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> Lc0
            if (r2 == 0) goto Lc2
            java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> Lc0
            android.media.projection.IMediaProjectionWatcherCallback r2 = (android.media.projection.IMediaProjectionWatcherCallback) r2     // Catch: java.lang.Throwable -> Lc0
            android.media.projection.MediaProjectionInfo r3 = r8.getProjectionInfo()     // Catch: java.lang.Throwable -> Lc0
            android.os.Handler r4 = r7.mHandler     // Catch: java.lang.Throwable -> Lc0
            com.android.server.media.projection.MediaProjectionManagerService$WatcherStopCallback r5 = new com.android.server.media.projection.MediaProjectionManagerService$WatcherStopCallback     // Catch: java.lang.Throwable -> Lc0
            r6 = 1
            r5.<init>(r6)     // Catch: java.lang.Throwable -> Lc0
            r5.mInfo = r3     // Catch: java.lang.Throwable -> Lc0
            r5.mCallback = r2     // Catch: java.lang.Throwable -> Lc0
            r4.post(r5)     // Catch: java.lang.Throwable -> Lc0
            goto La0
        Lc0:
            r7 = move-exception
            goto Lc4
        Lc2:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc0
            return
        Lc4:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc0
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.media.projection.MediaProjectionManagerService.m678$$Nest$mstartProjectionLocked(com.android.server.media.projection.MediaProjectionManagerService, com.android.server.media.projection.MediaProjectionManagerService$MediaProjection):void");
    }

    /* renamed from: -$$Nest$mstopProjectionLocked, reason: not valid java name */
    public static void m679$$Nest$mstopProjectionLocked(MediaProjectionManagerService mediaProjectionManagerService, MediaProjection mediaProjection) {
        mediaProjectionManagerService.getClass();
        Slog.d("MediaProjectionManagerService", "Content Recording: Stopped active MediaProjection and dispatching stop to callbacks");
        ContentRecordingSession contentRecordingSession = mediaProjection.mSession;
        int targetUid = contentRecordingSession != null ? contentRecordingSession.getTargetUid() : -2;
        MediaProjectionMetricsLogger mediaProjectionMetricsLogger = mediaProjectionManagerService.mMediaProjectionMetricsLogger;
        int i = mediaProjection.uid;
        boolean z = mediaProjectionMetricsLogger.mPreviousState == 4;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("logStopped: wasCaptureInProgress=", "MediaProjectionMetricsLogger", z);
        mediaProjectionMetricsLogger.writeStateChanged(mediaProjectionMetricsLogger.mSessionIdGenerator.getCurrentSessionId(), 7, i, targetUid, -1, 0);
        if (z) {
            MediaProjectionTimestampStore mediaProjectionTimestampStore = mediaProjectionMetricsLogger.mTimestampStore;
            synchronized (mediaProjectionTimestampStore.mTimestampLock) {
                mediaProjectionTimestampStore.mSharedPreferences.edit().putLong("media_projection_timestamp_key", mediaProjectionTimestampStore.mInstantSource.instant().toEpochMilli()).apply();
            }
        }
        mediaProjectionManagerService.mProjectionToken = null;
        mediaProjectionManagerService.mProjectionGrant = null;
        CallbackDelegate callbackDelegate = mediaProjectionManagerService.mCallbackDelegate;
        synchronized (callbackDelegate.mLock) {
            try {
                for (IMediaProjectionCallback iMediaProjectionCallback : ((ArrayMap) callbackDelegate.mClientCallbacks).values()) {
                    Handler handler = callbackDelegate.mHandler;
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3();
                    anonymousClass3.this$0 = iMediaProjectionCallback;
                    handler.post(anonymousClass3);
                }
                for (IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback : ((ArrayMap) callbackDelegate.mWatcherCallbacks).values()) {
                    MediaProjectionInfo projectionInfo = mediaProjection.getProjectionInfo();
                    Handler handler2 = callbackDelegate.mHandler;
                    WatcherStopCallback watcherStopCallback = new WatcherStopCallback(0);
                    watcherStopCallback.mInfo = projectionInfo;
                    watcherStopCallback.mCallback = iMediaProjectionWatcherCallback;
                    handler2.post(watcherStopCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public MediaProjectionManagerService(Context context) {
        this(context, new Injector());
    }

    public MediaProjectionManagerService(Context context, Injector injector) {
        super(context);
        MediaProjectionSessionIdGenerator mediaProjectionSessionIdGenerator;
        MediaProjectionTimestampStore mediaProjectionTimestampStore;
        this.mLock = new Object();
        this.mContext = context;
        this.mInjector = injector;
        this.mHandler = new Handler(Looper.getMainLooper());
        injector.getClass();
        this.mClock = new MediaProjectionManagerService$Injector$$ExternalSyntheticLambda0();
        this.mDeathEaters = new ArrayMap();
        this.mCallbackDelegate = new CallbackDelegate(Looper.getMainLooper());
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mPackageManager = context.getPackageManager();
        this.mWmInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mMediaRouter = (MediaRouter) context.getSystemService("media_router");
        this.mMediaRouterCallback = new MediaRouterCallback();
        if (MediaProjectionMetricsLogger.sSingleton == null) {
            FrameworkStatsLogWrapper frameworkStatsLogWrapper = new FrameworkStatsLogWrapper();
            synchronized (MediaProjectionSessionIdGenerator.sInstanceLock) {
                try {
                    if (MediaProjectionSessionIdGenerator.sInstance == null) {
                        MediaProjectionSessionIdGenerator.sInstance = new MediaProjectionSessionIdGenerator(context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "media_projection_session_id"), 0));
                    }
                    mediaProjectionSessionIdGenerator = MediaProjectionSessionIdGenerator.sInstance;
                } finally {
                }
            }
            synchronized (MediaProjectionTimestampStore.sInstanceLock) {
                try {
                    if (MediaProjectionTimestampStore.sInstance == null) {
                        MediaProjectionTimestampStore.sInstance = new MediaProjectionTimestampStore(context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "media_projection_timestamp"), 0), InstantSource.system());
                    }
                    mediaProjectionTimestampStore = MediaProjectionTimestampStore.sInstance;
                } finally {
                }
            }
            MediaProjectionMetricsLogger.sSingleton = new MediaProjectionMetricsLogger(frameworkStatsLogWrapper, mediaProjectionSessionIdGenerator, mediaProjectionTimestampStore);
        }
        this.mMediaProjectionMetricsLogger = MediaProjectionMetricsLogger.sSingleton;
        Watchdog.getInstance().addMonitor(this);
    }

    public MediaProjectionInfo addCallback(final IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
        MediaProjectionInfo projectionInfo;
        IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.media.projection.MediaProjectionManagerService.2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                MediaProjectionManagerService.m677$$Nest$mremoveCallback(MediaProjectionManagerService.this, iMediaProjectionWatcherCallback);
            }
        };
        synchronized (this.mLock) {
            try {
                CallbackDelegate callbackDelegate = this.mCallbackDelegate;
                synchronized (callbackDelegate.mLock) {
                    ((ArrayMap) callbackDelegate.mWatcherCallbacks).put(iMediaProjectionWatcherCallback.asBinder(), iMediaProjectionWatcherCallback);
                }
                try {
                    IBinder asBinder = iMediaProjectionWatcherCallback.asBinder();
                    asBinder.linkToDeath(deathRecipient, 0);
                    ((ArrayMap) this.mDeathEaters).put(asBinder, deathRecipient);
                } catch (RemoteException e) {
                    Slog.e("MediaProjectionManagerService", "Unable to link to death for media projection monitoring callback", e);
                }
                MediaProjection mediaProjection = this.mProjectionGrant;
                projectionInfo = mediaProjection != null ? mediaProjection.getProjectionInfo() : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return projectionInfo;
    }

    public MediaProjection createProjectionInternal(int i, String str, int i2, boolean z, UserHandle userHandle) {
        PackageInfo packageInfo;
        try {
            ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(str, PackageManager.ApplicationInfoFlags.of(0L), userHandle);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaProjection mediaProjection = new MediaProjection(i2, i, str, applicationInfoAsUser.targetSdkVersion, applicationInfoAsUser.isPrivilegedApp());
                if (z) {
                    this.mAppOps.setMode(46, mediaProjection.uid, mediaProjection.packageName, 0);
                }
                try {
                    packageInfo = this.mPackageManager.getPackageInfo(str, 64);
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.w("MediaProjectionManagerService", "Package not found: " + str, e);
                    packageInfo = null;
                }
                if (packageInfo != null && (packageInfo.applicationInfo.flags & 1) == 0 && "CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                    try {
                        IAppOpsService.Stub.asInterface(ServiceManager.getService("appops")).writePermissionAccessInformation(1000, i, str, null, this.mContext.getAttributionTag(), 600);
                    } catch (Exception e2) {
                        Slog.w("MediaProjectionManagerService", e2.getMessage(), e2);
                    }
                }
                return mediaProjection;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("No package matching :", str));
        }
    }

    public final void dispatchSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
        CallbackDelegate callbackDelegate = this.mCallbackDelegate;
        synchronized (callbackDelegate.mLock) {
            try {
                Iterator it = ((ArrayMap) callbackDelegate.mWatcherCallbacks).values().iterator();
                while (it.hasNext()) {
                    callbackDelegate.mHandler.post(new WatcherSessionCallback((IMediaProjectionWatcherCallback) it.next(), mediaProjectionInfo, contentRecordingSession));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public MediaProjectionInfo getActiveProjectionInfo() {
        synchronized (this.mLock) {
            try {
                MediaProjection mediaProjection = this.mProjectionGrant;
                if (mediaProjection == null) {
                    return null;
                }
                return mediaProjection.getProjectionInfo();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public MediaProjection getProjectionInternal(int i, String str) {
        ContentRecordingSession contentRecordingSession;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                MediaProjection mediaProjection = this.mProjectionGrant;
                PackageInfo packageInfo = null;
                if (mediaProjection != null && (contentRecordingSession = mediaProjection.mSession) != null && contentRecordingSession.isWaitingForConsent()) {
                    MediaProjection mediaProjection2 = this.mProjectionGrant;
                    if (mediaProjection2.uid != i || !Objects.equals(mediaProjection2.packageName, str)) {
                        Slog.e("MediaProjectionManagerService", "Reusing token: Not possible to reuse the current projection instance due to package details mismatching");
                        return null;
                    }
                    Slog.v("MediaProjectionManagerService", "Reusing token: getProjection can reuse the current projection");
                    try {
                        packageInfo = this.mPackageManager.getPackageInfo(str, 64);
                    } catch (PackageManager.NameNotFoundException e) {
                        Slog.w("MediaProjectionManagerService", "Package not found: " + str, e);
                    }
                    if (packageInfo != null && (packageInfo.applicationInfo.flags & 1) == 0 && "CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                        try {
                            IAppOpsService.Stub.asInterface(ServiceManager.getService("appops")).writePermissionAccessInformation(1000, i, str, null, this.mContext.getAttributionTag(), 600);
                        } catch (Exception e2) {
                            Slog.w("MediaProjectionManagerService", e2.getMessage(), e2);
                        }
                    }
                    return this.mProjectionGrant;
                }
                Slog.e("MediaProjectionManagerService", "Reusing token: Not possible to reuse the current projection instance");
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCurrentProjection(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                IBinder iBinder2 = this.mProjectionToken;
                if (iBinder2 == null) {
                    return false;
                }
                return iBinder2.equals(iBinder);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        synchronized (this.mLock) {
        }
    }

    public void notifyAppSelectorDisplayed(int i) {
        MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
        mediaProjectionMetricsLogger.getClass();
        Log.d("MediaProjectionMetricsLogger", "logAppSelectorDisplayed");
        mediaProjectionMetricsLogger.writeStateChanged(mediaProjectionMetricsLogger.mSessionIdGenerator.getCurrentSessionId(), 3, i, -2, -1, 0);
    }

    public void notifyPermissionRequestCancelled(int i) {
        MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
        mediaProjectionMetricsLogger.writeStateChanged(mediaProjectionMetricsLogger.mSessionIdGenerator.getCurrentSessionId(), 8, i, -2, -1, 0);
    }

    public void notifyPermissionRequestDisplayed(int i) {
        MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
        mediaProjectionMetricsLogger.getClass();
        Log.d("MediaProjectionMetricsLogger", "logPermissionRequestDisplayed");
        mediaProjectionMetricsLogger.writeStateChanged(mediaProjectionMetricsLogger.mSessionIdGenerator.getCurrentSessionId(), 2, i, -2, -1, 0);
    }

    public void notifyPermissionRequestInitiated(int i, int i2) {
        Duration duration;
        int currentSessionId;
        MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
        mediaProjectionMetricsLogger.getClass();
        Log.d("MediaProjectionMetricsLogger", "logInitiated");
        MediaProjectionTimestampStore mediaProjectionTimestampStore = mediaProjectionMetricsLogger.mTimestampStore;
        synchronized (mediaProjectionTimestampStore.mTimestampLock) {
            try {
                long j = mediaProjectionTimestampStore.mSharedPreferences.getLong("media_projection_timestamp_key", -1L);
                duration = null;
                Instant ofEpochMilli = j == -1 ? null : Instant.ofEpochMilli(j);
                if (ofEpochMilli != null) {
                    duration = Duration.between(ofEpochMilli, mediaProjectionTimestampStore.mInstantSource.instant());
                }
            } finally {
            }
        }
        int seconds = duration == null ? -1 : (int) duration.toSeconds();
        MediaProjectionSessionIdGenerator mediaProjectionSessionIdGenerator = mediaProjectionMetricsLogger.mSessionIdGenerator;
        synchronized (mediaProjectionSessionIdGenerator.mSessionIdLock) {
            currentSessionId = mediaProjectionSessionIdGenerator.getCurrentSessionId() + 1;
            mediaProjectionSessionIdGenerator.mSharedPreferences.edit().putInt("media_projection_session_id_key", currentSessionId).apply();
        }
        mediaProjectionMetricsLogger.writeStateChanged(currentSessionId, 1, i, -2, seconds, i2);
    }

    public void notifyWindowingModeChanged(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                MediaProjection mediaProjection = this.mProjectionGrant;
                if (mediaProjection == null) {
                    Slog.i("MediaProjectionManagerService", "Cannot log MediaProjectionTargetChanged atom due to null projection");
                } else {
                    MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
                    int i4 = mediaProjection.uid;
                    mediaProjectionMetricsLogger.getClass();
                    Log.d("MediaProjectionMetricsLogger", "logChangedWindowingMode");
                    int currentSessionId = mediaProjectionMetricsLogger.mSessionIdGenerator.getCurrentSessionId();
                    int contentToRecordToTargetType = mediaProjectionMetricsLogger.contentToRecordToTargetType(i);
                    int windowingModeToTargetWindowingMode = mediaProjectionMetricsLogger.windowingModeToTargetWindowingMode(i3);
                    mediaProjectionMetricsLogger.mFrameworkStatsLogWrapper.getClass();
                    FrameworkStatsLog.write(FrameworkStatsLog.MEDIA_PROJECTION_TARGET_CHANGED, currentSessionId, contentToRecordToTargetType, i4, i2, windowingModeToTargetWindowingMode);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("media_projection", new BinderService(this.mContext), false);
        this.mMediaRouter.addCallback(4, this.mMediaRouterCallback, 8);
        this.mActivityManagerInternal.registerProcessObserver(new IProcessObserver.Stub() { // from class: com.android.server.media.projection.MediaProjectionManagerService.1
            public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            }

            public final void onForegroundServicesChanged(int i, int i2, int i3) {
                MediaProjectionManagerService mediaProjectionManagerService = MediaProjectionManagerService.this;
                synchronized (mediaProjectionManagerService.mLock) {
                    MediaProjection mediaProjection = mediaProjectionManagerService.mProjectionGrant;
                    if (mediaProjection != null && mediaProjection.uid == i2) {
                        if (mediaProjection.mTargetSdkVersion < 29 || mediaProjection.mIsPrivileged) {
                            return;
                        }
                        if (mediaProjectionManagerService.mActivityManagerInternal.hasRunningForegroundService(i2, 32)) {
                            return;
                        }
                        synchronized (mediaProjectionManagerService.mLock) {
                            try {
                                Slog.d("MediaProjectionManagerService", "Content Recording: Stopped MediaProjection due to foreground service change");
                                MediaProjection mediaProjection2 = mediaProjectionManagerService.mProjectionGrant;
                                if (mediaProjection2 != null) {
                                    mediaProjection2.stop();
                                }
                            } finally {
                            }
                        }
                    }
                }
            }

            public final void onProcessDied(int i, int i2) {
            }

            public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {
            }
        });
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        this.mMediaRouter.rebindAsUser(targetUser2.getUserIdentifier());
        synchronized (this.mLock) {
            try {
                if (this.mProjectionGrant != null) {
                    Slog.d("MediaProjectionManagerService", "Content Recording: Stopped MediaProjection due to user switching");
                    this.mProjectionGrant.stop();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void requestConsentForInvalidProjection() {
        Intent flags;
        int i;
        synchronized (this.mLock) {
            flags = new Intent().setComponent(ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.error_handwriting_unsupported_password))).putExtra("extra_media_projection_user_consent_required", true).putExtra("extra_media_projection_package_reusing_consent", this.mProjectionGrant.packageName).setFlags(276824064);
            i = this.mProjectionGrant.uid;
        }
        Slog.v("MediaProjectionManagerService", "Reusing token: Reshow dialog for due to invalid projection.");
        this.mContext.startActivityAsUser(flags, UserHandle.getUserHandleForUid(i));
    }

    public boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
        boolean contentRecordingSession2 = this.mWmInternal.setContentRecordingSession(contentRecordingSession);
        synchronized (this.mLock) {
            try {
                if (!contentRecordingSession2) {
                    if (this.mProjectionGrant != null) {
                        Slog.w("MediaProjectionManagerService", "Content Recording: Stopped MediaProjection due to failing to set ContentRecordingSession - id= " + this.mProjectionGrant.mVirtualDisplayId + "type=" + (contentRecordingSession != null ? ContentRecordingSession.recordContentToString(contentRecordingSession.getContentToRecord()) : "none"));
                        this.mProjectionGrant.stop();
                    }
                    return false;
                }
                MediaProjection mediaProjection = this.mProjectionGrant;
                if (mediaProjection != null) {
                    mediaProjection.mSession = contentRecordingSession;
                    if (contentRecordingSession != null) {
                        MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
                        int i = mediaProjection.uid;
                        int targetUid = contentRecordingSession.getTargetUid();
                        mediaProjectionMetricsLogger.getClass();
                        Log.d("MediaProjectionMetricsLogger", "logInProgress");
                        mediaProjectionMetricsLogger.writeStateChanged(mediaProjectionMetricsLogger.mSessionIdGenerator.getCurrentSessionId(), 4, i, targetUid, -1, 0);
                    }
                    dispatchSessionSet(this.mProjectionGrant.getProjectionInfo(), contentRecordingSession);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setReviewedConsentSessionLocked(ContentRecordingSession contentRecordingSession) {
        if (contentRecordingSession != null) {
            contentRecordingSession.setWaitingForConsent(false);
            contentRecordingSession.setVirtualDisplayId(this.mProjectionGrant.mVirtualDisplayId);
        }
        Slog.v("MediaProjectionManagerService", "Reusing token: Processed consent so set the session " + contentRecordingSession);
        if (setContentRecordingSession(contentRecordingSession)) {
            return;
        }
        Slog.e("MediaProjectionManagerService", "Reusing token: Failed to set session for reused consent, so stop");
    }

    public void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) {
        IBinder asBinder;
        synchronized (this.mLock) {
            IBinder iBinder = null;
            if (i == 1 || i == 2) {
                if (iMediaProjection == null) {
                    asBinder = null;
                } else {
                    try {
                        asBinder = iMediaProjection.asBinder();
                    } finally {
                    }
                }
                if (!isCurrentProjection(asBinder)) {
                    Slog.v("MediaProjectionManagerService", "Reusing token: Ignore consent result of " + i + " for a token that isn't current");
                    return;
                }
            }
            MediaProjection mediaProjection = this.mProjectionGrant;
            if (mediaProjection == null) {
                Slog.w("MediaProjectionManagerService", "Reusing token: Can't review consent with no ongoing projection.");
                return;
            }
            ContentRecordingSession contentRecordingSession = mediaProjection.mSession;
            if (contentRecordingSession != null && contentRecordingSession.isWaitingForConsent()) {
                Slog.v("MediaProjectionManagerService", "Reusing token: Handling user consent result " + i);
                if (i == -1 || i == 0) {
                    setReviewedConsentSessionLocked(null);
                    if (this.mProjectionGrant != null) {
                        Slog.w("MediaProjectionManagerService", "Content Recording: Stopped MediaProjection due to user consent result of CANCEL - id= " + this.mProjectionGrant.mVirtualDisplayId);
                        this.mProjectionGrant.stop();
                    }
                } else if (i == 1) {
                    setReviewedConsentSessionLocked(ContentRecordingSession.createDisplaySession(0));
                } else if (i == 2) {
                    if (this.mProjectionGrant.getLaunchCookie() != null) {
                        iBinder = this.mProjectionGrant.getLaunchCookie().binder;
                    }
                    setReviewedConsentSessionLocked(ContentRecordingSession.createTaskSession(iBinder, this.mProjectionGrant.mTaskId));
                }
                return;
            }
            Slog.w("MediaProjectionManagerService", "Reusing token: Ignore consent result " + i + " if not waiting for the result.");
        }
    }
}
