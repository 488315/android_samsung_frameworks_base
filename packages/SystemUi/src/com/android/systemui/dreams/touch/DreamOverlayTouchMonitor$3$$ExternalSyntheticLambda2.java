package com.android.systemui.dreams.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2 implements DreamOverlayTouchMonitor.Evaluator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MotionEvent f$0;

    public /* synthetic */ DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(MotionEvent motionEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = motionEvent;
    }

    @Override // com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.Evaluator
    public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
        int i = this.$r8$classId;
        MotionEvent motionEvent = this.f$0;
        switch (i) {
            case 0:
                return onGestureListener.onDown(motionEvent);
            default:
                return onGestureListener.onSingleTapUp(motionEvent);
        }
    }
}
