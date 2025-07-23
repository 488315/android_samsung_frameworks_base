package com.android.systemui.blur;

import android.view.Choreographer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0 implements Choreographer.FrameCallback {
    public final /* synthetic */ Function1 function;

    public SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(Function1 function1) {
        this.function = function1;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final /* synthetic */ void doFrame(long j) {
        this.function.mo779invoke(Long.valueOf(j));
    }
}
