package com.android.wm.shell.common;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.UserHandle;
import android.view.Display;
import android.window.WindowContainerTransaction;
import com.android.internal.app.ResolverActivity;

/* loaded from: classes3.dex */
public final class HomeIntentProvider {
    public final Context context;

    public HomeIntentProvider(Context context) {
        this.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addLaunchHomePendingIntent(WindowContainerTransaction windowContainerTransaction, int i, Integer num) throws Resources.NotFoundException {
        UserHandle userHandleOf = UserHandle.of(num != null ? num.intValue() : ActivityManager.getCurrentUser());
        Intent intent = new Intent("android.intent.action.MAIN");
        if (i != 0) {
            intent.addCategory("android.intent.category.SECONDARY_HOME");
            Display display = ((DisplayManager) this.context.getSystemService(DisplayManager.class)).getDisplay(i);
            if (display != null && DisplayManager.isExternalDesktopDisplay(display)) {
                userHandleOf.getClass();
                int identifier = userHandleOf.getIdentifier();
                boolean z = this.context.getResources().getBoolean(17892001);
                String string = this.context.getResources().getString(R.string.face_error_lockout_permanent);
                if (z) {
                    intent.setPackage(string);
                } else {
                    Intent intent2 = new Intent("android.intent.action.MAIN");
                    intent2.addCategory("android.intent.category.HOME");
                    ResolveInfo resolveInfoResolveActivityAsUser = this.context.getPackageManager().resolveActivityAsUser(intent2, 0, identifier);
                    ActivityInfo activityInfo = resolveInfoResolveActivityAsUser != null ? resolveInfoResolveActivityAsUser.activityInfo : null;
                    if (ResolverActivity.class.getName().equals(activityInfo != null ? activityInfo.name : null)) {
                        activityInfo = null;
                    }
                    String str = activityInfo != null ? activityInfo.applicationInfo.packageName : null;
                    if (str != null) {
                        intent.setPackage(str);
                        if (this.context.getPackageManager().resolveActivityAsUser(intent, 0, identifier) == null) {
                            intent.setPackage(string);
                        }
                    }
                }
            }
        } else {
            intent.addCategory("android.intent.category.HOME");
        }
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(1);
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(3);
        activityOptionsMakeBasic.setLaunchDisplayId(i);
        windowContainerTransaction.sendPendingIntent(PendingIntent.getActivityAsUser(this.context, 0, intent, 67108864, null, userHandleOf), intent, activityOptionsMakeBasic.toBundle());
    }
}
