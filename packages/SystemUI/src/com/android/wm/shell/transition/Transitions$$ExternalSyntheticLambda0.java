package com.android.wm.shell.transition;

import com.android.wm.shell.transition.Transitions;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Transitions.ActiveTransition activeTransition = (Transitions.ActiveTransition) obj;
        switch (this.$r8$classId) {
            case 0:
                boolean z = Transitions.DEBUG_START_TRANSITION;
                break;
            default:
                boolean z2 = Transitions.DEBUG_START_TRANSITION;
                break;
        }
        return activeTransition.mToken;
    }
}
