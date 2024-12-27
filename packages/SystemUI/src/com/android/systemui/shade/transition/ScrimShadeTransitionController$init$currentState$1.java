package com.android.systemui.shade.transition;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScrimShadeTransitionController$init$currentState$1 implements ShadeExpansionListener, FunctionAdapter {
    public final /* synthetic */ ScrimShadeTransitionController $tmp0;

    public ScrimShadeTransitionController$init$currentState$1(ScrimShadeTransitionController scrimShadeTransitionController) {
        this.$tmp0 = scrimShadeTransitionController;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ShadeExpansionListener) && (obj instanceof FunctionAdapter)) {
            return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, ScrimShadeTransitionController.class, "onPanelExpansionChanged", "onPanelExpansionChanged(Lcom/android/systemui/shade/ShadeExpansionChangeEvent;)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        ScrimShadeTransitionController scrimShadeTransitionController = this.$tmp0;
        scrimShadeTransitionController.lastExpansionEvent = shadeExpansionChangeEvent;
        scrimShadeTransitionController.onStateChanged();
    }
}
