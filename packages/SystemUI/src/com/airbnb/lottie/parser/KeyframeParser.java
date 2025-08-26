package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

/* loaded from: classes.dex */
public class KeyframeParser {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final JsonReader.Options NAMES = JsonReader.Options.of("t", "s", "e", "o", "i", "h", "to", "ti");
    public static final JsonReader.Options INTERPOLATOR_NAMES = JsonReader.Options.of("x", "y");

    public static Interpolator interpolatorFor(PointF pointF, PointF pointF2) {
        pointF.x = MiscUtils.clamp(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
        pointF2.x = MiscUtils.clamp(pointF2.x, -1.0f, 1.0f);
        float fClamp = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
        pointF2.y = fClamp;
        Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
        try {
            return new PathInterpolator(pointF.x, pointF.y, pointF2.x, fClamp);
        } catch (IllegalArgumentException e) {
            return "The Path cannot loop back on itself.".equals(e.getMessage()) ? new PathInterpolator(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y) : new LinearInterpolator();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:98:0x0219  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Keyframe parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z, boolean z2) {
        Interpolator interpolatorInterpolatorFor;
        Object obj;
        Interpolator interpolatorInterpolatorFor2;
        Interpolator interpolatorInterpolatorFor3;
        Interpolator interpolatorInterpolatorFor4;
        Object obj2;
        Interpolator interpolator;
        JsonReader.Options options;
        JsonReader.Options options2;
        PointF pointF;
        float f2;
        JsonReader.Options options3 = NAMES;
        int i = 1;
        if (!z || !z2) {
            JsonReader.Options options4 = options3;
            if (!z) {
                return new Keyframe(valueParser.parse(jsonReader, f));
            }
            jsonReader.beginObject();
            PointF pointFJsonToPoint = null;
            PointF pointFJsonToPoint2 = null;
            PointF pointFJsonToPoint3 = null;
            float fNextDouble = 0.0f;
            PointF pointFJsonToPoint4 = null;
            boolean z3 = false;
            Object obj3 = null;
            Object obj4 = null;
            while (jsonReader.hasNext()) {
                JsonReader.Options options5 = options4;
                switch (jsonReader.selectName(options5)) {
                    case 0:
                        fNextDouble = (float) jsonReader.nextDouble();
                        break;
                    case 1:
                        obj3 = valueParser.parse(jsonReader, f);
                        break;
                    case 2:
                        obj4 = valueParser.parse(jsonReader, f);
                        break;
                    case 3:
                        pointFJsonToPoint4 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                        break;
                    case 4:
                        pointFJsonToPoint = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                        break;
                    case 5:
                        if (jsonReader.nextInt() != 1) {
                            z3 = false;
                            break;
                        } else {
                            z3 = true;
                            break;
                        }
                    case 6:
                        pointFJsonToPoint2 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    case 7:
                        pointFJsonToPoint3 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
                options4 = options5;
            }
            jsonReader.endObject();
            if (z3) {
                interpolatorInterpolatorFor = LINEAR_INTERPOLATOR;
                obj = obj3;
            } else {
                interpolatorInterpolatorFor = (pointFJsonToPoint4 == null || pointFJsonToPoint == null) ? LINEAR_INTERPOLATOR : interpolatorFor(pointFJsonToPoint4, pointFJsonToPoint);
                obj = obj4;
            }
            Keyframe keyframe = new Keyframe(lottieComposition, obj3, obj, interpolatorInterpolatorFor, fNextDouble, null);
            keyframe.pathCp1 = pointFJsonToPoint2;
            keyframe.pathCp2 = pointFJsonToPoint3;
            return keyframe;
        }
        jsonReader.beginObject();
        PointF pointFJsonToPoint5 = null;
        PointF pointFJsonToPoint6 = null;
        int i2 = 0;
        PointF pointFJsonToPoint7 = null;
        PointF pointFJsonToPoint8 = null;
        PointF pointF2 = null;
        Object obj5 = null;
        PointF pointF3 = null;
        PointF pointF4 = null;
        PointF pointF5 = null;
        float fNextDouble2 = 0.0f;
        Object obj6 = null;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(options3);
            JsonReader.Options options6 = INTERPOLATOR_NAMES;
            switch (iSelectName) {
                case 0:
                    options = options3;
                    fNextDouble2 = (float) jsonReader.nextDouble();
                    options3 = options;
                    i = 1;
                    break;
                case 1:
                    obj5 = valueParser.parse(jsonReader, f);
                    i = 1;
                    break;
                case 2:
                    obj6 = valueParser.parse(jsonReader, f);
                    i = 1;
                    break;
                case 3:
                    JsonReader.Options options7 = options3;
                    PointF pointF6 = pointFJsonToPoint5;
                    int i3 = i2;
                    Object obj7 = obj5;
                    if (jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                        jsonReader.beginObject();
                        float fNextDouble3 = 0.0f;
                        float fNextDouble4 = 0.0f;
                        float fNextDouble5 = 0.0f;
                        float fNextDouble6 = 0.0f;
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(options6);
                            if (iSelectName2 == 0) {
                                JsonReader.Token tokenPeek = jsonReader.peek();
                                JsonReader.Token token = JsonReader.Token.NUMBER;
                                if (tokenPeek == token) {
                                    fNextDouble5 = (float) jsonReader.nextDouble();
                                    fNextDouble3 = fNextDouble5;
                                } else {
                                    jsonReader.beginArray();
                                    fNextDouble3 = (float) jsonReader.nextDouble();
                                    fNextDouble5 = jsonReader.peek() == token ? (float) jsonReader.nextDouble() : fNextDouble3;
                                    jsonReader.endArray();
                                }
                            } else if (iSelectName2 != 1) {
                                jsonReader.skipValue();
                            } else {
                                JsonReader.Token tokenPeek2 = jsonReader.peek();
                                JsonReader.Token token2 = JsonReader.Token.NUMBER;
                                if (tokenPeek2 == token2) {
                                    fNextDouble6 = (float) jsonReader.nextDouble();
                                    fNextDouble4 = fNextDouble6;
                                } else {
                                    jsonReader.beginArray();
                                    fNextDouble4 = (float) jsonReader.nextDouble();
                                    fNextDouble6 = jsonReader.peek() == token2 ? (float) jsonReader.nextDouble() : fNextDouble4;
                                    jsonReader.endArray();
                                }
                            }
                        }
                        pointF2 = new PointF(fNextDouble3, fNextDouble4);
                        pointF3 = new PointF(fNextDouble5, fNextDouble6);
                        jsonReader.endObject();
                    } else {
                        pointFJsonToPoint7 = JsonUtils.jsonToPoint(jsonReader, f);
                    }
                    i2 = i3;
                    obj5 = obj7;
                    options3 = options7;
                    pointFJsonToPoint5 = pointF6;
                    i = 1;
                    break;
                case 4:
                    int i4 = i2;
                    if (jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                        jsonReader.beginObject();
                        float f3 = 0.0f;
                        float f4 = 0.0f;
                        float fNextDouble7 = 0.0f;
                        float fNextDouble8 = 0.0f;
                        while (jsonReader.hasNext()) {
                            Object obj8 = obj5;
                            int iSelectName3 = jsonReader.selectName(options6);
                            if (iSelectName3 != 0) {
                                options2 = options3;
                                if (iSelectName3 != 1) {
                                    jsonReader.skipValue();
                                    obj5 = obj8;
                                    options3 = options2;
                                } else {
                                    JsonReader.Token tokenPeek3 = jsonReader.peek();
                                    JsonReader.Token token3 = JsonReader.Token.NUMBER;
                                    if (tokenPeek3 == token3) {
                                        pointF = pointFJsonToPoint5;
                                        fNextDouble8 = (float) jsonReader.nextDouble();
                                        obj5 = obj8;
                                        pointFJsonToPoint6 = pointFJsonToPoint6;
                                        f4 = fNextDouble8;
                                    } else {
                                        pointF = pointFJsonToPoint5;
                                        PointF pointF7 = pointFJsonToPoint6;
                                        jsonReader.beginArray();
                                        float fNextDouble9 = (float) jsonReader.nextDouble();
                                        if (jsonReader.peek() == token3) {
                                            f4 = fNextDouble9;
                                            fNextDouble8 = (float) jsonReader.nextDouble();
                                        } else {
                                            f4 = fNextDouble9;
                                            fNextDouble8 = f4;
                                        }
                                        jsonReader.endArray();
                                        obj5 = obj8;
                                        pointFJsonToPoint6 = pointF7;
                                    }
                                }
                            } else {
                                options2 = options3;
                                pointF = pointFJsonToPoint5;
                                PointF pointF8 = pointFJsonToPoint6;
                                JsonReader.Token tokenPeek4 = jsonReader.peek();
                                JsonReader.Token token4 = JsonReader.Token.NUMBER;
                                if (tokenPeek4 == token4) {
                                    fNextDouble7 = (float) jsonReader.nextDouble();
                                    obj5 = obj8;
                                    pointFJsonToPoint6 = pointF8;
                                    f3 = fNextDouble7;
                                } else {
                                    jsonReader.beginArray();
                                    pointFJsonToPoint6 = pointF8;
                                    float fNextDouble10 = (float) jsonReader.nextDouble();
                                    if (jsonReader.peek() == token4) {
                                        f2 = fNextDouble10;
                                        fNextDouble7 = (float) jsonReader.nextDouble();
                                    } else {
                                        f2 = fNextDouble10;
                                        fNextDouble7 = f2;
                                    }
                                    jsonReader.endArray();
                                    obj5 = obj8;
                                    f3 = f2;
                                }
                            }
                            options3 = options2;
                            pointFJsonToPoint5 = pointF;
                        }
                        options = options3;
                        PointF pointF9 = new PointF(f3, f4);
                        PointF pointF10 = new PointF(fNextDouble7, fNextDouble8);
                        jsonReader.endObject();
                        i2 = i4;
                        pointF4 = pointF9;
                        pointF5 = pointF10;
                        options3 = options;
                        i = 1;
                        break;
                    } else {
                        pointFJsonToPoint8 = JsonUtils.jsonToPoint(jsonReader, f);
                        i2 = i4;
                        i = 1;
                    }
                case 5:
                    if (jsonReader.nextInt() != i) {
                        i2 = 0;
                        break;
                    } else {
                        i2 = i;
                        break;
                    }
                case 6:
                    pointFJsonToPoint5 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointFJsonToPoint6 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        PointF pointF11 = pointFJsonToPoint5;
        int i5 = i2;
        Object obj9 = obj5;
        jsonReader.endObject();
        if (i5 != 0) {
            obj2 = obj9;
            interpolator = LINEAR_INTERPOLATOR;
        } else {
            if (pointFJsonToPoint7 != null && pointFJsonToPoint8 != null) {
                interpolatorInterpolatorFor2 = interpolatorFor(pointFJsonToPoint7, pointFJsonToPoint8);
            } else {
                if (pointF2 != null && pointF3 != null && pointF4 != null && pointF5 != null) {
                    interpolatorInterpolatorFor3 = interpolatorFor(pointF2, pointF4);
                    interpolatorInterpolatorFor4 = interpolatorFor(pointF3, pointF5);
                    obj2 = obj6;
                    interpolator = null;
                    Keyframe keyframe2 = (interpolatorInterpolatorFor3 != null || interpolatorInterpolatorFor4 == null) ? new Keyframe(lottieComposition, obj9, obj2, interpolator, fNextDouble2, null) : new Keyframe(lottieComposition, obj9, obj2, interpolatorInterpolatorFor3, interpolatorInterpolatorFor4, fNextDouble2, null);
                    keyframe2.pathCp1 = pointF11;
                    keyframe2.pathCp2 = pointFJsonToPoint6;
                    return keyframe2;
                }
                interpolatorInterpolatorFor2 = LINEAR_INTERPOLATOR;
            }
            interpolator = interpolatorInterpolatorFor2;
            obj2 = obj6;
        }
        interpolatorInterpolatorFor3 = null;
        interpolatorInterpolatorFor4 = null;
        if (interpolatorInterpolatorFor3 != null) {
        }
        keyframe2.pathCp1 = pointF11;
        keyframe2.pathCp2 = pointFJsonToPoint6;
        return keyframe2;
    }
}
