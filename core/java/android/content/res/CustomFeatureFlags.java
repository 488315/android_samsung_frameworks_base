package android.content.res;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO,
                            Flags.FLAG_DEFAULT_LOCALE,
                            Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC,
                            Flags.FLAG_MANIFEST_FLAGGING,
                            Flags.FLAG_NINE_PATCH_FRRO,
                            Flags.FLAG_REGISTER_RESOURCE_PATHS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.content.res.FeatureFlags
    public boolean assetFileDescriptorFrro() {
        return getValue(
                Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO,
                new Predicate() { // from class:
                    // android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).assetFileDescriptorFrro();
                    }
                });
    }

    @Override // android.content.res.FeatureFlags
    public boolean defaultLocale() {
        return getValue(
                Flags.FLAG_DEFAULT_LOCALE,
                new Predicate() { // from class:
                    // android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).defaultLocale();
                    }
                });
    }

    @Override // android.content.res.FeatureFlags
    public boolean fontScaleConverterPublic() {
        return getValue(
                Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC,
                new Predicate() { // from class:
                    // android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fontScaleConverterPublic();
                    }
                });
    }

    @Override // android.content.res.FeatureFlags
    public boolean manifestFlagging() {
        return getValue(
                Flags.FLAG_MANIFEST_FLAGGING,
                new Predicate() { // from class:
                    // android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).manifestFlagging();
                    }
                });
    }

    @Override // android.content.res.FeatureFlags
    public boolean ninePatchFrro() {
        return getValue(
                Flags.FLAG_NINE_PATCH_FRRO,
                new Predicate() { // from class:
                    // android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ninePatchFrro();
                    }
                });
    }

    @Override // android.content.res.FeatureFlags
    public boolean registerResourcePaths() {
        return getValue(
                Flags.FLAG_REGISTER_RESOURCE_PATHS,
                new Predicate() { // from class:
                    // android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).registerResourcePaths();
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
        return Arrays.asList(
                Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO,
                Flags.FLAG_DEFAULT_LOCALE,
                Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC,
                Flags.FLAG_MANIFEST_FLAGGING,
                Flags.FLAG_NINE_PATCH_FRRO,
                Flags.FLAG_REGISTER_RESOURCE_PATHS);
    }
}
