package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.view.ViewTreeObserver;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipUiEventLogger;

/* loaded from: classes3.dex */
public class PipDismissTargetHandler implements ViewTreeObserver.OnPreDrawListener {
    public PipDismissTargetHandler(Context context, PipUiEventLogger pipUiEventLogger, PipMotionHelper pipMotionHelper, ShellExecutor shellExecutor) {
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        return true;
    }
}
