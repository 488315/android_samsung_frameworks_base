package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.view.ViewTreeObserver;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PipUiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipDismissTargetHandler implements ViewTreeObserver.OnPreDrawListener {
    public PipDismissTargetHandler(Context context, PipUiEventLogger pipUiEventLogger, PipMotionHelper pipMotionHelper, ShellExecutor shellExecutor) {
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        return true;
    }
}
