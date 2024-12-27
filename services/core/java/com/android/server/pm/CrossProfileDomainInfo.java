package com.android.server.pm;

import android.content.pm.ResolveInfo;

import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CrossProfileDomainInfo {
    public int mHighestApprovalLevel;
    public final ResolveInfo mResolveInfo;
    public final int mTargetUserId;

    public CrossProfileDomainInfo(ResolveInfo resolveInfo, int i, int i2) {
        this.mResolveInfo = resolveInfo;
        this.mHighestApprovalLevel = i;
        this.mTargetUserId = i2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CrossProfileDomainInfo{resolveInfo=");
        sb.append(this.mResolveInfo);
        sb.append(", highestApprovalLevel=");
        sb.append(this.mHighestApprovalLevel);
        sb.append(", targetUserId= ");
        return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                sb, this.mTargetUserId, '}');
    }
}
