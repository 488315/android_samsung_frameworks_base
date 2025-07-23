package androidx.compose.ui.hapticfeedback;

import android.view.View;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlatformHapticFeedback implements HapticFeedback {
    public final View view;

    public PlatformHapticFeedback(View view) {
        this.view = view;
    }

    @Override // androidx.compose.ui.hapticfeedback.HapticFeedback
    /* renamed from: performHapticFeedback-CdsT49E */
    public final void mo570performHapticFeedbackCdsT49E(int i) {
        HapticFeedbackType.Companion.getClass();
        PlatformHapticFeedbackType platformHapticFeedbackType = PlatformHapticFeedbackType.INSTANCE;
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.Confirm) {
            this.view.performHapticFeedback(16);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.ContextClick) {
            this.view.performHapticFeedback(6);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.GestureEnd) {
            this.view.performHapticFeedback(13);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.GestureThresholdActivate) {
            this.view.performHapticFeedback(23);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.KeyboardTap) {
            this.view.performHapticFeedback(3);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == 0) {
            this.view.performHapticFeedback(0);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.Reject) {
            this.view.performHapticFeedback(17);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.SegmentFrequentTick) {
            this.view.performHapticFeedback(27);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.SegmentTick) {
            this.view.performHapticFeedback(26);
            return;
        }
        if (i == HapticFeedbackType.Companion.m571getTextHandleMove5zf0vsI()) {
            this.view.performHapticFeedback(9);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.ToggleOff) {
            this.view.performHapticFeedback(22);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.ToggleOn) {
            this.view.performHapticFeedback(21);
            return;
        }
        platformHapticFeedbackType.getClass();
        if (i == PlatformHapticFeedbackType.VirtualKey) {
            this.view.performHapticFeedback(1);
        }
    }
}
