package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

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
    public final Object mo781invoke(Object obj) {
        int i = this.$r8$classId;
        float fFloatValue = ((Float) obj).floatValue();
        switch (i) {
            case 0:
                BlurConfig blurConfig = ((PrimaryBouncerToLockscreenTransitionViewModel) this.f$0).blurConfig;
                return Float.valueOf(MathUtils.lerp(blurConfig.maxBlurRadiusPx, blurConfig.minBlurRadiusPx, fFloatValue));
            default:
                return Float.valueOf(MathUtils.lerp(((Ref$FloatRef) this.f$0).element, 1.0f, fFloatValue));
        }
    }
}
