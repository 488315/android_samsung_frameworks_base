package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.parser.moshi.JsonReader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DropShadowEffectParser {
    public static final JsonReader.Options DROP_SHADOW_EFFECT_NAMES = JsonReader.Options.of("ef");
    public static final JsonReader.Options INNER_EFFECT_NAMES = JsonReader.Options.of("nm", "v");
    public AnimatableColorValue color;
    public AnimatableFloatValue direction;
    public AnimatableFloatValue distance;
    public AnimatableFloatValue opacity;
    public AnimatableFloatValue radius;
}
