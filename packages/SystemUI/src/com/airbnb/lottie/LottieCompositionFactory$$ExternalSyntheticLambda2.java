package com.airbnb.lottie;

import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public final /* synthetic */ class LottieCompositionFactory$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LottieCompositionFactory$$ExternalSyntheticLambda2(InputStream inputStream) {
        this.f$0 = inputStream;
    }

    @Override // java.lang.Runnable
    public final void run() throws IOException {
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
