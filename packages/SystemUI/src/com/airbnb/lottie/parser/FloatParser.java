package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FloatParser implements ValueParser {
    public static final FloatParser INSTANCE = new FloatParser();

    private FloatParser() {
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        return Float.valueOf(JsonUtils.valueFromObject(jsonReader) * f);
    }
}
