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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyframeParser {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final JsonReader.Options NAMES = JsonReader.Options.m50of("t", "s", "e", "o", "i", "h", "to", "ti");
    public static final JsonReader.Options INTERPOLATOR_NAMES = JsonReader.Options.m50of("x", "y");

    public static Interpolator interpolatorFor(PointF pointF, PointF pointF2) {
        pointF.x = MiscUtils.clamp(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
        pointF2.x = MiscUtils.clamp(pointF2.x, -1.0f, 1.0f);
        float clamp = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
        pointF2.y = clamp;
        float f = pointF.x;
        float f2 = pointF.y;
        float f3 = pointF2.x;
        Utils.C06051 c06051 = Utils.threadLocalPathMeasure;
        try {
            return new PathInterpolator(f, f2, f3, clamp);
        } catch (IllegalArgumentException e) {
            return "The Path cannot loop back on itself.".equals(e.getMessage()) ? new PathInterpolator(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y) : new LinearInterpolator();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0239 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Keyframe parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z, boolean z2) {
        Interpolator interpolatorFor;
        Object obj;
        Interpolator interpolatorFor2;
        Interpolator interpolatorFor3;
        Object obj2;
        Interpolator interpolator;
        Interpolator interpolatorFor4;
        PointF pointF;
        Keyframe keyframe;
        Interpolator interpolator2;
        PointF pointF2;
        JsonReader.Options options;
        PointF pointF3;
        PointF pointF4;
        PointF pointF5;
        JsonReader.Options options2;
        PointF pointF6;
        PointF pointF7;
        PointF pointF8;
        PointF pointF9;
        PointF pointF10;
        PointF pointF11;
        float f2;
        PointF pointF12;
        JsonReader.Options options3 = NAMES;
        Interpolator interpolator3 = LINEAR_INTERPOLATOR;
        if (!z || !z2) {
            JsonReader.Options options4 = options3;
            if (!z) {
                return new Keyframe(valueParser.parse(jsonReader, f));
            }
            jsonReader.beginObject();
            PointF pointF13 = null;
            PointF pointF14 = null;
            boolean z3 = false;
            Object obj3 = null;
            PointF pointF15 = null;
            float f3 = 0.0f;
            PointF pointF16 = null;
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
                        pointF15 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                        break;
                    case 4:
                        pointF13 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                        break;
                    case 5:
                        options4 = options5;
                        if (jsonReader.nextInt() == 1) {
                            z3 = true;
                            break;
                        } else {
                            z3 = false;
                            continue;
                        }
                    case 6:
                        pointF14 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    case 7:
                        pointF16 = JsonUtils.jsonToPoint(jsonReader, f);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
                options4 = options5;
            }
            jsonReader.endObject();
            if (z3) {
                obj = obj3;
                interpolatorFor = interpolator3;
            } else {
                interpolatorFor = (pointF15 == null || pointF13 == null) ? interpolator3 : interpolatorFor(pointF15, pointF13);
                obj = obj4;
            }
            Keyframe keyframe2 = new Keyframe(lottieComposition, obj3, obj, interpolatorFor, f3, null);
            keyframe2.pathCp1 = pointF14;
            keyframe2.pathCp2 = pointF16;
            return keyframe2;
        }
        jsonReader.beginObject();
        PointF pointF17 = null;
        PointF pointF18 = null;
        PointF pointF19 = null;
        PointF pointF20 = null;
        boolean z4 = false;
        PointF pointF21 = null;
        Object obj5 = null;
        PointF pointF22 = null;
        PointF pointF23 = null;
        PointF pointF24 = null;
        float f4 = 0.0f;
        Object obj6 = null;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(options3);
            JsonReader.Options options6 = INTERPOLATOR_NAMES;
            switch (selectName) {
                case 0:
                    options2 = options3;
                    interpolator2 = interpolator3;
                    pointF6 = pointF20;
                    pointF7 = pointF23;
                    pointF2 = pointF24;
                    pointF8 = pointF17;
                    f4 = (float) jsonReader.nextDouble();
                    pointF24 = pointF2;
                    pointF23 = pointF7;
                    pointF17 = pointF8;
                    interpolator3 = interpolator2;
                    options3 = options2;
                    pointF20 = pointF6;
                    break;
                case 1:
                    options = options3;
                    interpolator2 = interpolator3;
                    pointF3 = pointF20;
                    pointF4 = pointF23;
                    pointF2 = pointF24;
                    pointF5 = pointF17;
                    obj5 = valueParser.parse(jsonReader, f);
                    pointF12 = pointF3;
                    pointF24 = pointF2;
                    pointF23 = pointF4;
                    pointF17 = pointF5;
                    options3 = options;
                    pointF20 = pointF12;
                    interpolator3 = interpolator2;
                    break;
                case 2:
                    options = options3;
                    interpolator2 = interpolator3;
                    pointF3 = pointF20;
                    pointF4 = pointF23;
                    pointF2 = pointF24;
                    pointF5 = pointF17;
                    obj6 = valueParser.parse(jsonReader, f);
                    pointF12 = pointF3;
                    pointF24 = pointF2;
                    pointF23 = pointF4;
                    pointF17 = pointF5;
                    options3 = options;
                    pointF20 = pointF12;
                    interpolator3 = interpolator2;
                    break;
                case 3:
                    JsonReader.Options options7 = options3;
                    Interpolator interpolator4 = interpolator3;
                    PointF pointF25 = pointF20;
                    PointF pointF26 = pointF23;
                    PointF pointF27 = pointF24;
                    PointF pointF28 = pointF17;
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
                                    f5 = (float) jsonReader.nextDouble();
                                    f7 = f5;
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
                        PointF pointF29 = new PointF(f5, f6);
                        PointF pointF30 = new PointF(f7, f8);
                        jsonReader.endObject();
                        pointF17 = pointF30;
                        pointF23 = pointF29;
                    } else {
                        pointF23 = pointF26;
                        pointF21 = JsonUtils.jsonToPoint(jsonReader, f);
                        pointF17 = pointF28;
                    }
                    pointF24 = pointF27;
                    pointF20 = pointF25;
                    interpolator3 = interpolator4;
                    options3 = options7;
                    break;
                case 4:
                    interpolator2 = interpolator3;
                    pointF2 = pointF24;
                    if (jsonReader.peek() != JsonReader.Token.BEGIN_OBJECT) {
                        options = options3;
                        pointF3 = pointF20;
                        pointF4 = pointF23;
                        pointF5 = pointF17;
                        pointF22 = JsonUtils.jsonToPoint(jsonReader, f);
                        pointF12 = pointF3;
                        pointF24 = pointF2;
                        pointF23 = pointF4;
                        pointF17 = pointF5;
                        options3 = options;
                        pointF20 = pointF12;
                        interpolator3 = interpolator2;
                        break;
                    } else {
                        jsonReader.beginObject();
                        float f9 = 0.0f;
                        float f10 = 0.0f;
                        float f11 = 0.0f;
                        float f12 = 0.0f;
                        while (jsonReader.hasNext()) {
                            JsonReader.Options options8 = options3;
                            int selectName3 = jsonReader.selectName(options6);
                            if (selectName3 != 0) {
                                pointF9 = pointF20;
                                if (selectName3 != 1) {
                                    jsonReader.skipValue();
                                    pointF11 = pointF17;
                                    pointF10 = pointF23;
                                } else {
                                    JsonReader.Token peek3 = jsonReader.peek();
                                    JsonReader.Token token3 = JsonReader.Token.NUMBER;
                                    if (peek3 == token3) {
                                        pointF10 = pointF23;
                                        f10 = (float) jsonReader.nextDouble();
                                        pointF11 = pointF17;
                                        f12 = f10;
                                    } else {
                                        pointF10 = pointF23;
                                        jsonReader.beginArray();
                                        float nextDouble = (float) jsonReader.nextDouble();
                                        f12 = jsonReader.peek() == token3 ? (float) jsonReader.nextDouble() : nextDouble;
                                        jsonReader.endArray();
                                        f10 = nextDouble;
                                        pointF11 = pointF17;
                                    }
                                }
                            } else {
                                pointF9 = pointF20;
                                pointF10 = pointF23;
                                JsonReader.Token peek4 = jsonReader.peek();
                                JsonReader.Token token4 = JsonReader.Token.NUMBER;
                                if (peek4 == token4) {
                                    f9 = (float) jsonReader.nextDouble();
                                    f11 = f9;
                                    pointF11 = pointF17;
                                } else {
                                    jsonReader.beginArray();
                                    pointF11 = pointF17;
                                    float nextDouble2 = (float) jsonReader.nextDouble();
                                    if (jsonReader.peek() == token4) {
                                        f2 = nextDouble2;
                                        nextDouble2 = (float) jsonReader.nextDouble();
                                    } else {
                                        f2 = nextDouble2;
                                    }
                                    jsonReader.endArray();
                                    float f13 = f2;
                                    f11 = nextDouble2;
                                    f9 = f13;
                                }
                            }
                            pointF23 = pointF10;
                            pointF17 = pointF11;
                            options3 = options8;
                            pointF20 = pointF9;
                        }
                        options2 = options3;
                        pointF6 = pointF20;
                        pointF7 = pointF23;
                        pointF8 = pointF17;
                        PointF pointF31 = new PointF(f9, f10);
                        PointF pointF32 = new PointF(f11, f12);
                        jsonReader.endObject();
                        pointF19 = pointF32;
                        pointF18 = pointF31;
                        pointF24 = pointF2;
                        pointF23 = pointF7;
                        pointF17 = pointF8;
                        interpolator3 = interpolator2;
                        options3 = options2;
                        pointF20 = pointF6;
                        break;
                    }
                case 5:
                    options = options3;
                    interpolator2 = interpolator3;
                    pointF12 = pointF20;
                    pointF4 = pointF23;
                    pointF2 = pointF24;
                    z4 = jsonReader.nextInt() == 1;
                    pointF5 = pointF17;
                    pointF24 = pointF2;
                    pointF23 = pointF4;
                    pointF17 = pointF5;
                    options3 = options;
                    pointF20 = pointF12;
                    interpolator3 = interpolator2;
                    break;
                case 6:
                    pointF24 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    options = options3;
                    interpolator2 = interpolator3;
                    pointF12 = JsonUtils.jsonToPoint(jsonReader, f);
                    pointF4 = pointF23;
                    pointF2 = pointF24;
                    pointF5 = pointF17;
                    pointF24 = pointF2;
                    pointF23 = pointF4;
                    pointF17 = pointF5;
                    options3 = options;
                    pointF20 = pointF12;
                    interpolator3 = interpolator2;
                    break;
                default:
                    options = options3;
                    interpolator2 = interpolator3;
                    pointF3 = pointF20;
                    pointF4 = pointF23;
                    pointF2 = pointF24;
                    pointF5 = pointF17;
                    jsonReader.skipValue();
                    pointF12 = pointF3;
                    pointF24 = pointF2;
                    pointF23 = pointF4;
                    pointF17 = pointF5;
                    options3 = options;
                    pointF20 = pointF12;
                    interpolator3 = interpolator2;
                    break;
            }
        }
        Interpolator interpolator5 = interpolator3;
        PointF pointF33 = pointF20;
        PointF pointF34 = pointF23;
        PointF pointF35 = pointF24;
        PointF pointF36 = pointF17;
        jsonReader.endObject();
        if (z4) {
            obj6 = obj5;
        } else {
            if (pointF21 != null && pointF22 != null) {
                interpolatorFor4 = interpolatorFor(pointF21, pointF22);
                interpolator = interpolatorFor4;
                obj2 = obj6;
                interpolatorFor2 = null;
                interpolatorFor3 = null;
                if (interpolatorFor2 != null) {
                }
                pointF = pointF35;
                keyframe = new Keyframe(lottieComposition, obj5, obj2, interpolator, f4, null);
                keyframe.pathCp1 = pointF;
                keyframe.pathCp2 = pointF33;
                return keyframe;
            }
            if (pointF34 != null && pointF36 != null && pointF18 != null && pointF19 != null) {
                interpolatorFor2 = interpolatorFor(pointF34, pointF18);
                interpolatorFor3 = interpolatorFor(pointF36, pointF19);
                obj2 = obj6;
                interpolator = null;
                if (interpolatorFor2 != null || interpolatorFor3 == null) {
                    pointF = pointF35;
                    keyframe = new Keyframe(lottieComposition, obj5, obj2, interpolator, f4, null);
                } else {
                    pointF = pointF35;
                    keyframe = new Keyframe(lottieComposition, obj5, obj2, interpolatorFor2, interpolatorFor3, f4, null);
                }
                keyframe.pathCp1 = pointF;
                keyframe.pathCp2 = pointF33;
                return keyframe;
            }
        }
        interpolatorFor4 = interpolator5;
        interpolator = interpolatorFor4;
        obj2 = obj6;
        interpolatorFor2 = null;
        interpolatorFor3 = null;
        if (interpolatorFor2 != null) {
        }
        pointF = pointF35;
        keyframe = new Keyframe(lottieComposition, obj5, obj2, interpolator, f4, null);
        keyframe.pathCp1 = pointF;
        keyframe.pathCp2 = pointF33;
        return keyframe;
    }
}
