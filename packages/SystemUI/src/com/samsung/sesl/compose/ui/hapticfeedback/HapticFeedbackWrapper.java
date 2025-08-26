package com.samsung.sesl.compose.ui.hapticfeedback;

import androidx.compose.ui.hapticfeedback.HapticFeedback;

/* loaded from: classes4.dex */
public class HapticFeedbackWrapper implements HapticFeedback {
    public final /* synthetic */ HapticFeedback $$delegate_0;

    public HapticFeedbackWrapper(HapticFeedback hapticFeedback) {
        this.$$delegate_0 = hapticFeedback;
    }

    @Override // androidx.compose.ui.hapticfeedback.HapticFeedback
    /* renamed from: performHapticFeedback-CdsT49E */
    public void mo572performHapticFeedbackCdsT49E(int i) {
        this.$$delegate_0.mo572performHapticFeedbackCdsT49E(i);
    }
}
