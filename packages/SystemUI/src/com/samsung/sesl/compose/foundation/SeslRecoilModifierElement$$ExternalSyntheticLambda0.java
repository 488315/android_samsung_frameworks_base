package com.samsung.sesl.compose.foundation;

import com.samsung.sesl.compose.foundation.SeslFeedbackAlpha;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SeslRecoilModifierElement$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeslRecoilModifierElement f$0;

    public /* synthetic */ SeslRecoilModifierElement$$ExternalSyntheticLambda0(SeslRecoilModifierElement seslRecoilModifierElement, int i) {
        this.$r8$classId = i;
        this.f$0 = seslRecoilModifierElement;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SeslRecoilModifierElement seslRecoilModifierElement = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = SeslRecoilModifierElement.$r8$clinit;
                SeslFeedbackAlpha.Companion companion = SeslFeedbackAlpha.Companion;
                SeslFeedbackAlpha seslFeedbackAlpha = seslRecoilModifierElement.feedbackAlpha;
                companion.getClass();
                return SeslFeedbackAlpha.Companion.takeOrDefault(seslFeedbackAlpha);
            default:
                int i2 = SeslRecoilModifierElement.$r8$clinit;
                SeslFeedbackAlpha.Companion companion2 = SeslFeedbackAlpha.Companion;
                SeslFeedbackAlpha seslFeedbackAlpha2 = seslRecoilModifierElement.feedbackAlpha;
                companion2.getClass();
                return SeslFeedbackAlpha.Companion.takeOrDefault(seslFeedbackAlpha2);
        }
    }
}
