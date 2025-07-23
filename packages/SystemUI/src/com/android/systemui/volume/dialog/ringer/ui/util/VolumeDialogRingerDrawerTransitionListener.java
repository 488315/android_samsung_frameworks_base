package com.android.systemui.volume.dialog.ringer.ui.util;

import androidx.constraintlayout.motion.widget.MotionLayout;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogRingerDrawerTransitionListener implements MotionLayout.TransitionListener {
    public boolean notifyProgressChangeEnabled = true;
    public final Function1 onProgressChanged;

    public VolumeDialogRingerDrawerTransitionListener(Function1 function1) {
        this.onProgressChanged = function1;
    }

    @Override // androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
    public final void onTransitionChange(float f) {
        if (this.notifyProgressChangeEnabled) {
            this.onProgressChanged.mo779invoke(Float.valueOf(f));
        }
    }
}
