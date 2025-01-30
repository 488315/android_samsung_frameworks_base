package com.airbnb.lottie;

import com.airbnb.lottie.LottieDrawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieDrawable$$ExternalSyntheticLambda6 implements LottieDrawable.LazyCompositionTask {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LottieDrawable f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LottieDrawable$$ExternalSyntheticLambda6(LottieDrawable lottieDrawable, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = lottieDrawable;
        this.f$1 = i;
    }

    @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
    public final void run() {
        int i = this.$r8$classId;
        int i2 = this.f$1;
        LottieDrawable lottieDrawable = this.f$0;
        switch (i) {
            case 0:
                lottieDrawable.setFrame(i2);
                break;
            case 1:
                if (lottieDrawable.composition != null) {
                    lottieDrawable.animator.setMinAndMaxFrames(i2, (int) r4.maxFrame);
                    break;
                } else {
                    lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda6(lottieDrawable, i2, 1));
                    break;
                }
            default:
                lottieDrawable.setMaxFrame(i2);
                break;
        }
    }
}
