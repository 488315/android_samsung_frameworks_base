package android.content.res;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FakeFeatureFlagsImpl extends CustomFeatureFlags {
    private final FeatureFlags mDefaults;
    private final Map<String, Boolean> mFlagMap;

    public FakeFeatureFlagsImpl() {
        this(null);
    }

    public FakeFeatureFlagsImpl(FeatureFlags defaults) {
        super(null);
        this.mFlagMap = new HashMap();
        this.mDefaults = defaults;
        for (String flagName : getFlagNames()) {
            this.mFlagMap.put(flagName, null);
        }
    }

    @Override // android.content.res.CustomFeatureFlags
    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        Boolean value = this.mFlagMap.get(flagName);
        if (value != null) {
            return value.booleanValue();
        }
        if (this.mDefaults != null) {
            return getter.test(this.mDefaults);
        }
        throw new IllegalArgumentException(flagName + " is not set");
    }

    public void setFlag(String flagName, boolean value) {
        if (!this.mFlagMap.containsKey(flagName)) {
            throw new IllegalArgumentException("no such flag " + flagName);
        }
        this.mFlagMap.put(flagName, Boolean.valueOf(value));
    }

    public void resetAll() {
        for (Map.Entry entry : this.mFlagMap.entrySet()) {
            entry.setValue(null);
        }
    }
}
