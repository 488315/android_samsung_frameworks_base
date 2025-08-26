package com.android.systemui.shade;

import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final /* synthetic */ class PanelPopOverManager$setPanelController$1 implements ShadeStateListener, FunctionAdapter {
    public final /* synthetic */ PanelPopOverManager $tmp0;

    public PanelPopOverManager$setPanelController$1(PanelPopOverManager panelPopOverManager) {
        this.$tmp0 = panelPopOverManager;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ShadeStateListener) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, PanelPopOverManager.class, "onPanelStateChanged", "onPanelStateChanged(I)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.shade.ShadeStateListener
    public final void onPanelStateChanged$2(int i) {
        PanelPopOverManager panelPopOverManager = this.$tmp0;
        if (panelPopOverManager.currentPanelState != i) {
            panelPopOverManager.currentPanelState = i;
        }
    }
}
