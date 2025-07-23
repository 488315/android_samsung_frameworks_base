package com.android.internal.hidden_from_bootclasspath.android.service.chooser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ANNOUNCE_SHORTCUTS_AND_SUGGESTED_APPS_LEGACY, Flags.FLAG_CHOOSER_ALBUM_TEXT, Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, Flags.FLAG_ENABLE_CHOOSER_RESULT, Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, Flags.FLAG_FIX_RESOLVER_MEMORY_LEAK, Flags.FLAG_NOTIFY_SINGLE_ITEM_CHANGE_ON_ICON_LOAD, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean announceShortcutsAndSuggestedAppsLegacy() {
        return getValue(Flags.FLAG_ANNOUNCE_SHORTCUTS_AND_SUGGESTED_APPS_LEGACY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).announceShortcutsAndSuggestedAppsLegacy();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean chooserAlbumText() {
        return getValue(Flags.FLAG_CHOOSER_ALBUM_TEXT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).chooserAlbumText();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean chooserPayloadToggling() {
        return getValue(Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).chooserPayloadToggling();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean enableChooserResult() {
        return getValue(Flags.FLAG_ENABLE_CHOOSER_RESULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableChooserResult();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean enableSharesheetMetadataExtra() {
        return getValue(Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSharesheetMetadataExtra();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean fixResolverMemoryLeak() {
        return getValue(Flags.FLAG_FIX_RESOLVER_MEMORY_LEAK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixResolverMemoryLeak();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.chooser.FeatureFlags
    public boolean notifySingleItemChangeOnIconLoad() {
        return getValue(Flags.FLAG_NOTIFY_SINGLE_ITEM_CHANGE_ON_ICON_LOAD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.chooser.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifySingleItemChangeOnIconLoad();
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
        return Arrays.asList(Flags.FLAG_ANNOUNCE_SHORTCUTS_AND_SUGGESTED_APPS_LEGACY, Flags.FLAG_CHOOSER_ALBUM_TEXT, Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, Flags.FLAG_ENABLE_CHOOSER_RESULT, Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, Flags.FLAG_FIX_RESOLVER_MEMORY_LEAK, Flags.FLAG_NOTIFY_SINGLE_ITEM_CHANGE_ON_ICON_LOAD);
    }
}
