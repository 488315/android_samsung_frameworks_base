package com.samsung.sesl.compose.ui.hapticfeedback;

import android.view.View;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;

/* loaded from: classes4.dex */
public final class SeslHapticFeedback extends HapticFeedbackWrapper {
    public final HapticFeedback base;
    public final SeslHapticFeedbackConstants seslHapticFeedbackConstants;
    public final View view;

    public SeslHapticFeedback(HapticFeedback hapticFeedback, View view, SeslHapticFeedbackConstants seslHapticFeedbackConstants) {
        super(hapticFeedback);
        this.base = hapticFeedback;
        this.view = view;
        this.seslHapticFeedbackConstants = seslHapticFeedbackConstants;
    }

    @Override // com.samsung.sesl.compose.ui.hapticfeedback.HapticFeedbackWrapper, androidx.compose.ui.hapticfeedback.HapticFeedback
    /* renamed from: performHapticFeedback-CdsT49E */
    public final void mo572performHapticFeedbackCdsT49E(int i) {
        SeslHapticFeedbackConstants seslHapticFeedbackConstants = this.seslHapticFeedbackConstants;
        if (((Boolean) seslHapticFeedbackConstants.canHapticFeedback.mo781invoke(this.view)).booleanValue()) {
            SeslHapticFeedbackType.INSTANCE.getClass();
            int i2 = SeslHapticFeedbackType.EffectSwitch;
            HapticFeedbackType.Companion companion = HapticFeedbackType.Companion;
            Integer numValueOf = i == i2 ? Integer.valueOf(seslHapticFeedbackConstants.effectSwitch) : null;
            if (numValueOf != null) {
                this.view.performHapticFeedback(numValueOf.intValue());
            } else {
                this.base.mo572performHapticFeedbackCdsT49E(i);
            }
        }
    }
}
