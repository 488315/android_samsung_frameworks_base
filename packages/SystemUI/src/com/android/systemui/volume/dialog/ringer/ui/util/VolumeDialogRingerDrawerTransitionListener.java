package com.android.systemui.volume.dialog.ringer.ui.util;

import androidx.constraintlayout.motion.widget.MotionLayout;
import kotlin.jvm.functions.Function1;

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
            this.onProgressChanged.mo781invoke(Float.valueOf(f));
        }
    }
}
