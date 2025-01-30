package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes5.dex */
public class SemPersonaWrapper {
  Context mContext;
  SemPersonaManager mSemPersonaManager;

  public SemPersonaWrapper(Context context) {
    this.mContext = context;
    this.mSemPersonaManager = (SemPersonaManager) context.getSystemService("persona");
  }

  public boolean isValidVersion() {
    Bundle versionInfo = SemPersonaManager.getKnoxInfo();
    return versionInfo != null && "2.0".equals(versionInfo.getString("version"));
  }

  public boolean isDOProvisioningMode() {
    return SemPersonaManager.isDoEnabled(UserHandle.myUserId());
  }
}
