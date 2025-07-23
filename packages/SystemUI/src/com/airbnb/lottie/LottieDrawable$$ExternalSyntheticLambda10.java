package com.airbnb.lottie;

import com.airbnb.lottie.LottieDrawable;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieDrawable$$ExternalSyntheticLambda10 implements LottieDrawable.LazyCompositionTask {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LottieDrawable f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LottieDrawable$$ExternalSyntheticLambda10(LottieDrawable lottieDrawable, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = lottieDrawable;
        this.f$1 = i;
    }

    @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
    public final void run() {
        int i = this.f$1;
        LottieDrawable lottieDrawable = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Executor executor = LottieDrawable.setProgressExecutor;
                lottieDrawable.setFrame(i);
                break;
            case 1:
                Executor executor2 = LottieDrawable.setProgressExecutor;
                lottieDrawable.setMaxFrame(i);
                break;
            default:
                Executor executor3 = LottieDrawable.setProgressExecutor;
                lottieDrawable.setMinFrame(i);
                break;
        }
    }
}
