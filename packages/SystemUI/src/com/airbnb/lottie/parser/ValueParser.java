package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

/* loaded from: classes.dex */
public interface ValueParser {
    Object parse(JsonReader jsonReader, float f);
}
