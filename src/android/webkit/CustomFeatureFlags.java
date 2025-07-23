package android.webkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DEPRECATE_START_SAFE_BROWSING, Flags.FLAG_FILE_SYSTEM_ACCESS, Flags.FLAG_MAINLINE_APIS, Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER, Flags.FLAG_UPDATE_SERVICE_V2, Flags.FLAG_USE_B_ENTRY_POINT, Flags.FLAG_USER_AGENT_REDUCTION, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.webkit.FeatureFlags
    public boolean deprecateStartSafeBrowsing() {
        return getValue(Flags.FLAG_DEPRECATE_START_SAFE_BROWSING, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateStartSafeBrowsing();
            }
        });
    }

    @Override // android.webkit.FeatureFlags
    public boolean fileSystemAccess() {
        return getValue(Flags.FLAG_FILE_SYSTEM_ACCESS, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fileSystemAccess();
            }
        });
    }

    @Override // android.webkit.FeatureFlags
    public boolean mainlineApis() {
        return getValue(Flags.FLAG_MAINLINE_APIS, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mainlineApis();
            }
        });
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceIpcWrapper() {
        return getValue(Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateServiceIpcWrapper();
            }
        });
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceV2() {
        return getValue(Flags.FLAG_UPDATE_SERVICE_V2, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateServiceV2();
            }
        });
    }

    @Override // android.webkit.FeatureFlags
    public boolean useBEntryPoint() {
        return getValue(Flags.FLAG_USE_B_ENTRY_POINT, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useBEntryPoint();
            }
        });
    }

    @Override // android.webkit.FeatureFlags
    public boolean userAgentReduction() {
        return getValue(Flags.FLAG_USER_AGENT_REDUCTION, new Predicate() { // from class: android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).userAgentReduction();
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
        return Arrays.asList(Flags.FLAG_DEPRECATE_START_SAFE_BROWSING, Flags.FLAG_FILE_SYSTEM_ACCESS, Flags.FLAG_MAINLINE_APIS, Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER, Flags.FLAG_UPDATE_SERVICE_V2, Flags.FLAG_USE_B_ENTRY_POINT, Flags.FLAG_USER_AGENT_REDUCTION);
    }
}
