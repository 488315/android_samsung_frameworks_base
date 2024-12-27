package com.android.systemui.shade.transition;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.ScrimController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScrimShadeTransitionController {
    public Integer currentPanelState;
    public final DumpManager dumpManager;
    public ShadeExpansionChangeEvent lastExpansionEvent;
    public Float lastExpansionFraction;
    public final ScrimController scrimController;
    public final ShadeExpansionStateManager shadeExpansionStateManager;

    public ScrimShadeTransitionController(ShadeExpansionStateManager shadeExpansionStateManager, DumpManager dumpManager, ScrimController scrimController) {
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.dumpManager = dumpManager;
        this.scrimController = scrimController;
    }

    public final void onStateChanged() {
        ShadeExpansionChangeEvent shadeExpansionChangeEvent = this.lastExpansionEvent;
        if (shadeExpansionChangeEvent == null) {
            return;
        }
        ScrimController scrimController = this.scrimController;
        scrimController.getClass();
        float f = shadeExpansionChangeEvent.fraction;
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("rawPanelExpansionFraction should not be NaN");
        }
        scrimController.mRawPanelExpansionFraction = f;
        scrimController.calculateAndUpdatePanelExpansion();
        this.lastExpansionFraction = Float.valueOf(f);
    }
}
