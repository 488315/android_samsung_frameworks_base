package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/* loaded from: classes2.dex */
public final class QSCustomizerWindowHelper {
    public final WindowManager mWindowManager;
    public final Context sysUIContext;
    public View windowRootView;

    public QSCustomizerWindowHelper(Context context) {
        this.sysUIContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }
}
