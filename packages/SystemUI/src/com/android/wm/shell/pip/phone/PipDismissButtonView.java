package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.widget.FrameLayout;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.DismissViewManager$$ExternalSyntheticLambda0;

/* loaded from: classes3.dex */
public class PipDismissButtonView extends FrameLayout {
    public final DismissViewManager mDismissViewManager;

    public PipDismissButtonView(Context context) {
        super(context);
        this.mDismissViewManager = new DismissViewManager(context, 2);
    }

    public final void hideDismissTargetMaybe() {
        DismissViewManager dismissViewManager = this.mDismissViewManager;
        if (dismissViewManager.mView != null) {
            dismissViewManager.getClass();
            dismissViewManager.mView.hide(new DismissViewManager$$ExternalSyntheticLambda0(dismissViewManager));
            dismissViewManager.mView.updateView(false, false);
        }
    }
}
