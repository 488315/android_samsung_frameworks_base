package com.samsung.sesl.compose.ui.hapticfeedback;

import androidx.compose.ui.hapticfeedback.HapticFeedback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class HapticFeedbackWrapper implements HapticFeedback {
    public final /* synthetic */ HapticFeedback $$delegate_0;

    public HapticFeedbackWrapper(HapticFeedback hapticFeedback) {
        this.$$delegate_0 = hapticFeedback;
    }

    @Override // androidx.compose.ui.hapticfeedback.HapticFeedback
    /* renamed from: performHapticFeedback-CdsT49E */
    public void mo570performHapticFeedbackCdsT49E(int i) {
        this.$$delegate_0.mo570performHapticFeedbackCdsT49E(i);
    }
}
