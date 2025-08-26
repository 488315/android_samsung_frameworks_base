package com.android.wm.shell.common.split;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

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
