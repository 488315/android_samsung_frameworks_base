package com.android.server.wm;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.widget.Toast;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.UiThread;
import com.android.server.wm.ActivityRefresher;
import com.android.server.wm.CameraStateMonitor;
import com.android.server.wm.utils.OptPropFactory;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayRotationCompatPolicy implements CameraStateMonitor.CameraCompatStateListener, ActivityRefresher.Evaluator {
    public final ActivityRefresher mActivityRefresher;
    public final CameraStateMonitor mCameraStateMonitor;
    public Task mCameraTask;
    public final DisplayContent mDisplayContent;
    public boolean mIsRunning;
    public int mLastReportedOrientation = -2;
    public final WindowManagerService mWmService;

    public DisplayRotationCompatPolicy(DisplayContent displayContent, CameraStateMonitor cameraStateMonitor, ActivityRefresher activityRefresher) {
        this.mDisplayContent = displayContent;
        this.mWmService = displayContent.mWmService;
        this.mCameraStateMonitor = cameraStateMonitor;
        this.mActivityRefresher = activityRefresher;
    }

    public final boolean isCameraActive(boolean z, ActivityRecord activityRecord) {
        return ((z && activityRecord.inMultiWindowMode()) || this.mCameraStateMonitor.getCameraIdForActivity(activityRecord) == null) ? false : true;
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public final boolean isTreatmentEnabledForActivity(boolean z, ActivityRecord activityRecord) {
        Task task;
        ActivityRecord activityBelow;
        if (activityRecord == null || !isCameraActive(z, activityRecord)) {
            return false;
        }
        if (activityRecord.getRequestedConfigurationOrientation() == 0) {
            if (!CoreRune.MT_APP_COMPAT_CAMERA_POLICY) {
                return false;
            }
            if (((activityRecord.occludesParent(false) || (task = activityRecord.task) == null || (activityBelow = task.getActivityBelow(activityRecord)) == null) ? 0 : activityBelow.getRequestedConfigurationOrientation()) == 0) {
                return false;
            }
        }
        return (activityRecord.getOverrideOrientation() == 5 || activityRecord.getOverrideOrientation() == 14 || !activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides.shouldForceRotateForCameraCompat()) ? false : true;
    }

    public final boolean isTreatmentEnabledForDisplay() {
        if (this.mWmService.mAppCompatConfiguration.mDeviceConfig.getFlagValue("enable_compat_camera_treatment")) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.getIgnoreOrientationRequest() && displayContent.mDisplay.getType() == 1) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0046 A[Catch: all -> 0x0066, TryCatch #0 {all -> 0x0066, blocks: (B:12:0x0020, B:14:0x0026, B:17:0x0033, B:19:0x0046, B:21:0x004c, B:22:0x0068, B:24:0x006a), top: B:11:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a A[Catch: all -> 0x0066, DONT_GENERATE, TRY_LEAVE, TryCatch #0 {all -> 0x0066, blocks: (B:12:0x0020, B:14:0x0026, B:17:0x0033, B:19:0x0046, B:21:0x004c, B:22:0x0068, B:24:0x006a), top: B:11:0x0020 }] */
    @Override // com.android.server.wm.CameraStateMonitor.CameraCompatStateListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onCameraClosed(java.lang.String r13) {
        /*
            r12 = this;
            boolean r0 = com.android.window.flags.Flags.cameraCompatFullscreenPickSameTaskActivity()
            r1 = 0
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L14
            com.android.server.wm.Task r0 = r12.mCameraTask
            if (r0 == 0) goto L12
            com.android.server.wm.ActivityRecord r0 = r0.getTopActivity(r3, r2)
            goto L1a
        L12:
            r0 = r1
            goto L1a
        L14:
            com.android.server.wm.DisplayContent r0 = r12.mDisplayContent
            com.android.server.wm.ActivityRecord r0 = r0.topRunningActivity(r3)
        L1a:
            r12.mCameraTask = r1
            if (r0 != 0) goto L1f
            return r3
        L1f:
            monitor-enter(r12)
            boolean r1 = r12.isTreatmentEnabledForActivity(r3, r0)     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L43
            com.android.server.wm.CameraStateMonitor r1 = r12.mCameraStateMonitor     // Catch: java.lang.Throwable -> L66
            java.lang.String r1 = r1.getCameraIdForActivity(r0)     // Catch: java.lang.Throwable -> L66
            boolean r13 = r13.equals(r1)     // Catch: java.lang.Throwable -> L66
            if (r13 != 0) goto L33
            goto L43
        L33:
            com.android.server.wm.ActivityRefresher r13 = r12.mActivityRefresher     // Catch: java.lang.Throwable -> L66
            r13.getClass()     // Catch: java.lang.Throwable -> L66
            com.android.server.wm.AppCompatController r13 = r0.mAppCompatController     // Catch: java.lang.Throwable -> L66
            com.android.server.wm.AppCompatOverrides r13 = r13.mAppCompatOverrides     // Catch: java.lang.Throwable -> L66
            com.android.server.wm.AppCompatCameraOverrides r13 = r13.mAppCompatCameraOverrides     // Catch: java.lang.Throwable -> L66
            com.android.server.wm.AppCompatCameraOverrides$AppCompatCameraOverridesState r13 = r13.mAppCompatCameraOverridesState     // Catch: java.lang.Throwable -> L66
            boolean r13 = r13.mIsRefreshRequested     // Catch: java.lang.Throwable -> L66
            goto L44
        L43:
            r13 = r2
        L44:
            if (r13 == 0) goto L6a
            boolean[] r13 = com.android.internal.protolog.ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled     // Catch: java.lang.Throwable -> L66
            boolean r13 = r13[r3]     // Catch: java.lang.Throwable -> L66
            if (r13 == 0) goto L68
            com.android.server.wm.DisplayContent r13 = r12.mDisplayContent     // Catch: java.lang.Throwable -> L66
            int r13 = r13.mDisplayId     // Catch: java.lang.Throwable -> L66
            long r0 = (long) r13     // Catch: java.lang.Throwable -> L66
            com.android.internal.protolog.ProtoLogGroup r3 = com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION     // Catch: java.lang.Throwable -> L66
            java.lang.Long r13 = java.lang.Long.valueOf(r0)     // Catch: java.lang.Throwable -> L66
            java.lang.Object[] r8 = new java.lang.Object[]{r13}     // Catch: java.lang.Throwable -> L66
            r6 = 1
            r7 = 0
            r4 = -5121743609317543819(0xb8ebe952d0003075, double:-1.6798574785979571E-34)
            com.android.internal.protolog.ProtoLogImpl_54989576.v(r3, r4, r6, r7, r8)     // Catch: java.lang.Throwable -> L66
            goto L68
        L66:
            r13 = move-exception
            goto Lb9
        L68:
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L66
            return r2
        L6a:
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L66
            boolean[] r13 = com.android.internal.protolog.ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled
            boolean r13 = r13[r3]
            if (r13 == 0) goto L8a
            com.android.server.wm.DisplayContent r13 = r12.mDisplayContent
            int r13 = r13.mDisplayId
            long r4 = (long) r13
            com.android.internal.protolog.ProtoLogGroup r6 = com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION
            java.lang.Long r13 = java.lang.Long.valueOf(r4)
            java.lang.Object[] r11 = new java.lang.Object[]{r13}
            r9 = 1
            r10 = 0
            r7 = 1769752961776628557(0x188f6cf132e4334d, double:2.204122436059243E-190)
            com.android.internal.protolog.ProtoLogImpl_54989576.v(r6, r7, r9, r10, r11)
        L8a:
            int r13 = r0.getWindowingMode()
            if (r13 == r3) goto L91
            return r3
        L91:
            com.android.server.wm.AppCompatController r13 = r0.mAppCompatController
            com.android.server.wm.AppCompatOverrides r13 = r13.mAppCompatOverrides
            com.android.server.wm.AppCompatCameraOverrides r13 = r13.mAppCompatCameraOverrides
            com.android.server.wm.ActivityRecord r1 = r13.mActivityRecord
            android.content.pm.ActivityInfo r1 = r1.info
            r4 = 265456536(0xfd28b98, double:1.31152955E-315)
            boolean r1 = r1.isChangeEnabled(r4)
            if (r1 != 0) goto Lb0
            boolean r1 = r13.isCameraCompatSplitScreenAspectRatioAllowed()
            if (r1 != 0) goto Lb0
            boolean r13 = r13.shouldOverrideMinAspectRatioForCamera()
            if (r13 == 0) goto Lb3
        Lb0:
            r0.recomputeConfiguration()
        Lb3:
            com.android.server.wm.DisplayContent r12 = r12.mDisplayContent
            r12.updateOrientation(r2)
            return r3
        Lb9:
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L66
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayRotationCompatPolicy.onCameraClosed(java.lang.String):boolean");
    }

    @Override // com.android.server.wm.CameraStateMonitor.CameraCompatStateListener
    public final void onCameraOpened(ActivityRecord activityRecord) {
        this.mCameraTask = activityRecord.task;
        if (activityRecord.getWindowingMode() == 1) {
            AppCompatCameraOverrides appCompatCameraOverrides = activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides;
            if (appCompatCameraOverrides.mActivityRecord.info.isChangeEnabled(265456536L) || appCompatCameraOverrides.isCameraCompatSplitScreenAspectRatioAllowed() || appCompatCameraOverrides.shouldOverrideMinAspectRatioForCamera()) {
                activityRecord.recomputeConfiguration();
            }
            this.mDisplayContent.updateOrientation(false);
            return;
        }
        Task task = this.mCameraTask;
        if (task != null && task.getWindowingMode() == 6 && isTreatmentEnabledForActivity(false, activityRecord)) {
            PackageManager packageManager = this.mWmService.mContext.getPackageManager();
            try {
                showToast(R.string.ime_action_done, (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(activityRecord.packageName, 0)));
            } catch (PackageManager.NameNotFoundException unused) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[4]) {
                    ProtoLogImpl_54989576.e(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1534784331886673955L, 0, null, String.valueOf(activityRecord.packageName));
                }
            }
        }
    }

    @Override // com.android.server.wm.ActivityRefresher.Evaluator
    public final boolean shouldRefreshActivity(ActivityRecord activityRecord, Configuration configuration, Configuration configuration2) {
        boolean z = configuration.windowConfiguration.getDisplayRotation() != configuration2.windowConfiguration.getDisplayRotation();
        if (!isTreatmentEnabledForDisplay() || !isTreatmentEnabledForActivity(true, activityRecord)) {
            return false;
        }
        AppCompatCameraOverrides appCompatCameraOverrides = activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides;
        boolean isChangeEnabled = appCompatCameraOverrides.mActivityRecord.info.isChangeEnabled(264304459L);
        OptPropFactory.OptProp optProp = appCompatCameraOverrides.mCameraCompatAllowRefreshOptProp;
        if (!optProp.mCondition.getAsBoolean() || optProp.getValue() == 0 || isChangeEnabled) {
            return false;
        }
        return z || activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatCameraOverrides.isCameraCompatSplitScreenAspectRatioAllowed();
    }

    public void showToast(final int i) {
        UiThread.getHandler().post(new Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DisplayRotationCompatPolicy displayRotationCompatPolicy = DisplayRotationCompatPolicy.this;
                Toast.makeText(displayRotationCompatPolicy.mWmService.mContext, i, 1).show();
            }
        });
    }

    public void showToast(final int i, final String str) {
        UiThread.getHandler().post(new Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DisplayRotationCompatPolicy displayRotationCompatPolicy = DisplayRotationCompatPolicy.this;
                int i2 = i;
                String str2 = str;
                Context context = displayRotationCompatPolicy.mWmService.mContext;
                Toast.makeText(context, context.getString(i2, str2), 1).show();
            }
        });
    }
}
