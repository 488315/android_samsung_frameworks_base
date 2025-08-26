package com.android.wm.shell.bubbles.bar;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleEducationViewController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BubbleEducationViewController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                int i = BubbleEducationViewController.$r8$clinit;
                return Unit.INSTANCE;
            default:
                int i2 = BubbleEducationViewController.$r8$clinit;
                return new PhysicsAnimator.SpringConfig(1500.0f, 0.75f);
        }
    }
}
