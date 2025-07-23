package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableSplitDimensionPathValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AnimatablePathValueParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("k", "x", "y");

    private AnimatablePathValueParser() {
    }

    public static AnimatablePathValue parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        ArrayList arrayList = new ArrayList();
        if (jsonUtf8Reader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonUtf8Reader.beginArray();
            while (jsonUtf8Reader.hasNext()) {
                JsonUtf8Reader jsonUtf8Reader2 = jsonUtf8Reader;
                LottieComposition lottieComposition2 = lottieComposition;
                arrayList.add(new PathKeyframe(lottieComposition2, KeyframeParser.parse(jsonUtf8Reader2, lottieComposition2, Utils.dpScale(), PathParser.INSTANCE, jsonUtf8Reader.peek() == JsonReader.Token.BEGIN_OBJECT, false)));
                jsonUtf8Reader = jsonUtf8Reader2;
                lottieComposition = lottieComposition2;
            }
            jsonUtf8Reader.endArray();
            KeyframesParser.setEndFrames(arrayList);
        } else {
            arrayList.add(new Keyframe(JsonUtils.jsonToPoint(jsonUtf8Reader, Utils.dpScale())));
        }
        return new AnimatablePathValue(arrayList);
    }

    public static AnimatableValue parseSplitPath(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        jsonUtf8Reader.beginObject();
        AnimatablePathValue animatablePathValue = null;
        AnimatableFloatValue animatableFloatValue = null;
        boolean z = false;
        AnimatableFloatValue animatableFloatValue2 = null;
        while (jsonUtf8Reader.peek() != JsonReader.Token.END_OBJECT) {
            int selectName = jsonUtf8Reader.selectName(NAMES);
            if (selectName == 0) {
                animatablePathValue = parse(jsonUtf8Reader, lottieComposition);
            } else if (selectName != 1) {
                if (selectName != 2) {
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                } else if (jsonUtf8Reader.peek() == JsonReader.Token.STRING) {
                    jsonUtf8Reader.skipValue();
                    z = true;
                } else {
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                }
            } else if (jsonUtf8Reader.peek() == JsonReader.Token.STRING) {
                jsonUtf8Reader.skipValue();
                z = true;
            } else {
                animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
            }
        }
        jsonUtf8Reader.endObject();
        if (z) {
            lottieComposition.addWarning("Lottie doesn't support expressions.");
        }
        return animatablePathValue != null ? animatablePathValue : new AnimatableSplitDimensionPathValue(animatableFloatValue2, animatableFloatValue);
    }
}
