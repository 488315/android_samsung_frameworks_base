package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
