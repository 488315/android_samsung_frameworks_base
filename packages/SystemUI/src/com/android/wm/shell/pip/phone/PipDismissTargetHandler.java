package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.view.ViewTreeObserver;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipUiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipDismissTargetHandler implements ViewTreeObserver.OnPreDrawListener {
    public PipDismissTargetHandler(Context context, PipUiEventLogger pipUiEventLogger, PipMotionHelper pipMotionHelper, ShellExecutor shellExecutor) {
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        return true;
    }
}
