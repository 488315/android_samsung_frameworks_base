package com.android.systemui.ambient.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.ambient.touch.TouchMonitor;

public final /* synthetic */ class TouchMonitor$4$$ExternalSyntheticLambda4 implements TouchMonitor.Evaluator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MotionEvent f$0;
    public final /* synthetic */ MotionEvent f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ TouchMonitor$4$$ExternalSyntheticLambda4(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, int i) {
        this.$r8$classId = i;
        this.f$0 = motionEvent;
        this.f$1 = motionEvent2;
        this.f$2 = f;
        this.f$3 = f2;
    }

    @Override // com.android.systemui.ambient.touch.TouchMonitor.Evaluator
    public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
        switch (this.$r8$classId) {
            case 0:
                return onGestureListener.onScroll(this.f$0, this.f$1, this.f$2, this.f$3);
            default:
                return onGestureListener.onFling(this.f$0, this.f$1, this.f$2, this.f$3);
        }
    }
}
