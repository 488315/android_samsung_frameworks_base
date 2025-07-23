package com.android.internal.vibrator.persistence;

import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class XmlReader {
    public static void readDocumentStartTag(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException, IOException {
        readDocumentStart(typedXmlPullParser);
        String name = typedXmlPullParser.getName();
        XmlValidator.checkParserCondition(str.equals(name), "Unexpected root tag found %s, expected %s", name, str);
    }

    public static void readDocumentStart(TypedXmlPullParser typedXmlPullParser) throws XmlParserException, IOException {
        try {
            int eventType = typedXmlPullParser.getEventType();
            Preconditions.checkArgument(eventType == 0, "Unexpected type, expected %d", Integer.valueOf(eventType));
            typedXmlPullParser.nextTag();
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException("document start tag", e);
        }
    }

    public static void readDocumentEndTag(TypedXmlPullParser typedXmlPullParser) throws XmlParserException, IOException {
        try {
            boolean z = true;
            XmlValidator.checkParserCondition(typedXmlPullParser.getEventType() == 3, "Unexpected element at document end, expected end of root tag", new Object[0]);
            int next = typedXmlPullParser.next();
            if (next == 4 && typedXmlPullParser.isWhitespace()) {
                next = typedXmlPullParser.next();
            }
            if (next != 1) {
                z = false;
            }
            XmlValidator.checkParserCondition(z, "Unexpected tag found %s, expected document end", typedXmlPullParser.getName());
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException("document end tag", e);
        }
    }

    public static boolean readNextTagWithin(TypedXmlPullParser typedXmlPullParser, int i) throws XmlParserException, IOException {
        try {
            if (typedXmlPullParser.getEventType() == 3 && typedXmlPullParser.getDepth() == i) {
                return false;
            }
            int nextTag = typedXmlPullParser.nextTag();
            if (nextTag == 2 && typedXmlPullParser.getDepth() == i + 1) {
                return true;
            }
            XmlValidator.checkParserCondition(nextTag == 3 && typedXmlPullParser.getDepth() == i, "Unexpected tag found %s, expected end tag at depth %d", typedXmlPullParser.getName(), Integer.valueOf(i));
            return false;
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(typedXmlPullParser.getName(), e);
        }
    }

    public static void readNextText(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException, IOException {
        try {
            int next = typedXmlPullParser.next();
            XmlValidator.checkParserCondition(next == 4, "Unexpected event %s of type %d, expected text event inside tag %s", typedXmlPullParser.getName(), Integer.valueOf(next), str);
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException("text event", e);
        }
    }

    public static void readEndTag(TypedXmlPullParser typedXmlPullParser) throws XmlParserException, IOException {
        readEndTag(typedXmlPullParser, typedXmlPullParser.getName(), typedXmlPullParser.getDepth());
    }

    public static void readEndTag(TypedXmlPullParser typedXmlPullParser, String str, int i) throws XmlParserException, IOException {
        XmlValidator.checkParserCondition(!readNextTagWithin(typedXmlPullParser, i), "Unexpected nested tag %s found in tag %s", typedXmlPullParser.getName(), str);
    }

    public static int readAttributeIntNonNegative(TypedXmlPullParser typedXmlPullParser, String str, int i) throws XmlParserException {
        return typedXmlPullParser.getAttributeIndex(XmlConstants.NAMESPACE, str) < 0 ? i : readAttributeIntNonNegative(typedXmlPullParser, str);
    }

    public static int readAttributeIntNonNegative(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        int readAttributeInt = readAttributeInt(typedXmlPullParser, str);
        XmlValidator.checkParserCondition(readAttributeInt >= 0, "Unexpected %s = %d in tag %s, expected %s >= 0", str, Integer.valueOf(readAttributeInt), name, str);
        return readAttributeInt;
    }

    public static int readAttributeIntInRange(TypedXmlPullParser typedXmlPullParser, String str, int i, int i2) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        int readAttributeInt = readAttributeInt(typedXmlPullParser, str);
        XmlValidator.checkParserCondition(readAttributeInt >= i && readAttributeInt <= i2, "Unexpected %s = %d in tag %s, expected %s in [%d, %d]", str, Integer.valueOf(readAttributeInt), name, str, Integer.valueOf(i), Integer.valueOf(i2));
        return readAttributeInt;
    }

    public static float readAttributeFloatInRange(TypedXmlPullParser typedXmlPullParser, String str, float f, float f2, float f3) throws XmlParserException {
        return typedXmlPullParser.getAttributeIndex(XmlConstants.NAMESPACE, str) < 0 ? f3 : readAttributeFloatInRange(typedXmlPullParser, str, f, f2);
    }

    public static float readAttributeFloatInRange(TypedXmlPullParser typedXmlPullParser, String str, float f, float f2) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        float readAttributeFloat = readAttributeFloat(typedXmlPullParser, str);
        XmlValidator.checkParserCondition(readAttributeFloat >= f && readAttributeFloat <= f2, "Unexpected %s = %f in tag %s, expected %s in [%f, %f]", str, Float.valueOf(readAttributeFloat), name, str, Float.valueOf(f), Float.valueOf(f2));
        return readAttributeFloat;
    }

    public static float readAttributePositiveFloat(TypedXmlPullParser typedXmlPullParser, String str, float f) throws XmlParserException {
        return typedXmlPullParser.getAttributeIndex(XmlConstants.NAMESPACE, str) < 0 ? f : readAttributePositiveFloat(typedXmlPullParser, str);
    }

    public static float readAttributePositiveFloat(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        float readAttributeFloat = readAttributeFloat(typedXmlPullParser, str);
        XmlValidator.checkParserCondition(readAttributeFloat > 0.0f, "Unexpected %s = %d in tag %s, expected %s > 0", str, Float.valueOf(readAttributeFloat), name, str);
        return readAttributeFloat;
    }

    public static long readAttributePositiveLong(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        long readAttributeLong = readAttributeLong(typedXmlPullParser, str);
        XmlValidator.checkParserCondition(readAttributeLong > 0, "Unexpected %s = %d in tag %s, expected %s > 0", str, Long.valueOf(readAttributeLong), name, str);
        return readAttributeLong;
    }

    private static int readAttributeInt(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        try {
            return typedXmlPullParser.getAttributeInt(XmlConstants.NAMESPACE, str);
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(name, str, typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, str), e);
        }
    }

    private static float readAttributeFloat(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        try {
            return typedXmlPullParser.getAttributeFloat(XmlConstants.NAMESPACE, str);
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(name, str, typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, str), e);
        }
    }

    private static long readAttributeLong(TypedXmlPullParser typedXmlPullParser, String str) throws XmlParserException {
        String name = typedXmlPullParser.getName();
        try {
            return typedXmlPullParser.getAttributeLong(XmlConstants.NAMESPACE, str);
        } catch (XmlPullParserException e) {
            throw XmlParserException.createFromPullParserException(name, str, typedXmlPullParser.getAttributeValue(XmlConstants.NAMESPACE, str), e);
        }
    }
}
