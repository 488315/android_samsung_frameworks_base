package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.PointF;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class JsonUtils {
    public static final JsonReader.Options POINT_NAMES = JsonReader.Options.m50of("x", "y");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.airbnb.lottie.parser.JsonUtils$1 */
    public abstract /* synthetic */ class AbstractC06031 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token;

        static {
            int[] iArr = new int[JsonReader.Token.values().length];
            $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token = iArr;
            try {
                iArr[JsonReader.Token.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[JsonReader.Token.BEGIN_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[JsonReader.Token.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private JsonUtils() {
    }

    public static int jsonToColor(JsonReader jsonReader) {
        jsonReader.beginArray();
        int nextDouble = (int) (jsonReader.nextDouble() * 255.0d);
        int nextDouble2 = (int) (jsonReader.nextDouble() * 255.0d);
        int nextDouble3 = (int) (jsonReader.nextDouble() * 255.0d);
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return Color.argb(255, nextDouble, nextDouble2, nextDouble3);
    }

    public static PointF jsonToPoint(JsonReader jsonReader, float f) {
        int i = AbstractC06031.$SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[jsonReader.peek().ordinal()];
        if (i == 1) {
            float nextDouble = (float) jsonReader.nextDouble();
            float nextDouble2 = (float) jsonReader.nextDouble();
            while (jsonReader.hasNext()) {
                jsonReader.skipValue();
            }
            return new PointF(nextDouble * f, nextDouble2 * f);
        }
        if (i == 2) {
            jsonReader.beginArray();
            float nextDouble3 = (float) jsonReader.nextDouble();
            float nextDouble4 = (float) jsonReader.nextDouble();
            while (jsonReader.peek() != JsonReader.Token.END_ARRAY) {
                jsonReader.skipValue();
            }
            jsonReader.endArray();
            return new PointF(nextDouble3 * f, nextDouble4 * f);
        }
        if (i != 3) {
            throw new IllegalArgumentException("Unknown point starts with " + jsonReader.peek());
        }
        jsonReader.beginObject();
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(POINT_NAMES);
            if (selectName == 0) {
                f2 = valueFromObject(jsonReader);
            } else if (selectName != 1) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                f3 = valueFromObject(jsonReader);
            }
        }
        jsonReader.endObject();
        return new PointF(f2 * f, f3 * f);
    }

    public static List jsonToPoints(JsonReader jsonReader, float f) {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
            arrayList.add(jsonToPoint(jsonReader, f));
            jsonReader.endArray();
        }
        jsonReader.endArray();
        return arrayList;
    }

    public static float valueFromObject(JsonReader jsonReader) {
        JsonReader.Token peek = jsonReader.peek();
        int i = AbstractC06031.$SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[peek.ordinal()];
        if (i == 1) {
            return (float) jsonReader.nextDouble();
        }
        if (i != 2) {
            throw new IllegalArgumentException("Unknown value for token of type " + peek);
        }
        jsonReader.beginArray();
        float nextDouble = (float) jsonReader.nextDouble();
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return nextDouble;
    }
}
