package com.android.media.projection.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_APP_CONTENT_SHARING, Flags.FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY, Flags.FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY_NO_VIRTUAL_DEVICE, Flags.FLAG_RECORDING_OVERLAY, Flags.FLAG_SHOW_STOP_DIALOG_POST_CALL_END, Flags.FLAG_STOP_MEDIA_PROJECTION_ON_CALL_END, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean appContentSharing() {
        return getValue(Flags.FLAG_APP_CONTENT_SHARING, new Predicate() { // from class: com.android.media.projection.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appContentSharing();
            }
        });
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean mediaProjectionConnectedDisplay() {
        return getValue(Flags.FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY, new Predicate() { // from class: com.android.media.projection.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mediaProjectionConnectedDisplay();
            }
        });
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean mediaProjectionConnectedDisplayNoVirtualDevice() {
        return getValue(Flags.FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY_NO_VIRTUAL_DEVICE, new Predicate() { // from class: com.android.media.projection.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mediaProjectionConnectedDisplayNoVirtualDevice();
            }
        });
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean recordingOverlay() {
        return getValue(Flags.FLAG_RECORDING_OVERLAY, new Predicate() { // from class: com.android.media.projection.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).recordingOverlay();
            }
        });
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean showStopDialogPostCallEnd() {
        return getValue(Flags.FLAG_SHOW_STOP_DIALOG_POST_CALL_END, new Predicate() { // from class: com.android.media.projection.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showStopDialogPostCallEnd();
            }
        });
    }

    @Override // com.android.media.projection.flags.FeatureFlags
    public boolean stopMediaProjectionOnCallEnd() {
        return getValue(Flags.FLAG_STOP_MEDIA_PROJECTION_ON_CALL_END, new Predicate() { // from class: com.android.media.projection.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stopMediaProjectionOnCallEnd();
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
        return Arrays.asList(Flags.FLAG_APP_CONTENT_SHARING, Flags.FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY, Flags.FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY_NO_VIRTUAL_DEVICE, Flags.FLAG_RECORDING_OVERLAY, Flags.FLAG_SHOW_STOP_DIALOG_POST_CALL_END, Flags.FLAG_STOP_MEDIA_PROJECTION_ON_CALL_END);
    }
}
