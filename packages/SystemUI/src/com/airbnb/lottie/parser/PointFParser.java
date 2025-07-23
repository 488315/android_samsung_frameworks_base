package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.parser.moshi.JsonReader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PointFParser implements ValueParser {
    public static final PointFParser INSTANCE = new PointFParser();

    private PointFParser() {
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        JsonReader.Token peek = jsonReader.peek();
        if (peek == JsonReader.Token.BEGIN_ARRAY) {
            return JsonUtils.jsonToPoint(jsonReader, f);
        }
        if (peek == JsonReader.Token.BEGIN_OBJECT) {
            return JsonUtils.jsonToPoint(jsonReader, f);
        }
        if (peek != JsonReader.Token.NUMBER) {
            throw new IllegalArgumentException("Cannot convert json to point. Next token is " + peek);
        }
        PointF pointF = new PointF(((float) jsonReader.nextDouble()) * f, ((float) jsonReader.nextDouble()) * f);
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        return pointF;
    }
}
