package com.android.systemui.animation;

import android.view.View;
import com.android.systemui.animation.DialogLaunchAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewDialogLaunchAnimatorController implements DialogLaunchAnimator.Controller {
    public final DialogCuj cuj;
    public final View source;
    public final Object sourceIdentity;

    public ViewDialogLaunchAnimatorController(View view, DialogCuj dialogCuj) {
        this.source = view;
        this.cuj = dialogCuj;
        this.sourceIdentity = view;
    }
}
