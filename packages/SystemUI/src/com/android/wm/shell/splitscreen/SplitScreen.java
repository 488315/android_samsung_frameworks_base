package com.android.wm.shell.splitscreen;

import android.graphics.Rect;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;

/* loaded from: classes3.dex */
public interface SplitScreen {
    static String stageTypeToString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "STAGE_C" : "STAGE_B" : "STAGE_A" : "SIDE" : "MAIN" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
    }

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
