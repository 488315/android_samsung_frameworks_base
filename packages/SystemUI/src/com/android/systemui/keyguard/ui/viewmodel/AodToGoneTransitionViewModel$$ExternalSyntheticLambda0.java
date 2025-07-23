package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AodToGoneTransitionViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Ref$FloatRef f$0;

    public /* synthetic */ AodToGoneTransitionViewModel$$ExternalSyntheticLambda0(Ref$FloatRef ref$FloatRef, int i) {
        this.$r8$classId = i;
        this.f$0 = ref$FloatRef;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Float f = (Float) obj;
        switch (this.$r8$classId) {
            case 0:
                f.floatValue();
                return Float.valueOf(this.f$0.element);
            default:
                return Float.valueOf(MathUtils.lerp(this.f$0.element, 0.0f, f.floatValue()));
        }
    }
}
