package android.net.vcn;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.net.vcn.FeatureFlags
    public boolean allowDisableIpsecLossDetector() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean enforceMainUser() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean evaluateIpsecLossOnLpNcChange() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean handleSeqNumLeap() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean networkMetricMonitor() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean safeModeConfig() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean safeModeTimeoutConfig() {
        return true;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean validateNetworkOnIpsecLoss() {
        return true;
    }
}
