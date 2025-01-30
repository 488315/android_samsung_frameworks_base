package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.parser.moshi.JsonReader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DropShadowEffectParser {
    public static final JsonReader.Options DROP_SHADOW_EFFECT_NAMES = JsonReader.Options.m50of("ef");
    public static final JsonReader.Options INNER_EFFECT_NAMES = JsonReader.Options.m50of("nm", "v");
    public AnimatableColorValue color;
    public AnimatableFloatValue direction;
    public AnimatableFloatValue distance;
    public AnimatableFloatValue opacity;
    public AnimatableFloatValue radius;
}
