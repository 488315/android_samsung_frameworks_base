package com.android.systemui.qs.tiles.base.domain.actions;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Iterator;

/* loaded from: classes2.dex */
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
        Object next;
        if (pendingIntent.isActivity()) {
            this.activityStarter.postStartActivityDismissingKeyguard(pendingIntent, expandable != null ? expandable.activityTransitionController(32) : null);
            return;
        }
        if (z) {
            Intent intentAddFlags = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(pendingIntent.getCreatorPackage()).addFlags(270532608);
            Iterator it = this.packageManager.queryIntentActivitiesAsUser(intentAddFlags, PackageManager.ResolveInfoFlags.of(0L), this.userHandle.getIdentifier()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (((ResolveInfo) next).activityInfo.exported) {
                        break;
                    }
                }
            }
            ResolveInfo resolveInfo = (ResolveInfo) next;
            if (resolveInfo != null) {
                intentAddFlags.setPackage(null);
                intentAddFlags.setComponent(resolveInfo.activityInfo.getComponentName());
                QSTileIntentUserInputHandler.handle$default(this, expandable, intentAddFlags);
            }
        }
    }
}
