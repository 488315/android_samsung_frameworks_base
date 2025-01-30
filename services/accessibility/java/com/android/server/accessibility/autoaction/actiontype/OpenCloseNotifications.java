package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.statusbar.IStatusBarService;

/* loaded from: classes.dex */
public class OpenCloseNotifications extends CornerActionType {
  public Context mContext;

  public static int getStringResId() {
    return R.string.activity_resolver_use_always;
  }

  public OpenCloseNotifications(Context context) {
    this.mContext = context;
  }

  public static OpenCloseNotifications createAction(Context context) {
    return new OpenCloseNotifications(context);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
      this.mContext.sendBroadcast(
          new Intent("com.samsung.android.app.aodservice.sublauncher.REQUEST_FOCUS_NOTIFICATION"));
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
