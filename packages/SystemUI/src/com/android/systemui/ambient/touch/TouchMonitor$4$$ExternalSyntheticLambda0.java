package com.android.systemui.ambient.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$4$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MotionEvent f$0;

    public /* synthetic */ TouchMonitor$4$$ExternalSyntheticLambda0(MotionEvent motionEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = motionEvent;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        MotionEvent motionEvent = this.f$0;
        GestureDetector.OnGestureListener onGestureListener = (GestureDetector.OnGestureListener) obj;
        switch (i) {
            case 0:
                onGestureListener.onLongPress(motionEvent);
                break;
            default:
                onGestureListener.onShowPress(motionEvent);
                break;
        }
    }
}
