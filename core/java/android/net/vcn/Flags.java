package android.net.vcn;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALLOW_DISABLE_IPSEC_LOSS_DETECTOR = "android.net.vcn.allow_disable_ipsec_loss_detector";
    public static final String FLAG_ENFORCE_MAIN_USER = "android.net.vcn.enforce_main_user";
    public static final String FLAG_EVALUATE_IPSEC_LOSS_ON_LP_NC_CHANGE = "android.net.vcn.evaluate_ipsec_loss_on_lp_nc_change";
    public static final String FLAG_HANDLE_SEQ_NUM_LEAP = "android.net.vcn.handle_seq_num_leap";
    public static final String FLAG_NETWORK_METRIC_MONITOR = "android.net.vcn.network_metric_monitor";
    public static final String FLAG_SAFE_MODE_CONFIG = "android.net.vcn.safe_mode_config";
    public static final String FLAG_SAFE_MODE_TIMEOUT_CONFIG = "android.net.vcn.safe_mode_timeout_config";
    public static final String FLAG_VALIDATE_NETWORK_ON_IPSEC_LOSS = "android.net.vcn.validate_network_on_ipsec_loss";

    public static boolean allowDisableIpsecLossDetector() {
        return FEATURE_FLAGS.allowDisableIpsecLossDetector();
    }

    public static boolean enforceMainUser() {
        return FEATURE_FLAGS.enforceMainUser();
    }

    public static boolean evaluateIpsecLossOnLpNcChange() {
        return FEATURE_FLAGS.evaluateIpsecLossOnLpNcChange();
    }

    public static boolean handleSeqNumLeap() {
        return FEATURE_FLAGS.handleSeqNumLeap();
    }

    public static boolean networkMetricMonitor() {
        return FEATURE_FLAGS.networkMetricMonitor();
    }

    public static boolean safeModeConfig() {
        return FEATURE_FLAGS.safeModeConfig();
    }

    public static boolean safeModeTimeoutConfig() {
        return FEATURE_FLAGS.safeModeTimeoutConfig();
    }

    public static boolean validateNetworkOnIpsecLoss() {
        return FEATURE_FLAGS.validateNetworkOnIpsecLoss();
    }
}
