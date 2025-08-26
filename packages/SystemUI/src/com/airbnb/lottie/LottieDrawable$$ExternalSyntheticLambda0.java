package com.airbnb.lottie;

import com.airbnb.lottie.LottieDrawable;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class LottieDrawable$$ExternalSyntheticLambda0 implements LottieDrawable.LazyCompositionTask {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LottieDrawable f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ LottieDrawable$$ExternalSyntheticLambda0(LottieDrawable lottieDrawable, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = lottieDrawable;
        this.f$1 = str;
    }

    @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
    public final void run() {
        String str = this.f$1;
        LottieDrawable lottieDrawable = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Executor executor = LottieDrawable.setProgressExecutor;
                lottieDrawable.setMinAndMaxFrame(str);
                break;
            case 1:
                Executor executor2 = LottieDrawable.setProgressExecutor;
                lottieDrawable.setMaxFrame(str);
                break;
            default:
                Executor executor3 = LottieDrawable.setProgressExecutor;
                lottieDrawable.setMinFrame(str);
                break;
        }
    }
}
