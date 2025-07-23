package com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags;

import android.os.Build;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(""));
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry(Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, 35), Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxActivitySdkBasedContext() {
        return getValue(Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sandboxActivitySdkBasedContext();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxClientImportanceListener() {
        return getValue(Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sandboxClientImportanceListener();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxInstrumentationInfo() {
        return getValue(Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sdkSandboxInstrumentationInfo();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxUidToAppUidApi() {
        return getValue(Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sdkSandboxUidToAppUidApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxInputSelector() {
        return getValue(Flags.FLAG_SELINUX_INPUT_SELECTOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selinuxInputSelector();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxSdkSandboxAudit() {
        return getValue(Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selinuxSdkSandboxAudit();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, Flags.FLAG_SELINUX_INPUT_SELECTOR, Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
