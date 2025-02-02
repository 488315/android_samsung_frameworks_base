package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;

/* loaded from: classes.dex */
public final class AnimatableValueParser {
    static AnimatableColorValue parseColor(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new AnimatableColorValue(KeyframesParser.parse(1.0f, lottieComposition, ColorParser.INSTANCE, jsonReader));
    }

    static AnimatableTextFrame parseDocumentData(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new AnimatableTextFrame(KeyframesParser.parse(1.0f, lottieComposition, DocumentDataParser.INSTANCE, jsonReader));
    }

    public static AnimatableFloatValue parseFloat(JsonReader jsonReader, LottieComposition lottieComposition, boolean z) throws IOException {
        return new AnimatableFloatValue(KeyframesParser.parse(z ? Utils.dpScale() : 1.0f, lottieComposition, FloatParser.INSTANCE, jsonReader));
    }

    static AnimatableGradientColorValue parseGradientColor(JsonReader jsonReader, LottieComposition lottieComposition, int i) throws IOException {
        return new AnimatableGradientColorValue(KeyframesParser.parse(1.0f, lottieComposition, new GradientColorParser(i), jsonReader));
    }

    static AnimatableIntegerValue parseInteger(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new AnimatableIntegerValue(KeyframesParser.parse(1.0f, lottieComposition, IntegerParser.INSTANCE, jsonReader));
    }

    static AnimatablePointValue parsePoint(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new AnimatablePointValue(KeyframesParser.parse(Utils.dpScale(), lottieComposition, PointFParser.INSTANCE, jsonReader));
    }

    static AnimatableScaleValue parseScale(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new AnimatableScaleValue(KeyframesParser.parse(1.0f, lottieComposition, ScaleXYParser.INSTANCE, jsonReader));
    }
}
