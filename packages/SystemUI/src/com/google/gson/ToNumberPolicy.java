package com.google.gson;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.math.BigDecimal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum ToNumberPolicy implements ToNumberStrategy {
    DOUBLE { // from class: com.google.gson.ToNumberPolicy.1
        @Override // com.google.gson.ToNumberStrategy
        public Double readNumber(JsonReader jsonReader) throws IOException {
            return Double.valueOf(jsonReader.nextDouble());
        }
    },
    LAZILY_PARSED_NUMBER { // from class: com.google.gson.ToNumberPolicy.2
        @Override // com.google.gson.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) throws IOException {
            return new LazilyParsedNumber(jsonReader.nextString());
        }
    },
    LONG_OR_DOUBLE { // from class: com.google.gson.ToNumberPolicy.3
        @Override // com.google.gson.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) throws IOException, JsonParseException {
            String nextString = jsonReader.nextString();
            try {
                try {
                    return Long.valueOf(Long.parseLong(nextString));
                } catch (NumberFormatException e) {
                    StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Cannot parse ", nextString, "; at path ");
                    m.append(jsonReader.getPreviousPath());
                    throw new JsonParseException(m.toString(), e);
                }
            } catch (NumberFormatException unused) {
                Double valueOf = Double.valueOf(nextString);
                if (!valueOf.isInfinite()) {
                    if (valueOf.isNaN()) {
                    }
                    return valueOf;
                }
                if (!jsonReader.isLenient()) {
                    throw new MalformedJsonException("JSON forbids NaN and infinities: " + valueOf + "; at path " + jsonReader.getPreviousPath());
                }
                return valueOf;
            }
        }
    },
    BIG_DECIMAL { // from class: com.google.gson.ToNumberPolicy.4
        @Override // com.google.gson.ToNumberStrategy
        public BigDecimal readNumber(JsonReader jsonReader) throws IOException {
            String nextString = jsonReader.nextString();
            try {
                return new BigDecimal(nextString);
            } catch (NumberFormatException e) {
                StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Cannot parse ", nextString, "; at path ");
                m.append(jsonReader.getPreviousPath());
                throw new JsonParseException(m.toString(), e);
            }
        }
    }
}
