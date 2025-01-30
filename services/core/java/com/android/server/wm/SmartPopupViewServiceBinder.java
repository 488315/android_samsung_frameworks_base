package com.android.server.wm;

import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.multiwindow.SmartPopupViewUtil;
import com.samsung.android.rune.CoreRune;
import java.util.List;

/* loaded from: classes3.dex */
public class SmartPopupViewServiceBinder extends FreeformContainerServiceBinder {
  public SmartPopupViewServiceBinder(ActivityTaskManagerService activityTaskManagerService) {
    super(activityTaskManagerService);
    setServiceComponent(
        "com.android.systemui", "com.android.wm.shell.freeform.SmartPopupViewService");
  }

  @Override // com.android.server.wm.FreeformContainerServiceBinder
  public boolean okToBind() {
    return CoreRune.MW_FREEFORM_SMART_POPUP_VIEW
        && super.okToBind()
        && hasSmartPopupViewPackage()
        && isSmartPopupViewOn();
  }

  @Override // com.android.server.wm.FreeformContainerServiceBinder
  public boolean okToUnbind() {
    if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
      return (!super.okToUnbind() && hasSmartPopupViewPackage() && isSmartPopupViewOn())
          ? false
          : true;
    }
    return false;
  }

  public final boolean hasSmartPopupViewPackage() {
    List packageStrListFromDB = SmartPopupViewUtil.getPackageStrListFromDB(this.mAtm.mContext);
    return (packageStrListFromDB == null || packageStrListFromDB.isEmpty()) ? false : true;
  }

  public final boolean isSmartPopupViewOn() {
    try {
      int currentUserId = this.mAtm.getCurrentUserId();
      boolean z =
          Settings.Secure.getIntForUser(
                  this.mAtm.mContext.getContentResolver(), "notification_bubbles", currentUserId)
              == 2;
      Slog.i(this.TAG, "isSmartPopupViewOn=" + z + "  userId=" + currentUserId);
      return z;
    } catch (Settings.SettingNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }
}
