package com.android.wm.shell.splitscreen;

import android.graphics.Rect;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SplitScreen {
    static String stageTypeToString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("UNKNOWN(", i, ")") : "SIDE" : "MAIN" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
