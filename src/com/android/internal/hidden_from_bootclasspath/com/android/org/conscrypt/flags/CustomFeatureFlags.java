package com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags;

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
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.FeatureFlags
    public boolean certificateTransparencyCheckservertrustedApi() {
        return getValue(Flags.FLAG_CERTIFICATE_TRANSPARENCY_CHECKSERVERTRUSTED_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).certificateTransparencyCheckservertrustedApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.FeatureFlags
    public boolean spake2plusApi() {
        return getValue(Flags.FLAG_SPAKE2PLUS_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).spake2plusApi();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_CERTIFICATE_TRANSPARENCY_CHECKSERVERTRUSTED_API, Flags.FLAG_SPAKE2PLUS_API);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
