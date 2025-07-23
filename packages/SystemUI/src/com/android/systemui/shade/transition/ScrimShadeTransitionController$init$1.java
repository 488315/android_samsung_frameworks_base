package com.android.systemui.shade.transition;

import com.android.systemui.shade.ShadeStateListener;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ScrimShadeTransitionController$init$1 implements ShadeStateListener, FunctionAdapter {
    public final /* synthetic */ ScrimShadeTransitionController $tmp0;

    public ScrimShadeTransitionController$init$1(ScrimShadeTransitionController scrimShadeTransitionController) {
        this.$tmp0 = scrimShadeTransitionController;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ShadeStateListener) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, ScrimShadeTransitionController.class, "onPanelStateChanged", "onPanelStateChanged(I)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.shade.ShadeStateListener
    public final void onPanelStateChanged$1(int i) {
        Integer valueOf = Integer.valueOf(i);
        ScrimShadeTransitionController scrimShadeTransitionController = this.$tmp0;
        scrimShadeTransitionController.currentPanelState = valueOf;
        scrimShadeTransitionController.onStateChanged();
    }
}
