package com.android.internal.vibrator.persistence;

import android.os.VibrationEffect;
import android.text.TextUtils;
import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlPullParser;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class XmlValidator {
    public static void checkStartTag(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        checkStartTag(typedXmlPullParser);
        checkParserCondition(str.equals(typedXmlPullParser.getName()), "Unexpected start tag found %s, expected %s", typedXmlPullParser.getName(), str);
    }

    public static void checkStartTag(TypedXmlPullParser typedXmlPullParser) throws XmlParserException {
        try {
            checkParserCondition(typedXmlPullParser.getEventType() == 2, "Expected start tag, got " + typedXmlPullParser.getEventType(), new Object[0]);
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(typedXmlPullParser.getName(), e);
        }
    }

    public static void checkTagHasNoUnexpectedAttributes(TypedXmlPullParser typedXmlPullParser, String... strArr) throws XmlParserException {
        if (strArr == null || strArr.length == 0) {
            checkParserCondition(typedXmlPullParser.getAttributeCount() == 0, "Unexpected attributes in tag %s, expected no attributes", typedXmlPullParser.getName());
            return;
        }
        String name = typedXmlPullParser.getName();
        int attributeCount = typedXmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = typedXmlPullParser.getAttributeName(i);
            checkParserCondition(ArrayUtils.contains(strArr, attributeName), "Unexpected attribute %s found in tag %s", attributeName, name);
        }
    }

    public static void checkSerializedVibration(XmlSerializedVibration<? extends VibrationEffect> xmlSerializedVibration, VibrationEffect vibrationEffect) throws XmlSerializerException {
        VibrationEffect vibrationEffect2 = (VibrationEffect) Objects.requireNonNull(xmlSerializedVibration.deserialize());
        checkSerializerCondition(Objects.equals(vibrationEffect, vibrationEffect2), "Unexpected serialized vibration %s: found deserialization %s, expected %s", xmlSerializedVibration, vibrationEffect2, vibrationEffect);
    }

    public static void checkSerializerCondition(boolean z, String str, Object... objArr) throws XmlSerializerException {
        if (!z) {
            throw new XmlSerializerException(TextUtils.formatSimple(str, objArr));
        }
    }

    public static void checkParserCondition(boolean z, String str, Object... objArr) throws XmlParserException {
        if (!z) {
            throw new XmlParserException(TextUtils.formatSimple(str, objArr));
        }
    }
}
