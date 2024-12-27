package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
