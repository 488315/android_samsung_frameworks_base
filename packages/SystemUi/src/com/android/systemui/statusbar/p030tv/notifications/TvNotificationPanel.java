package com.android.systemui.statusbar.p030tv.notifications;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvNotificationPanel implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final String mNotificationHandlerPackage;

    public TvNotificationPanel(Context context, CommandQueue commandQueue) {
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mNotificationHandlerPackage = context.getResources().getString(R.string.dynamic_mode_notification_title_v2);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateCollapsePanels(int i, boolean z) {
        String str = this.mNotificationHandlerPackage;
        if (str.isEmpty() || (i & 4) != 0) {
            openInternalNotificationPanel("android.app.action.CLOSE_NOTIFICATION_HANDLER_PANEL");
            return;
        }
        Intent intent = new Intent("android.app.action.CLOSE_NOTIFICATION_HANDLER_PANEL");
        intent.setPackage(str);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandNotificationsPanel() {
        if (this.mNotificationHandlerPackage.isEmpty()) {
            openInternalNotificationPanel("android.app.action.OPEN_NOTIFICATION_HANDLER_PANEL");
        } else {
            startNotificationHandlerActivity(new Intent("android.app.action.OPEN_NOTIFICATION_HANDLER_PANEL"));
        }
    }

    public final void openInternalNotificationPanel(String str) {
        Context context = this.mContext;
        Intent intent = new Intent(context, (Class<?>) TvNotificationPanelActivity.class);
        intent.setFlags(603979776);
        intent.setAction(str);
        context.startActivityAsUser(intent, UserHandle.SYSTEM);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }

    public final void startNotificationHandlerActivity(Intent intent) {
        ActivityInfo activityInfo;
        intent.setPackage(this.mNotificationHandlerPackage);
        Context context = this.mContext;
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
        if (resolveActivity == null || (activityInfo = resolveActivity.activityInfo) == null) {
            Log.e("TvNotificationPanel", "Not launching notification handler activity: Could not resolve activityInfo for intent " + intent.getAction());
            return;
        }
        String str = activityInfo.permission;
        if (str != null && str.equals("android.permission.STATUS_BAR_SERVICE")) {
            intent.setFlags(603979776);
            context.startActivityAsUser(intent, UserHandle.CURRENT);
        } else {
            Log.e("TvNotificationPanel", "Not launching notification handler activity: Notification handler does not require the STATUS_BAR_SERVICE permission for intent " + intent.getAction());
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void togglePanel() {
        if (this.mNotificationHandlerPackage.isEmpty()) {
            openInternalNotificationPanel("android.app.action.TOGGLE_NOTIFICATION_HANDLER_PANEL");
        } else {
            startNotificationHandlerActivity(new Intent("android.app.action.TOGGLE_NOTIFICATION_HANDLER_PANEL"));
        }
    }
}
