package android.app.contextualsearch.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CONTEXTUAL_SEARCH_MEDIA_PROJECTION, Flags.FLAG_CONTEXTUAL_SEARCH_PREVENT_SELF_CAPTURE, Flags.FLAG_ENABLE_SERVICE, Flags.FLAG_ENABLE_TOKEN_REFRESH, Flags.FLAG_INCLUDE_AUDIO_PLAYING_STATUS, Flags.FLAG_MULTI_WINDOW_SCREEN_CONTEXT, Flags.FLAG_REPORT_SECURE_SURFACES_IN_ASSIST_STRUCTURE, Flags.FLAG_SELF_INVOCATION, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean contextualSearchMediaProjection() {
        return getValue(Flags.FLAG_CONTEXTUAL_SEARCH_MEDIA_PROJECTION, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).contextualSearchMediaProjection();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean contextualSearchPreventSelfCapture() {
        return getValue(Flags.FLAG_CONTEXTUAL_SEARCH_PREVENT_SELF_CAPTURE, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).contextualSearchPreventSelfCapture();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableService() {
        return getValue(Flags.FLAG_ENABLE_SERVICE, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableService();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableTokenRefresh() {
        return getValue(Flags.FLAG_ENABLE_TOKEN_REFRESH, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTokenRefresh();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean includeAudioPlayingStatus() {
        return getValue(Flags.FLAG_INCLUDE_AUDIO_PLAYING_STATUS, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).includeAudioPlayingStatus();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean multiWindowScreenContext() {
        return getValue(Flags.FLAG_MULTI_WINDOW_SCREEN_CONTEXT, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiWindowScreenContext();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean reportSecureSurfacesInAssistStructure() {
        return getValue(Flags.FLAG_REPORT_SECURE_SURFACES_IN_ASSIST_STRUCTURE, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reportSecureSurfacesInAssistStructure();
            }
        });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean selfInvocation() {
        return getValue(Flags.FLAG_SELF_INVOCATION, new Predicate() { // from class: android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).selfInvocation();
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
        return Arrays.asList(Flags.FLAG_CONTEXTUAL_SEARCH_MEDIA_PROJECTION, Flags.FLAG_CONTEXTUAL_SEARCH_PREVENT_SELF_CAPTURE, Flags.FLAG_ENABLE_SERVICE, Flags.FLAG_ENABLE_TOKEN_REFRESH, Flags.FLAG_INCLUDE_AUDIO_PLAYING_STATUS, Flags.FLAG_MULTI_WINDOW_SCREEN_CONTEXT, Flags.FLAG_REPORT_SECURE_SURFACES_IN_ASSIST_STRUCTURE, Flags.FLAG_SELF_INVOCATION);
    }
}
