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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimatableTransformParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.m50of("a", "p", "s", "rz", "r", "o", "so", "eo", "sk", "sa");
    public static final JsonReader.Options ANIMATABLE_NAMES = JsonReader.Options.m50of("k");

    private AnimatableTransformParser() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x01ab, code lost:
    
        if ((r0.scaleX == 1.0f && r0.scaleY == 1.0f) != false) goto L89;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AnimatableTransform parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        AnimatablePathValue animatablePathValue;
        AnimatableValue animatableValue;
        boolean z;
        AnimatableScaleValue animatableScaleValue;
        AnimatableValue animatableValue2;
        List list;
        AnimatableFloatValue animatableFloatValue;
        boolean z2 = jsonUtf8Reader.peek() == JsonReader.Token.BEGIN_OBJECT;
        if (z2) {
            jsonUtf8Reader.beginObject();
        }
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableScaleValue animatableScaleValue2 = null;
        AnimatableValue animatableValue3 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        AnimatableFloatValue animatableFloatValue4 = null;
        AnimatablePathValue animatablePathValue2 = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        AnimatableFloatValue animatableFloatValue6 = null;
        while (jsonUtf8Reader.hasNext()) {
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    animatableScaleValue = animatableScaleValue2;
                    animatableValue2 = animatableValue3;
                    jsonUtf8Reader.beginObject();
                    while (jsonUtf8Reader.hasNext()) {
                        if (jsonUtf8Reader.selectName(ANIMATABLE_NAMES) != 0) {
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                        } else {
                            animatablePathValue2 = AnimatablePathValueParser.parse(jsonUtf8Reader, lottieComposition);
                        }
                    }
                    jsonUtf8Reader.endObject();
                    animatableScaleValue2 = animatableScaleValue;
                    animatableValue3 = animatableValue2;
                    break;
                case 1:
                    animatableValue3 = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                    break;
                case 2:
                    animatableValue2 = animatableValue3;
                    animatableScaleValue2 = new AnimatableScaleValue((List<Keyframe>) KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, ScaleXYParser.INSTANCE, false));
                    animatableValue3 = animatableValue2;
                    break;
                case 3:
                    lottieComposition.addWarning("Lottie doesn't support 3D layers.");
                    AnimatableFloatValue parseFloat = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    list = parseFloat.keyframes;
                    if (list.isEmpty()) {
                        animatableFloatValue = parseFloat;
                        animatableScaleValue = animatableScaleValue2;
                        animatableValue2 = animatableValue3;
                        if (((Keyframe) list.get(0)).startValue == null) {
                            list.set(0, new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.endFrame)));
                        }
                    } else {
                        animatableFloatValue = parseFloat;
                        animatableScaleValue = animatableScaleValue2;
                        animatableValue2 = animatableValue3;
                        list.add(new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.endFrame)));
                    }
                    animatableFloatValue2 = animatableFloatValue;
                    animatableScaleValue2 = animatableScaleValue;
                    animatableValue3 = animatableValue2;
                    break;
                case 4:
                    AnimatableFloatValue parseFloat2 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    list = parseFloat2.keyframes;
                    if (list.isEmpty()) {
                    }
                    animatableFloatValue2 = animatableFloatValue;
                    animatableScaleValue2 = animatableScaleValue;
                    animatableValue3 = animatableValue2;
                    break;
                case 5:
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                    break;
                case 6:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    break;
                case 7:
                    animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    break;
                case 8:
                    animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    break;
                case 9:
                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    break;
                default:
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    break;
            }
        }
        AnimatableScaleValue animatableScaleValue3 = animatableScaleValue2;
        AnimatableValue animatableValue4 = animatableValue3;
        if (z2) {
            jsonUtf8Reader.endObject();
        }
        if (animatablePathValue2 == null || (animatablePathValue2.isStatic() && ((PointF) ((Keyframe) animatablePathValue2.keyframes.get(0)).startValue).equals(0.0f, 0.0f))) {
            animatableValue = animatableValue4;
            animatablePathValue = null;
        } else {
            animatablePathValue = animatablePathValue2;
            animatableValue = animatableValue4;
        }
        AnimatableValue animatableValue5 = animatableValue == null || (!(animatableValue instanceof AnimatableSplitDimensionPathValue) && animatableValue.isStatic() && ((PointF) ((Keyframe) animatableValue.getKeyframes().get(0)).startValue).equals(0.0f, 0.0f)) ? null : animatableValue;
        if (animatableFloatValue2 == null || (animatableFloatValue2.isStatic() && ((Float) ((Keyframe) animatableFloatValue2.keyframes.get(0)).startValue).floatValue() == 0.0f)) {
            animatableFloatValue2 = null;
        }
        if (animatableScaleValue3 != null) {
            if (animatableScaleValue3.isStatic()) {
                ScaleXY scaleXY = (ScaleXY) ((Keyframe) animatableScaleValue3.keyframes.get(0)).startValue;
            }
            z = false;
            AnimatableScaleValue animatableScaleValue4 = !z ? null : animatableScaleValue3;
            if (animatableFloatValue4 != null || (animatableFloatValue4.isStatic() && ((Float) ((Keyframe) animatableFloatValue4.keyframes.get(0)).startValue).floatValue() == 0.0f)) {
                animatableFloatValue4 = null;
            }
            return new AnimatableTransform(animatablePathValue, animatableValue5, animatableScaleValue4, animatableFloatValue2, animatableIntegerValue, animatableFloatValue5, animatableFloatValue6, animatableFloatValue4, !(animatableFloatValue3 != null || (animatableFloatValue3.isStatic() && (((Float) ((Keyframe) animatableFloatValue3.keyframes.get(0)).startValue).floatValue() > 0.0f ? 1 : (((Float) ((Keyframe) animatableFloatValue3.keyframes.get(0)).startValue).floatValue() == 0.0f ? 0 : -1)) == 0)) ? null : animatableFloatValue3);
        }
        z = true;
        if (!z) {
        }
        if (animatableFloatValue4 != null || (animatableFloatValue4.isStatic() && ((Float) ((Keyframe) animatableFloatValue4.keyframes.get(0)).startValue).floatValue() == 0.0f)) {
        }
        return new AnimatableTransform(animatablePathValue, animatableValue5, animatableScaleValue4, animatableFloatValue2, animatableIntegerValue, animatableFloatValue5, animatableFloatValue6, animatableFloatValue4, !(animatableFloatValue3 != null || (animatableFloatValue3.isStatic() && (((Float) ((Keyframe) animatableFloatValue3.keyframes.get(0)).startValue).floatValue() > 0.0f ? 1 : (((Float) ((Keyframe) animatableFloatValue3.keyframes.get(0)).startValue).floatValue() == 0.0f ? 0 : -1)) == 0)) ? null : animatableFloatValue3);
    }
}
