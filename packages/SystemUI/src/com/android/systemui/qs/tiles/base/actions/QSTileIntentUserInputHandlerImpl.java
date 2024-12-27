package com.android.systemui.qs.tiles.base.actions;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSTileIntentUserInputHandlerImpl implements QSTileIntentUserInputHandler {
    public final ActivityStarter activityStarter;
    public final PackageManager packageManager;
    public final UserHandle userHandle;

    public QSTileIntentUserInputHandlerImpl(ActivityStarter activityStarter, PackageManager packageManager, UserHandle userHandle) {
        this.activityStarter = activityStarter;
        this.packageManager = packageManager;
        this.userHandle = userHandle;
    }

    public final void handle(Expandable expandable, PendingIntent pendingIntent, boolean z) {
        Object obj;
        if (pendingIntent.isActivity()) {
            this.activityStarter.postStartActivityDismissingKeyguard(pendingIntent, expandable != null ? expandable.activityTransitionController(32) : null);
            return;
        }
        if (z) {
            Intent addFlags = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(pendingIntent.getCreatorPackage()).addFlags(270532608);
            Iterator it = this.packageManager.queryIntentActivitiesAsUser(addFlags, PackageManager.ResolveInfoFlags.of(0L), this.userHandle.getIdentifier()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it.next();
                    if (((ResolveInfo) obj).activityInfo.exported) {
                        break;
                    }
                }
            }
            ResolveInfo resolveInfo = (ResolveInfo) obj;
            if (resolveInfo != null) {
                addFlags.setPackage(null);
                addFlags.setComponent(resolveInfo.activityInfo.getComponentName());
                QSTileIntentUserInputHandler.handle$default(this, expandable, addFlags);
            }
        }
    }
}
