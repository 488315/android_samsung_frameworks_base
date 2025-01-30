package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.shade.ShadeExpansionChangeEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthDialogPanelInteractionDetector$onPanelExpansionChanged$1 implements Runnable {
    public final /* synthetic */ ShadeExpansionChangeEvent $event;
    public final /* synthetic */ AuthDialogPanelInteractionDetector this$0;

    public AuthDialogPanelInteractionDetector$onPanelExpansionChanged$1(AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector, ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        this.this$0 = authDialogPanelInteractionDetector;
        this.$event = shadeExpansionChangeEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector = this.this$0;
        Action action = authDialogPanelInteractionDetector.action;
        if (action != null) {
            ShadeExpansionChangeEvent shadeExpansionChangeEvent = this.$event;
            if (shadeExpansionChangeEvent.tracking || (shadeExpansionChangeEvent.expanded && shadeExpansionChangeEvent.fraction > 0.0f && authDialogPanelInteractionDetector.panelState == 1)) {
                Log.i("AuthDialogPanelInteractionDetector", "onPanelExpansionChanged, event: " + shadeExpansionChangeEvent);
                action.onPanelInteraction.run();
                authDialogPanelInteractionDetector.disable();
            }
        }
    }
}
