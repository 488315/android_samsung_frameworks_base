package com.airbnb.lottie;

import com.airbnb.lottie.LottieDrawable;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class LottieDrawable$$ExternalSyntheticLambda1 implements LottieDrawable.LazyCompositionTask {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LottieDrawable f$0;

    public /* synthetic */ LottieDrawable$$ExternalSyntheticLambda1(LottieDrawable lottieDrawable, int i) {
        this.$r8$classId = i;
        this.f$0 = lottieDrawable;
    }

    @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
    public final void run() {
        LottieDrawable lottieDrawable = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Executor executor = LottieDrawable.setProgressExecutor;
                lottieDrawable.resumeAnimation();
                break;
            default:
                Executor executor2 = LottieDrawable.setProgressExecutor;
                lottieDrawable.playAnimation();
                break;
        }
    }
}
