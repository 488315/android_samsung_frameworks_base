package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.widget.FrameLayout;
import com.android.wm.shell.common.DismissButtonManager;
import com.android.wm.shell.common.DismissButtonManager$$ExternalSyntheticLambda0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipDismissButtonView extends FrameLayout {
    public final DismissButtonManager mDismissButtonManager;

    public PipDismissButtonView(Context context) {
        super(context);
        this.mDismissButtonManager = new DismissButtonManager(context, 2);
    }

    public final void hideDismissTargetMaybe() {
        DismissButtonManager dismissButtonManager = this.mDismissButtonManager;
        if (dismissButtonManager.mView != null) {
            dismissButtonManager.getClass();
            dismissButtonManager.hide(new DismissButtonManager$$ExternalSyntheticLambda0(dismissButtonManager));
            dismissButtonManager.mView.updateView(false, false);
        }
    }
}
