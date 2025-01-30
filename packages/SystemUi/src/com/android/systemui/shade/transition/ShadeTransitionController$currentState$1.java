package com.android.systemui.shade.transition;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeTransitionController$currentState$1 implements ShadeExpansionListener, FunctionAdapter {
    public final /* synthetic */ ShadeTransitionController $tmp0;

    public ShadeTransitionController$currentState$1(ShadeTransitionController shadeTransitionController) {
        this.$tmp0 = shadeTransitionController;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ShadeExpansionListener) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, ShadeTransitionController.class, "onPanelExpansionChanged", "onPanelExpansionChanged(Lcom/android/systemui/shade/ShadeExpansionChangeEvent;)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        ShadeTransitionController shadeTransitionController = this.$tmp0;
        shadeTransitionController.lastShadeExpansionChangeEvent = shadeExpansionChangeEvent;
        ScrimShadeTransitionController scrimShadeTransitionController = shadeTransitionController.scrimShadeTransitionController;
        scrimShadeTransitionController.lastExpansionEvent = shadeExpansionChangeEvent;
        scrimShadeTransitionController.onStateChanged();
    }
}
