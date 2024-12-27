package com.android.internal.vibrator.persistence;

import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public final class XmlReader {
    public static void readDocumentStartTag(TypedXmlPullParser parser, String expectedRootTag) throws XmlParserException, IOException {
        readDocumentStart(parser);
        String tagName = parser.getName();
        XmlValidator.checkParserCondition(expectedRootTag.equals(tagName), "Unexpected root tag found %s, expected %s", tagName, expectedRootTag);
    }

    public static void readDocumentStart(TypedXmlPullParser parser) throws XmlParserException, IOException {
        try {
            int type = parser.getEventType();
            Preconditions.checkArgument(type == 0, "Unexpected type, expected %d", Integer.valueOf(type));
            parser.nextTag();
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException("document start tag", e);
        }
    }

    public static void readDocumentEndTag(TypedXmlPullParser parser) throws XmlParserException, IOException {
        try {
            boolean z = true;
            XmlValidator.checkParserCondition(parser.getEventType() == 3, "Unexpected element at document end, expected end of root tag", new Object[0]);
            int type = parser.next();
            if (type == 4 && parser.isWhitespace()) {
                type = parser.next();
            }
            if (type != 1) {
                z = false;
            }
            XmlValidator.checkParserCondition(z, "Unexpected tag found %s, expected document end", parser.getName());
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException("document end tag", e);
        }
    }

    public static boolean readNextTagWithin(TypedXmlPullParser parser, int outerDepth) throws XmlParserException, IOException {
        try {
            if (parser.getEventType() == 3 && parser.getDepth() == outerDepth) {
                return false;
            }
            int type = parser.nextTag();
            if (type == 2 && parser.getDepth() == outerDepth + 1) {
                return true;
            }
            XmlValidator.checkParserCondition(type == 3 && parser.getDepth() == outerDepth, "Unexpected tag found %s, expected end tag at depth %d", parser.getName(), Integer.valueOf(outerDepth));
            return false;
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(parser.getName(), e);
        }
    }

    public static void readEndTag(TypedXmlPullParser parser) throws XmlParserException, IOException {
        readEndTag(parser, parser.getName(), parser.getDepth());
    }

    public static void readEndTag(TypedXmlPullParser parser, String tagName, int tagDepth) throws XmlParserException, IOException {
        boolean hasNestedTag = readNextTagWithin(parser, tagDepth);
        XmlValidator.checkParserCondition(!hasNestedTag, "Unexpected nested tag %s found in tag %s", parser.getName(), tagName);
    }

    public static int readAttributeIntNonNegative(TypedXmlPullParser parser, String attrName, int defaultValue) throws XmlParserException {
        if (parser.getAttributeIndex(XmlConstants.NAMESPACE, attrName) < 0) {
            return defaultValue;
        }
        return readAttributeIntNonNegative(parser, attrName);
    }

    public static int readAttributeIntNonNegative(TypedXmlPullParser parser, String attrName) throws XmlParserException {
        String tagName = parser.getName();
        int value = readAttributeInt(parser, attrName);
        XmlValidator.checkParserCondition(value >= 0, "Unexpected %s = %d in tag %s, expected %s >= 0", attrName, Integer.valueOf(value), tagName, attrName);
        return value;
    }

    public static int readAttributeIntInRange(TypedXmlPullParser parser, String attrName, int lowerInclusive, int upperInclusive) throws XmlParserException {
        String tagName = parser.getName();
        int value = readAttributeInt(parser, attrName);
        XmlValidator.checkParserCondition(value >= lowerInclusive && value <= upperInclusive, "Unexpected %s = %d in tag %s, expected %s in [%d, %d]", attrName, Integer.valueOf(value), tagName, attrName, Integer.valueOf(lowerInclusive), Integer.valueOf(upperInclusive));
        return value;
    }

    public static float readAttributeFloatInRange(TypedXmlPullParser parser, String attrName, float lowerInclusive, float upperInclusive, float defaultValue) throws XmlParserException {
        if (parser.getAttributeIndex(XmlConstants.NAMESPACE, attrName) < 0) {
            return defaultValue;
        }
        String tagName = parser.getName();
        float value = readAttributeFloat(parser, attrName);
        XmlValidator.checkParserCondition(value >= lowerInclusive && value <= upperInclusive, "Unexpected %s = %f in tag %s, expected %s in [%f, %f]", attrName, Float.valueOf(value), tagName, attrName, Float.valueOf(lowerInclusive), Float.valueOf(upperInclusive));
        return value;
    }

    private static int readAttributeInt(TypedXmlPullParser parser, String attrName) throws XmlParserException {
        String tagName = parser.getName();
        try {
            return parser.getAttributeInt(XmlConstants.NAMESPACE, attrName);
        } catch (XmlPullParserException e) {
            String rawValue = parser.getAttributeValue(XmlConstants.NAMESPACE, attrName);
            throw XmlParserException.createFromPullParserException(tagName, attrName, rawValue, e);
        }
    }

    private static float readAttributeFloat(TypedXmlPullParser parser, String attrName) throws XmlParserException {
        String tagName = parser.getName();
        try {
            return parser.getAttributeFloat(XmlConstants.NAMESPACE, attrName);
        } catch (XmlPullParserException e) {
            String rawValue = parser.getAttributeValue(XmlConstants.NAMESPACE, attrName);
            throw XmlParserException.createFromPullParserException(tagName, attrName, rawValue, e);
        }
    }
}
