package android.flags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FeatureFlagsFake extends FeatureFlags {
    private final Map<BooleanFlagBase, Boolean> mFlagValues;
    private final Set<BooleanFlagBase> mReadFlags;

    public FeatureFlagsFake(IFeatureFlags iFeatureFlags) {
        super(iFeatureFlags);
        this.mFlagValues = new HashMap();
        this.mReadFlags = new HashSet();
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(BooleanFlag flag) {
        return requireFlag(flag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(FusedOffFlag flag) {
        return requireFlag(flag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(FusedOnFlag flag) {
        return requireFlag(flag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isCurrentlyEnabled(DynamicBooleanFlag flag) {
        return requireFlag(flag);
    }

    @Override // android.flags.FeatureFlags
    protected void syncInternal(Set<Flag<?>> dirtyFlags) {}

    /* JADX WARN: Multi-variable type inference failed */
    public void setFlagValue(BooleanFlagBase booleanFlagBase, boolean value) {
        if (!(booleanFlagBase instanceof DynamicBooleanFlag)
                && this.mReadFlags.contains(booleanFlagBase)) {
            throw new RuntimeException(
                    "You can not set the value of a flag after it has been read. Tried to set "
                            + booleanFlagBase
                            + " to "
                            + value
                            + " but it already "
                            + this.mFlagValues.get(booleanFlagBase));
        }
        this.mFlagValues.put(booleanFlagBase, Boolean.valueOf(value));
        if (booleanFlagBase instanceof DynamicBooleanFlag) {
            onFlagChange((DynamicFlag) booleanFlagBase);
        }
    }

    private boolean requireFlag(BooleanFlagBase flag) {
        if (!this.mFlagValues.containsKey(flag)) {
            throw new IllegalStateException(
                    "Tried to access "
                            + flag
                            + " in test but no overrided specified. You must call #setFlagValue for"
                            + " each flag read in a test.");
        }
        this.mReadFlags.add(flag);
        return this.mFlagValues.get(flag).booleanValue();
    }
}
