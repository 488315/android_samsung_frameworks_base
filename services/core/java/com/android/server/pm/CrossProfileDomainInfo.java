package com.android.server.pm;

import android.content.pm.ResolveInfo;

/* loaded from: classes3.dex */
public final class CrossProfileDomainInfo {
  public int mHighestApprovalLevel;
  public ResolveInfo mResolveInfo;
  public int mTargetUserId;

  public CrossProfileDomainInfo(ResolveInfo resolveInfo, int i, int i2) {
    this.mResolveInfo = resolveInfo;
    this.mHighestApprovalLevel = i;
    this.mTargetUserId = i2;
  }

  public String toString() {
    return "CrossProfileDomainInfo{resolveInfo="
        + this.mResolveInfo
        + ", highestApprovalLevel="
        + this.mHighestApprovalLevel
        + ", targetUserId= "
        + this.mTargetUserId
        + '}';
  }
}
