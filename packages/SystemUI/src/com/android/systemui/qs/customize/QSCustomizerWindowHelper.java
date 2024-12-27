package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

public final class QSCustomizerWindowHelper {
    public final WindowManager mWindowManager;
    public final Context sysUIContext;
    public View windowRootView;
    public final String WINDOW_TITLE = "CustomizerDummyWindow";
    public final int WINDOW_PREFIX = 100;

    public QSCustomizerWindowHelper(Context context) {
        this.sysUIContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }
}
