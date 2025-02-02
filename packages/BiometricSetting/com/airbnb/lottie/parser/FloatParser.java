package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes.dex */
public final class FloatParser implements ValueParser<Float> {
    public static final FloatParser INSTANCE = new FloatParser();

    @Override // com.airbnb.lottie.parser.ValueParser
    public final Float parse(JsonReader jsonReader, float f) throws IOException {
        return Float.valueOf(JsonUtils.valueFromObject(jsonReader) * f);
    }
}
