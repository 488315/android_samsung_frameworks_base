package com.android.server.wm;

import android.animation.ValueAnimator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
