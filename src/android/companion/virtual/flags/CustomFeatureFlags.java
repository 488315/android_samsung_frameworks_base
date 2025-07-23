package android.companion.virtual.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CROSS_DEVICE_CLIPBOARD, Flags.FLAG_DYNAMIC_POLICY, Flags.FLAG_PERSISTENT_DEVICE_ID_API, Flags.FLAG_VDM_CUSTOM_HOME, Flags.FLAG_VDM_CUSTOM_IME, Flags.FLAG_VDM_PUBLIC_APIS, Flags.FLAG_VIRTUAL_CAMERA, Flags.FLAG_VIRTUAL_STYLUS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean crossDeviceClipboard() {
        return getValue(Flags.FLAG_CROSS_DEVICE_CLIPBOARD, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).crossDeviceClipboard();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean dynamicPolicy() {
        return getValue(Flags.FLAG_DYNAMIC_POLICY, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dynamicPolicy();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean persistentDeviceIdApi() {
        return getValue(Flags.FLAG_PERSISTENT_DEVICE_ID_API, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).persistentDeviceIdApi();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmCustomHome() {
        return getValue(Flags.FLAG_VDM_CUSTOM_HOME, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vdmCustomHome();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmCustomIme() {
        return getValue(Flags.FLAG_VDM_CUSTOM_IME, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vdmCustomIme();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmPublicApis() {
        return getValue(Flags.FLAG_VDM_PUBLIC_APIS, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vdmPublicApis();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean virtualCamera() {
        return getValue(Flags.FLAG_VIRTUAL_CAMERA, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualCamera();
            }
        });
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean virtualStylus() {
        return getValue(Flags.FLAG_VIRTUAL_STYLUS, new Predicate() { // from class: android.companion.virtual.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).virtualStylus();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_CROSS_DEVICE_CLIPBOARD, Flags.FLAG_DYNAMIC_POLICY, Flags.FLAG_PERSISTENT_DEVICE_ID_API, Flags.FLAG_VDM_CUSTOM_HOME, Flags.FLAG_VDM_CUSTOM_IME, Flags.FLAG_VDM_PUBLIC_APIS, Flags.FLAG_VIRTUAL_CAMERA, Flags.FLAG_VIRTUAL_STYLUS);
    }
}
