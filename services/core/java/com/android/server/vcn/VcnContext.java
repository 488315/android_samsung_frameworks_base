package com.android.server.vcn;

import android.content.Context;
import android.net.IpSecTransformState;
import android.net.vcn.FeatureFlags;
import android.net.vcn.FeatureFlagsImpl;
import android.os.Looper;

import java.util.Objects;

public final class VcnContext {
    public final Context mContext;
    public final FeatureFlags mFeatureFlags;
    public final boolean mIsInTestMode;
    public final Looper mLooper;
    public final VcnNetworkProvider mVcnNetworkProvider;

    public VcnContext(
            Context context, Looper looper, VcnNetworkProvider vcnNetworkProvider, boolean z) {
        Objects.requireNonNull(context, "Missing context");
        this.mContext = context;
        Objects.requireNonNull(looper, "Missing looper");
        this.mLooper = looper;
        Objects.requireNonNull(vcnNetworkProvider, "Missing networkProvider");
        this.mVcnNetworkProvider = vcnNetworkProvider;
        this.mIsInTestMode = z;
        this.mFeatureFlags = new FeatureFlagsImpl();
    }

    public static boolean isFlagIpSecTransformStateEnabled() {
        try {
            new IpSecTransformState.Builder();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public final void ensureRunningOnLooperThread() {
        if (this.mLooper.getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Not running on VcnMgmtSvc thread");
        }
    }

    public final boolean isFlagNetworkMetricMonitorEnabled() {
        return this.mFeatureFlags.networkMetricMonitor();
    }
}
