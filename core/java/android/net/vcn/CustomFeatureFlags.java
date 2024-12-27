package android.net.vcn;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ALLOW_DISABLE_IPSEC_LOSS_DETECTOR,
                            Flags.FLAG_ENFORCE_MAIN_USER,
                            Flags.FLAG_EVALUATE_IPSEC_LOSS_ON_LP_NC_CHANGE,
                            Flags.FLAG_HANDLE_SEQ_NUM_LEAP,
                            Flags.FLAG_NETWORK_METRIC_MONITOR,
                            Flags.FLAG_SAFE_MODE_CONFIG,
                            Flags.FLAG_SAFE_MODE_TIMEOUT_CONFIG,
                            Flags.FLAG_VALIDATE_NETWORK_ON_IPSEC_LOSS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean allowDisableIpsecLossDetector() {
        return getValue(
                Flags.FLAG_ALLOW_DISABLE_IPSEC_LOSS_DETECTOR,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowDisableIpsecLossDetector();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean enforceMainUser() {
        return getValue(
                Flags.FLAG_ENFORCE_MAIN_USER,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enforceMainUser();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean evaluateIpsecLossOnLpNcChange() {
        return getValue(
                Flags.FLAG_EVALUATE_IPSEC_LOSS_ON_LP_NC_CHANGE,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).evaluateIpsecLossOnLpNcChange();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean handleSeqNumLeap() {
        return getValue(
                Flags.FLAG_HANDLE_SEQ_NUM_LEAP,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).handleSeqNumLeap();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean networkMetricMonitor() {
        return getValue(
                Flags.FLAG_NETWORK_METRIC_MONITOR,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).networkMetricMonitor();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean safeModeConfig() {
        return getValue(
                Flags.FLAG_SAFE_MODE_CONFIG,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).safeModeConfig();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean safeModeTimeoutConfig() {
        return getValue(
                Flags.FLAG_SAFE_MODE_TIMEOUT_CONFIG,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).safeModeTimeoutConfig();
                    }
                });
    }

    @Override // android.net.vcn.FeatureFlags
    public boolean validateNetworkOnIpsecLoss() {
        return getValue(
                Flags.FLAG_VALIDATE_NETWORK_ON_IPSEC_LOSS,
                new Predicate() { // from class:
                                  // android.net.vcn.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).validateNetworkOnIpsecLoss();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ALLOW_DISABLE_IPSEC_LOSS_DETECTOR,
                Flags.FLAG_ENFORCE_MAIN_USER,
                Flags.FLAG_EVALUATE_IPSEC_LOSS_ON_LP_NC_CHANGE,
                Flags.FLAG_HANDLE_SEQ_NUM_LEAP,
                Flags.FLAG_NETWORK_METRIC_MONITOR,
                Flags.FLAG_SAFE_MODE_CONFIG,
                Flags.FLAG_SAFE_MODE_TIMEOUT_CONFIG,
                Flags.FLAG_VALIDATE_NETWORK_ON_IPSEC_LOSS);
    }
}
