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
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALWAYS_FALSE, Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO, Flags.FLAG_DEFAULT_LOCALE, Flags.FLAG_DIMENSION_FRRO, Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC, Flags.FLAG_HANDLE_ALL_CONFIG_CHANGES, Flags.FLAG_LAYOUT_READWRITE_FLAGS, Flags.FLAG_MANIFEST_FLAGGING, Flags.FLAG_NINE_PATCH_FRRO, Flags.FLAG_REGISTER_RESOURCE_PATHS, Flags.FLAG_RESOURCES_MINOR_VERSION_SUPPORT, Flags.FLAG_RRO_CONSTRAINTS, Flags.FLAG_RRO_CONTROL_FOR_ANDROID_NO_OVERLAYABLE, Flags.FLAG_SELF_TARGETING_ANDROID_RESOURCE_FRRO, Flags.FLAG_SYSTEM_CONTEXT_HANDLE_APP_INFO_CHANGED, Flags.FLAG_USE_NEW_ACONFIG_STORAGE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.content.res.FeatureFlags
    public boolean alwaysFalse() {
        return getValue(Flags.FLAG_ALWAYS_FALSE, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).alwaysFalse();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean assetFileDescriptorFrro() {
        return getValue(Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).assetFileDescriptorFrro();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean defaultLocale() {
        return getValue(Flags.FLAG_DEFAULT_LOCALE, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).defaultLocale();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean dimensionFrro() {
        return getValue(Flags.FLAG_DIMENSION_FRRO, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dimensionFrro();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean fontScaleConverterPublic() {
        return getValue(Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fontScaleConverterPublic();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean handleAllConfigChanges() {
        return getValue(Flags.FLAG_HANDLE_ALL_CONFIG_CHANGES, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handleAllConfigChanges();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean layoutReadwriteFlags() {
        return getValue(Flags.FLAG_LAYOUT_READWRITE_FLAGS, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).layoutReadwriteFlags();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean manifestFlagging() {
        return getValue(Flags.FLAG_MANIFEST_FLAGGING, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).manifestFlagging();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean ninePatchFrro() {
        return getValue(Flags.FLAG_NINE_PATCH_FRRO, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ninePatchFrro();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean registerResourcePaths() {
        return getValue(Flags.FLAG_REGISTER_RESOURCE_PATHS, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).registerResourcePaths();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean resourcesMinorVersionSupport() {
        return getValue(Flags.FLAG_RESOURCES_MINOR_VERSION_SUPPORT, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).resourcesMinorVersionSupport();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean rroConstraints() {
        return getValue(Flags.FLAG_RRO_CONSTRAINTS, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rroConstraints();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean rroControlForAndroidNoOverlayable() {
        return getValue(Flags.FLAG_RRO_CONTROL_FOR_ANDROID_NO_OVERLAYABLE, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rroControlForAndroidNoOverlayable();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean selfTargetingAndroidResourceFrro() {
        return getValue(Flags.FLAG_SELF_TARGETING_ANDROID_RESOURCE_FRRO, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selfTargetingAndroidResourceFrro();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean systemContextHandleAppInfoChanged() {
        return getValue(Flags.FLAG_SYSTEM_CONTEXT_HANDLE_APP_INFO_CHANGED, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemContextHandleAppInfoChanged();
            }
        });
    }

    @Override // android.content.res.FeatureFlags
    public boolean useNewAconfigStorage() {
        return getValue(Flags.FLAG_USE_NEW_ACONFIG_STORAGE, new Predicate() { // from class: android.content.res.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useNewAconfigStorage();
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
        return Arrays.asList(Flags.FLAG_ALWAYS_FALSE, Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO, Flags.FLAG_DEFAULT_LOCALE, Flags.FLAG_DIMENSION_FRRO, Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC, Flags.FLAG_HANDLE_ALL_CONFIG_CHANGES, Flags.FLAG_LAYOUT_READWRITE_FLAGS, Flags.FLAG_MANIFEST_FLAGGING, Flags.FLAG_NINE_PATCH_FRRO, Flags.FLAG_REGISTER_RESOURCE_PATHS, Flags.FLAG_RESOURCES_MINOR_VERSION_SUPPORT, Flags.FLAG_RRO_CONSTRAINTS, Flags.FLAG_RRO_CONTROL_FOR_ANDROID_NO_OVERLAYABLE, Flags.FLAG_SELF_TARGETING_ANDROID_RESOURCE_FRRO, Flags.FLAG_SYSTEM_CONTEXT_HANDLE_APP_INFO_CHANGED, Flags.FLAG_USE_NEW_ACONFIG_STORAGE);
    }
}
