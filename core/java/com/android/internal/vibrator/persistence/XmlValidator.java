package com.android.internal.vibrator.persistence;

import android.text.TextUtils;

import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlPullParser;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;

public final class XmlValidator {
    public static void checkStartTag(TypedXmlPullParser parser, String expectedTag)
            throws XmlParserException {
        checkStartTag(parser);
        checkParserCondition(
                expectedTag.equals(parser.getName()),
                "Unexpected start tag found %s, expected %s",
                parser.getName(),
                expectedTag);
    }

    public static void checkStartTag(TypedXmlPullParser parser) throws XmlParserException {
        try {
            checkParserCondition(
                    parser.getEventType() == 2,
                    "Expected start tag, got " + parser.getEventType(),
                    new Object[0]);
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(parser.getName(), e);
        }
    }

    public static void checkTagHasNoUnexpectedAttributes(
            TypedXmlPullParser parser, String... expectedAttributes) throws XmlParserException {
        if (expectedAttributes == null || expectedAttributes.length == 0) {
            checkParserCondition(
                    parser.getAttributeCount() == 0,
                    "Unexpected attributes in tag %s, expected no attributes",
                    parser.getName());
            return;
        }
        String tagName = parser.getName();
        int attributeCount = parser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = parser.getAttributeName(i);
            checkParserCondition(
                    ArrayUtils.contains(expectedAttributes, attributeName),
                    "Unexpected attribute %s found in tag %s",
                    attributeName,
                    tagName);
        }
    }

    public static <T> void checkSerializedVibration(
            XmlSerializedVibration<T> serializedVibration, T expectedVibration)
            throws XmlSerializerException {
        Object requireNonNull = Objects.requireNonNull(serializedVibration.deserialize());
        checkSerializerCondition(
                Objects.equals(expectedVibration, requireNonNull),
                "Unexpected serialized vibration %s: found deserialization %s, expected %s",
                serializedVibration,
                requireNonNull,
                expectedVibration);
    }

    public static void checkSerializerCondition(
            boolean expression, String messageTemplate, Object... messageArgs)
            throws XmlSerializerException {
        if (!expression) {
            throw new XmlSerializerException(TextUtils.formatSimple(messageTemplate, messageArgs));
        }
    }

    public static void checkParserCondition(
            boolean expression, String messageTemplate, Object... messageArgs)
            throws XmlParserException {
        if (!expression) {
            throw new XmlParserException(TextUtils.formatSimple(messageTemplate, messageArgs));
        }
    }
}
