package com.android.systemui.shade;

import android.util.Log;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final void onPanelStateChanged$1(int i) {
        PanelPopOverManager panelPopOverManager = this.$tmp0;
        boolean z = panelPopOverManager.isClosingByOutsideTouch;
        if (z && panelPopOverManager.currentPanelState != i) {
            Log.d("PanelPopOverManager", "onPanelStateChanged state: " + i + " | isClosingByOutsideTouch: " + z + " to false");
            panelPopOverManager.isClosingByOutsideTouch = false;
        }
        panelPopOverManager.currentPanelState = i;
    }
}
