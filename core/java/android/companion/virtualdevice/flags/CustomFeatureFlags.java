package android.companion.virtualdevice.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CAMERA_DEVICE_AWARENESS, Flags.FLAG_DEVICE_AWARE_DRM, Flags.FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION, Flags.FLAG_INTENT_INTERCEPTION_ACTION_MATCHING_FIX, Flags.FLAG_METRICS_COLLECTION, Flags.FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY, Flags.FLAG_VIRTUAL_DISPLAY_MULTI_WINDOW_MODE_SUPPORT, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean cameraDeviceAwareness() {
        return getValue(Flags.FLAG_CAMERA_DEVICE_AWARENESS, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraDeviceAwareness();
            }
        });
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareDrm() {
        return getValue(Flags.FLAG_DEVICE_AWARE_DRM, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwareDrm();
            }
        });
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareRecordAudioPermission() {
        return getValue(Flags.FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAwareRecordAudioPermission();
            }
        });
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean intentInterceptionActionMatchingFix() {
        return getValue(Flags.FLAG_INTENT_INTERCEPTION_ACTION_MATCHING_FIX, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).intentInterceptionActionMatchingFix();
            }
        });
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean metricsCollection() {
        return getValue(Flags.FLAG_METRICS_COLLECTION, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).metricsCollection();
            }
        });
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualCameraServiceDiscovery() {
        return getValue(Flags.FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualCameraServiceDiscovery();
            }
        });
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualDisplayMultiWindowModeSupport() {
        return getValue(Flags.FLAG_VIRTUAL_DISPLAY_MULTI_WINDOW_MODE_SUPPORT, new Predicate() { // from class: android.companion.virtualdevice.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualDisplayMultiWindowModeSupport();
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
        return Arrays.asList(Flags.FLAG_CAMERA_DEVICE_AWARENESS, Flags.FLAG_DEVICE_AWARE_DRM, Flags.FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION, Flags.FLAG_INTENT_INTERCEPTION_ACTION_MATCHING_FIX, Flags.FLAG_METRICS_COLLECTION, Flags.FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY, Flags.FLAG_VIRTUAL_DISPLAY_MULTI_WINDOW_MODE_SUPPORT);
    }
}
