package androidx.compose.ui.hapticfeedback;

import androidx.compose.ui.hapticfeedback.HapticFeedbackType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlatformHapticFeedbackType {
    public static final int Confirm;
    public static final int ContextClick;
    public static final int GestureEnd;
    public static final int GestureThresholdActivate;
    public static final PlatformHapticFeedbackType INSTANCE = new PlatformHapticFeedbackType();
    public static final int KeyboardTap;
    public static final int Reject;
    public static final int SegmentFrequentTick;
    public static final int SegmentTick;
    public static final int TextHandleMove;
    public static final int ToggleOff;
    public static final int ToggleOn;
    public static final int VirtualKey;

    static {
        HapticFeedbackType.Companion companion = HapticFeedbackType.Companion;
        Confirm = 16;
        ContextClick = 6;
        GestureEnd = 13;
        GestureThresholdActivate = 23;
        KeyboardTap = 3;
        Reject = 17;
        SegmentFrequentTick = 27;
        SegmentTick = 26;
        TextHandleMove = 9;
        ToggleOff = 22;
        ToggleOn = 21;
        VirtualKey = 1;
    }

    private PlatformHapticFeedbackType() {
    }
}
