package com.android.systemui.ambient.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.ambient.touch.TouchMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
