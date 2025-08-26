package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableSplitDimensionPathValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

/* loaded from: classes.dex */
public class AnimatableTransformParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("a", "p", "s", "rz", "r", "o", "so", "eo", "sk", "sa");
    public static final JsonReader.Options ANIMATABLE_NAMES = JsonReader.Options.of("k");

    private AnimatableTransformParser() {
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x017f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AnimatableTransform parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        AnimatableScaleValue animatableScaleValue;
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        boolean z;
        boolean z2 = false;
        boolean z3 = jsonUtf8Reader.peek() == JsonReader.Token.BEGIN_OBJECT;
        if (z3) {
            jsonUtf8Reader.beginObject();
        }
        AnimatableFloatValue animatableFloatValue3 = null;
        AnimatablePathValue animatablePathValue = null;
        AnimatableValue splitPath = null;
        AnimatableScaleValue animatableScaleValue2 = null;
        AnimatableFloatValue animatableFloatValue4 = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        AnimatableIntegerValue integer = null;
        AnimatableFloatValue animatableFloatValue6 = null;
        AnimatableFloatValue animatableFloatValue7 = null;
        while (jsonUtf8Reader.hasNext()) {
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    jsonUtf8Reader.beginObject();
                    while (jsonUtf8Reader.hasNext()) {
                        if (jsonUtf8Reader.selectName(ANIMATABLE_NAMES) != 0) {
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                        } else {
                            animatablePathValue = AnimatablePathValueParser.parse(jsonUtf8Reader, lottieComposition);
                        }
                    }
                    jsonUtf8Reader.endObject();
                    z2 = false;
                    break;
                case 1:
                    splitPath = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                    z2 = false;
                    break;
                case 2:
                    animatableScaleValue2 = new AnimatableScaleValue((List<Keyframe>) KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, ScaleXYParser.INSTANCE, z2));
                    z2 = false;
                    break;
                case 3:
                    lottieComposition.addWarning("Lottie doesn't support 3D layers.");
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    if (animatableFloatValue.keyframes.isEmpty()) {
                        animatableFloatValue2 = animatableFloatValue;
                        if (((Keyframe) animatableFloatValue2.keyframes.get(0)).startValue == null) {
                            z = false;
                            animatableFloatValue2.keyframes.set(0, new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.endFrame)));
                        }
                        z2 = z;
                        animatableFloatValue3 = animatableFloatValue2;
                        break;
                    } else {
                        animatableFloatValue2 = animatableFloatValue;
                        animatableFloatValue.keyframes.add(new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.endFrame)));
                    }
                    z = false;
                    z2 = z;
                    animatableFloatValue3 = animatableFloatValue2;
                case 4:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    if (animatableFloatValue.keyframes.isEmpty()) {
                    }
                    z = false;
                    z2 = z;
                    animatableFloatValue3 = animatableFloatValue2;
                    break;
                case 5:
                    integer = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                    break;
                case 6:
                    animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    break;
                case 7:
                    animatableFloatValue7 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    break;
                case 8:
                    animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    break;
                case 9:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    break;
                default:
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    break;
            }
        }
        if (z3) {
            jsonUtf8Reader.endObject();
        }
        AnimatablePathValue animatablePathValue2 = (animatablePathValue == null || (animatablePathValue.isStatic() && ((PointF) ((Keyframe) animatablePathValue.keyframes.get(0)).startValue).equals(0.0f, 0.0f))) ? null : animatablePathValue;
        if (splitPath == null || (!(splitPath instanceof AnimatableSplitDimensionPathValue) && splitPath.isStatic() && ((PointF) ((Keyframe) splitPath.getKeyframes().get(0)).startValue).equals(0.0f, 0.0f))) {
            splitPath = null;
        }
        AnimatableFloatValue animatableFloatValue8 = (animatableFloatValue3 == null || (animatableFloatValue3.isStatic() && ((Float) ((Keyframe) animatableFloatValue3.keyframes.get(0)).startValue).floatValue() == 0.0f)) ? null : animatableFloatValue3;
        if (animatableScaleValue2 == null) {
            animatableScaleValue = null;
        } else {
            if (animatableScaleValue2.isStatic()) {
                ScaleXY scaleXY = (ScaleXY) ((Keyframe) animatableScaleValue2.keyframes.get(0)).startValue;
                if (scaleXY.scaleX != 1.0f || scaleXY.scaleY != 1.0f) {
                }
            }
            animatableScaleValue = animatableScaleValue2;
        }
        return new AnimatableTransform(animatablePathValue2, splitPath, animatableScaleValue, animatableFloatValue8, integer, animatableFloatValue6, animatableFloatValue7, (animatableFloatValue4 == null || (animatableFloatValue4.isStatic() && ((Float) ((Keyframe) animatableFloatValue4.keyframes.get(0)).startValue).floatValue() == 0.0f)) ? null : animatableFloatValue4, (animatableFloatValue5 == null || (animatableFloatValue5.isStatic() && ((Float) ((Keyframe) animatableFloatValue5.keyframes.get(0)).startValue).floatValue() == 0.0f)) ? null : animatableFloatValue5);
    }
}
