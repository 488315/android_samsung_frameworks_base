package com.android.server.enterprise.adapter;

import android.os.Bundle;
import java.util.List;

/* loaded from: classes2.dex */
public interface IPersonaManagerAdapter extends IAdapterHandle {
  int getAttributes(int i);

  int getFocusedUserWithActivityManager();

  String getKnoxContainerVersionString();

  List getKnoxIds(boolean z);

  Bundle getKnoxInfo();

  boolean isAppSeparationUserId(int i);

  boolean isContainerCore(String str);

  boolean isContainerDesktop(String str);

  boolean isContainerService(int i);

  boolean isDarDualEncryptionEnabled(int i);

  boolean isDeviceOrProfileOwnerEnabled();

  boolean isDoEnabled(int i);

  boolean isExternalStorageEnabled(int i);

  boolean isKnoxActivated();

  boolean isKnoxId(int i);

  boolean isKnoxVersionSupported(int i);

  boolean isLegacyClId(int i);

  boolean isLegacyContainer(int i);

  boolean isOrganizationOwnedDeviceWithManagedProfile();

  boolean isPersonaEnabled(int i);

  boolean isPremiumContainer(int i);

  boolean isSamsungWorkspace(int i);

  boolean isSecureFolderId(int i);

  boolean isValidKnoxId(int i);

  void postPwdChangeNotificationForDeviceOwner(int i);
}
