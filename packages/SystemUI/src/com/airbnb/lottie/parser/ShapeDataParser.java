package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class ShapeDataParser implements ValueParser {
    public static final ShapeDataParser INSTANCE = new ShapeDataParser();
    public static final JsonReader.Options NAMES = JsonReader.Options.of("c", "v", "i", "o");

    private ShapeDataParser() {
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        if (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
        }
        jsonReader.beginObject();
        List listJsonToPoints = null;
        List listJsonToPoints2 = null;
        List listJsonToPoints3 = null;
        boolean zNextBoolean = false;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                zNextBoolean = jsonReader.nextBoolean();
            } else if (iSelectName == 1) {
                listJsonToPoints = JsonUtils.jsonToPoints(jsonReader, f);
            } else if (iSelectName == 2) {
                listJsonToPoints2 = JsonUtils.jsonToPoints(jsonReader, f);
            } else if (iSelectName != 3) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                listJsonToPoints3 = JsonUtils.jsonToPoints(jsonReader, f);
            }
        }
        jsonReader.endObject();
        if (jsonReader.peek() == JsonReader.Token.END_ARRAY) {
            jsonReader.endArray();
        }
        if (listJsonToPoints == null || listJsonToPoints2 == null || listJsonToPoints3 == null) {
            throw new IllegalArgumentException("Shape data was missing information.");
        }
        if (listJsonToPoints.isEmpty()) {
            return new ShapeData(new PointF(), false, Collections.EMPTY_LIST);
        }
        int size = listJsonToPoints.size();
        PointF pointF = (PointF) listJsonToPoints.get(0);
        ArrayList arrayList = new ArrayList(size);
        for (int i = 1; i < size; i++) {
            PointF pointF2 = (PointF) listJsonToPoints.get(i);
            int i2 = i - 1;
            arrayList.add(new CubicCurveData(MiscUtils.addPoints((PointF) listJsonToPoints.get(i2), (PointF) listJsonToPoints3.get(i2)), MiscUtils.addPoints(pointF2, (PointF) listJsonToPoints2.get(i)), pointF2));
        }
        if (zNextBoolean) {
            PointF pointF3 = (PointF) listJsonToPoints.get(0);
            int i3 = size - 1;
            arrayList.add(new CubicCurveData(MiscUtils.addPoints((PointF) listJsonToPoints.get(i3), (PointF) listJsonToPoints3.get(i3)), MiscUtils.addPoints(pointF3, (PointF) listJsonToPoints2.get(0)), pointF3));
        }
        return new ShapeData(pointF, zNextBoolean, arrayList);
    }
}
