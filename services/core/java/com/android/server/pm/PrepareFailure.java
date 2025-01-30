package com.android.server.pm;

import android.util.ExceptionUtils;

/* loaded from: classes3.dex */
final class PrepareFailure extends PackageManagerException {
  public String mConflictingPackage;
  public String mConflictingPermission;

  public PrepareFailure(int i) {
    super(i, "Failed to prepare for install.");
  }

  public PrepareFailure(int i, String str) {
    super(i, str);
  }

  public static PrepareFailure ofInternalError(String str, int i) {
    return new PrepareFailure(-110, str, i);
  }

  public PrepareFailure(int i, String str, int i2) {
    super(i, str, i2);
  }

  public PrepareFailure(String str, Exception exc) {
    super(((PackageManagerException) exc).error, ExceptionUtils.getCompleteMessage(str, exc));
  }

  public PrepareFailure conflictsWithExistingPermission(String str, String str2) {
    this.mConflictingPermission = str;
    this.mConflictingPackage = str2;
    return this;
  }
}
