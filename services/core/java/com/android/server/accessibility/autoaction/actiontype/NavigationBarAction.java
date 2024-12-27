package com.android.server.accessibility.autoaction.actiontype;

import android.app.Instrumentation;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

import com.samsung.android.knoxguard.service.utils.Constants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NavigationBarAction extends CornerActionType {
    public Context mContext;
    public String mType;
    public int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.accessibility.autoaction.actiontype.NavigationBarAction$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(100L);
                new Instrumentation().sendKeyDownUpSync(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        StatusBarManagerInternal statusBarManagerInternal;
        String str = this.mType;
        str.getClass();
        switch (str) {
            case "accessibility_button":
                if (Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "accessibility_button_mode",
                                0,
                                this.mUserId)
                        != 1) {
                    AccessibilityManager.getInstance(this.mContext)
                            .notifyAccessibilityButtonClicked(i);
                    return;
                } else {
                    this.mContext.sendBroadcast(
                            ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                    "com.android.systemui.accessibility.floatingmenu.SHOW",
                                    Constants.SYSTEMUI_PACKAGE_NAME));
                    return;
                }
            case "back":
                new Thread(new AnonymousClass1()).start();
                return;
            case "home":
                this.mContext.sendBroadcast(new Intent("SYSTEM_ACTION_HOME"));
                return;
            case "recents":
                if (AccessibilityUtils.makeToastForCoverScreen(this.mContext, (String) null)
                        || (statusBarManagerInternal =
                                        (StatusBarManagerInternal)
                                                LocalServices.getService(
                                                        StatusBarManagerInternal.class))
                                == null) {
                    return;
                }
                ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                        .toggleRecentAppsToType(
                                StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
                return;
            default:
                throw new IllegalArgumentException("Wrong NavigationBar Action Type");
        }
    }
}
