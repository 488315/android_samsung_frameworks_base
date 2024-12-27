package com.android.systemui.ambient.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.ambient.touch.TouchMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class TouchMonitor$4$$ExternalSyntheticLambda2 implements TouchMonitor.Evaluator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MotionEvent f$0;

    public /* synthetic */ TouchMonitor$4$$ExternalSyntheticLambda2(MotionEvent motionEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = motionEvent;
    }

    @Override // com.android.systemui.ambient.touch.TouchMonitor.Evaluator
    public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
        int i = this.$r8$classId;
        MotionEvent motionEvent = this.f$0;
        switch (i) {
            case 0:
                return onGestureListener.onSingleTapUp(motionEvent);
            default:
                return onGestureListener.onDown(motionEvent);
        }
    }
}
