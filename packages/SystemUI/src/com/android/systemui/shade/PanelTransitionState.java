package com.android.systemui.shade;

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
