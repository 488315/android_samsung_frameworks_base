package android.flags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class FeatureFlagsFake extends FeatureFlags {
    private final Map<BooleanFlagBase, Boolean> mFlagValues;
    private final Set<BooleanFlagBase> mReadFlags;

    @Override // android.flags.FeatureFlags
    protected void syncInternal(Set<Flag<?>> set) {
    }

    public FeatureFlagsFake(IFeatureFlags iFeatureFlags) {
        super(iFeatureFlags);
        this.mFlagValues = new HashMap();
        this.mReadFlags = new HashSet();
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(BooleanFlag booleanFlag) {
        return requireFlag(booleanFlag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(FusedOffFlag fusedOffFlag) {
        return requireFlag(fusedOffFlag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(FusedOnFlag fusedOnFlag) {
        return requireFlag(fusedOnFlag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isCurrentlyEnabled(DynamicBooleanFlag dynamicBooleanFlag) {
        return requireFlag(dynamicBooleanFlag);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setFlagValue(BooleanFlagBase booleanFlagBase, boolean z) {
        boolean z2 = booleanFlagBase instanceof DynamicBooleanFlag;
        if (!z2 && this.mReadFlags.contains(booleanFlagBase)) {
            throw new RuntimeException("You can not set the value of a flag after it has been read. Tried to set " + booleanFlagBase + " to " + z + " but it already " + this.mFlagValues.get(booleanFlagBase));
        }
        this.mFlagValues.put(booleanFlagBase, Boolean.valueOf(z));
        if (z2) {
            onFlagChange((DynamicFlag) booleanFlagBase);
        }
    }

    private boolean requireFlag(BooleanFlagBase booleanFlagBase) {
        if (!this.mFlagValues.containsKey(booleanFlagBase)) {
            throw new IllegalStateException("Tried to access " + booleanFlagBase + " in test but no overrided specified. You must call #setFlagValue for each flag read in a test.");
        }
        this.mReadFlags.add(booleanFlagBase);
        return this.mFlagValues.get(booleanFlagBase).booleanValue();
    }
}
