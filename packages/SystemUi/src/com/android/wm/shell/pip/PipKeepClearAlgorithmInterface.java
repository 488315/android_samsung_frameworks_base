package com.android.wm.shell.pip;

import android.graphics.Rect;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PipKeepClearAlgorithmInterface {
    default Rect adjust(PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm) {
        return pipBoundsState.getBounds();
    }

    default Rect findUnoccludedPosition(Rect rect, Set set, Set set2, Rect rect2) {
        return rect;
    }
}
