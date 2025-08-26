package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

/* loaded from: classes2.dex */
public final /* synthetic */ class AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Float f = (Float) obj;
        switch (this.$r8$classId) {
            case 0:
                return Float.valueOf(MathUtils.lerp(((Ref$FloatRef) this.f$0).element, 0.0f, ((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(f.floatValue())));
            case 1:
                return Float.valueOf(MathUtils.lerp(((Ref$FloatRef) this.f$0).element, 0.0f, ((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(f.floatValue())));
            case 2:
                return Float.valueOf(MathUtils.lerp(((Ref$FloatRef) this.f$0).element, 1.0f, f.floatValue()));
            default:
                f.floatValue();
                return ((AodToLockscreenTransitionViewModel) this.f$0).isShadeExpanded ? Float.valueOf(1.0f) : f;
        }
    }
}
