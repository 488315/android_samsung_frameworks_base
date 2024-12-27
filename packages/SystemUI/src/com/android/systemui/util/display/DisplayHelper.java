package com.android.systemui.util.display;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.view.WindowManager;

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
