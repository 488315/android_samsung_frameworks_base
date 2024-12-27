package com.android.server.pm;

import android.content.pm.ResolveInfo;

import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;

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
