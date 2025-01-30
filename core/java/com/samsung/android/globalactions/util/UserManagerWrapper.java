package com.samsung.android.globalactions.util;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import java.util.List;

/* loaded from: classes5.dex */
public class UserManagerWrapper {
  private final UserManager mUserManager;

  public UserManagerWrapper(Context mContext) {
    this.mUserManager = (UserManager) mContext.getSystemService("user");
  }

  public boolean isAllowedSafeBoot() {
    return !this.mUserManager.hasUserRestriction(UserManager.DISALLOW_SAFE_BOOT);
  }

  public boolean isUserSwitcherEnabled() {
    return this.mUserManager.isUserSwitcherEnabled();
  }

  List<UserInfo> getUsers() {
    return this.mUserManager.getUsers();
  }
}
