package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyframesParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.m50of("k");

    private KeyframesParser() {
    }

    public static List parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z) {
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
                    arrayList.add(KeyframeParser.parse(jsonReader, lottieComposition, f, valueParser, false, z));
                } else {
                    while (jsonReader.hasNext()) {
                        arrayList.add(KeyframeParser.parse(jsonReader, lottieComposition, f, valueParser, true, z));
                    }
                }
                jsonReader.endArray();
            } else {
                arrayList.add(KeyframeParser.parse(jsonReader, lottieComposition, f, valueParser, false, z));
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
