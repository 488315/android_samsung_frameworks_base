package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PrimaryBouncerToLockscreenTransitionViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PrimaryBouncerToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = this.$r8$classId;
        float floatValue = ((Float) obj).floatValue();
        switch (i) {
            case 0:
                BlurConfig blurConfig = ((PrimaryBouncerToLockscreenTransitionViewModel) this.f$0).blurConfig;
                return Float.valueOf(MathUtils.lerp(blurConfig.maxBlurRadiusPx, blurConfig.minBlurRadiusPx, floatValue));
            default:
                return Float.valueOf(MathUtils.lerp(((Ref$FloatRef) this.f$0).element, 1.0f, floatValue));
        }
    }
}
