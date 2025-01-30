package com.android.server.pm;

/* loaded from: classes3.dex */
final class SystemDeleteException extends Exception {
  final PackageManagerException mReason;

  public SystemDeleteException(PackageManagerException packageManagerException) {
    this.mReason = packageManagerException;
  }
}
