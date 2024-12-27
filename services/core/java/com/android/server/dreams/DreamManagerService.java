package com.android.server.dreams;

import android.R;
import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.hardware.display.AmbientDisplayConfiguration;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.BatteryManagerInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.DreamManagerInternal;
import android.service.dreams.DreamService;
import android.service.dreams.IDreamManager;
import android.service.dreams.IDreamService;
import android.util.Log;
import android.util.Slog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.dreams.DreamController.DreamRecord;
import com.android.server.input.InputManagerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.ActivityInterceptorCallback;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.AppTaskImpl;

import libcore.io.IoUtils;

import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public final class DreamManagerService extends SystemService {
    public final AnonymousClass1 mActivityInterceptorCallback;
    public final ComponentName mAmbientDisplayComponent;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final BatteryManagerInternal mBatteryManagerInternal;
    public final AnonymousClass2 mChargingReceiver;
    public final Context mContext;
    public final DreamController mController;
    public DreamRecord mCurrentDream;
    public final boolean mDismissDreamOnActivityStart;
    public final AnonymousClass2 mDockStateReceiver;
    public final AmbientDisplayConfiguration mDozeConfig;
    public final AnonymousClass5 mDozeEnabledObserver;
    public final PowerManager.WakeLock mDozeWakeLock;
    public final CopyOnWriteArrayList mDreamManagerStateListeners;
    public ComponentName mDreamOverlayServiceName;
    public final DreamUiEventLoggerImpl mDreamUiEventLogger;
    public final boolean mDreamsActivatedOnChargeByDefault;
    public final boolean mDreamsActivatedOnDockByDefault;
    public final boolean mDreamsDisabledByAmbientModeSuppressionConfig;
    public final boolean mDreamsEnabledByDefaultConfig;
    public boolean mDreamsEnabledSetting;
    public final boolean mDreamsOnlyEnabledForDockUser;
    public boolean mForceAmbientDisplayEnabled;
    public final Handler mHandler;
    public boolean mIsCharging;
    public boolean mIsDocked;
    public final boolean mKeepDreamingWhenUnpluggingDefault;
    public final Object mLock;
    public final PackageManagerInternal mPmInternal;
    public final PowerManager mPowerManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public AnonymousClass5 mSettingsObserver;
    public ComponentName mSystemDreamComponent;
    public final AnonymousClass6 mSystemPropertiesChanged;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public int mWhenToDream;

    /* renamed from: com.android.server.dreams.DreamManagerService$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public AnonymousClass4() {}

        public final void onDreamStopped(Binder binder) {
            synchronized (DreamManagerService.this.mLock) {
                try {
                    DreamManagerService dreamManagerService = DreamManagerService.this;
                    DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                    if (dreamRecord != null && dreamRecord.token == binder) {
                        dreamManagerService.cleanupDreamLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            DreamManagerService dreamManagerService2 = DreamManagerService.this;
            dreamManagerService2.getClass();
            dreamManagerService2.mHandler.post(
                    new DreamManagerService$$ExternalSyntheticLambda7(
                            dreamManagerService2,
                            new DreamManagerService$$ExternalSyntheticLambda0(1)));
        }
    }

    /* renamed from: com.android.server.dreams.DreamManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 extends ContentObserver {
        public final /* synthetic */ int $r8$classId = 0;

        public AnonymousClass5() {
            super(null);
        }

        public AnonymousClass5(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    DreamManagerService.this.writePulseGestureEnabled();
                    break;
                default:
                    super.onChange(z);
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            switch (this.$r8$classId) {
                case 1:
                    DreamManagerService.this.updateWhenToDreamSettings();
                    break;
                default:
                    super.onChange(z, uri);
                    break;
            }
        }
    }

    public final class BinderService extends IDreamManager.Stub {
        public BinderService() {}

        public final void awaken() {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService dreamManagerService = DreamManagerService.this;
                dreamManagerService.getClass();
                dreamManagerService.mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
                dreamManagerService.stopDreamInternal("request awaken", false);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean canStartDreaming(boolean z) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.READ_DREAM_STATE");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DreamManagerService.m486$$Nest$mcanStartDreamingInternal(
                        DreamManagerService.this, z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dream() {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService.this.requestDreamInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    DreamManagerService.this.mContext, "DreamManagerService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DreamManagerService.m488$$Nest$mdumpInternal(
                            DreamManagerService.this, printWriter);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void finishSelf(IBinder iBinder, boolean z) {
            if (iBinder == null) {
                throw new IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService dreamManagerService = DreamManagerService.this;
                synchronized (dreamManagerService.mLock) {
                    try {
                        DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                        if (dreamRecord != null && dreamRecord.token == iBinder) {
                            dreamManagerService.stopDreamLocked("finished self", z);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void forceAmbientDisplayEnabled(boolean z) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.DEVICE_POWER");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService dreamManagerService = DreamManagerService.this;
                synchronized (dreamManagerService.mLock) {
                    dreamManagerService.mForceAmbientDisplayEnabled = z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ComponentName getDefaultDreamComponentForUser(int i) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.READ_DREAM_STATE");
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            true,
                            "getDefaultDreamComponent",
                            null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                String stringForUser =
                        Settings.Secure.getStringForUser(
                                DreamManagerService.this.mContext.getContentResolver(),
                                "screensaver_default_component",
                                handleIncomingUser);
                return stringForUser == null
                        ? null
                        : ComponentName.unflattenFromString(stringForUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ComponentName[] getDreamComponents() {
            return getDreamComponentsForUser(UserHandle.getCallingUserId());
        }

        public final ComponentName[] getDreamComponentsForUser(int i) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.READ_DREAM_STATE");
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            true,
                            "getDreamComponents",
                            null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DreamManagerService.this.getDreamComponentsForUser(handleIncomingUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDreaming() {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.READ_DREAM_STATE");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DreamManagerService.this.isDreamingInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDreamingOrInPreview() {
            boolean z;
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.READ_DREAM_STATE");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService dreamManagerService = DreamManagerService.this;
                synchronized (dreamManagerService.mLock) {
                    try {
                        DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                        z = (dreamRecord == null || dreamRecord.isWaking) ? false : true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new DreamShellCommand(DreamManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void registerDreamOverlayService(ComponentName componentName) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            DreamManagerService.this.mDreamOverlayServiceName = componentName;
        }

        public final void semStartDozing(
                IBinder iBinder, int i, int i2, int i3, int i4, boolean z) {
            if (iBinder == null) {
                throw new IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService.m489$$Nest$msemStartDozingInternal(
                        DreamManagerService.this, iBinder, i, i2, i3, i4, z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDreamComponents(ComponentName[] componentNameArr) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                setDreamComponentsForUser(callingUserId, componentNameArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDreamComponentsForUser(int i, ComponentName[] componentNameArr) {
            String sb;
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            true,
                            "setDreamComponents",
                            null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ContentResolver contentResolver =
                        DreamManagerService.this.mContext.getContentResolver();
                if (componentNameArr == null) {
                    sb = null;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    for (ComponentName componentName : componentNameArr) {
                        if (sb2.length() > 0) {
                            sb2.append(',');
                        }
                        sb2.append(componentName.flattenToString());
                    }
                    sb = sb2.toString();
                }
                Settings.Secure.putStringForUser(
                        contentResolver, "screensaver_components", sb, handleIncomingUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDreamIsObscured(final boolean z) {
            if (android.service.dreams.Flags.dreamHandlesBeingObscured()) {
                DreamManagerService.m487$$Nest$mcheckPermission(
                        DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DreamManagerService.this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.dreams.DreamManagerService$BinderService$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DreamManagerService.BinderService binderService =
                                            DreamManagerService.BinderService.this;
                                    boolean z2 = z;
                                    DreamController.DreamRecord dreamRecord =
                                            DreamManagerService.this.mController.mCurrentDream;
                                    if (dreamRecord != null) {
                                        dreamRecord.mDreamIsObscured = z2;
                                    }
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void setSystemDreamComponent(ComponentName componentName) {
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService.m490$$Nest$msetSystemDreamComponentInternal(
                        DreamManagerService.this, componentName);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startDozing(IBinder iBinder, int i, int i2, int i3) {
            if (iBinder == null) {
                throw new IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService.m491$$Nest$mstartDozingInternal(
                        DreamManagerService.this, iBinder, i, i2, i3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startDreamActivity(final Intent intent) {
            final int callingUid = Binder.getCallingUid();
            final int callingPid = Binder.getCallingPid();
            DreamManagerService.this.mHandler.post(
                    new Runnable() { // from class:
                        // com.android.server.dreams.DreamManagerService$BinderService$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamManagerService.BinderService binderService =
                                    DreamManagerService.BinderService.this;
                            Intent intent2 = intent;
                            int i = callingUid;
                            int i2 = callingPid;
                            synchronized (DreamManagerService.this.mLock) {
                                try {
                                    DreamManagerService.DreamRecord dreamRecord =
                                            DreamManagerService.this.mCurrentDream;
                                    if (dreamRecord == null) {
                                        Slog.e(
                                                "DreamManagerService",
                                                "Attempt to start DreamActivity, but the device is"
                                                    + " not dreaming. Aborting without starting the"
                                                    + " DreamActivity.");
                                        return;
                                    }
                                    Binder binder = dreamRecord.token;
                                    String packageName = dreamRecord.name.getPackageName();
                                    String str = intent2.getPackage();
                                    if (packageName == null || str == null) {
                                        Slog.e(
                                                "DreamManagerService",
                                                "Cannot launch dream activity due to invalid state."
                                                    + " dream component= "
                                                        + packageName
                                                        + ", packageName="
                                                        + str);
                                    } else {
                                        if (!((PackageManagerService.PackageManagerInternalImpl)
                                                        DreamManagerService.this.mPmInternal)
                                                .isSameApp(i, UserHandle.getUserId(i), 0L, str)) {
                                            Slog.e(
                                                    "DreamManagerService",
                                                    "Cannot launch dream activity because package="
                                                            + str
                                                            + " does not match callingUid="
                                                            + i);
                                        } else {
                                            if (str.equals(packageName)) {
                                                AppTaskImpl startDreamActivity =
                                                        DreamManagerService.this.mAtmInternal
                                                                .startDreamActivity(i, i2, intent2);
                                                if (startDreamActivity == null) {
                                                    Slog.e(
                                                            "DreamManagerService",
                                                            "Could not start dream activity.");
                                                    DreamManagerService.this.stopDreamInternal(
                                                            "DreamActivity not started", true);
                                                    return;
                                                }
                                                DreamController dreamController =
                                                        DreamManagerService.this.mController;
                                                DreamController.DreamRecord dreamRecord2 =
                                                        dreamController.mCurrentDream;
                                                if (dreamRecord2 != null
                                                        && dreamRecord2.mToken == binder
                                                        && dreamRecord2.mAppTask == null) {
                                                    dreamRecord2.mAppTask = startDreamActivity;
                                                    return;
                                                }
                                                Slog.e(
                                                        "DreamController",
                                                        "Illegal dream activity start."
                                                            + " mCurrentDream.mToken = "
                                                                + dreamController
                                                                        .mCurrentDream
                                                                        .mToken
                                                                + ", illegal dreamToken = "
                                                                + binder
                                                                + ". Ending this dream activity.");
                                                try {
                                                    startDreamActivity.finishAndRemoveTask();
                                                    return;
                                                } catch (RemoteException
                                                        | RuntimeException unused) {
                                                    Slog.e(
                                                            "DreamController",
                                                            "Unable to stop illegal dream"
                                                                + " activity.");
                                                    return;
                                                }
                                            }
                                            Slog.e(
                                                    "DreamManagerService",
                                                    "Dream packageName does not match active dream."
                                                        + " Package "
                                                            + str
                                                            + " does not match "
                                                            + packageName);
                                        }
                                    }
                                    Slog.e(
                                            "DreamManagerService",
                                            "The dream activity can be started only when the device"
                                                + " is dreaming and only by the active dream"
                                                + " package.");
                                } finally {
                                }
                            }
                        }
                    });
        }

        public final void stopDozing(IBinder iBinder) {
            if (iBinder == null) {
                throw new IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService.this.stopDozingInternal(iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void testDream(int i, ComponentName componentName) {
            if (componentName == null) {
                throw new IllegalArgumentException("dream must not be null");
            }
            DreamManagerService.m487$$Nest$mcheckPermission(
                    DreamManagerService.this, "android.permission.WRITE_DREAM_STATE");
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            true,
                            "testDream",
                            null);
            int currentUser = ActivityManager.getCurrentUser();
            if (handleIncomingUser != currentUser) {
                PendingIntentController$$ExternalSyntheticOutline0.m(
                        handleIncomingUser,
                        currentUser,
                        "Aborted attempt to start a test dream while a different  user is active:"
                            + " userId=",
                        ", currentUserId=",
                        "DreamManagerService");
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DreamManagerService dreamManagerService = DreamManagerService.this;
                synchronized (dreamManagerService.mLock) {
                    dreamManagerService.startDreamLocked(
                            componentName, true, false, handleIncomingUser, "test dream");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final class DreamHandler extends Handler {}

    public final class DreamRecord {
        public final boolean canDoze;
        public final boolean isPreview;
        public final ComponentName name;
        public final int userId;
        public final Binder token = new Binder();
        public boolean isDozing = false;
        public boolean isWaking = false;
        public int dozeScreenState = 0;
        public int dozeScreenBrightness = -1;

        public DreamRecord(ComponentName componentName, int i, boolean z, boolean z2) {
            this.name = componentName;
            this.userId = i;
            this.isPreview = z;
            this.canDoze = z2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DreamRecord{token=");
            sb.append(this.token);
            sb.append(", name=");
            sb.append(this.name);
            sb.append(", userId=");
            sb.append(this.userId);
            sb.append(", isPreview=");
            sb.append(this.isPreview);
            sb.append(", canDoze=");
            sb.append(this.canDoze);
            sb.append(", isDozing=");
            sb.append(this.isDozing);
            sb.append(", isWaking=");
            sb.append(this.isWaking);
            sb.append(", dozeScreenState=");
            sb.append(this.dozeScreenState);
            sb.append(", dozeScreenBrightness=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.dozeScreenBrightness, '}');
        }
    }

    public final class LocalService extends DreamManagerInternal {
        public LocalService() {}

        public final boolean canStartDreaming(boolean z) {
            return DreamManagerService.m486$$Nest$mcanStartDreamingInternal(
                    DreamManagerService.this, z);
        }

        public final boolean isDreaming() {
            return DreamManagerService.this.isDreamingInternal();
        }

        public final void registerDreamManagerStateListener(
                DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener) {
            DreamManagerService.this.mDreamManagerStateListeners.add(dreamManagerStateListener);
            DreamManagerService dreamManagerService = DreamManagerService.this;
            dreamManagerStateListener.onKeepDreamingWhenUnpluggingChanged(
                    dreamManagerService.mKeepDreamingWhenUnpluggingDefault
                            && dreamManagerService.mSystemDreamComponent == null);
        }

        public final void requestDream() {
            DreamManagerService.this.requestDreamInternal();
        }

        public final void startDream(boolean z, String str) {
            DreamManagerService.this.startDreamInternal(str, z);
        }

        public final void stopDream(boolean z, String str) {
            DreamManagerService.this.stopDreamInternal(str, z);
        }

        public final void unregisterDreamManagerStateListener(
                DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener) {
            DreamManagerService.this.mDreamManagerStateListeners.remove(dreamManagerStateListener);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0040, code lost:

       if (r6 == ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getMainUserId()) goto L29;
    */
    /* renamed from: -$$Nest$mcanStartDreamingInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m486$$Nest$mcanStartDreamingInternal(
            com.android.server.dreams.DreamManagerService r5, boolean r6) {
        /*
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            r1 = 1
            r2 = 0
            if (r6 == 0) goto L22
            boolean r6 = r5.isDreamingInternal()     // Catch: java.lang.Throwable -> L20
            if (r6 == 0) goto L22
            boolean r6 = android.service.dreams.Flags.dreamHandlesBeingObscured()     // Catch: java.lang.Throwable -> L20
            if (r6 == 0) goto L1e
            com.android.server.dreams.DreamController r6 = r5.mController     // Catch: java.lang.Throwable -> L20
            com.android.server.dreams.DreamController$DreamRecord r6 = r6.mCurrentDream     // Catch: java.lang.Throwable -> L20
            if (r6 == 0) goto L22
            boolean r6 = r6.mDreamIsObscured     // Catch: java.lang.Throwable -> L20
            r6 = r6 ^ r1
            if (r6 == 0) goto L22
        L1e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L20:
            r5 = move-exception
            goto L77
        L22:
            boolean r6 = r5.mDreamsEnabledSetting     // Catch: java.lang.Throwable -> L20
            if (r6 != 0) goto L28
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L28:
            int r6 = android.app.ActivityManager.getCurrentUser()     // Catch: java.lang.Throwable -> L20
            boolean r3 = r5.mDreamsOnlyEnabledForDockUser     // Catch: java.lang.Throwable -> L20
            if (r3 != 0) goto L31
            goto L42
        L31:
            if (r6 >= 0) goto L34
            goto L75
        L34:
            java.lang.Class<com.android.server.pm.UserManagerInternal> r3 = com.android.server.pm.UserManagerInternal.class
            java.lang.Object r3 = com.android.server.LocalServices.getService(r3)     // Catch: java.lang.Throwable -> L20
            com.android.server.pm.UserManagerInternal r3 = (com.android.server.pm.UserManagerInternal) r3     // Catch: java.lang.Throwable -> L20
            int r3 = r3.getMainUserId()     // Catch: java.lang.Throwable -> L20
            if (r6 != r3) goto L75
        L42:
            android.os.UserManager r6 = r5.mUserManager     // Catch: java.lang.Throwable -> L20
            boolean r6 = r6.isUserUnlocked()     // Catch: java.lang.Throwable -> L20
            if (r6 != 0) goto L4c
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L4c:
            boolean r6 = r5.mDreamsDisabledByAmbientModeSuppressionConfig     // Catch: java.lang.Throwable -> L20
            if (r6 == 0) goto L61
            android.os.PowerManagerInternal r6 = r5.mPowerManagerInternal     // Catch: java.lang.Throwable -> L20
            boolean r6 = r6.isAmbientDisplaySuppressed()     // Catch: java.lang.Throwable -> L20
            if (r6 == 0) goto L61
            java.lang.String r5 = "DreamManagerService"
            java.lang.String r6 = "Can't start dreaming because ambient is suppressed."
            android.util.Slog.i(r5, r6)     // Catch: java.lang.Throwable -> L20
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L61:
            int r6 = r5.mWhenToDream     // Catch: java.lang.Throwable -> L20
            r3 = r6 & 2
            r4 = 2
            if (r3 != r4) goto L6c
            boolean r2 = r5.mIsCharging     // Catch: java.lang.Throwable -> L20
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L6c:
            r6 = r6 & r1
            if (r6 != r1) goto L73
            boolean r2 = r5.mIsDocked     // Catch: java.lang.Throwable -> L20
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L73:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            goto L76
        L75:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
        L76:
            return r2
        L77:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L20
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.dreams.DreamManagerService.m486$$Nest$mcanStartDreamingInternal(com.android.server.dreams.DreamManagerService,"
                    + " boolean):boolean");
    }

    /* renamed from: -$$Nest$mcheckPermission, reason: not valid java name */
    public static void m487$$Nest$mcheckPermission(
            DreamManagerService dreamManagerService, String str) {
        if (dreamManagerService.mContext.checkCallingOrSelfPermission(str) == 0) {
            return;
        }
        throw new SecurityException(
                "Access denied to process: "
                        + Binder.getCallingPid()
                        + ", must have permission "
                        + str);
    }

    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    public static void m488$$Nest$mdumpInternal(
            final DreamManagerService dreamManagerService, PrintWriter printWriter) {
        synchronized (dreamManagerService.mLock) {
            printWriter.println("DREAM MANAGER (dumpsys dreams)");
            printWriter.println();
            printWriter.println("mCurrentDream=" + dreamManagerService.mCurrentDream);
            printWriter.println(
                    "mForceAmbientDisplayEnabled="
                            + dreamManagerService.mForceAmbientDisplayEnabled);
            printWriter.println(
                    "mDreamsOnlyEnabledForDockUser="
                            + dreamManagerService.mDreamsOnlyEnabledForDockUser);
            printWriter.println(
                    "mDreamsEnabledSetting=" + dreamManagerService.mDreamsEnabledSetting);
            printWriter.println(
                    "mDreamsActivatedOnDockByDefault="
                            + dreamManagerService.mDreamsActivatedOnDockByDefault);
            printWriter.println(
                    "mDreamsActivatedOnChargeByDefault="
                            + dreamManagerService.mDreamsActivatedOnChargeByDefault);
            printWriter.println("mIsDocked=" + dreamManagerService.mIsDocked);
            printWriter.println("mIsCharging=" + dreamManagerService.mIsCharging);
            printWriter.println("mWhenToDream=" + dreamManagerService.mWhenToDream);
            printWriter.println(
                    "mKeepDreamingWhenUnpluggingDefault="
                            + dreamManagerService.mKeepDreamingWhenUnpluggingDefault);
            StringBuilder sb = new StringBuilder("getDozeComponent()=");
            ActivityManager.getCurrentUser();
            sb.append(
                    ComponentName.unflattenFromString(
                            dreamManagerService.mDozeConfig.ambientDisplayComponent()));
            printWriter.println(sb.toString());
            printWriter.println(
                    "mDreamOverlayServiceName="
                            + ComponentName.flattenToShortString(
                                    dreamManagerService.mDreamOverlayServiceName));
            printWriter.println();
            DumpUtils.dumpAsync(
                    dreamManagerService.mHandler,
                    new DumpUtils.Dump() { // from class:
                        // com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda6
                        public final void dump(PrintWriter printWriter2, String str) {
                            DreamController dreamController = DreamManagerService.this.mController;
                            dreamController.getClass();
                            printWriter2.println("Dreamland:");
                            if (dreamController.mCurrentDream != null) {
                                StringBuilder m$1 =
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                                printWriter2, "  mCurrentDream:", "    mToken=");
                                m$1.append(dreamController.mCurrentDream.mToken);
                                printWriter2.println(m$1.toString());
                                printWriter2.println(
                                        "    mName=" + dreamController.mCurrentDream.mName);
                                StringBuilder m =
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                new StringBuilder(
                                                                                                        "    mIsPreviewMode="),
                                                                                                dreamController
                                                                                                        .mCurrentDream
                                                                                                        .mIsPreviewMode,
                                                                                                printWriter2,
                                                                                                "    mCanDoze="),
                                                                                dreamController
                                                                                        .mCurrentDream
                                                                                        .mCanDoze,
                                                                                printWriter2,
                                                                                "    mUserId="),
                                                                dreamController
                                                                        .mCurrentDream
                                                                        .mUserId,
                                                                printWriter2,
                                                                "    mBound="),
                                                dreamController.mCurrentDream.mBound,
                                                printWriter2,
                                                "    mService=");
                                m.append(dreamController.mCurrentDream.mService);
                                printWriter2.println(m.toString());
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("    mWakingGently="),
                                        dreamController.mCurrentDream.mWakingGently,
                                        printWriter2);
                            } else {
                                printWriter2.println("  mCurrentDream: null");
                            }
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("  mSentStartBroadcast="),
                                    dreamController.mSentStartBroadcast,
                                    printWriter2);
                        }
                    },
                    printWriter,
                    "",
                    200L);
        }
    }

    /* renamed from: -$$Nest$msemStartDozingInternal, reason: not valid java name */
    public static void m489$$Nest$msemStartDozingInternal(
            DreamManagerService dreamManagerService,
            IBinder iBinder,
            int i,
            int i2,
            int i3,
            int i4,
            boolean z) {
        synchronized (dreamManagerService.mLock) {
            try {
                DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                if (dreamRecord != null && dreamRecord.token == iBinder && dreamRecord.canDoze) {
                    dreamRecord.dozeScreenState = i;
                    dreamRecord.dozeScreenBrightness = i3;
                    dreamManagerService.mPowerManagerInternal.setDozeOverrideFromDreamManager(
                            i, i2, i3, i4, z);
                    DreamRecord dreamRecord2 = dreamManagerService.mCurrentDream;
                    if (!dreamRecord2.isDozing) {
                        dreamRecord2.isDozing = true;
                        dreamManagerService.mDozeWakeLock.acquire();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x000d, code lost:

       r4 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0068, code lost:

       throw r4;
    */
    /* renamed from: -$$Nest$msetSystemDreamComponentInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m490$$Nest$msetSystemDreamComponentInternal(
            com.android.server.dreams.DreamManagerService r4, android.content.ComponentName r5) {
        /*
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.content.ComponentName r1 = r4.mSystemDreamComponent     // Catch: java.lang.Throwable -> Ld
            boolean r1 = java.util.Objects.equals(r1, r5)     // Catch: java.lang.Throwable -> Ld
            if (r1 == 0) goto Lf
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            goto L66
        Ld:
            r4 = move-exception
            goto L67
        Lf:
            r4.mSystemDreamComponent = r5     // Catch: java.lang.Throwable -> Ld
            boolean r1 = r4.mKeepDreamingWhenUnpluggingDefault     // Catch: java.lang.Throwable -> Ld
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L1b
            if (r5 != 0) goto L1b
            r5 = r3
            goto L1c
        L1b:
            r5 = r2
        L1c:
            com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda3 r1 = new com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda3     // Catch: java.lang.Throwable -> Ld
            r1.<init>()     // Catch: java.lang.Throwable -> Ld
            com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda7 r5 = new com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda7     // Catch: java.lang.Throwable -> Ld
            r5.<init>(r4, r1)     // Catch: java.lang.Throwable -> Ld
            android.os.Handler r1 = r4.mHandler     // Catch: java.lang.Throwable -> Ld
            r1.post(r5)     // Catch: java.lang.Throwable -> Ld
            boolean r5 = r4.isDreamingInternal()     // Catch: java.lang.Throwable -> Ld
            if (r5 == 0) goto L65
            java.lang.Object r5 = r4.mLock     // Catch: java.lang.Throwable -> Ld
            monitor-enter(r5)     // Catch: java.lang.Throwable -> Ld
            com.android.server.dreams.DreamManagerService$DreamRecord r1 = r4.mCurrentDream     // Catch: java.lang.Throwable -> L3d
            if (r1 == 0) goto L3f
            boolean r1 = r1.isDozing     // Catch: java.lang.Throwable -> L3d
            if (r1 == 0) goto L3f
            goto L40
        L3d:
            r4 = move-exception
            goto L63
        L3f:
            r3 = r2
        L40:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L3d
            if (r3 != 0) goto L65
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            r5.<init>()     // Catch: java.lang.Throwable -> Ld
            android.content.ComponentName r1 = r4.mSystemDreamComponent     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto L50
            java.lang.String r1 = "clear"
            goto L53
        L50:
            java.lang.String r1 = "set"
        L53:
            r5.append(r1)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r1 = " system dream component"
            r5.append(r1)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Ld
            r4.startDreamInternal(r5, r2)     // Catch: java.lang.Throwable -> Ld
            goto L65
        L63:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L3d
            throw r4     // Catch: java.lang.Throwable -> Ld
        L65:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
        L66:
            return
        L67:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.dreams.DreamManagerService.m490$$Nest$msetSystemDreamComponentInternal(com.android.server.dreams.DreamManagerService,"
                    + " android.content.ComponentName):void");
    }

    /* renamed from: -$$Nest$mstartDozingInternal, reason: not valid java name */
    public static void m491$$Nest$mstartDozingInternal(
            DreamManagerService dreamManagerService, IBinder iBinder, int i, int i2, int i3) {
        synchronized (dreamManagerService.mLock) {
            try {
                DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                if (dreamRecord != null && dreamRecord.token == iBinder && dreamRecord.canDoze) {
                    dreamRecord.dozeScreenState = i;
                    dreamRecord.dozeScreenBrightness = i3;
                    dreamManagerService.mPowerManagerInternal.setDozeOverrideFromDreamManager(
                            i, i2, i3);
                    DreamRecord dreamRecord2 = dreamManagerService.mCurrentDream;
                    if (!dreamRecord2.isDozing) {
                        dreamRecord2.isDozing = true;
                        dreamManagerService.mDozeWakeLock.acquire();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public DreamManagerService(Context context) {
        this(context, new DreamHandler(FgThread.get().getLooper(), null, true));
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.dreams.DreamManagerService$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.dreams.DreamManagerService$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.dreams.DreamManagerService$2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.dreams.DreamManagerService$6] */
    public DreamManagerService(Context context, Handler handler) {
        super(context);
        this.mLock = new Object();
        this.mDreamManagerStateListeners = new CopyOnWriteArrayList();
        this.mActivityInterceptorCallback =
                new ActivityInterceptorCallback() { // from class:
                                                    // com.android.server.dreams.DreamManagerService.1
                    @Override // com.android.server.wm.ActivityInterceptorCallback
                    public final void onActivityLaunched(
                            TaskInfo taskInfo,
                            ActivityInfo activityInfo,
                            ActivityInterceptorCallback.ActivityInterceptorInfo
                                    activityInterceptorInfo) {
                        DreamManagerService dreamManagerService;
                        int activityType = taskInfo.getActivityType();
                        boolean z = true;
                        boolean z2 = activityType == 2 || activityType == 5 || activityType == 4;
                        synchronized (DreamManagerService.this.mLock) {
                            try {
                                dreamManagerService = DreamManagerService.this;
                                DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                                if (dreamRecord == null
                                        || dreamRecord.isWaking
                                        || dreamRecord.isDozing
                                        || z2) {
                                    z = false;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            String str =
                                    "stopping dream due to activity start: " + activityInfo.name;
                            dreamManagerService.mPowerManager.userActivity(
                                    SystemClock.uptimeMillis(), false);
                            dreamManagerService.stopDreamInternal(str, false);
                        }
                    }

                    @Override // com.android.server.wm.ActivityInterceptorCallback
                    public final ActivityInterceptorCallback.ActivityInterceptResult
                            onInterceptActivityLaunch(
                                    ActivityInterceptorCallback.ActivityInterceptorInfo
                                            activityInterceptorInfo) {
                        return null;
                    }
                };
        final int i = 0;
        this.mChargingReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.dreams.DreamManagerService.2
                    public final /* synthetic */ DreamManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                Flags.useBatteryChangedBroadcast();
                                DreamManagerService dreamManagerService = this.this$0;
                                dreamManagerService.mIsCharging =
                                        dreamManagerService.mBatteryManagerInternal.isPowered(15);
                                break;
                            default:
                                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.DOCK_STATE", 0);
                                    this.this$0.mIsDocked = intExtra != 0;
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mDockStateReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.dreams.DreamManagerService.2
                    public final /* synthetic */ DreamManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i2) {
                            case 0:
                                Flags.useBatteryChangedBroadcast();
                                DreamManagerService dreamManagerService = this.this$0;
                                dreamManagerService.mIsCharging =
                                        dreamManagerService.mBatteryManagerInternal.isPowered(15);
                                break;
                            default:
                                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.DOCK_STATE", 0);
                                    this.this$0.mIsDocked = intExtra != 0;
                                    break;
                                }
                                break;
                        }
                    }
                };
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mDozeEnabledObserver = new AnonymousClass5();
        this.mSystemPropertiesChanged =
                new Runnable() { // from class: com.android.server.dreams.DreamManagerService.6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ComponentName componentName;
                        synchronized (DreamManagerService.this.mLock) {
                            try {
                                DreamManagerService dreamManagerService = DreamManagerService.this;
                                DreamRecord dreamRecord = dreamManagerService.mCurrentDream;
                                if (dreamRecord != null
                                        && (componentName = dreamRecord.name) != null
                                        && dreamRecord.canDoze) {
                                    ActivityManager.getCurrentUser();
                                    if (!componentName.equals(
                                            ComponentName.unflattenFromString(
                                                    dreamManagerService.mDozeConfig
                                                            .ambientDisplayComponent()))) {
                                        DreamManagerService.this.mPowerManager.wakeUp(
                                                SystemClock.uptimeMillis(),
                                                "android.server.dreams:SYSPROP");
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mHandler = handler;
        this.mController = new DreamController(context, handler, anonymousClass4);
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mPowerManagerInternal =
                (PowerManagerInternal) getLocalService(PowerManagerInternal.class);
        this.mAtmInternal =
                (ActivityTaskManagerInternal) getLocalService(ActivityTaskManagerInternal.class);
        this.mPmInternal = (PackageManagerInternal) getLocalService(PackageManagerInternal.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mDozeWakeLock = powerManager.newWakeLock(64, "dream:doze");
        this.mDozeConfig = new AmbientDisplayConfiguration(context);
        this.mUiEventLogger = new UiEventLoggerImpl();
        this.mDreamUiEventLogger =
                new DreamUiEventLoggerImpl(
                        context.getResources()
                                .getStringArray(R.array.wfcOperatorErrorNotificationMessages));
        this.mAmbientDisplayComponent =
                ComponentName.unflattenFromString(
                        new AmbientDisplayConfiguration(context).ambientDisplayComponent());
        this.mDreamsOnlyEnabledForDockUser =
                context.getResources().getBoolean(R.bool.config_earcFeatureDisabled_allowed);
        this.mDismissDreamOnActivityStart =
                context.getResources().getBoolean(R.bool.config_displayWhiteBalanceEnabledDefault);
        this.mDreamsEnabledByDefaultConfig =
                context.getResources().getBoolean(R.bool.config_eap_sim_based_auth_supported);
        this.mDreamsActivatedOnChargeByDefault =
                context.getResources().getBoolean(R.bool.config_dreamsSupported);
        this.mDreamsActivatedOnDockByDefault =
                context.getResources().getBoolean(R.bool.config_dreamsOnlyEnabledForDockUser);
        this.mSettingsObserver = new AnonymousClass5(handler);
        this.mKeepDreamingWhenUnpluggingDefault =
                context.getResources()
                        .getBoolean(
                                R.bool
                                        .config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled);
        this.mDreamsDisabledByAmbientModeSuppressionConfig =
                context.getResources().getBoolean(R.bool.config_duplicate_port_omadm_wappush);
        Flags.useBatteryChangedBroadcast();
        this.mBatteryManagerInternal =
                (BatteryManagerInternal) getLocalService(BatteryManagerInternal.class);
    }

    public final void cleanupDreamLocked() {
        this.mHandler.post(new DreamManagerService$$ExternalSyntheticLambda1(this, 1));
        DreamRecord dreamRecord = this.mCurrentDream;
        if (dreamRecord == null) {
            return;
        }
        if (!dreamRecord.name.equals(this.mAmbientDisplayComponent)) {
            UiEventLoggerImpl uiEventLoggerImpl = this.mUiEventLogger;
            DreamUiEventLogger$DreamUiEventEnum dreamUiEventLogger$DreamUiEventEnum =
                    DreamUiEventLogger$DreamUiEventEnum.DREAM_STOP;
            uiEventLoggerImpl.log(dreamUiEventLogger$DreamUiEventEnum);
            this.mDreamUiEventLogger.log(
                    dreamUiEventLogger$DreamUiEventEnum, this.mCurrentDream.name.flattenToString());
        }
        if (this.mCurrentDream.isDozing) {
            this.mDozeWakeLock.release();
        }
        this.mCurrentDream = null;
    }

    public final ComponentName[] getDreamComponentsForUser(int i) {
        ComponentName[] componentNameArr;
        boolean z = true;
        if (this.mDreamsOnlyEnabledForDockUser
                && (i < 0
                        || i
                                != ((UserManagerInternal)
                                                LocalServices.getService(UserManagerInternal.class))
                                        .getMainUserId())) {
            z = false;
        }
        if (!z) {
            return null;
        }
        String stringForUser =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(), "screensaver_components", i);
        if (stringForUser == null) {
            componentNameArr = null;
        } else {
            String[] split = stringForUser.split(",");
            componentNameArr = new ComponentName[split.length];
            for (int i2 = 0; i2 < split.length; i2++) {
                componentNameArr[i2] = ComponentName.unflattenFromString(split[i2]);
            }
        }
        ArrayList arrayList = new ArrayList();
        if (componentNameArr != null) {
            for (ComponentName componentName : componentNameArr) {
                if (validateDream(componentName)) {
                    arrayList.add(componentName);
                }
            }
        }
        if (arrayList.isEmpty()) {
            String stringForUser2 =
                    Settings.Secure.getStringForUser(
                            this.mContext.getContentResolver(), "screensaver_default_component", i);
            ComponentName unflattenFromString =
                    stringForUser2 != null
                            ? ComponentName.unflattenFromString(stringForUser2)
                            : null;
            if (unflattenFromString != null) {
                Slog.w(
                        "DreamManagerService",
                        "Falling back to default dream " + unflattenFromString);
                arrayList.add(unflattenFromString);
            }
        }
        return (ComponentName[]) arrayList.toArray(new ComponentName[arrayList.size()]);
    }

    public final boolean isDreamingInternal() {
        boolean z;
        synchronized (this.mLock) {
            try {
                DreamRecord dreamRecord = this.mCurrentDream;
                z =
                        (dreamRecord == null || dreamRecord.isPreview || dreamRecord.isWaking)
                                ? false
                                : true;
            } finally {
            }
        }
        return z;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 600) {
            if (Build.IS_DEBUGGABLE) {
                SystemProperties.addChangeCallback(this.mSystemPropertiesChanged);
            }
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("doze_pulse_on_double_tap"),
                            false,
                            this.mDozeEnabledObserver,
                            -1);
            writePulseGestureEnabled();
            if (this.mDismissDreamOnActivityStart) {
                this.mAtmInternal.registerActivityStartInterceptor(
                        4, this.mActivityInterceptorCallback);
            }
            this.mContext.registerReceiver(
                    this.mDockStateReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"));
            IntentFilter intentFilter = new IntentFilter();
            Flags.useBatteryChangedBroadcast();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            intentFilter.setPriority(1000);
            this.mContext.registerReceiver(this.mChargingReceiver, intentFilter);
            this.mSettingsObserver = new AnonymousClass5(this.mHandler);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("screensaver_activate_on_sleep"),
                            false,
                            this.mSettingsObserver,
                            -1);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("screensaver_activate_on_dock"),
                            false,
                            this.mSettingsObserver,
                            -1);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("screensaver_enabled"),
                            false,
                            this.mSettingsObserver,
                            -1);
            this.mIsCharging =
                    ((BatteryManager) this.mContext.getSystemService(BatteryManager.class))
                            .isCharging();
            updateWhenToDreamSettings();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("dreams", new BinderService());
        publishLocalService(DreamManagerInternal.class, new LocalService());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        updateWhenToDreamSettings();
        this.mHandler.post(new DreamManagerService$$ExternalSyntheticLambda1(this, 0));
    }

    public final void requestDreamInternal() {
        DreamController.DreamRecord dreamRecord;
        IDreamService iDreamService;
        if (isDreamingInternal()
                && android.service.dreams.Flags.dreamHandlesBeingObscured()
                && (((dreamRecord = this.mController.mCurrentDream) == null
                                || !(!dreamRecord.mDreamIsObscured))
                        && dreamRecord != null
                        && (iDreamService = dreamRecord.mService) != null)) {
            try {
                iDreamService.comeToFront();
                return;
            } catch (RemoteException e) {
                Slog.e("DreamController", "Error asking dream to come to the front", e);
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mPowerManager.userActivity(uptimeMillis, true);
        this.mPowerManagerInternal.nap(uptimeMillis, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0031 A[Catch: all -> 0x0039, TryCatch #0 {all -> 0x0039, blocks: (B:6:0x000a, B:11:0x0031, B:12:0x003b, B:16:0x001d, B:19:0x0022, B:21:0x0028, B:23:0x002b), top: B:4:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startDreamInternal(java.lang.String r8, boolean r9) {
        /*
            r7 = this;
            int r4 = android.app.ActivityManager.getCurrentUser()
            java.lang.Object r6 = r7.mLock
            monitor-enter(r6)
            r0 = 0
            if (r9 == 0) goto L1d
            android.hardware.display.AmbientDisplayConfiguration r1 = r7.mDozeConfig     // Catch: java.lang.Throwable -> L39
            java.lang.String r1 = r1.ambientDisplayComponent()     // Catch: java.lang.Throwable -> L39
            android.content.ComponentName r1 = android.content.ComponentName.unflattenFromString(r1)     // Catch: java.lang.Throwable -> L39
            boolean r2 = r7.validateDream(r1)     // Catch: java.lang.Throwable -> L39
            if (r2 == 0) goto L1b
            r0 = r1
        L1b:
            r1 = r0
            goto L2f
        L1d:
            android.content.ComponentName r1 = r7.mSystemDreamComponent     // Catch: java.lang.Throwable -> L39
            if (r1 == 0) goto L22
            goto L2f
        L22:
            android.content.ComponentName[] r1 = r7.getDreamComponentsForUser(r4)     // Catch: java.lang.Throwable -> L39
            if (r1 == 0) goto L1b
            int r2 = r1.length     // Catch: java.lang.Throwable -> L39
            if (r2 == 0) goto L1b
            r0 = 0
            r0 = r1[r0]     // Catch: java.lang.Throwable -> L39
            goto L1b
        L2f:
            if (r1 == 0) goto L3b
            r2 = 0
            r0 = r7
            r3 = r9
            r5 = r8
            r0.startDreamLocked(r1, r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L39
            goto L3b
        L39:
            r7 = move-exception
            goto L3d
        L3b:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L39
            return
        L3d:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L39
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.dreams.DreamManagerService.startDreamInternal(java.lang.String,"
                    + " boolean):void");
    }

    public final void startDreamLocked(
            final ComponentName componentName,
            final boolean z,
            final boolean z2,
            final int i,
            final String str) {
        DreamRecord dreamRecord = this.mCurrentDream;
        if (dreamRecord != null
                && !dreamRecord.isWaking
                && Objects.equals(dreamRecord.name, componentName)) {
            DreamRecord dreamRecord2 = this.mCurrentDream;
            if (dreamRecord2.isPreview == z
                    && dreamRecord2.canDoze == z2
                    && dreamRecord2.userId == i) {
                Slog.i("DreamManagerService", "Already in target dream.");
                return;
            }
        }
        Slog.i("DreamManagerService", "Entering dreamland.");
        DreamRecord dreamRecord3 = this.mCurrentDream;
        if (dreamRecord3 != null && dreamRecord3.isDozing) {
            stopDozingInternal(dreamRecord3.token);
        }
        DreamRecord dreamRecord4 = new DreamRecord(componentName, i, z, z2);
        this.mCurrentDream = dreamRecord4;
        if (!dreamRecord4.name.equals(this.mAmbientDisplayComponent)) {
            UiEventLoggerImpl uiEventLoggerImpl = this.mUiEventLogger;
            DreamUiEventLogger$DreamUiEventEnum dreamUiEventLogger$DreamUiEventEnum =
                    DreamUiEventLogger$DreamUiEventEnum.DREAM_START;
            uiEventLoggerImpl.log(dreamUiEventLogger$DreamUiEventEnum);
            this.mDreamUiEventLogger.log(
                    dreamUiEventLogger$DreamUiEventEnum, this.mCurrentDream.name.flattenToString());
        }
        final PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "dream:dream");
        final Binder binder = this.mCurrentDream.token;
        this.mHandler.post(
                newWakeLock.wrap(
                        new Runnable() { // from class:
                                         // com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                String str2;
                                long j;
                                DreamManagerService dreamManagerService = DreamManagerService.this;
                                ComponentName componentName2 = componentName;
                                Binder binder2 = binder;
                                boolean z3 = z;
                                boolean z4 = z2;
                                int i2 = i;
                                PowerManager.WakeLock wakeLock = newWakeLock;
                                String str3 = str;
                                dreamManagerService.mAtmInternal.notifyActiveDreamChanged(
                                        componentName2);
                                ComponentName componentName3 =
                                        dreamManagerService.mDreamOverlayServiceName;
                                DreamController dreamController = dreamManagerService.mController;
                                dreamController.getClass();
                                Trace.traceBegin(131072L, "startDream");
                                try {
                                    dreamController.mContext.sendBroadcastAsUser(
                                            dreamController.mCloseNotificationShadeIntent,
                                            UserHandle.ALL,
                                            null,
                                            dreamController.mCloseNotificationShadeOptions);
                                    Slog.i(
                                            "DreamController",
                                            "Starting dream: name="
                                                    + componentName2
                                                    + ", isPreviewMode="
                                                    + z3
                                                    + ", canDoze="
                                                    + z4
                                                    + ", userId="
                                                    + i2
                                                    + ", reason='"
                                                    + str3
                                                    + "'");
                                    DreamController.DreamRecord dreamRecord5 =
                                            dreamController.mCurrentDream;
                                    DreamController.DreamRecord dreamRecord6 =
                                            dreamController
                                            .new DreamRecord(
                                                    binder2, componentName2, z3, z4, i2, wakeLock);
                                    dreamController.mCurrentDream = dreamRecord6;
                                    if (dreamRecord5 != null) {
                                        if (Objects.equals(
                                                dreamRecord5.mName, dreamRecord6.mName)) {
                                            dreamController.stopDreamInstance(
                                                    true, "restarting same dream", dreamRecord5);
                                        } else {
                                            dreamController.mPreviousDreams.add(dreamRecord5);
                                        }
                                    }
                                    dreamController.mCurrentDream.mDreamStartTime =
                                            SystemClock.elapsedRealtime();
                                    MetricsLogger.visible(
                                            dreamController.mContext,
                                            dreamController.mCurrentDream.mCanDoze
                                                    ? FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED
                                                    : 222);
                                    Intent intent =
                                            new Intent("android.service.dreams.DreamService");
                                    intent.setComponent(componentName2);
                                    intent.addFlags(8388608);
                                    DreamService.setDreamOverlayComponent(intent, componentName3);
                                    try {
                                        if (dreamController.mContext.bindServiceAsUser(
                                                intent,
                                                dreamController.mCurrentDream,
                                                67108865,
                                                new UserHandle(i2))) {
                                            DreamController.DreamRecord dreamRecord7 =
                                                    dreamController.mCurrentDream;
                                            dreamRecord7.mBound = true;
                                            dreamController.mHandler.postDelayed(
                                                    dreamRecord7.mStopUnconnectedDreamRunnable,
                                                    5000L);
                                            j = 131072;
                                        } else {
                                            str2 = "Unable to bind dream service: ";
                                            try {
                                                Slog.e("DreamController", str2 + intent);
                                                dreamController.stopDream(
                                                        true, "bindService failed");
                                                j = 131072;
                                            } catch (SecurityException e) {
                                                e = e;
                                                Slog.e("DreamController", str2 + intent, e);
                                                dreamController.stopDream(
                                                        true, "unable to bind service: SecExp.");
                                                j = 131072;
                                                Trace.traceEnd(j);
                                            }
                                        }
                                    } catch (SecurityException e2) {
                                        e = e2;
                                        str2 = "Unable to bind dream service: ";
                                    }
                                    Trace.traceEnd(j);
                                } catch (Throwable th) {
                                    Trace.traceEnd(131072L);
                                    throw th;
                                }
                            }
                        }));
    }

    public final void stopDozingInternal(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                DreamRecord dreamRecord = this.mCurrentDream;
                if (dreamRecord != null && dreamRecord.token == iBinder && dreamRecord.isDozing) {
                    dreamRecord.isDozing = false;
                    this.mDozeWakeLock.release();
                    this.mPowerManagerInternal.setDozeOverrideFromDreamManager(0, 5, -1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopDreamInternal(String str, boolean z) {
        synchronized (this.mLock) {
            stopDreamLocked(str, z);
        }
    }

    public final void stopDreamLocked(final String str, final boolean z) {
        DreamRecord dreamRecord = this.mCurrentDream;
        if (dreamRecord != null) {
            if (z) {
                Slog.i("DreamManagerService", "Leaving dreamland.");
                cleanupDreamLocked();
            } else {
                if (dreamRecord.isWaking) {
                    return;
                }
                Slog.i("DreamManagerService", "Gently waking up from dream.");
                this.mCurrentDream.isWaking = true;
            }
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamManagerService dreamManagerService = DreamManagerService.this;
                            dreamManagerService.mController.stopDream(z, str);
                        }
                    });
        }
    }

    public final void updateWhenToDreamSettings() {
        synchronized (this.mLock) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            boolean z = true;
            this.mWhenToDream =
                    (Settings.Secure.getIntForUser(
                                                    contentResolver,
                                                    "screensaver_activate_on_sleep",
                                                    this.mDreamsActivatedOnChargeByDefault ? 1 : 0,
                                                    -2)
                                            != 0
                                    ? 2
                                    : 0)
                            + (Settings.Secure.getIntForUser(
                                                    contentResolver,
                                                    "screensaver_activate_on_dock",
                                                    this.mDreamsActivatedOnDockByDefault ? 1 : 0,
                                                    -2)
                                            != 0
                                    ? 1
                                    : 0);
            if (Settings.Secure.getIntForUser(
                            contentResolver,
                            "screensaver_enabled",
                            this.mDreamsEnabledByDefaultConfig ? 1 : 0,
                            -2)
                    == 0) {
                z = false;
            }
            this.mDreamsEnabledSetting = z;
        }
    }

    public final boolean validateDream(ComponentName componentName) {
        ServiceInfo serviceInfo;
        if (componentName == null) {
            return false;
        }
        try {
            serviceInfo =
                    this.mContext.getPackageManager().getServiceInfo(componentName, 268435456);
        } catch (PackageManager.NameNotFoundException unused) {
            serviceInfo = null;
        }
        if (serviceInfo == null) {
            Slog.w("DreamManagerService", "Dream " + componentName + " does not exist");
            return false;
        }
        if (serviceInfo.applicationInfo.targetSdkVersion < 21
                || "android.permission.BIND_DREAM_SERVICE".equals(serviceInfo.permission)) {
            return true;
        }
        Slog.w(
                "DreamManagerService",
                "Dream "
                        + componentName
                        + " is not available because its manifest is missing the"
                        + " android.permission.BIND_DREAM_SERVICE permission on the dream service"
                        + " declaration.");
        return false;
    }

    public final void writePulseGestureEnabled() {
        ActivityManager.getCurrentUser();
        boolean validateDream =
                validateDream(
                        ComponentName.unflattenFromString(
                                this.mDozeConfig.ambientDisplayComponent()));
        InputManagerService inputManagerService = InputManagerService.this;
        if (inputManagerService.mDoubleTouchGestureEnableFile == null) {
            return;
        }
        FileWriter fileWriter = null;
        try {
            try {
                FileWriter fileWriter2 =
                        new FileWriter(inputManagerService.mDoubleTouchGestureEnableFile);
                try {
                    fileWriter2.write(validateDream ? "1" : "0");
                    IoUtils.closeQuietly(fileWriter2);
                } catch (IOException e) {
                    e = e;
                    fileWriter = fileWriter2;
                    Log.wtf("InputManager", "Unable to setPulseGestureEnabled", e);
                    IoUtils.closeQuietly(fileWriter);
                } catch (Throwable th) {
                    th = th;
                    fileWriter = fileWriter2;
                    IoUtils.closeQuietly(fileWriter);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
