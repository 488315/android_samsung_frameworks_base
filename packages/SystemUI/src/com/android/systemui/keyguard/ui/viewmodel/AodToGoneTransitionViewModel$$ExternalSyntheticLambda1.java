package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AodToGoneTransitionViewModel$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Ref$FloatRef f$0;
    public final /* synthetic */ ViewStateAccessor f$1;

    public /* synthetic */ AodToGoneTransitionViewModel$$ExternalSyntheticLambda1(Ref$FloatRef ref$FloatRef, ViewStateAccessor viewStateAccessor, int i) {
        this.$r8$classId = i;
        this.f$0 = ref$FloatRef;
        this.f$1 = viewStateAccessor;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.element = ((Number) this.f$1.alpha.invoke()).floatValue();
                break;
            default:
                this.f$0.element = ((Number) this.f$1.alpha.invoke()).floatValue();
                break;
        }
        return Unit.INSTANCE;
    }
}
