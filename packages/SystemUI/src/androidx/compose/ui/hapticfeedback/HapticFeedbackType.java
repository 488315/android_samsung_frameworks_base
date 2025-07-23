package androidx.compose.ui.hapticfeedback;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HapticFeedbackType {
    public static final Companion Companion = new Companion(null);
    public final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getTextHandleMove-5zf0vsI, reason: not valid java name */
        public static int m571getTextHandleMove5zf0vsI() {
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
        if (i2 == Companion.m571getTextHandleMove5zf0vsI()) {
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
