package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes.dex */
public final class PathParser implements ValueParser<PointF> {
    public static final PathParser INSTANCE = new PathParser();

    @Override // com.airbnb.lottie.parser.ValueParser
    public final PointF parse(JsonReader jsonReader, float f) throws IOException {
        return JsonUtils.jsonToPoint(jsonReader, f);
    }
}
