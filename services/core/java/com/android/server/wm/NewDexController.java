package com.android.server.wm;

import android.os.Handler;

import com.android.server.UiModeManagerService;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NewDexController implements IController {
    public final ActivityTaskManagerService mAtm;
    public H mH;
    public UiModeManagerService.LocalService mUiModeManagerInternal;
    public WindowManagerService mWm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {}

    public NewDexController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[NewDexController]");
        printWriter.println();
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0090 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldAbortStartActivity(android.content.pm.ActivityInfo r8) {
        /*
            r7 = this;
            android.content.pm.ApplicationInfo r0 = r8.applicationInfo
            java.lang.String r1 = r0.packageName
            r2 = 4
            r3 = 0
            if (r1 == 0) goto L8c
            java.util.Set r4 = com.android.server.wm.DexController.DEFAULT_ALLOW_HOME_SET
            com.android.server.wm.ActivityTaskManagerService r7 = r7.mAtm
            com.android.server.wm.DexController r5 = r7.mDexController
            r5.getClass()
            java.lang.String r1 = com.android.server.wm.DexController.toHashText(r1)
            boolean r1 = r4.contains(r1)
            if (r1 != 0) goto L8c
            android.os.Bundle r8 = r8.metaData
            if (r8 == 0) goto L27
            java.lang.String r1 = "com.samsung.android.dex.launchpolicy.allow_home_activity"
            boolean r8 = r8.getBoolean(r1, r3)
            if (r8 != 0) goto L8c
        L27:
            android.os.Bundle r8 = r0.metaData
            if (r8 == 0) goto L34
            java.lang.String r1 = "com.samsung.android.dex.launchpolicy.allow_home_app"
            boolean r8 = r8.getBoolean(r1, r3)
            if (r8 == 0) goto L34
            goto L8c
        L34:
            android.content.Intent r8 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.MAIN"
            r8.<init>(r1)
            java.lang.String r1 = "android.intent.category.HOME"
            android.content.Intent r8 = r8.addCategory(r1)
            java.lang.String r1 = r0.packageName
            android.content.Intent r8 = r8.setPackage(r1)
            android.content.Context r1 = r7.mContext
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            r4 = 65536(0x10000, double:3.2379E-319)
            android.content.pm.PackageManager$ResolveInfoFlags r6 = android.content.pm.PackageManager.ResolveInfoFlags.of(r4)
            int r0 = r0.uid
            int r0 = android.os.UserHandle.getUserId(r0)
            android.content.pm.ResolveInfo r8 = r1.resolveActivityAsUser(r8, r6, r0)
            if (r8 == 0) goto L8c
            com.android.server.wm.RootWindowContainer r0 = r7.mRootWindowContainer
            com.android.server.wm.ActivityTaskManagerService r0 = r0.mService
            android.content.Intent r0 = r0.getHomeIntent()
            android.content.Context r7 = r7.mContext
            android.content.pm.PackageManager r7 = r7.getPackageManager()
            android.content.pm.PackageManager$ResolveInfoFlags r1 = android.content.pm.PackageManager.ResolveInfoFlags.of(r4)
            android.content.pm.ResolveInfo r7 = r7.resolveActivity(r0, r1)
            if (r7 == 0) goto L8c
            android.content.pm.ComponentInfo r7 = r7.getComponentInfo()
            java.lang.String r7 = r7.packageName
            android.content.pm.ComponentInfo r8 = r8.getComponentInfo()
            java.lang.String r8 = r8.packageName
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L8c
            r7 = r2
            goto L8d
        L8c:
            r7 = r3
        L8d:
            r7 = r7 & r2
            if (r7 == 0) goto L91
            r3 = 1
        L91:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.NewDexController.shouldAbortStartActivity(android.content.pm.ActivityInfo):boolean");
    }
}
