package androidx.compose.ui.hapticfeedback;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class HapticFeedbackType {
    public static final Companion Companion = new Companion(null);
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getTextHandleMove-5zf0vsI, reason: not valid java name */
        public static int m573getTextHandleMove5zf0vsI() {
            PlatformHapticFeedbackType.INSTANCE.getClass();
            return PlatformHapticFeedbackType.TextHandleMove;
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof HapticFeedbackType) {
            return this.value == ((HapticFeedbackType) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        Companion.getClass();
        PlatformHapticFeedbackType platformHapticFeedbackType = PlatformHapticFeedbackType.INSTANCE;
        platformHapticFeedbackType.getClass();
        int i = PlatformHapticFeedbackType.Confirm;
        int i2 = this.value;
        if (i2 == i) {
            return "Confirm";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.ContextClick) {
            return "ContextClick";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.GestureEnd) {
            return "GestureEnd";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.GestureThresholdActivate) {
            return "GestureThresholdActivate";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.KeyboardTap) {
            return "KeyboardTap";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == 0) {
            return "LongPress";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.Reject) {
            return "Reject";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.SegmentFrequentTick) {
            return "SegmentFrequentTick";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.SegmentTick) {
            return "SegmentTick";
        }
        if (i2 == Companion.m573getTextHandleMove5zf0vsI()) {
            return "TextHandleMove";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.ToggleOff) {
            return "ToggleOff";
        }
        platformHapticFeedbackType.getClass();
        if (i2 == PlatformHapticFeedbackType.ToggleOn) {
            return "ToggleOn";
        }
        platformHapticFeedbackType.getClass();
        return i2 == PlatformHapticFeedbackType.VirtualKey ? "VirtualKey" : "Invalid";
    }
}
