package com.airbnb.lottie;

import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipInputStream;
import okio.Okio;
import okio.RealBufferedSource;

/* loaded from: classes.dex */
public final /* synthetic */ class LottieCompositionFactory$$ExternalSyntheticLambda1 implements Callable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ LottieCompositionFactory$$ExternalSyntheticLambda1(InputStream inputStream, String str) {
        this.f$0 = inputStream;
        this.f$1 = str;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        String str = this.f$1;
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                return LottieCompositionFactory.fromJsonInputStreamSync((InputStream) obj, str);
            case 1:
                Map map = LottieCompositionFactory.taskCache;
                RealBufferedSource realBufferedSource = new RealBufferedSource(Okio.source(new ByteArrayInputStream(str.getBytes())));
                String[] strArr = JsonReader.REPLACEMENT_CHARS;
                return LottieCompositionFactory.fromJsonReaderSyncInternal(new JsonUtf8Reader(realBufferedSource), (String) obj, true);
            default:
                return LottieCompositionFactory.fromZipStreamSync(null, (ZipInputStream) obj, str);
        }
    }

    public /* synthetic */ LottieCompositionFactory$$ExternalSyntheticLambda1(String str, String str2) {
        this.f$1 = str;
        this.f$0 = str2;
    }

    public /* synthetic */ LottieCompositionFactory$$ExternalSyntheticLambda1(ZipInputStream zipInputStream, String str) {
        this.f$0 = zipInputStream;
        this.f$1 = str;
    }
}
