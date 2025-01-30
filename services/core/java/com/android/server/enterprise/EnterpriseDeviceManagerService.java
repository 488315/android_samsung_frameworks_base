package com.android.server.enterprise;

import android.content.ComponentName;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class EnterpriseDeviceManagerService extends IEnterpriseDeviceManager.Stub {
  public abstract String getActiveAdminPackageName(int i);

  public List getActiveAdmins(int i) {
    return null;
  }

  public List getActiveAdminsInfo(int i) {
    return null;
  }

  public boolean getAdminRemovable(ContextInfo contextInfo, String str) {
    return false;
  }

  public abstract boolean getFirmwareUpgrade();

  public abstract int getOrganizationOwnedProfileUserId();

  public abstract int getPseudoAdminUid();

  public boolean isAdminRemovable(ComponentName componentName) {
    return false;
  }

  public boolean isMdmAdminPresent() {
    return false;
  }

  public boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) {
    return false;
  }

  public abstract void startDeferredServicesIfNeeded();

  public static EnterpriseDeviceManagerService getInstance() {
    return (EnterpriseDeviceManagerService) EnterpriseService.getEdmsInstance();
  }
}
