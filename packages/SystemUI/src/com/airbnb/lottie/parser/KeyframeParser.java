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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyframeParser {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final JsonReader.Options NAMES = JsonReader.Options.of("t", "s", "e", "o", "i", "h", "to", "ti");
    public static final JsonReader.Options INTERPOLATOR_NAMES = JsonReader.Options.of("x", "y");

    public static Interpolator interpolatorFor(PointF pointF, PointF pointF2) {
        pointF.x = MiscUtils.clamp(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
        pointF2.x = MiscUtils.clamp(pointF2.x, -1.0f, 1.0f);
        float clamp = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
        pointF2.y = clamp;
        Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
        try {
            return new PathInterpolator(pointF.x, pointF.y, pointF2.x, clamp);
        } catch (IllegalArgumentException e) {
            return "The Path cannot loop back on itself.".equals(e.getMessage()) ? new PathInterpolator(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y) : new LinearInterpolator();
        }
    }

    public static Keyframe parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z, boolean z2) {
        Interpolator interpolatorFor;
        Object obj;
        Interpolator interpolator;
        Interpolator interpolatorFor2;
        Interpolator interpolatorFor3;
        Object obj2;
        Interpolator interpolator2;
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
            PointF pointF2 = null;
            PointF pointF3 = null;
            PointF pointF4 = null;
            float f3 = 0.0f;
            PointF pointF5 = null;
            boolean z3 = false;
            Object obj3 = null;
            Object obj4 = null;
            while (jsonReader.hasNext()) {
                JsonReader.Options options5 = options4;
                switch (jsonReader.selectName(options5)) {
                    case 0:
                        f3 = (float) jsonReader.nextDouble();
                        break;
                    case 1:
                        obj3 = valueParser.parse(jsonReader, f);
                        break;
                    case 2:
                        obj4 = valueParser.parse(jsonReader, f);
                        break;
                    case 3:
                        pointF5 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                        break;
                    case 4:
                        pointF2 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
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
                        pointF3 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    case 7:
                        pointF4 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
                options4 = options5;
            }
            jsonReader.endObject();
            if (z3) {
                interpolatorFor = LINEAR_INTERPOLATOR;
                obj = obj3;
            } else {
                interpolatorFor = (pointF5 == null || pointF2 == null) ? LINEAR_INTERPOLATOR : interpolatorFor(pointF5, pointF2);
                obj = obj4;
            }
            Keyframe keyframe = new Keyframe(lottieComposition, obj3, obj, interpolatorFor, f3, null);
            keyframe.pathCp1 = pointF3;
            keyframe.pathCp2 = pointF4;
            return keyframe;
        }
        jsonReader.beginObject();
        PointF pointF6 = null;
        PointF pointF7 = null;
        int i2 = 0;
        PointF pointF8 = null;
        PointF pointF9 = null;
        PointF pointF10 = null;
        Object obj5 = null;
        PointF pointF11 = null;
        PointF pointF12 = null;
        PointF pointF13 = null;
        float f4 = 0.0f;
        Object obj6 = null;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(options3);
            JsonReader.Options options6 = INTERPOLATOR_NAMES;
            switch (selectName) {
                case 0:
                    options = options3;
                    f4 = (float) jsonReader.nextDouble();
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
                    PointF pointF14 = pointF6;
                    int i3 = i2;
                    Object obj7 = obj5;
                    if (jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                        jsonReader.beginObject();
                        float f5 = 0.0f;
                        float f6 = 0.0f;
                        float f7 = 0.0f;
                        float f8 = 0.0f;
                        while (jsonReader.hasNext()) {
                            int selectName2 = jsonReader.selectName(options6);
                            if (selectName2 == 0) {
                                JsonReader.Token peek = jsonReader.peek();
                                JsonReader.Token token = JsonReader.Token.NUMBER;
                                if (peek == token) {
                                    f7 = (float) jsonReader.nextDouble();
                                    f5 = f7;
                                } else {
                                    jsonReader.beginArray();
                                    f5 = (float) jsonReader.nextDouble();
                                    f7 = jsonReader.peek() == token ? (float) jsonReader.nextDouble() : f5;
                                    jsonReader.endArray();
                                }
                            } else if (selectName2 != 1) {
                                jsonReader.skipValue();
                            } else {
                                JsonReader.Token peek2 = jsonReader.peek();
                                JsonReader.Token token2 = JsonReader.Token.NUMBER;
                                if (peek2 == token2) {
                                    f8 = (float) jsonReader.nextDouble();
                                    f6 = f8;
                                } else {
                                    jsonReader.beginArray();
                                    f6 = (float) jsonReader.nextDouble();
                                    f8 = jsonReader.peek() == token2 ? (float) jsonReader.nextDouble() : f6;
                                    jsonReader.endArray();
                                }
                            }
                        }
                        pointF10 = new PointF(f5, f6);
                        pointF11 = new PointF(f7, f8);
                        jsonReader.endObject();
                    } else {
                        pointF8 = JsonUtils.jsonToPoint(jsonReader, f);
                    }
                    i2 = i3;
                    obj5 = obj7;
                    options3 = options7;
                    pointF6 = pointF14;
                    i = 1;
                    break;
                case 4:
                    int i4 = i2;
                    if (jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                        jsonReader.beginObject();
                        float f9 = 0.0f;
                        float f10 = 0.0f;
                        float f11 = 0.0f;
                        float f12 = 0.0f;
                        while (jsonReader.hasNext()) {
                            Object obj8 = obj5;
                            int selectName3 = jsonReader.selectName(options6);
                            if (selectName3 != 0) {
                                options2 = options3;
                                if (selectName3 != 1) {
                                    jsonReader.skipValue();
                                    obj5 = obj8;
                                    options3 = options2;
                                } else {
                                    JsonReader.Token peek3 = jsonReader.peek();
                                    JsonReader.Token token3 = JsonReader.Token.NUMBER;
                                    if (peek3 == token3) {
                                        pointF = pointF6;
                                        f12 = (float) jsonReader.nextDouble();
                                        obj5 = obj8;
                                        pointF7 = pointF7;
                                        f10 = f12;
                                    } else {
                                        pointF = pointF6;
                                        PointF pointF15 = pointF7;
                                        jsonReader.beginArray();
                                        float nextDouble = (float) jsonReader.nextDouble();
                                        if (jsonReader.peek() == token3) {
                                            f10 = nextDouble;
                                            f12 = (float) jsonReader.nextDouble();
                                        } else {
                                            f10 = nextDouble;
                                            f12 = f10;
                                        }
                                        jsonReader.endArray();
                                        obj5 = obj8;
                                        pointF7 = pointF15;
                                    }
                                }
                            } else {
                                options2 = options3;
                                pointF = pointF6;
                                PointF pointF16 = pointF7;
                                JsonReader.Token peek4 = jsonReader.peek();
                                JsonReader.Token token4 = JsonReader.Token.NUMBER;
                                if (peek4 == token4) {
                                    f11 = (float) jsonReader.nextDouble();
                                    obj5 = obj8;
                                    pointF7 = pointF16;
                                    f9 = f11;
                                } else {
                                    jsonReader.beginArray();
                                    pointF7 = pointF16;
                                    float nextDouble2 = (float) jsonReader.nextDouble();
                                    if (jsonReader.peek() == token4) {
                                        f2 = nextDouble2;
                                        f11 = (float) jsonReader.nextDouble();
                                    } else {
                                        f2 = nextDouble2;
                                        f11 = f2;
                                    }
                                    jsonReader.endArray();
                                    obj5 = obj8;
                                    f9 = f2;
                                }
                            }
                            options3 = options2;
                            pointF6 = pointF;
                        }
                        options = options3;
                        PointF pointF17 = new PointF(f9, f10);
                        PointF pointF18 = new PointF(f11, f12);
                        jsonReader.endObject();
                        i2 = i4;
                        pointF12 = pointF17;
                        pointF13 = pointF18;
                        options3 = options;
                        i = 1;
                        break;
                    } else {
                        pointF9 = JsonUtils.jsonToPoint(jsonReader, f);
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
                    pointF6 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointF7 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        PointF pointF19 = pointF6;
        int i5 = i2;
        Object obj9 = obj5;
        jsonReader.endObject();
        if (i5 != 0) {
            obj2 = obj9;
            interpolator2 = LINEAR_INTERPOLATOR;
        } else {
            if (pointF8 != null && pointF9 != null) {
                interpolator = interpolatorFor(pointF8, pointF9);
            } else {
                if (pointF10 != null && pointF11 != null && pointF12 != null && pointF13 != null) {
                    interpolatorFor2 = interpolatorFor(pointF10, pointF12);
                    interpolatorFor3 = interpolatorFor(pointF11, pointF13);
                    obj2 = obj6;
                    interpolator2 = null;
                    Keyframe keyframe2 = (interpolatorFor2 != null || interpolatorFor3 == null) ? new Keyframe(lottieComposition, obj9, obj2, interpolator2, f4, null) : new Keyframe(lottieComposition, obj9, obj2, interpolatorFor2, interpolatorFor3, f4, null);
                    keyframe2.pathCp1 = pointF19;
                    keyframe2.pathCp2 = pointF7;
                    return keyframe2;
                }
                interpolator = LINEAR_INTERPOLATOR;
            }
            interpolator2 = interpolator;
            obj2 = obj6;
        }
        interpolatorFor2 = null;
        interpolatorFor3 = null;
        if (interpolatorFor2 != null) {
        }
        keyframe2.pathCp1 = pointF19;
        keyframe2.pathCp2 = pointF7;
        return keyframe2;
    }
}
