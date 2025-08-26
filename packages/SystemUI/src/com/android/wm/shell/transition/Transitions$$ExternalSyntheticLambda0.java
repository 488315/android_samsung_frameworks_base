package com.android.wm.shell.transition;

import com.android.wm.shell.transition.Transitions;
import java.util.function.Function;

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
