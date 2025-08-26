package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

/* loaded from: classes.dex */
public class AnimatableTextPropertiesParser {
    public static final JsonReader.Options PROPERTIES_NAMES = JsonReader.Options.of("a");
    public static final JsonReader.Options ANIMATABLE_PROPERTIES_NAMES = JsonReader.Options.of("fc", "sc", "sw", "t");

    private AnimatableTextPropertiesParser() {
    }
}
