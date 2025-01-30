package com.android.systemui.biometrics;

import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthDialogPanelInteractionDetector$onPanelStateChanged$1 implements Runnable {
    public final /* synthetic */ int $state;
    public final /* synthetic */ AuthDialogPanelInteractionDetector this$0;

    public AuthDialogPanelInteractionDetector$onPanelStateChanged$1(AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector, int i) {
        this.this$0 = authDialogPanelInteractionDetector;
        this.$state = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector = this.this$0;
        int i = this.$state;
        authDialogPanelInteractionDetector.panelState = i;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("onPanelStateChanged, state: ", i, "AuthDialogPanelInteractionDetector");
    }
}
