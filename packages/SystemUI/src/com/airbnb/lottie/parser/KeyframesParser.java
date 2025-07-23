package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyframesParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("k");

    private KeyframesParser() {
    }

    public static List parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z) {
        JsonReader jsonReader2;
        LottieComposition lottieComposition2;
        float f2;
        ValueParser valueParser2;
        boolean z2;
        ArrayList arrayList = new ArrayList();
        if (jsonReader.peek() == JsonReader.Token.STRING) {
            lottieComposition.addWarning("Lottie doesn't support expressions.");
            return arrayList;
        }
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(NAMES) != 0) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
                jsonReader.beginArray();
                if (jsonReader.peek() == JsonReader.Token.NUMBER) {
                    JsonReader jsonReader3 = jsonReader;
                    LottieComposition lottieComposition3 = lottieComposition;
                    float f3 = f;
                    ValueParser valueParser3 = valueParser;
                    boolean z3 = z;
                    Keyframe parse = KeyframeParser.parse(jsonReader3, lottieComposition3, f3, valueParser3, false, z3);
                    jsonReader2 = jsonReader3;
                    lottieComposition2 = lottieComposition3;
                    f2 = f3;
                    valueParser2 = valueParser3;
                    z2 = z3;
                    arrayList.add(parse);
                } else {
                    jsonReader2 = jsonReader;
                    lottieComposition2 = lottieComposition;
                    f2 = f;
                    valueParser2 = valueParser;
                    z2 = z;
                    while (jsonReader2.hasNext()) {
                        arrayList.add(KeyframeParser.parse(jsonReader2, lottieComposition2, f2, valueParser2, true, z2));
                    }
                }
                jsonReader2.endArray();
                jsonReader = jsonReader2;
                lottieComposition = lottieComposition2;
                f = f2;
                valueParser = valueParser2;
                z = z2;
            } else {
                JsonReader jsonReader4 = jsonReader;
                arrayList.add(KeyframeParser.parse(jsonReader4, lottieComposition, f, valueParser, false, z));
                jsonReader = jsonReader4;
            }
        }
        jsonReader.endObject();
        setEndFrames(arrayList);
        return arrayList;
    }

    public static void setEndFrames(List list) {
        int i;
        Object obj;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            Keyframe keyframe = (Keyframe) arrayList.get(i2);
            i2++;
            Keyframe keyframe2 = (Keyframe) arrayList.get(i2);
            keyframe.endFrame = Float.valueOf(keyframe2.startFrame);
            if (keyframe.endValue == null && (obj = keyframe2.startValue) != null) {
                keyframe.endValue = obj;
                if (keyframe instanceof PathKeyframe) {
                    ((PathKeyframe) keyframe).createPath();
                }
            }
        }
        Keyframe keyframe3 = (Keyframe) arrayList.get(i);
        if ((keyframe3.startValue == null || keyframe3.endValue == null) && arrayList.size() > 1) {
            arrayList.remove(keyframe3);
        }
    }
}
