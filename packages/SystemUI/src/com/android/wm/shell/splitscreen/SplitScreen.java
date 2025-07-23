package com.android.wm.shell.splitscreen;

import android.graphics.Rect;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SplitScreen {
    static String stageTypeToString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "STAGE_C" : "STAGE_B" : "STAGE_A" : "SIDE" : "MAIN" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SplitScreenListener {
        default void onSplitVisibilityChanged(boolean z) {
        }

        default void onStagePositionChanged(int i, int i2) {
        }

        default void onSplitBoundsChanged(Rect rect, Rect rect2, Rect rect3) {
        }

        default void onTaskStageChanged(int i, int i2, boolean z) {
        }
    }
}
