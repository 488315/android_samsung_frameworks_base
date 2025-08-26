package com.android.systemui.blur;

import android.view.Choreographer;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0 implements Choreographer.FrameCallback {
    public final /* synthetic */ Function1 function;

    public SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(Function1 function1) {
        this.function = function1;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final /* synthetic */ void doFrame(long j) {
        this.function.mo781invoke(Long.valueOf(j));
    }
}
