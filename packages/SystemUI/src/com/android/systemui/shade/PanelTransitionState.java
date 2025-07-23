package com.android.systemui.shade;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PanelTransitionState {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new PanelTransitionState();
    }

    private PanelTransitionState() {
    }

    public static final String toString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "COMBINED_STATE" : "TRANSITION_STATE" : "SHADE_STATE" : "QS_STATE";
    }
}
