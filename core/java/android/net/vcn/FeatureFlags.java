package android.net.vcn;

public interface FeatureFlags {
    boolean allowDisableIpsecLossDetector();

    boolean enforceMainUser();

    boolean evaluateIpsecLossOnLpNcChange();

    boolean handleSeqNumLeap();

    boolean networkMetricMonitor();

    boolean safeModeConfig();

    boolean safeModeTimeoutConfig();

    boolean validateNetworkOnIpsecLoss();
}
