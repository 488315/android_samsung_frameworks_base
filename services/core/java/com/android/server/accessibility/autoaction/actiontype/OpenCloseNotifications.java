package com.android.server.accessibility.autoaction.actiontype;

import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OpenCloseNotifications extends CornerActionType {
    public Context mContext;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            this.mContext.sendBroadcast(
                    new Intent(
                            "com.samsung.android.app.aodservice.sublauncher.REQUEST_FOCUS_NOTIFICATION"));
            return;
        }
        try {
            IStatusBarService asInterface =
                    IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            if (asInterface != null) {
                int naturalBarTypeByDisplayId =
                        StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i);
                if (asInterface.getPanelExpandStateToType(naturalBarTypeByDisplayId)) {
                    asInterface.collapsePanelsToType(naturalBarTypeByDisplayId);
                } else {
                    asInterface.expandNotificationsPanelToType(naturalBarTypeByDisplayId);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
