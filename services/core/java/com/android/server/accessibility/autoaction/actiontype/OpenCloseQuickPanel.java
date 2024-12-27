package com.android.server.accessibility.autoaction.actiontype;

import android.app.StatusBarManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OpenCloseQuickPanel extends CornerActionType {
    public Context mContext;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        try {
            IStatusBarService asInterface =
                    IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            if (asInterface != null) {
                int naturalBarTypeByDisplayId =
                        StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i);
                if (asInterface.getQuickSettingPanelExpandStateToType(naturalBarTypeByDisplayId)) {
                    asInterface.collapsePanelsToType(naturalBarTypeByDisplayId);
                } else {
                    asInterface.expandSettingsPanelToType((String) null, naturalBarTypeByDisplayId);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
