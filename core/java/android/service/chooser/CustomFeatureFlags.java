package android.service.chooser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CHOOSER_ALBUM_TEXT, Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, Flags.FLAG_ENABLE_CHOOSER_RESULT, Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, Flags.FLAG_FIX_RESOLVER_MEMORY_LEAK, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean chooserAlbumText() {
        return getValue(Flags.FLAG_CHOOSER_ALBUM_TEXT, new Predicate() { // from class: android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).chooserAlbumText();
            }
        });
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean chooserPayloadToggling() {
        return getValue(Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, new Predicate() { // from class: android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).chooserPayloadToggling();
            }
        });
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableChooserResult() {
        return getValue(Flags.FLAG_ENABLE_CHOOSER_RESULT, new Predicate() { // from class: android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableChooserResult();
            }
        });
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableSharesheetMetadataExtra() {
        return getValue(Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, new Predicate() { // from class: android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSharesheetMetadataExtra();
            }
        });
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean fixResolverMemoryLeak() {
        return getValue(Flags.FLAG_FIX_RESOLVER_MEMORY_LEAK, new Predicate() { // from class: android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixResolverMemoryLeak();
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
        return Arrays.asList(Flags.FLAG_CHOOSER_ALBUM_TEXT, Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, Flags.FLAG_ENABLE_CHOOSER_RESULT, Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, Flags.FLAG_FIX_RESOLVER_MEMORY_LEAK);
    }
}
