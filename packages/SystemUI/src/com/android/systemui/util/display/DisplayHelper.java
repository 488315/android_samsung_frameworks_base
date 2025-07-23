package com.android.systemui.util.display;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DisplayHelper {
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final WindowManagerProvider mWindowManagerProvider;

    public DisplayHelper(Context context, DisplayManager displayManager, WindowManagerProvider windowManagerProvider) {
        this.mContext = context;
        this.mDisplayManager = displayManager;
        this.mWindowManagerProvider = windowManagerProvider;
    }

    public Rect getMaxBounds(int i, int i2) {
        Display display = this.mDisplayManager.getDisplay(i);
        WindowManagerProvider windowManagerProvider = this.mWindowManagerProvider;
        Context createWindowContext = this.mContext.createDisplayContext(display).createWindowContext(i2, null);
        ((WindowManagerProviderImpl) windowManagerProvider).getClass();
        return WindowManagerUtils.getWindowManager(createWindowContext).getMaximumWindowMetrics().getBounds();
    }
}
