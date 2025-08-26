package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Utils;

/* loaded from: classes.dex */
public class AnimatableValueParser {
    private AnimatableValueParser() {
    }

    public static AnimatableColorValue parseColor(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        return new AnimatableColorValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, ColorParser.INSTANCE, false));
    }

    public static AnimatableFloatValue parseFloat(JsonReader jsonReader, LottieComposition lottieComposition, boolean z) {
        return new AnimatableFloatValue(KeyframesParser.parse(jsonReader, lottieComposition, z ? Utils.dpScale() : 1.0f, FloatParser.INSTANCE, false));
    }

    public static AnimatableIntegerValue parseInteger(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        return new AnimatableIntegerValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, IntegerParser.INSTANCE, false));
    }

    public static AnimatablePointValue parsePoint(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        return new AnimatablePointValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), PointFParser.INSTANCE, true));
    }
}
