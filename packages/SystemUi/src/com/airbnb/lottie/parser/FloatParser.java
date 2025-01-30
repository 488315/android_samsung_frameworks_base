package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FloatParser implements ValueParser {
    public static final FloatParser INSTANCE = new FloatParser();

    private FloatParser() {
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        return Float.valueOf(JsonUtils.valueFromObject(jsonReader) * f);
    }
}
