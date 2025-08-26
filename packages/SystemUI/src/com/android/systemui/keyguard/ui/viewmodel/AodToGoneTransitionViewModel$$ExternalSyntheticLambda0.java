package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

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
    public final Object mo781invoke(Object obj) {
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
