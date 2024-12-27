package com.android.systemui.shade;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
