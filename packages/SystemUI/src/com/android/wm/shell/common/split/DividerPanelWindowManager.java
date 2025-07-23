package com.android.wm.shell.common.split;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DividerPanelWindowManager {
    public DividerView mDividerView;
    public WindowManager.LayoutParams mLp;
    public View mView;
    public final WindowManager mWm;

    public DividerPanelWindowManager(Context context) {
        this.mWm = (WindowManager) context.getSystemService("window");
    }
}
