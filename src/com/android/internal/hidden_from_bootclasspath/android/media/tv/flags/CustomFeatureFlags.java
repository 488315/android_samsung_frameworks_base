package com.android.internal.hidden_from_bootclasspath.android.media.tv.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_APPLY_PICTURE_PROFILES, Flags.FLAG_BROADCAST_VISIBILITY_TYPES, Flags.FLAG_ENABLE_AD_SERVICE_FW, Flags.FLAG_ENABLE_LE_AUDIO_BROADCAST_UI, Flags.FLAG_ENABLE_LE_AUDIO_UNICAST_UI, Flags.FLAG_HDMI_CONTROL_COLLECT_PHYSICAL_ADDRESS, Flags.FLAG_HDMI_CONTROL_ENHANCED_BEHAVIOR, Flags.FLAG_KIDS_MODE_TVDB_SHARING, Flags.FLAG_MEDIA_QUALITY_FW, Flags.FLAG_MEDIA_QUALITY_FW_BUGFIX, Flags.FLAG_MEDIACAS_UPDATE_CLIENT_PROFILE_PRIORITY, Flags.FLAG_SET_RESOURCE_HOLDER_RETAIN, Flags.FLAG_TIAF_V_APIS, Flags.FLAG_TIF_EXTENSION_STANDARDIZATION, Flags.FLAG_TIF_EXTENSION_STANDARDIZATION_BUGFIX, Flags.FLAG_TIF_UNBIND_INACTIVE_TIS, Flags.FLAG_TUNER_W_APIS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean applyPictureProfiles() {
        return getValue(Flags.FLAG_APPLY_PICTURE_PROFILES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).applyPictureProfiles();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean broadcastVisibilityTypes() {
        return getValue(Flags.FLAG_BROADCAST_VISIBILITY_TYPES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).broadcastVisibilityTypes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean enableAdServiceFw() {
        return getValue(Flags.FLAG_ENABLE_AD_SERVICE_FW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAdServiceFw();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean enableLeAudioBroadcastUi() {
        return getValue(Flags.FLAG_ENABLE_LE_AUDIO_BROADCAST_UI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLeAudioBroadcastUi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean enableLeAudioUnicastUi() {
        return getValue(Flags.FLAG_ENABLE_LE_AUDIO_UNICAST_UI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableLeAudioUnicastUi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean hdmiControlCollectPhysicalAddress() {
        return getValue(Flags.FLAG_HDMI_CONTROL_COLLECT_PHYSICAL_ADDRESS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hdmiControlCollectPhysicalAddress();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean hdmiControlEnhancedBehavior() {
        return getValue(Flags.FLAG_HDMI_CONTROL_ENHANCED_BEHAVIOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hdmiControlEnhancedBehavior();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean kidsModeTvdbSharing() {
        return getValue(Flags.FLAG_KIDS_MODE_TVDB_SHARING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).kidsModeTvdbSharing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean mediaQualityFw() {
        return getValue(Flags.FLAG_MEDIA_QUALITY_FW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mediaQualityFw();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean mediaQualityFwBugfix() {
        return getValue(Flags.FLAG_MEDIA_QUALITY_FW_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mediaQualityFwBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean mediacasUpdateClientProfilePriority() {
        return getValue(Flags.FLAG_MEDIACAS_UPDATE_CLIENT_PROFILE_PRIORITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mediacasUpdateClientProfilePriority();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean setResourceHolderRetain() {
        return getValue(Flags.FLAG_SET_RESOURCE_HOLDER_RETAIN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setResourceHolderRetain();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean tiafVApis() {
        return getValue(Flags.FLAG_TIAF_V_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tiafVApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean tifExtensionStandardization() {
        return getValue(Flags.FLAG_TIF_EXTENSION_STANDARDIZATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tifExtensionStandardization();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean tifExtensionStandardizationBugfix() {
        return getValue(Flags.FLAG_TIF_EXTENSION_STANDARDIZATION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tifExtensionStandardizationBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean tifUnbindInactiveTis() {
        return getValue(Flags.FLAG_TIF_UNBIND_INACTIVE_TIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tifUnbindInactiveTis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean tunerWApis() {
        return getValue(Flags.FLAG_TUNER_W_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tunerWApis();
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
        return Arrays.asList(Flags.FLAG_APPLY_PICTURE_PROFILES, Flags.FLAG_BROADCAST_VISIBILITY_TYPES, Flags.FLAG_ENABLE_AD_SERVICE_FW, Flags.FLAG_ENABLE_LE_AUDIO_BROADCAST_UI, Flags.FLAG_ENABLE_LE_AUDIO_UNICAST_UI, Flags.FLAG_HDMI_CONTROL_COLLECT_PHYSICAL_ADDRESS, Flags.FLAG_HDMI_CONTROL_ENHANCED_BEHAVIOR, Flags.FLAG_KIDS_MODE_TVDB_SHARING, Flags.FLAG_MEDIA_QUALITY_FW, Flags.FLAG_MEDIA_QUALITY_FW_BUGFIX, Flags.FLAG_MEDIACAS_UPDATE_CLIENT_PROFILE_PRIORITY, Flags.FLAG_SET_RESOURCE_HOLDER_RETAIN, Flags.FLAG_TIAF_V_APIS, Flags.FLAG_TIF_EXTENSION_STANDARDIZATION, Flags.FLAG_TIF_EXTENSION_STANDARDIZATION_BUGFIX, Flags.FLAG_TIF_UNBIND_INACTIVE_TIS, Flags.FLAG_TUNER_W_APIS);
    }
}
