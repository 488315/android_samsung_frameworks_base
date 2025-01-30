package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.app.StatusBarManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;

/* loaded from: classes.dex */
public class OpenCloseQuickPanel extends CornerActionType {
  public Context mContext;

  public static int getStringResId() {
    return R.string.activity_resolver_use_once;
  }

  public OpenCloseQuickPanel(Context context) {
    this.mContext = context;
  }

  public static OpenCloseQuickPanel createAction(Context context) {
    return new OpenCloseQuickPanel(context);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
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
