package com.android.server.wm;

import android.animation.ValueAnimator;

public final /* synthetic */ class DragState$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ValueAnimator f$0;

    public /* synthetic */ DragState$$ExternalSyntheticLambda3(ValueAnimator valueAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = valueAnimator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ValueAnimator valueAnimator = this.f$0;
        switch (i) {
            case 0:
                valueAnimator.start();
                break;
            default:
                valueAnimator.start();
                break;
        }
    }
}
