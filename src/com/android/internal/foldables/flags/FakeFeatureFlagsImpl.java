package com.android.internal.foldables.flags;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl extends CustomFeatureFlags {
    private final FeatureFlags mDefaults;
    private final Map<String, Boolean> mFlagMap;

    public FakeFeatureFlagsImpl() {
        this(null);
    }

    public FakeFeatureFlagsImpl(FeatureFlags featureFlags) {
        super(null);
        this.mFlagMap = new HashMap();
        this.mDefaults = featureFlags;
        Iterator<String> it = getFlagNames().iterator();
        while (it.hasNext()) {
            this.mFlagMap.put(it.next(), null);
        }
    }

    @Override // com.android.internal.foldables.flags.CustomFeatureFlags
    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        Boolean bool = this.mFlagMap.get(str);
        if (bool != null) {
            return bool.booleanValue();
        }
        FeatureFlags featureFlags = this.mDefaults;
        if (featureFlags != null) {
            return predicate.test(featureFlags);
        }
        throw new IllegalArgumentException(str + " is not set");
    }

    public void setFlag(String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, Boolean.valueOf(z));
    }

    public void resetAll() {
        Iterator<Map.Entry<String, Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }
}
