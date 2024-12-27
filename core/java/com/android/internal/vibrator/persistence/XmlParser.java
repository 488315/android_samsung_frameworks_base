package com.android.internal.vibrator.persistence;

import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;

@FunctionalInterface
/* loaded from: classes5.dex */
public interface XmlParser<T> {
    XmlSerializedVibration<T> parseTag(TypedXmlPullParser typedXmlPullParser) throws XmlParserException, IOException;
}
