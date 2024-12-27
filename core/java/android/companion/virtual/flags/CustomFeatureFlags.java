package android.companion.virtual.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_CONSISTENT_DISPLAY_FLAGS,
                            Flags.FLAG_CROSS_DEVICE_CLIPBOARD,
                            Flags.FLAG_DYNAMIC_POLICY,
                            Flags.FLAG_ENABLE_NATIVE_VDM,
                            Flags.FLAG_EXPRESS_METRICS,
                            Flags.FLAG_IMPULSE_VELOCITY_STRATEGY_FOR_TOUCH_NAVIGATION,
                            Flags.FLAG_INTERACTIVE_SCREEN_MIRROR,
                            Flags.FLAG_INTERCEPT_INTENTS_BEFORE_APPLYING_POLICY,
                            Flags.FLAG_PERSISTENT_DEVICE_ID_API,
                            Flags.FLAG_STREAM_CAMERA,
                            Flags.FLAG_STREAM_PERMISSIONS,
                            Flags.FLAG_VDM_CUSTOM_HOME,
                            Flags.FLAG_VDM_CUSTOM_IME,
                            Flags.FLAG_VDM_PUBLIC_APIS,
                            Flags.FLAG_VIRTUAL_CAMERA,
                            Flags.FLAG_VIRTUAL_STYLUS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean consistentDisplayFlags() {
        return getValue(
                Flags.FLAG_CONSISTENT_DISPLAY_FLAGS,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).consistentDisplayFlags();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean crossDeviceClipboard() {
        return getValue(
                Flags.FLAG_CROSS_DEVICE_CLIPBOARD,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).crossDeviceClipboard();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean dynamicPolicy() {
        return getValue(
                Flags.FLAG_DYNAMIC_POLICY,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dynamicPolicy();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean enableNativeVdm() {
        return getValue(
                Flags.FLAG_ENABLE_NATIVE_VDM,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableNativeVdm();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean expressMetrics() {
        return getValue(
                Flags.FLAG_EXPRESS_METRICS,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).expressMetrics();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean impulseVelocityStrategyForTouchNavigation() {
        return getValue(
                Flags.FLAG_IMPULSE_VELOCITY_STRATEGY_FOR_TOUCH_NAVIGATION,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).impulseVelocityStrategyForTouchNavigation();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean interactiveScreenMirror() {
        return getValue(
                Flags.FLAG_INTERACTIVE_SCREEN_MIRROR,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).interactiveScreenMirror();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean interceptIntentsBeforeApplyingPolicy() {
        return getValue(
                Flags.FLAG_INTERCEPT_INTENTS_BEFORE_APPLYING_POLICY,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).interceptIntentsBeforeApplyingPolicy();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean persistentDeviceIdApi() {
        return getValue(
                Flags.FLAG_PERSISTENT_DEVICE_ID_API,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).persistentDeviceIdApi();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean streamCamera() {
        return getValue(
                Flags.FLAG_STREAM_CAMERA,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).streamCamera();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean streamPermissions() {
        return getValue(
                Flags.FLAG_STREAM_PERMISSIONS,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).streamPermissions();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmCustomHome() {
        return getValue(
                Flags.FLAG_VDM_CUSTOM_HOME,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).vdmCustomHome();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmCustomIme() {
        return getValue(
                Flags.FLAG_VDM_CUSTOM_IME,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).vdmCustomIme();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmPublicApis() {
        return getValue(
                Flags.FLAG_VDM_PUBLIC_APIS,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).vdmPublicApis();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean virtualCamera() {
        return getValue(
                Flags.FLAG_VIRTUAL_CAMERA,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).virtualCamera();
                    }
                });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean virtualStylus() {
        return getValue(
                Flags.FLAG_VIRTUAL_STYLUS,
                new Predicate() { // from class:
                    // android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).virtualStylus();
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
                Flags.FLAG_CONSISTENT_DISPLAY_FLAGS,
                Flags.FLAG_CROSS_DEVICE_CLIPBOARD,
                Flags.FLAG_DYNAMIC_POLICY,
                Flags.FLAG_ENABLE_NATIVE_VDM,
                Flags.FLAG_EXPRESS_METRICS,
                Flags.FLAG_IMPULSE_VELOCITY_STRATEGY_FOR_TOUCH_NAVIGATION,
                Flags.FLAG_INTERACTIVE_SCREEN_MIRROR,
                Flags.FLAG_INTERCEPT_INTENTS_BEFORE_APPLYING_POLICY,
                Flags.FLAG_PERSISTENT_DEVICE_ID_API,
                Flags.FLAG_STREAM_CAMERA,
                Flags.FLAG_STREAM_PERMISSIONS,
                Flags.FLAG_VDM_CUSTOM_HOME,
                Flags.FLAG_VDM_CUSTOM_IME,
                Flags.FLAG_VDM_PUBLIC_APIS,
                Flags.FLAG_VIRTUAL_CAMERA,
                Flags.FLAG_VIRTUAL_STYLUS);
    }
}
