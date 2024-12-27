package com.android.systemui.shade;

import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ShadeExpansionStateManager {
    public boolean expanded;
    public float fraction;
    public float oldFraction;
    public int state;
    public boolean tracking;
    public final CopyOnWriteArrayList expansionListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList stateListeners = new CopyOnWriteArrayList();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public final ShadeExpansionChangeEvent addExpansionListener(ShadeExpansionListener shadeExpansionListener) {
        this.expansionListeners.add(shadeExpansionListener);
        return new ShadeExpansionChangeEvent(this.fraction, this.expanded, this.tracking);
    }

    public final boolean isClosed() {
        return this.state == 0;
    }

    public final void removeExpansionListener(ShadeExpansionListener shadeExpansionListener) {
        this.expansionListeners.remove(shadeExpansionListener);
    }

    public final void updateStateInternal(int i) {
        ShadeExpansionStateManagerKt.panelStateToString(this.state);
        ShadeExpansionStateManagerKt.panelStateToString(i);
        int i2 = this.state;
        if (i2 != i) {
            MWBixbyController$$ExternalSyntheticOutline0.m("updateStateInternal: ", ShadeExpansionStateManagerKt.panelStateToString(i2), " -> ", ShadeExpansionStateManagerKt.panelStateToString(i), ShadeExpansionStateManagerKt.TAG);
        }
        this.state = i;
        Iterator it = this.stateListeners.iterator();
        while (it.hasNext()) {
            ((ShadeStateListener) it.next()).onPanelStateChanged(i);
        }
    }
}
