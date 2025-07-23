package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.Shape;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SeslRecoilNode$$ExternalSyntheticLambda3 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SeslRecoilNode$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(((SeslRecoilNode) this.f$0).enabled);
            case 1:
                return ((SeslRecoilNode) this.f$0).feedbackMargin;
            default:
                return (Shape) this.f$0;
        }
    }
}
