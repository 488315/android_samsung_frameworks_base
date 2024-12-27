package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCompatCameraPolicy {
    public final ActivityRefresher mActivityRefresher;
    public final CameraCompatFreeformPolicy mCameraCompatFreeformPolicy;
    final CameraStateMonitor mCameraStateMonitor;
    public final DisplayRotationCompatPolicy mDisplayRotationCompatPolicy;

    /* JADX WARN: Removed duplicated region for block: B:18:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AppCompatCameraPolicy(
            com.android.server.wm.WindowManagerService r7,
            com.android.server.wm.DisplayContent r8) {
        /*
            r6 = this;
            r6.<init>()
            com.android.server.wm.AppCompatConfiguration r0 = r7.mAppCompatConfiguration
            com.android.server.wm.SynchedDeviceConfig r0 = r0.mDeviceConfig
            java.lang.String r1 = "enable_compat_camera_treatment"
            boolean r0 = r0.isBuildTimeFlagEnabled(r1)
            boolean r1 = com.android.window.flags.Flags.cameraCompatForFreeform()
            if (r1 == 0) goto L2b
            android.content.Context r1 = r7.mContext
            boolean r2 = com.android.server.wm.DesktopModeLaunchParamsModifier.ENFORCE_DEVICE_RESTRICTIONS
            boolean r2 = com.android.window.flags.Flags.enableDesktopWindowingMode()
            if (r2 == 0) goto L2b
            boolean r2 = com.android.server.wm.DesktopModeLaunchParamsModifier.enforceDeviceRestrictions()
            if (r2 == 0) goto L29
            boolean r1 = com.android.server.wm.DesktopModeLaunchParamsModifier.isDesktopModeSupported(r1)
            if (r1 == 0) goto L2b
        L29:
            r1 = 1
            goto L2c
        L2b:
            r1 = 0
        L2c:
            r2 = 0
            if (r0 != 0) goto L3b
            if (r1 == 0) goto L32
            goto L3b
        L32:
            r6.mDisplayRotationCompatPolicy = r2
            r6.mCameraCompatFreeformPolicy = r2
            r6.mCameraStateMonitor = r2
            r6.mActivityRefresher = r2
            goto L61
        L3b:
            com.android.server.wm.CameraStateMonitor r3 = new com.android.server.wm.CameraStateMonitor
            com.android.server.wm.WindowManagerService$H r4 = r7.mH
            r3.<init>(r8, r4)
            r6.mCameraStateMonitor = r3
            com.android.server.wm.ActivityRefresher r4 = new com.android.server.wm.ActivityRefresher
            com.android.server.wm.WindowManagerService$H r5 = r7.mH
            r4.<init>(r7, r5)
            r6.mActivityRefresher = r4
            if (r0 == 0) goto L55
            com.android.server.wm.DisplayRotationCompatPolicy r7 = new com.android.server.wm.DisplayRotationCompatPolicy
            r7.<init>(r8, r3, r4)
            goto L56
        L55:
            r7 = r2
        L56:
            r6.mDisplayRotationCompatPolicy = r7
            if (r1 == 0) goto L5f
            com.android.server.wm.CameraCompatFreeformPolicy r2 = new com.android.server.wm.CameraCompatFreeformPolicy
            r2.<init>(r8, r3, r4)
        L5f:
            r6.mCameraCompatFreeformPolicy = r2
        L61:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.AppCompatCameraPolicy.<init>(com.android.server.wm.WindowManagerService,"
                    + " com.android.server.wm.DisplayContent):void");
    }
}
