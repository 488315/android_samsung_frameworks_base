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
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import java.io.IOException;

/* loaded from: classes.dex */
public final class AnimatableTransformParser {
    private static JsonReader.Options NAMES = JsonReader.Options.m8of("a", "p", "s", "rz", "r", "o", "so", "eo", "sk", "sa");
    private static JsonReader.Options ANIMATABLE_NAMES = JsonReader.Options.m8of("k");

    /* JADX WARN: Multi-variable type inference failed */
    public static AnimatableTransform parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT;
        if (z) {
            jsonReader.beginObject();
        }
        AnimatableFloatValue animatableFloatValue = null;
        AnimatablePathValue animatablePathValue = null;
        AnimatableValue<PointF, PointF> animatableValue = null;
        AnimatableScaleValue animatableScaleValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        AnimatableFloatValue animatableFloatValue4 = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.selectName(ANIMATABLE_NAMES) != 0) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            animatablePathValue = AnimatablePathValueParser.parse(jsonReader, lottieComposition);
                        }
                    }
                    jsonReader.endObject();
                    continue;
                case 1:
                    animatableValue = AnimatablePathValueParser.parseSplitPath(jsonReader, lottieComposition);
                    continue;
                case 2:
                    animatableScaleValue = AnimatableValueParser.parseScale(jsonReader, lottieComposition);
                    continue;
                case 3:
                    lottieComposition.addWarning("Lottie doesn't support 3D layers.");
                    break;
                case 4:
                    break;
                case 5:
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    continue;
                case 6:
                    animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    continue;
                case 7:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    continue;
                case 8:
                    animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    continue;
                case 9:
                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    continue;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    continue;
            }
            AnimatableFloatValue parseFloat = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            if (parseFloat.getKeyframes().isEmpty()) {
                parseFloat.getKeyframes().add(new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.getEndFrame())));
            } else if (((Keyframe) parseFloat.getKeyframes().get(0)).startValue == 0) {
                parseFloat.getKeyframes().set(0, new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(lottieComposition.getEndFrame())));
            }
            animatableFloatValue = parseFloat;
        }
        if (z) {
            jsonReader.endObject();
        }
        if (animatablePathValue == null || (animatablePathValue.isStatic() && animatablePathValue.getKeyframes().get(0).startValue.equals(0.0f, 0.0f))) {
            animatablePathValue = null;
        }
        if (animatableValue == null || (!(animatableValue instanceof AnimatableSplitDimensionPathValue) && animatableValue.isStatic() && animatableValue.getKeyframes().get(0).startValue.equals(0.0f, 0.0f))) {
            animatableValue = null;
        }
        return new AnimatableTransform(animatablePathValue, animatableValue, animatableScaleValue == null || (animatableScaleValue.isStatic() && ((ScaleXY) ((Keyframe) animatableScaleValue.getKeyframes().get(0)).startValue).equals()) ? null : animatableScaleValue, animatableFloatValue == null || (animatableFloatValue.isStatic() && (((Float) ((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue).floatValue() > 0.0f ? 1 : (((Float) ((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue).floatValue() == 0.0f ? 0 : -1)) == 0) ? null : animatableFloatValue, animatableIntegerValue, animatableFloatValue4, animatableFloatValue5, animatableFloatValue2 == null || (animatableFloatValue2.isStatic() && (((Float) ((Keyframe) animatableFloatValue2.getKeyframes().get(0)).startValue).floatValue() > 0.0f ? 1 : (((Float) ((Keyframe) animatableFloatValue2.getKeyframes().get(0)).startValue).floatValue() == 0.0f ? 0 : -1)) == 0) ? null : animatableFloatValue2, animatableFloatValue3 == null || (animatableFloatValue3.isStatic() && (((Float) ((Keyframe) animatableFloatValue3.getKeyframes().get(0)).startValue).floatValue() > 0.0f ? 1 : (((Float) ((Keyframe) animatableFloatValue3.getKeyframes().get(0)).startValue).floatValue() == 0.0f ? 0 : -1)) == 0) ? null : animatableFloatValue3);
    }
}
