package com.airbnb.lottie;

import com.airbnb.lottie.utils.Utils;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieCompositionFactory$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LottieCompositionFactory$$ExternalSyntheticLambda2(InputStream inputStream) {
        this.f$0 = inputStream;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                Map map = LottieCompositionFactory.taskCache;
                Utils.closeQuietly((InputStream) obj);
                break;
            default:
                Map map2 = LottieCompositionFactory.taskCache;
                Utils.closeQuietly((ZipInputStream) obj);
                break;
        }
    }
}
