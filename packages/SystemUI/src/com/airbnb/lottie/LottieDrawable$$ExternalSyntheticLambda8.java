package com.airbnb.lottie;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class LottieDrawable$$ExternalSyntheticLambda8 implements LottieDrawable.LazyCompositionTask {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LottieDrawable f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ LottieDrawable$$ExternalSyntheticLambda8(LottieDrawable lottieDrawable, float f, int i) {
        this.$r8$classId = i;
        this.f$0 = lottieDrawable;
        this.f$1 = f;
    }

    @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
    public final void run() {
        float f = this.f$1;
        LottieDrawable lottieDrawable = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Executor executor = LottieDrawable.setProgressExecutor;
                LottieComposition lottieComposition = lottieDrawable.composition;
                if (lottieComposition != null) {
                    lottieDrawable.setMinFrame((int) MiscUtils.lerp(lottieComposition.startFrame, lottieComposition.endFrame, f));
                    break;
                } else {
                    lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda8(lottieDrawable, f, 0));
                    break;
                }
            case 1:
                Executor executor2 = LottieDrawable.setProgressExecutor;
                LottieComposition lottieComposition2 = lottieDrawable.composition;
                if (lottieComposition2 != null) {
                    LottieValueAnimator lottieValueAnimator = lottieDrawable.animator;
                    lottieValueAnimator.setMinAndMaxFrames(lottieValueAnimator.minFrame, MiscUtils.lerp(lottieComposition2.startFrame, lottieComposition2.endFrame, f));
                    break;
                } else {
                    lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda8(lottieDrawable, f, 1));
                    break;
                }
            default:
                Executor executor3 = LottieDrawable.setProgressExecutor;
                lottieDrawable.setProgress(f);
                break;
        }
    }
}
