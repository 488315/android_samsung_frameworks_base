package com.android.server.appwidget;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.BroadcastOptions;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetManagerInternal;
import android.appwidget.flags.Flags;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.graphics.Point;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Message;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import com.android.internal.os.BackgroundThread;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppWidgetBackupBridge;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.WidgetBackupProvider;
import com.android.server.appwidget.AppWidgetServiceImpl;
import com.samsung.android.knox.SemPersonaManager;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

public final class AppWidgetService extends SystemService {
    public final AppWidgetServiceImpl mImpl;

    public AppWidgetService(Context context) {
        super(context);
        this.mImpl = new AppWidgetServiceImpl(context);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 550) {
            boolean isSafeMode = isSafeMode();
            AppWidgetServiceImpl appWidgetServiceImpl = this.mImpl;
            appWidgetServiceImpl.mSafeMode = isSafeMode;
            appWidgetServiceImpl.getClass();
            appWidgetServiceImpl.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
            appWidgetServiceImpl.mAppOpsManagerInternal = (AppOpsManagerInternal) LocalServices.getService(AppOpsManagerInternal.class);
            appWidgetServiceImpl.mUsageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        final ?? r0 = this.mImpl;
        ContentResolver contentResolver = r0.mContext.getContentResolver();
        if (contentResolver != null) {
            Slog.d("AppWidgetServiceImpl", "set appwidget_prevent_remove_all");
            Settings.Global.putInt(contentResolver, "appwidget_prevent_remove_all", 1);
        }
        r0.mPackageManager = AppGlobals.getPackageManager();
        r0.mSpm = (SemPersonaManager) r0.mContext.getSystemService(SemPersonaManager.class);
        r0.mAlarmManager = (AlarmManager) r0.mContext.getSystemService("alarm");
        r0.mUserManager = (UserManager) r0.mContext.getSystemService("user");
        r0.mAppOpsManager = (AppOpsManager) r0.mContext.getSystemService("appops");
        r0.mKeyguardManager = (KeyguardManager) r0.mContext.getSystemService("keyguard");
        r0.mDevicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        r0.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (Flags.removeAppWidgetServiceIoFromCriticalPath()) {
            r0.mSaveStateHandler = new Handler(BackgroundThread.get().getLooper(), new Handler.Callback() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda0
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    int i;
                    SparseArray sparseArray;
                    AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                    appWidgetServiceImpl.getClass();
                    int i2 = message.what;
                    synchronized (appWidgetServiceImpl.mLock) {
                        Trace.traceBegin(64L, "convert_state_to_bytes");
                        appWidgetServiceImpl.ensureGroupStateLoadedLocked(i2, false);
                        appWidgetServiceImpl.tagProvidersAndHosts();
                        int[] enabledGroupProfileIds = appWidgetServiceImpl.mSecurityPolicy.getEnabledGroupProfileIds(i2);
                        sparseArray = new SparseArray();
                        for (int i3 : enabledGroupProfileIds) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            if (appWidgetServiceImpl.writeProfileStateToStreamLocked(byteArrayOutputStream, i3)) {
                                sparseArray.put(i3, byteArrayOutputStream.toByteArray());
                            }
                        }
                        Trace.traceEnd(64L);
                    }
                    Trace.traceBegin(64L, "byte_to_disk_io");
                    for (i = 0; i < sparseArray.size(); i++) {
                        int keyAt = sparseArray.keyAt(i);
                        byte[] bArr = (byte[]) sparseArray.valueAt(i);
                        AtomicFile savedStateFile = AppWidgetServiceImpl.getSavedStateFile(keyAt);
                        try {
                            FileOutputStream startWrite = savedStateFile.startWrite();
                            try {
                                startWrite.write(bArr);
                                savedStateFile.finishWrite(startWrite);
                            } catch (IOException e) {
                                Log.e("AppWidgetServiceImpl", "Failed to write state byte stream to file", e);
                                savedStateFile.failWrite(startWrite);
                            }
                        } catch (IOException e2) {
                            Log.e("AppWidgetServiceImpl", "Failed to start writing stream", e2);
                        }
                    }
                    Trace.traceEnd(64L);
                    return true;
                }
            });
        } else {
            r0.mSaveStateHandler = BackgroundThread.getHandler();
        }
        r0.mCallbackHandler = new AppWidgetServiceImpl.CallbackHandler(Watchdog$$ExternalSyntheticOutline0.m(-2, "AppWidgetServiceImpl", false).getLooper());
        r0.mBackupRestoreController = new AppWidgetServiceImpl.BackupRestoreController();
        r0.mSecurityPolicy = new AppWidgetServiceImpl.SecurityPolicy();
        r0.mIsCombinedBroadcastEnabled = DeviceConfig.getBoolean("systemui", "combined_broadcast_enabled", true);
        long j = DeviceConfig.getLong("systemui", "generated_preview_api_reset_interval_ms", AppWidgetServiceImpl.DEFAULT_GENERATED_PREVIEW_RESET_INTERVAL_MS);
        int i = DeviceConfig.getInt("systemui", "generated_preview_api_reset_interval_ms", 2);
        r0.mGeneratedPreviewsApiCounter = new AppWidgetServiceImpl.ApiCounter(j, i);
        r0.mGeneratedTemplatePreviewsApiCounter = new AppWidgetServiceImpl.ApiCounter(j, i);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new HandlerExecutor(r0.mCallbackHandler), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                AppWidgetServiceImpl appWidgetServiceImpl = AppWidgetServiceImpl.this;
                appWidgetServiceImpl.getClass();
                Set keyset = properties.getKeyset();
                synchronized (appWidgetServiceImpl.mLock) {
                    try {
                        if (keyset.contains("generated_preview_api_reset_interval_ms")) {
                            appWidgetServiceImpl.mGeneratedPreviewsApiCounter.mResetIntervalMs = properties.getLong("generated_preview_api_reset_interval_ms", appWidgetServiceImpl.mGeneratedPreviewsApiCounter.mResetIntervalMs);
                        }
                        if (keyset.contains("generated_preview_api_max_calls_per_interval")) {
                            appWidgetServiceImpl.mGeneratedPreviewsApiCounter.mMaxCallsPerInterval = properties.getInt("generated_preview_api_max_calls_per_interval", appWidgetServiceImpl.mGeneratedPreviewsApiCounter.mMaxCallsPerInterval);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        });
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setBackgroundActivityStartsAllowed(false);
        makeBasic.setInteractive(true);
        r0.mInteractiveBroadcast = makeBasic.toBundle();
        Display displayNoVerify = r0.mContext.getDisplayNoVerify();
        Point point = new Point();
        displayNoVerify.getRealSize(point);
        r0.mMaxWidgetBitmapMemory = point.x * 8 * point.y;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.setPriority(1000);
        Context context = r0.mContext;
        AppWidgetServiceImpl.AnonymousClass1 anonymousClass1 = r0.mBroadcastReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass1, userHandle, intentFilter, null, r0.mCallbackHandler);
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_DATA_CLEARED", "android.intent.action.PACKAGE_RESTARTED");
        m.addAction("android.intent.action.PACKAGE_UNSTOPPED");
        m.addDataScheme("package");
        m.setPriority(1000);
        r0.mContext.registerReceiverAsUser(r0.mBroadcastReceiver, userHandle, m, null, r0.mCallbackHandler);
        r0.mContext.registerReceiverAsUser(r0.mBroadcastReceiver, userHandle, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE", "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"), null, r0.mCallbackHandler);
        r0.mContext.registerReceiverAsUser(r0.mBroadcastReceiver, userHandle, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.MANAGED_PROFILE_AVAILABLE", "android.intent.action.MANAGED_PROFILE_UNAVAILABLE"), null, r0.mCallbackHandler);
        r0.mContext.registerReceiverAsUser(r0.mBroadcastReceiver, userHandle, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGES_SUSPENDED", "android.intent.action.PACKAGES_UNSUSPENDED"), null, r0.mCallbackHandler);
        try {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
            intentFilter2.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            r0.mContext.registerReceiverAsUser(r0.mBroadcastReceiver, userHandle, intentFilter2, null, r0.mCallbackHandler);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "exceptoin registeBroadcastReceiver ", "AppWidgetServiceImpl");
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal = r0.mDevicePolicyManagerInternal;
        if (devicePolicyManagerInternal != null) {
            devicePolicyManagerInternal.addOnCrossProfileWidgetProvidersChangeListener((DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener) r0);
        }
        LocalServices.addService(AppWidgetManagerInternal.class, new AppWidgetServiceImpl.AppWidgetManagerLocal());
        r0.mActivityManager = (ActivityManager) r0.mContext.getSystemService("activity");
        r0.mLocale = Locale.getDefault();
        r0.mScreenDensity = r0.mContext.getResources().getConfiguration().densityDpi;
        ContainerStateReceiver.register(r0.mContext, new ContainerStateReceiver() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.2
            public AnonymousClass2() {
            }

            public final void onContainerAdminLocked(Context context2, int i2, Bundle bundle) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "onContainerAdminLocked is triggered: ", "AppWidgetServiceImpl");
                synchronized (AppWidgetServiceImpl.this.mLock) {
                    AppWidgetServiceImpl.this.reloadWidgetsMaskedState(i2);
                }
            }

            public final void onContainerAdminUnlocked(Context context2, int i2, Bundle bundle) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "onContainerAdminUnlocked is triggered: ", "AppWidgetServiceImpl");
                synchronized (AppWidgetServiceImpl.this.mLock) {
                    AppWidgetServiceImpl.this.reloadWidgetsMaskedState(i2);
                }
            }
        });
        publishBinderService("appwidget", r0);
        AppWidgetBackupBridge.register((WidgetBackupProvider) r0);
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        boolean z;
        AppWidgetServiceImpl appWidgetServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        if (AppWidgetServiceImpl.DEBUG) {
            appWidgetServiceImpl.getClass();
            Slog.i("AppWidgetServiceImpl", "onUserStopped() " + userIdentifier);
        }
        synchronized (appWidgetServiceImpl.mLock) {
            try {
                int size = appWidgetServiceImpl.mWidgets.size() - 1;
                while (true) {
                    z = false;
                    if (size < 0) {
                        break;
                    }
                    AppWidgetServiceImpl.Widget widget = (AppWidgetServiceImpl.Widget) appWidgetServiceImpl.mWidgets.get(size);
                    boolean z2 = widget.host.getUserId() == userIdentifier;
                    AppWidgetServiceImpl.Provider provider = widget.provider;
                    boolean z3 = provider != null;
                    if (z3 && provider.getUserId() == userIdentifier) {
                        z = true;
                    }
                    if (z2 && (!z3 || z)) {
                        appWidgetServiceImpl.removeWidgetLocked(widget);
                        widget.host.widgets.remove(widget);
                        widget.host = null;
                        if (z3) {
                            widget.provider.widgets.remove(widget);
                            widget.provider = null;
                        }
                    }
                    size--;
                }
                for (int size2 = appWidgetServiceImpl.mHosts.size() - 1; size2 >= 0; size2--) {
                    AppWidgetServiceImpl.Host host = (AppWidgetServiceImpl.Host) appWidgetServiceImpl.mHosts.get(size2);
                    if (host.getUserId() == userIdentifier) {
                        z |= !host.widgets.isEmpty();
                        appWidgetServiceImpl.deleteHostLocked(host);
                    }
                }
                for (int size3 = appWidgetServiceImpl.mPackagesWithBindWidgetPermission.size() - 1; size3 >= 0; size3--) {
                    if (((Integer) ((Pair) appWidgetServiceImpl.mPackagesWithBindWidgetPermission.valueAt(size3)).first).intValue() == userIdentifier) {
                        appWidgetServiceImpl.mPackagesWithBindWidgetPermission.removeAt(size3);
                    }
                }
                int indexOfKey = appWidgetServiceImpl.mLoadedUserIds.indexOfKey(userIdentifier);
                if (indexOfKey >= 0) {
                    appWidgetServiceImpl.mLoadedUserIds.removeAt(indexOfKey);
                }
                int indexOfKey2 = appWidgetServiceImpl.mNextAppWidgetIds.indexOfKey(userIdentifier);
                if (indexOfKey2 >= 0) {
                    appWidgetServiceImpl.mNextAppWidgetIds.removeAt(indexOfKey2);
                }
                if (z) {
                    appWidgetServiceImpl.saveGroupStateAsync(userIdentifier);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        this.mImpl.reloadWidgetsMaskedStateForGroup(targetUser2.getUserIdentifier());
    }
}
