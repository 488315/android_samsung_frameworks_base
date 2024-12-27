package com.android.systemui.util.display;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.view.WindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class DisplayHelper {
    private final Context mContext;
    private final DisplayManager mDisplayManager;

    public DisplayHelper(Context context, DisplayManager displayManager) {
        this.mContext = context;
        this.mDisplayManager = displayManager;
    }

    public Rect getMaxBounds(int i, int i2) {
        return ((WindowManager) this.mContext.createDisplayContext(this.mDisplayManager.getDisplay(i)).createWindowContext(i2, null).getSystemService(WindowManager.class)).getMaximumWindowMetrics().getBounds();
    }
}
