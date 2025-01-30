package com.android.server.media.projection;

import android.R;
import android.app.ActivityManagerInternal;
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
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.ContentRecordingSession;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.internal.app.IAppOpsService;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaProjectionManagerService extends SystemService implements Watchdog.Monitor {
  static final long MEDIA_PROJECTION_PREVENTS_REUSING_CONSENT = 266201607;
  public final ActivityManagerInternal mActivityManagerInternal;
  public final AppOpsManager mAppOps;
  public final CallbackDelegate mCallbackDelegate;
  public final Clock mClock;
  public final Context mContext;
  public final Map mDeathEaters;
  public final Handler mHandler;
  public final Injector mInjector;
  public final Object mLock;
  public MediaRouter.RouteInfo mMediaRouteInfo;
  public final MediaRouter mMediaRouter;
  public final MediaRouterCallback mMediaRouterCallback;
  public final PackageManager mPackageManager;
  public MediaProjection mProjectionGrant;
  public IBinder mProjectionToken;
  public final WindowManagerInternal mWmInternal;

  interface Clock {
    long uptimeMillis();
  }

  public MediaProjectionManagerService(Context context) {
    this(context, new Injector());
  }

  public MediaProjectionManagerService(Context context, Injector injector) {
    super(context);
    this.mLock = new Object();
    this.mContext = context;
    this.mInjector = injector;
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mClock = injector.createClock();
    this.mDeathEaters = new ArrayMap();
    this.mCallbackDelegate = new CallbackDelegate();
    this.mAppOps = (AppOpsManager) context.getSystemService("appops");
    this.mActivityManagerInternal =
        (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    this.mPackageManager = context.getPackageManager();
    this.mWmInternal =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    this.mMediaRouter = (MediaRouter) context.getSystemService("media_router");
    this.mMediaRouterCallback = new MediaRouterCallback();
    Watchdog.getInstance().addMonitor(this);
  }

  class Injector {
    public boolean shouldMediaProjectionPreventReusingConsent(MediaProjection mediaProjection) {
      return CompatChanges.isChangeEnabled(
          MediaProjectionManagerService.MEDIA_PROJECTION_PREVENTS_REUSING_CONSENT,
          mediaProjection.packageName,
          UserHandle.getUserHandleForUid(mediaProjection.uid));
    }

    public Clock createClock() {
      return new Clock() { // from class:
                           // com.android.server.media.projection.MediaProjectionManagerService$Injector$$ExternalSyntheticLambda0
        @Override // com.android.server.media.projection.MediaProjectionManagerService.Clock
        public final long uptimeMillis() {
          return SystemClock.uptimeMillis();
        }
      };
    }
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("media_projection", new BinderService(this.mContext), false);
    this.mMediaRouter.addCallback(4, this.mMediaRouterCallback, 8);
    this.mActivityManagerInternal.registerProcessObserver(
        new IProcessObserver
            .Stub() { // from class:
                      // com.android.server.media.projection.MediaProjectionManagerService.1
          public void onForegroundActivitiesChanged(int i, int i2, boolean z) {}

          public void onProcessDied(int i, int i2) {}

          public void onForegroundServicesChanged(int i, int i2, int i3) {
            MediaProjectionManagerService.this.handleForegroundServicesChanged(i, i2, i3);
          }
        });
  }

  @Override // com.android.server.SystemService
  public void onUserSwitching(
      SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    this.mMediaRouter.rebindAsUser(targetUser2.getUserIdentifier());
    synchronized (this.mLock) {
      MediaProjection mediaProjection = this.mProjectionGrant;
      if (mediaProjection != null) {
        mediaProjection.stop();
      }
    }
  }

  @Override // com.android.server.Watchdog.Monitor
  public void monitor() {
    synchronized (this.mLock) {
    }
  }

  public final void handleForegroundServicesChanged(int i, int i2, int i3) {
    synchronized (this.mLock) {
      MediaProjection mediaProjection = this.mProjectionGrant;
      if (mediaProjection != null && mediaProjection.uid == i2) {
        if (mediaProjection.requiresForegroundService()) {
          if (this.mActivityManagerInternal.hasRunningForegroundService(i2, 32)) {
            return;
          }
          synchronized (this.mLock) {
            MediaProjection mediaProjection2 = this.mProjectionGrant;
            if (mediaProjection2 != null) {
              mediaProjection2.stop();
            }
          }
        }
      }
    }
  }

  public final void startProjectionLocked(MediaProjection mediaProjection) {
    MediaProjection mediaProjection2 = this.mProjectionGrant;
    if (mediaProjection2 != null) {
      mediaProjection2.stop();
    }
    MediaRouter.RouteInfo routeInfo = this.mMediaRouteInfo;
    if (routeInfo != null && routeInfo.getStatusCode() == 6) {
      SemWifiDisplay desktopModeWifiDisplay = getDesktopModeWifiDisplay();
      if (desktopModeWifiDisplay != null) {
        showDexExceptionToast(desktopModeWifiDisplay);
      } else {
        showSmartViewExceptionToast();
      }
      this.mMediaRouter.getFallbackRoute().select();
    }
    this.mProjectionToken = mediaProjection.asBinder();
    this.mProjectionGrant = mediaProjection;
    dispatchStart(mediaProjection);
  }

  public final void stopProjectionLocked(MediaProjection mediaProjection) {
    this.mProjectionToken = null;
    this.mProjectionGrant = null;
    dispatchStop(mediaProjection);
  }

  public final void addCallback(
      final IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
    IBinder.DeathRecipient deathRecipient =
        new IBinder
            .DeathRecipient() { // from class:
                                // com.android.server.media.projection.MediaProjectionManagerService.2
          @Override // android.os.IBinder.DeathRecipient
          public void binderDied() {
            MediaProjectionManagerService.this.removeCallback(iMediaProjectionWatcherCallback);
          }
        };
    synchronized (this.mLock) {
      this.mCallbackDelegate.add(iMediaProjectionWatcherCallback);
      linkDeathRecipientLocked(iMediaProjectionWatcherCallback, deathRecipient);
    }
  }

  public final void removeCallback(
      IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
    synchronized (this.mLock) {
      unlinkDeathRecipientLocked(iMediaProjectionWatcherCallback);
      this.mCallbackDelegate.remove(iMediaProjectionWatcherCallback);
    }
  }

  public final void linkDeathRecipientLocked(
      IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback,
      IBinder.DeathRecipient deathRecipient) {
    try {
      IBinder asBinder = iMediaProjectionWatcherCallback.asBinder();
      asBinder.linkToDeath(deathRecipient, 0);
      this.mDeathEaters.put(asBinder, deathRecipient);
    } catch (RemoteException e) {
      Slog.e(
          "MediaProjectionManagerService",
          "Unable to link to death for media projection monitoring callback",
          e);
    }
  }

  public final void unlinkDeathRecipientLocked(
      IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
    IBinder asBinder = iMediaProjectionWatcherCallback.asBinder();
    IBinder.DeathRecipient deathRecipient =
        (IBinder.DeathRecipient) this.mDeathEaters.remove(asBinder);
    if (deathRecipient != null) {
      asBinder.unlinkToDeath(deathRecipient, 0);
    }
  }

  public final void dispatchStart(MediaProjection mediaProjection) {
    this.mCallbackDelegate.dispatchStart(mediaProjection);
  }

  public final void dispatchStop(MediaProjection mediaProjection) {
    this.mCallbackDelegate.dispatchStop(mediaProjection);
  }

  public boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
    boolean contentRecordingSession2 =
        this.mWmInternal.setContentRecordingSession(contentRecordingSession);
    synchronized (this.mLock) {
      if (!contentRecordingSession2) {
        MediaProjection mediaProjection = this.mProjectionGrant;
        if (mediaProjection != null) {
          mediaProjection.stop();
        }
        return false;
      }
      MediaProjection mediaProjection2 = this.mProjectionGrant;
      if (mediaProjection2 != null) {
        mediaProjection2.mSession = contentRecordingSession;
      }
      return true;
    }
  }

  public boolean isCurrentProjection(IBinder iBinder) {
    synchronized (this.mLock) {
      IBinder iBinder2 = this.mProjectionToken;
      if (iBinder2 == null) {
        return false;
      }
      return iBinder2.equals(iBinder);
    }
  }

  public void requestConsentForInvalidProjection() {
    Intent buildReviewGrantedConsentIntentLocked;
    int i;
    synchronized (this.mLock) {
      buildReviewGrantedConsentIntentLocked = buildReviewGrantedConsentIntentLocked();
      i = this.mProjectionGrant.uid;
    }
    Slog.v(
        "MediaProjectionManagerService",
        "Reusing token: Reshow dialog for due to invalid projection.");
    this.mContext.startActivityAsUser(
        buildReviewGrantedConsentIntentLocked, UserHandle.getUserHandleForUid(i));
  }

  public final Intent buildReviewGrantedConsentIntentLocked() {
    return new Intent()
        .setComponent(
            ComponentName.unflattenFromString(
                this.mContext.getResources().getString(R.string.duration_minutes_relative)))
        .putExtra("extra_media_projection_user_consent_required", true)
        .putExtra(
            "extra_media_projection_package_reusing_consent", this.mProjectionGrant.packageName)
        .setFlags(276824064);
  }

  public void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) {
    synchronized (this.mLock) {
      if (i == 1 || i == 2) {
        if (!isCurrentProjection(iMediaProjection == null ? null : iMediaProjection.asBinder())) {
          Slog.v(
              "MediaProjectionManagerService",
              "Reusing token: Ignore consent result of " + i + " for a token that isn't current");
          return;
        }
      }
      MediaProjection mediaProjection = this.mProjectionGrant;
      if (mediaProjection == null) {
        Slog.w(
            "MediaProjectionManagerService",
            "Reusing token: Can't review consent with no ongoing projection.");
        return;
      }
      if (mediaProjection.mSession != null
          && this.mProjectionGrant.mSession.isWaitingForConsent()) {
        Slog.v("MediaProjectionManagerService", "Reusing token: Handling user consent result " + i);
        if (i == -1 || i == 0) {
          setReviewedConsentSessionLocked(null);
          MediaProjection mediaProjection2 = this.mProjectionGrant;
          if (mediaProjection2 != null) {
            mediaProjection2.stop();
          }
        } else if (i == 1) {
          setReviewedConsentSessionLocked(ContentRecordingSession.createDisplaySession(0));
        } else if (i == 2) {
          setReviewedConsentSessionLocked(
              ContentRecordingSession.createTaskSession(this.mProjectionGrant.getLaunchCookie()));
        }
        return;
      }
      Slog.w(
          "MediaProjectionManagerService",
          "Reusing token: Ignore consent result " + i + " if not waiting for the result.");
    }
  }

  public final void setReviewedConsentSessionLocked(
      ContentRecordingSession contentRecordingSession) {
    if (contentRecordingSession != null) {
      contentRecordingSession.setWaitingForConsent(false);
      contentRecordingSession.setVirtualDisplayId(this.mProjectionGrant.mVirtualDisplayId);
    }
    Slog.v(
        "MediaProjectionManagerService",
        "Reusing token: Processed consent so set the session " + contentRecordingSession);
    if (setContentRecordingSession(contentRecordingSession)) {
      return;
    }
    Slog.e(
        "MediaProjectionManagerService",
        "Reusing token: Failed to set session for reused consent, so stop");
  }

  public MediaProjection createProjectionInternal(
      int i, String str, int i2, boolean z, UserHandle userHandle) {
    PackageInfo packageInfo;
    try {
      ApplicationInfo applicationInfoAsUser =
          this.mPackageManager.getApplicationInfoAsUser(
              str, PackageManager.ApplicationInfoFlags.of(0L), userHandle);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        MediaProjection mediaProjection =
            new MediaProjection(
                i2,
                i,
                str,
                applicationInfoAsUser.targetSdkVersion,
                applicationInfoAsUser.isPrivilegedApp());
        if (z) {
          this.mAppOps.setMode(46, mediaProjection.uid, mediaProjection.packageName, 0);
        }
        try {
          packageInfo = this.mPackageManager.getPackageInfo(str, 64);
        } catch (PackageManager.NameNotFoundException e) {
          Slog.w("MediaProjectionManagerService", "Package not found: " + str, e);
          packageInfo = null;
        }
        if (packageInfo != null
            && (packageInfo.applicationInfo.flags & 1) == 0
            && "CHINA"
                .equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
          try {
            IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"))
                .writePermissionAccessInformation(
                    1000, i, str, null, this.mContext.getAttributionTag(), 600);
          } catch (Exception e2) {
            Slog.w("MediaProjectionManagerService", e2.getMessage(), e2);
          }
        }
        return mediaProjection;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } catch (PackageManager.NameNotFoundException unused) {
      throw new IllegalArgumentException("No package matching :" + str);
    }
  }

  public MediaProjection getProjectionInternal(int i, String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        MediaProjection mediaProjection = this.mProjectionGrant;
        PackageInfo packageInfo = null;
        if (mediaProjection != null
            && mediaProjection.mSession != null
            && this.mProjectionGrant.mSession.isWaitingForConsent()) {
          MediaProjection mediaProjection2 = this.mProjectionGrant;
          if (mediaProjection2.uid != i || !Objects.equals(mediaProjection2.packageName, str)) {
            Slog.e(
                "MediaProjectionManagerService",
                "Reusing token: Not possible to reuse the current projection instance due to"
                    + " package details mismatching");
            return null;
          }
          Slog.v(
              "MediaProjectionManagerService",
              "Reusing token: getProjection can reuse the current projection");
          try {
            packageInfo = this.mPackageManager.getPackageInfo(str, 64);
          } catch (PackageManager.NameNotFoundException e) {
            Slog.w("MediaProjectionManagerService", "Package not found: " + str, e);
          }
          if (packageInfo != null
              && (packageInfo.applicationInfo.flags & 1) == 0
              && "CHINA"
                  .equalsIgnoreCase(
                      SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            try {
              IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"))
                  .writePermissionAccessInformation(
                      1000, i, str, null, this.mContext.getAttributionTag(), 600);
            } catch (Exception e2) {
              Slog.w("MediaProjectionManagerService", e2.getMessage(), e2);
            }
          }
          return this.mProjectionGrant;
        }
        Slog.e(
            "MediaProjectionManagerService",
            "Reusing token: Not possible to reuse the current projection instance");
        return null;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public MediaProjectionInfo getActiveProjectionInfo() {
    synchronized (this.mLock) {
      MediaProjection mediaProjection = this.mProjectionGrant;
      if (mediaProjection == null) {
        return null;
      }
      return mediaProjection.getProjectionInfo();
    }
  }

  public final void dump(PrintWriter printWriter) {
    printWriter.println("MEDIA PROJECTION MANAGER (dumpsys media_projection)");
    synchronized (this.mLock) {
      printWriter.println("Media Projection: ");
      MediaProjection mediaProjection = this.mProjectionGrant;
      if (mediaProjection != null) {
        mediaProjection.dump(printWriter);
      } else {
        printWriter.println("null");
      }
    }
  }

  public final void showSmartViewExceptionToast() {
    new Handler(Looper.getMainLooper())
        .post(
            new Runnable() { // from class:
                             // com.android.server.media.projection.MediaProjectionManagerService.3
              @Override // java.lang.Runnable
              public void run() {
                SemWifiDisplay activeDisplay;
                DisplayManager displayManager =
                    (DisplayManager)
                        MediaProjectionManagerService.this.mContext.getSystemService("display");
                if (displayManager.semGetWifiDisplayStatus() == null
                    || displayManager.semGetWifiDisplayStatus().getActiveDisplayState() != 2
                    || (activeDisplay = displayManager.semGetWifiDisplayStatus().getActiveDisplay())
                        == null) {
                  return;
                }
                if (SystemProperties.get("ro.build.characteristics", "phone").contains("tablet")) {
                  Toast.makeText(
                          new ContextThemeWrapper(
                              MediaProjectionManagerService.this.mContext,
                              R.style.Theme.DeviceDefault.Light),
                          MediaProjectionManagerService.this.mContext.getString(
                              17043288, activeDisplay.getDeviceName()),
                          1)
                      .show();
                } else {
                  Toast.makeText(
                          new ContextThemeWrapper(
                              MediaProjectionManagerService.this.mContext,
                              R.style.Theme.DeviceDefault.Light),
                          MediaProjectionManagerService.this.mContext.getString(
                              17043287, activeDisplay.getDeviceName()),
                          1)
                      .show();
                }
              }
            });
  }

  public final void showDexExceptionToast(final SemWifiDisplay semWifiDisplay) {
    new Handler(Looper.getMainLooper())
        .post(
            new Runnable() { // from class:
                             // com.android.server.media.projection.MediaProjectionManagerService$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                MediaProjectionManagerService.this.lambda$showDexExceptionToast$0(semWifiDisplay);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$showDexExceptionToast$0(SemWifiDisplay semWifiDisplay) {
    Toast.makeText(
            new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light),
            this.mContext.getString(
                SystemProperties.get("ro.build.characteristics", "phone").contains("tablet")
                    ? 17042506
                    : 17042505,
                semWifiDisplay.getDeviceName()),
            1)
        .show();
  }

  public final SemWifiDisplay getDesktopModeWifiDisplay() {
    SemWifiDisplay activeDisplay;
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
    if (!(semDesktopModeManager != null
        && semDesktopModeManager.getDesktopModeState().enabled == 4)) {
      return null;
    }
    DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
    if (displayManager.semGetWifiDisplayStatus() == null
        || displayManager.semGetWifiDisplayStatus().getActiveDisplayState() != 2
        || (activeDisplay = displayManager.semGetWifiDisplayStatus().getActiveDisplay()) == null) {
      return null;
    }
    return activeDisplay;
  }

  public final class BinderService extends IMediaProjectionManager.Stub {
    public BinderService(Context context) {
      super(PermissionEnforcer.fromContext(context));
    }

    public boolean hasProjectionPermission(int i, String str) {
      boolean z;
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (!checkPermission(str, "android.permission.CAPTURE_VIDEO_OUTPUT")) {
          if (MediaProjectionManagerService.this.mAppOps.noteOpNoThrow(46, i, str) != 0) {
            z = false;
            return z | false;
          }
        }
        z = true;
        return z | false;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public IMediaProjection createProjection(int i, String str, int i2, boolean z) {
      if (MediaProjectionManagerService.this.mContext.checkCallingPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to grant projection permission");
      }
      if (str == null || str.isEmpty()) {
        throw new IllegalArgumentException("package name must not be empty");
      }
      return MediaProjectionManagerService.this.createProjectionInternal(
          i, str, i2, z, Binder.getCallingUserHandle());
    }

    public IMediaProjection getProjection(int i, String str) {
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

    public boolean isCurrentProjection(IMediaProjection iMediaProjection) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to check if the given projection is"
                + " current.");
      }
      return MediaProjectionManagerService.this.isCurrentProjection(
          iMediaProjection == null ? null : iMediaProjection.asBinder());
    }

    public MediaProjectionInfo getActiveProjectionInfo() {
      if (MediaProjectionManagerService.this.mContext.checkCallingPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to get active projection info");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return MediaProjectionManagerService.this.getActiveProjectionInfo();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void stopActiveProjection() {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to stop the active projection");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (MediaProjectionManagerService.this.mLock) {
          if (MediaProjectionManagerService.this.mProjectionGrant != null) {
            MediaProjectionManagerService.this.mProjectionGrant.stop();
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void notifyActiveProjectionCapturedContentResized(int i, int i2) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to notify on captured content resize");
      }
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (isCurrentProjection(MediaProjectionManagerService.this.mProjectionGrant)) {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            synchronized (MediaProjectionManagerService.this.mLock) {
              if (MediaProjectionManagerService.this.mProjectionGrant != null
                  && MediaProjectionManagerService.this.mCallbackDelegate != null) {
                MediaProjectionManagerService.this.mCallbackDelegate.dispatchResize(
                    MediaProjectionManagerService.this.mProjectionGrant, i, i2);
              }
            }
          } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
          }
        }
      }
    }

    public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to notify on captured content visibility"
                + " changed");
      }
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (isCurrentProjection(MediaProjectionManagerService.this.mProjectionGrant)) {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            synchronized (MediaProjectionManagerService.this.mLock) {
              if (MediaProjectionManagerService.this.mProjectionGrant != null
                  && MediaProjectionManagerService.this.mCallbackDelegate != null) {
                MediaProjectionManagerService.this.mCallbackDelegate.dispatchVisibilityChanged(
                    MediaProjectionManagerService.this.mProjectionGrant, z);
              }
            }
          } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
          }
        }
      }
    }

    public void addCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
      if (MediaProjectionManagerService.this.mContext.checkCallingPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to add projection callbacks");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        MediaProjectionManagerService.this.addCallback(iMediaProjectionWatcherCallback);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void removeCallback(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
      if (MediaProjectionManagerService.this.mContext.checkCallingPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION in order to remove projection callbacks");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        MediaProjectionManagerService.this.removeCallback(iMediaProjectionWatcherCallback);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean setContentRecordingSession(
        ContentRecordingSession contentRecordingSession, IMediaProjection iMediaProjection) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException("Requires MANAGE_MEDIA_PROJECTION to set session details.");
      }
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (!isCurrentProjection(iMediaProjection)) {
          throw new SecurityException(
              "Unable to set ContentRecordingSession on non-current MediaProjection");
        }
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return MediaProjectionManagerService.this.setContentRecordingSession(
            contentRecordingSession);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void requestConsentForInvalidProjection(IMediaProjection iMediaProjection) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION to check if the givenprojection is valid.");
      }
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (!isCurrentProjection(iMediaProjection)) {
          Slog.v(
              "MediaProjectionManagerService",
              "Reusing token: Won't request consent again for a token that isn't current");
          return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          MediaProjectionManagerService.this.requestConsentForInvalidProjection();
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public void setUserReviewGrantedConsentResult(int i, IMediaProjection iMediaProjection) {
      setUserReviewGrantedConsentResult_enforcePermission();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        MediaProjectionManagerService.this.setUserReviewGrantedConsentResult(i, iMediaProjection);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpPermission(
          MediaProjectionManagerService.this.mContext,
          "MediaProjectionManagerService",
          printWriter)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          MediaProjectionManagerService.this.dump(printWriter);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public final boolean checkPermission(String str, String str2) {
      return MediaProjectionManagerService.this
              .mContext
              .getPackageManager()
              .checkPermission(str2, str)
          == 0;
    }
  }

  final class MediaProjection extends IMediaProjection.Stub {
    public IMediaProjectionCallback mCallback;
    public int mCountStarts;
    public final long mCreateTimeMs;
    public IBinder.DeathRecipient mDeathEater;
    public final long mDefaultTimeoutMs;
    public final boolean mIsPrivileged;
    public IBinder mLaunchCookie;
    public boolean mRestoreSystemAlertWindow;
    public ContentRecordingSession mSession;
    public final int mTargetSdkVersion;
    public long mTimeoutMs;
    public IBinder mToken;
    public final int mType;
    public int mVirtualDisplayId;
    public final String packageName;
    public final int uid;
    public final UserHandle userHandle;

    public boolean canProjectSecureVideo() {
      return false;
    }

    public MediaProjection(int i, int i2, String str, int i3, boolean z) {
      long millis = Duration.ofMinutes(5L).toMillis();
      this.mDefaultTimeoutMs = millis;
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
      this.mCreateTimeMs = MediaProjectionManagerService.this.mClock.uptimeMillis();
      MediaProjectionManagerService.this.mActivityManagerInternal.notifyMediaProjectionEvent(
          i2, asBinder(), 0);
    }

    public boolean canProjectVideo() {
      int i = this.mType;
      return i == 1 || i == 0;
    }

    public boolean canProjectAudio() {
      int i = this.mType;
      return i == 1 || i == 2 || i == 0;
    }

    public int applyVirtualDisplayFlags(int i) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION to apply virtual display flags.");
      }
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

    public void start(final IMediaProjectionCallback iMediaProjectionCallback) {
      if (iMediaProjectionCallback == null) {
        throw new IllegalArgumentException("callback must not be null");
      }
      boolean hasRunningForegroundService =
          MediaProjectionManagerService.this.mActivityManagerInternal.hasRunningForegroundService(
              this.uid, 32);
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (MediaProjectionManagerService.this.isCurrentProjection(asBinder())) {
          Slog.w(
              "MediaProjectionManagerService",
              "UID "
                  + Binder.getCallingUid()
                  + " attempted to start already started MediaProjection");
          this.mCountStarts = this.mCountStarts + 1;
          return;
        }
        if (requiresForegroundService() && !hasRunningForegroundService) {
          throw new SecurityException(
              "Media projections require a foreground service of type"
                  + " ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION");
        }
        this.mCallback = iMediaProjectionCallback;
        registerCallback(iMediaProjectionCallback);
        try {
          this.mToken = iMediaProjectionCallback.asBinder();
          IBinder.DeathRecipient deathRecipient =
              new IBinder
                  .DeathRecipient() { // from class:
                                      // com.android.server.media.projection.MediaProjectionManagerService.MediaProjection.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                  MediaProjectionManagerService.this.mCallbackDelegate.remove(
                      iMediaProjectionCallback);
                  MediaProjection.this.stop();
                }
              };
          this.mDeathEater = deathRecipient;
          this.mToken.linkToDeath(deathRecipient, 0);
          if (this.mType == 0) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
              try {
                if (ArrayUtils.contains(
                        MediaProjectionManagerService.this.mPackageManager.getPackageInfoAsUser(
                                this.packageName,
                                IInstalld.FLAG_USE_QUOTA,
                                UserHandle.getUserId(this.uid))
                            .requestedPermissions,
                        "android.permission.SYSTEM_ALERT_WINDOW")
                    && MediaProjectionManagerService.this.mAppOps.unsafeCheckOpRawNoThrow(
                            24, this.uid, this.packageName)
                        == 3) {
                  MediaProjectionManagerService.this.mAppOps.setMode(
                      24, this.uid, this.packageName, 0);
                  this.mRestoreSystemAlertWindow = true;
                }
              } catch (PackageManager.NameNotFoundException e) {
                Slog.w(
                    "MediaProjectionManagerService",
                    "Package not found, aborting MediaProjection",
                    e);
                return;
              }
            } finally {
              Binder.restoreCallingIdentity(clearCallingIdentity);
            }
          }
          MediaProjectionManagerService.this.startProjectionLocked(this);
          this.mCountStarts++;
        } catch (RemoteException e2) {
          Slog.w(
              "MediaProjectionManagerService",
              "MediaProjectionCallbacks must be valid, aborting MediaProjection",
              e2);
        }
      }
    }

    public void stop() {
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (!MediaProjectionManagerService.this.isCurrentProjection(asBinder())) {
          Slog.w(
              "MediaProjectionManagerService",
              "Attempted to stop inactive MediaProjection (uid="
                  + Binder.getCallingUid()
                  + ", pid="
                  + Binder.getCallingPid()
                  + ")");
          return;
        }
        if (this.mRestoreSystemAlertWindow) {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            if (MediaProjectionManagerService.this.mAppOps.unsafeCheckOpRawNoThrow(
                    24, this.uid, this.packageName)
                == 0) {
              MediaProjectionManagerService.this.mAppOps.setMode(24, this.uid, this.packageName, 3);
            }
            this.mRestoreSystemAlertWindow = false;
            Binder.restoreCallingIdentity(clearCallingIdentity);
          } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
          }
        }
        MediaProjectionManagerService.this.stopProjectionLocked(this);
        this.mToken.unlinkToDeath(this.mDeathEater, 0);
        this.mToken = null;
        unregisterCallback(this.mCallback);
        this.mCallback = null;
        MediaProjectionManagerService.this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.media.projection.MediaProjectionManagerService$MediaProjection$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                MediaProjectionManagerService.MediaProjection.this.lambda$stop$0();
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stop$0() {
      MediaProjectionManagerService.this.mActivityManagerInternal.notifyMediaProjectionEvent(
          this.uid, asBinder(), 1);
    }

    public void registerCallback(IMediaProjectionCallback iMediaProjectionCallback) {
      if (iMediaProjectionCallback == null) {
        throw new IllegalArgumentException("callback must not be null");
      }
      MediaProjectionManagerService.this.mCallbackDelegate.add(iMediaProjectionCallback);
    }

    public void unregisterCallback(IMediaProjectionCallback iMediaProjectionCallback) {
      if (iMediaProjectionCallback == null) {
        throw new IllegalArgumentException("callback must not be null");
      }
      MediaProjectionManagerService.this.mCallbackDelegate.remove(iMediaProjectionCallback);
    }

    public void setLaunchCookie(IBinder iBinder) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException("Requires MANAGE_MEDIA_PROJECTION to set launch cookie.");
      }
      this.mLaunchCookie = iBinder;
    }

    public IBinder getLaunchCookie() {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException("Requires MANAGE_MEDIA_PROJECTION to get launch cookie.");
      }
      return this.mLaunchCookie;
    }

    public boolean isValid() {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION to check if thisprojection is valid.");
      }
      synchronized (MediaProjectionManagerService.this.mLock) {
        if (((((MediaProjectionManagerService.this.mClock.uptimeMillis() - this.mCreateTimeMs)
                            > this.mTimeoutMs
                        ? 1
                        : ((MediaProjectionManagerService.this.mClock.uptimeMillis()
                                    - this.mCreateTimeMs)
                                == this.mTimeoutMs
                            ? 0
                            : -1))
                    > 0)
                || this.mCountStarts > 1
                || (this.mVirtualDisplayId != -1))
            ? false
            : true) {
          return true;
        }
        if (!MediaProjectionManagerService.this.mInjector
            .shouldMediaProjectionPreventReusingConsent(
                MediaProjectionManagerService.this.mProjectionGrant)) {
          return false;
        }
        Slog.v(
            "MediaProjectionManagerService",
            "Reusing token: Throw exception due to invalid projection.");
        MediaProjectionManagerService.this.mProjectionGrant.stop();
        throw new SecurityException(
            "Don't re-use the resultData to retrieve the same projection instance, and don't use a"
                + " token that has timed out. Don't take multiple captures by invoking"
                + " MediaProjection#createVirtualDisplay multiple times on the same instance.");
      }
    }

    public void notifyVirtualDisplayCreated(int i) {
      if (MediaProjectionManagerService.this.mContext.checkCallingOrSelfPermission(
              "android.permission.MANAGE_MEDIA_PROJECTION")
          != 0) {
        throw new SecurityException(
            "Requires MANAGE_MEDIA_PROJECTION to notify virtual display created.");
      }
      synchronized (MediaProjectionManagerService.this.mLock) {
        this.mVirtualDisplayId = i;
        ContentRecordingSession contentRecordingSession = this.mSession;
        if (contentRecordingSession != null
            && contentRecordingSession.getVirtualDisplayId() == -1) {
          Slog.v(
              "MediaProjectionManagerService",
              "Virtual display now created, so update session with the virtual display id");
          this.mSession.setVirtualDisplayId(this.mVirtualDisplayId);
          if (!MediaProjectionManagerService.this.setContentRecordingSession(this.mSession)) {
            Slog.e("MediaProjectionManagerService", "Failed to set session for virtual display id");
          }
        }
      }
    }

    public MediaProjectionInfo getProjectionInfo() {
      return new MediaProjectionInfo(this.packageName, this.userHandle);
    }

    public boolean requiresForegroundService() {
      return this.mTargetSdkVersion >= 29 && !this.mIsPrivileged;
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println(
          "("
              + this.packageName
              + ", uid="
              + this.uid
              + "): "
              + MediaProjectionManagerService.typeToString(this.mType));
    }
  }

  public class MediaRouterCallback extends MediaRouter.SimpleCallback {
    public MediaRouterCallback() {}

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
      synchronized (MediaProjectionManagerService.this.mLock) {
        if ((i & 4) != 0) {
          MediaProjectionManagerService.this.mMediaRouteInfo = routeInfo;
          if (MediaProjectionManagerService.this.mProjectionGrant != null) {
            MediaProjectionManagerService.this.mProjectionGrant.stop();
          }
        }
      }
    }

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
      if (MediaProjectionManagerService.this.mMediaRouteInfo == routeInfo) {
        MediaProjectionManagerService.this.mMediaRouteInfo = null;
      }
    }
  }

  public class CallbackDelegate {
    public final Object mLock = new Object();
    public Handler mHandler = new Handler(Looper.getMainLooper(), null, true);
    public Map mClientCallbacks = new ArrayMap();
    public Map mWatcherCallbacks = new ArrayMap();

    public void add(IMediaProjectionCallback iMediaProjectionCallback) {
      synchronized (this.mLock) {
        this.mClientCallbacks.put(iMediaProjectionCallback.asBinder(), iMediaProjectionCallback);
      }
    }

    public void add(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
      synchronized (this.mLock) {
        this.mWatcherCallbacks.put(
            iMediaProjectionWatcherCallback.asBinder(), iMediaProjectionWatcherCallback);
      }
    }

    public void remove(IMediaProjectionCallback iMediaProjectionCallback) {
      synchronized (this.mLock) {
        this.mClientCallbacks.remove(iMediaProjectionCallback.asBinder());
      }
    }

    public void remove(IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
      synchronized (this.mLock) {
        this.mWatcherCallbacks.remove(iMediaProjectionWatcherCallback.asBinder());
      }
    }

    public void dispatchStart(MediaProjection mediaProjection) {
      if (mediaProjection == null) {
        Slog.e(
            "MediaProjectionManagerService",
            "Tried to dispatch start notification for a null media projection. Ignoring!");
        return;
      }
      synchronized (this.mLock) {
        for (IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback :
            this.mWatcherCallbacks.values()) {
          this.mHandler.post(
              new WatcherStartCallback(
                  mediaProjection.getProjectionInfo(), iMediaProjectionWatcherCallback));
        }
      }
    }

    public void dispatchStop(MediaProjection mediaProjection) {
      if (mediaProjection == null) {
        Slog.e(
            "MediaProjectionManagerService",
            "Tried to dispatch stop notification for a null media projection. Ignoring!");
        return;
      }
      synchronized (this.mLock) {
        Iterator it = this.mClientCallbacks.values().iterator();
        while (it.hasNext()) {
          this.mHandler.post(new ClientStopCallback((IMediaProjectionCallback) it.next()));
        }
        for (IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback :
            this.mWatcherCallbacks.values()) {
          this.mHandler.post(
              new WatcherStopCallback(
                  mediaProjection.getProjectionInfo(), iMediaProjectionWatcherCallback));
        }
      }
    }

    public void dispatchResize(MediaProjection mediaProjection, final int i, final int i2) {
      if (mediaProjection == null) {
        Slog.e(
            "MediaProjectionManagerService",
            "Tried to dispatch resize notification for a null media projection. Ignoring!");
        return;
      }
      synchronized (this.mLock) {
        for (final IMediaProjectionCallback iMediaProjectionCallback :
            this.mClientCallbacks.values()) {
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  MediaProjectionManagerService.CallbackDelegate.lambda$dispatchResize$0(
                      iMediaProjectionCallback, i, i2);
                }
              });
        }
      }
    }

    public static /* synthetic */ void lambda$dispatchResize$0(
        IMediaProjectionCallback iMediaProjectionCallback, int i, int i2) {
      try {
        iMediaProjectionCallback.onCapturedContentResize(i, i2);
      } catch (RemoteException e) {
        Slog.w(
            "MediaProjectionManagerService",
            "Failed to notify media projection has resized to " + i + " x " + i2,
            e);
      }
    }

    public void dispatchVisibilityChanged(MediaProjection mediaProjection, final boolean z) {
      if (mediaProjection == null) {
        Slog.e(
            "MediaProjectionManagerService",
            "Tried to dispatch visibility changed notification for a null media projection."
                + " Ignoring!");
        return;
      }
      synchronized (this.mLock) {
        for (final IMediaProjectionCallback iMediaProjectionCallback :
            this.mClientCallbacks.values()) {
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.media.projection.MediaProjectionManagerService$CallbackDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  MediaProjectionManagerService.CallbackDelegate.lambda$dispatchVisibilityChanged$1(
                      iMediaProjectionCallback, z);
                }
              });
        }
      }
    }

    public static /* synthetic */ void lambda$dispatchVisibilityChanged$1(
        IMediaProjectionCallback iMediaProjectionCallback, boolean z) {
      try {
        iMediaProjectionCallback.onCapturedContentVisibilityChanged(z);
      } catch (RemoteException e) {
        Slog.w(
            "MediaProjectionManagerService",
            "Failed to notify media projection has captured content visibility change to " + z,
            e);
      }
    }
  }

  public final class WatcherStartCallback implements Runnable {
    public IMediaProjectionWatcherCallback mCallback;
    public MediaProjectionInfo mInfo;

    public WatcherStartCallback(
        MediaProjectionInfo mediaProjectionInfo,
        IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
      this.mInfo = mediaProjectionInfo;
      this.mCallback = iMediaProjectionWatcherCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
      try {
        this.mCallback.onStart(this.mInfo);
      } catch (RemoteException e) {
        Slog.w("MediaProjectionManagerService", "Failed to notify media projection has stopped", e);
      }
    }
  }

  public final class WatcherStopCallback implements Runnable {
    public IMediaProjectionWatcherCallback mCallback;
    public MediaProjectionInfo mInfo;

    public WatcherStopCallback(
        MediaProjectionInfo mediaProjectionInfo,
        IMediaProjectionWatcherCallback iMediaProjectionWatcherCallback) {
      this.mInfo = mediaProjectionInfo;
      this.mCallback = iMediaProjectionWatcherCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
      try {
        this.mCallback.onStop(this.mInfo);
      } catch (RemoteException e) {
        Slog.w("MediaProjectionManagerService", "Failed to notify media projection has stopped", e);
      }
    }
  }

  public final class ClientStopCallback implements Runnable {
    public IMediaProjectionCallback mCallback;

    public ClientStopCallback(IMediaProjectionCallback iMediaProjectionCallback) {
      this.mCallback = iMediaProjectionCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
      try {
        this.mCallback.onStop();
      } catch (RemoteException e) {
        Slog.w("MediaProjectionManagerService", "Failed to notify media projection has stopped", e);
      }
    }
  }

  public static String typeToString(int i) {
    return i != 0
        ? i != 1 ? i != 2 ? Integer.toString(i) : "TYPE_PRESENTATION" : "TYPE_MIRRORING"
        : "TYPE_SCREEN_CAPTURE";
  }
}
