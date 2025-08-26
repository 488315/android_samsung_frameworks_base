package com.android.wm.shell;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShellTaskOrganizer$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ShellTaskOrganizer.AnonymousClass1 f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ ShellTaskOrganizer$1$$ExternalSyntheticLambda0(ShellTaskOrganizer.AnonymousClass1 anonymousClass1, String str) {
        this.f$0 = anonymousClass1;
        this.f$1 = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003b  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws PackageManager.NameNotFoundException {
        PackageManager packageManager;
        ApplicationInfo applicationInfo;
        String string;
        switch (this.$r8$classId) {
            case 0:
                ShellTaskOrganizer.AnonymousClass1 anonymousClass1 = this.f$0;
                String str = this.f$1;
                ForcedResizableInfoActivityController forcedResizableInfoActivityController = anonymousClass1.this$0.mForcedResizableController;
                forcedResizableInfoActivityController.getClass();
                if (str != null && !"com.android.systemui".equals(str)) {
                    forcedResizableInfoActivityController.mPackagesShownInSession.contains(str);
                    forcedResizableInfoActivityController.mPackagesShownInSession.add(str);
                    break;
                }
                break;
            default:
                ShellTaskOrganizer.AnonymousClass1 anonymousClass12 = this.f$0;
                String str2 = this.f$1;
                ForcedResizableInfoActivityController forcedResizableInfoActivityController2 = anonymousClass12.this$0.mForcedResizableController;
                forcedResizableInfoActivityController2.getClass();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (jElapsedRealtime - forcedResizableInfoActivityController2.mLastShowingTime >= 5000) {
                    if (!TextUtils.isEmpty(str2)) {
                        try {
                            packageManager = forcedResizableInfoActivityController2.mContext.getPackageManager();
                            applicationInfo = packageManager.getApplicationInfo(str2, 0);
                        } catch (Exception unused) {
                        }
                        string = applicationInfo != null ? applicationInfo.loadLabel(packageManager).toString() : null;
                    }
                    Toast.makeText(forcedResizableInfoActivityController2.mContext, string != null ? forcedResizableInfoActivityController2.mContext.getString(com.android.systemui.R.string.multi_window_dismiss_split_with_app_name, string) : forcedResizableInfoActivityController2.mContext.getString(com.android.systemui.R.string.multi_window_dismiss_split), 1).show();
                    forcedResizableInfoActivityController2.mLastShowingTime = jElapsedRealtime;
                    if (CoreRune.MW_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced(SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, "Switch to MW-incompatible app");
                        break;
                    }
                }
                break;
        }
    }

    public /* synthetic */ ShellTaskOrganizer$1$$ExternalSyntheticLambda0(ShellTaskOrganizer.AnonymousClass1 anonymousClass1, String str, int i, int i2) {
        this.f$0 = anonymousClass1;
        this.f$1 = str;
    }
}
