package com.android.sdksandbox.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC, Flags.FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API, Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, Flags.FLAG_SDK_SANDBOX_DEX_VERIFIER, Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, Flags.FLAG_SELINUX_INPUT_SELECTOR, Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean firstAndLastSdkSandboxUidPublic() {
        return getValue(Flags.FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).firstAndLastSdkSandboxUidPublic();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean getEffectiveTargetSdkVersionApi() {
        return getValue(Flags.FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getEffectiveTargetSdkVersionApi();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxActivitySdkBasedContext() {
        return getValue(Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sandboxActivitySdkBasedContext();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxClientImportanceListener() {
        return getValue(Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sandboxClientImportanceListener();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxDexVerifier() {
        return getValue(Flags.FLAG_SDK_SANDBOX_DEX_VERIFIER, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sdkSandboxDexVerifier();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxInstrumentationInfo() {
        return getValue(Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sdkSandboxInstrumentationInfo();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxUidToAppUidApi() {
        return getValue(Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sdkSandboxUidToAppUidApi();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxInputSelector() {
        return getValue(Flags.FLAG_SELINUX_INPUT_SELECTOR, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selinuxInputSelector();
            }
        });
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxSdkSandboxAudit() {
        return getValue(Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT, new Predicate() { // from class: com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selinuxSdkSandboxAudit();
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
        return Arrays.asList(Flags.FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC, Flags.FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API, Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, Flags.FLAG_SDK_SANDBOX_DEX_VERIFIER, Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, Flags.FLAG_SELINUX_INPUT_SELECTOR, Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT);
    }
}
