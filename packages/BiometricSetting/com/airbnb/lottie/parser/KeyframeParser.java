package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
final class KeyframeParser {
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static JsonReader.Options NAMES = JsonReader.Options.m8of("t", "s", "e", "o", "i", "h", "to", "ti");
    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;

    KeyframeParser() {
    }

    static <T> Keyframe<T> parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z) throws IOException {
        Interpolator interpolator;
        Interpolator interpolator2;
        T t;
        WeakReference weakReference;
        if (!z) {
            return new Keyframe<>(valueParser.parse(jsonReader, f));
        }
        jsonReader.beginObject();
        PointF pointF = null;
        PointF pointF2 = null;
        T t2 = null;
        PointF pointF3 = null;
        PointF pointF4 = null;
        T t3 = null;
        float f2 = 0.0f;
        while (true) {
            boolean z2 = false;
            float f3 = f2;
            T t4 = t2;
            while (jsonReader.hasNext()) {
                switch (jsonReader.selectName(NAMES)) {
                    case 0:
                        f3 = (float) jsonReader.nextDouble();
                        break;
                    case 1:
                        t4 = valueParser.parse(jsonReader, f);
                        break;
                    case 2:
                        t3 = valueParser.parse(jsonReader, f);
                        break;
                    case 3:
                        pointF3 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    case 4:
                        pointF4 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    case 5:
                        z2 = true;
                        if (jsonReader.nextInt() == 1) {
                            break;
                        }
                        break;
                    case 6:
                        pointF2 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    case 7:
                        pointF = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            if (z2) {
                interpolator2 = LINEAR_INTERPOLATOR;
                t = t4;
            } else {
                if (pointF3 == null || pointF4 == null) {
                    interpolator = LINEAR_INTERPOLATOR;
                } else {
                    float f4 = -f;
                    pointF3.x = MiscUtils.clamp(pointF3.x, f4, f);
                    pointF3.y = MiscUtils.clamp(pointF3.y, -100.0f, 100.0f);
                    pointF4.x = MiscUtils.clamp(pointF4.x, f4, f);
                    float clamp = MiscUtils.clamp(pointF4.y, -100.0f, 100.0f);
                    pointF4.y = clamp;
                    float f5 = pointF3.x;
                    float f6 = pointF3.y;
                    float f7 = pointF4.x;
                    int i = Utils.$r8$clinit;
                    int i2 = f5 != 0.0f ? (int) (527 * f5) : 17;
                    if (f6 != 0.0f) {
                        i2 = (int) (i2 * 31 * f6);
                    }
                    if (f7 != 0.0f) {
                        i2 = (int) (i2 * 31 * f7);
                    }
                    if (clamp != 0.0f) {
                        i2 = (int) (i2 * 31 * clamp);
                    }
                    synchronized (KeyframeParser.class) {
                        if (pathInterpolatorCache == null) {
                            pathInterpolatorCache = new SparseArrayCompat<>();
                        }
                        SparseArrayCompat<WeakReference<Interpolator>> sparseArrayCompat = pathInterpolatorCache;
                        sparseArrayCompat.getClass();
                        weakReference = (WeakReference) SparseArrayCompatKt.commonGet(sparseArrayCompat, i2);
                    }
                    interpolator = weakReference != null ? (Interpolator) weakReference.get() : null;
                    if (weakReference == null || interpolator == null) {
                        pointF3.x /= f;
                        pointF3.y /= f;
                        float f8 = pointF4.x / f;
                        pointF4.x = f8;
                        float f9 = pointF4.y / f;
                        pointF4.y = f9;
                        try {
                            interpolator = new PathInterpolator(pointF3.x, pointF3.y, f8, f9);
                        } catch (IllegalArgumentException e) {
                            interpolator = e.getMessage().equals("The Path cannot loop back on itself.") ? new PathInterpolator(Math.min(pointF3.x, 1.0f), pointF3.y, Math.max(pointF4.x, 0.0f), pointF4.y) : new LinearInterpolator();
                        }
                        try {
                            WeakReference<Interpolator> weakReference2 = new WeakReference<>(interpolator);
                            synchronized (KeyframeParser.class) {
                                pathInterpolatorCache.put(i2, weakReference2);
                            }
                        } catch (ArrayIndexOutOfBoundsException unused) {
                        }
                    }
                }
                interpolator2 = interpolator;
                t = t3;
            }
            Keyframe<T> keyframe = new Keyframe<>(lottieComposition, t4, t, interpolator2, f3, null);
            keyframe.pathCp1 = pointF2;
            keyframe.pathCp2 = pointF;
            return keyframe;
            t2 = t4;
            f2 = f3;
        }
    }
}
