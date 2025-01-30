package com.android.p038wm.shell.common.split;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DividerPanelWindowManager {
    public DividerView mDividerView;
    public WindowManager.LayoutParams mLp;
    public View mView;
    public final WindowManager mWm;

    public DividerPanelWindowManager(Context context) {
        this.mWm = (WindowManager) context.getSystemService("window");
    }
}
