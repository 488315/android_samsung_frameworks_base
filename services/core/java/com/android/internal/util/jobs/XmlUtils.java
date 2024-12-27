package com.android.internal.util.jobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Xml;

import com.android.internal.util.HexDump;
import com.android.internal.util.XmlPullParserWrapper;
import com.android.internal.util.XmlSerializerWrapper;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import libcore.util.HexEncoding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XmlUtils {
    private static final String STRING_ARRAY_SEPARATOR = ":";

    public final class ForcedTypedXmlPullParser extends XmlPullParserWrapper
            implements TypedXmlPullParser {
        public final boolean getAttributeBoolean(int i) {
            String attributeValue = getAttributeValue(i);
            if ("true".equalsIgnoreCase(attributeValue)) {
                return true;
            }
            if ("false".equalsIgnoreCase(attributeValue)) {
                return false;
            }
            throw new XmlPullParserException(
                    "Invalid attribute " + getAttributeName(i) + ": " + attributeValue);
        }

        public final byte[] getAttributeBytesBase64(int i) {
            try {
                return Base64.decode(getAttributeValue(i), 2);
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final byte[] getAttributeBytesHex(int i) {
            try {
                return HexDump.hexStringToByteArray(getAttributeValue(i));
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final double getAttributeDouble(int i) {
            try {
                return Double.parseDouble(getAttributeValue(i));
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final float getAttributeFloat(int i) {
            try {
                return Float.parseFloat(getAttributeValue(i));
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final int getAttributeInt(int i) {
            try {
                return Integer.parseInt(getAttributeValue(i));
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final int getAttributeIntHex(int i) {
            try {
                return Integer.parseInt(getAttributeValue(i), 16);
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final long getAttributeLong(int i) {
            try {
                return Long.parseLong(getAttributeValue(i));
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }

        public final long getAttributeLongHex(int i) {
            try {
                return Long.parseLong(getAttributeValue(i), 16);
            } catch (Exception e) {
                throw new XmlPullParserException(
                        "Invalid attribute " + this.getAttributeName(i) + ": " + e);
            }
        }
    }

    public final class ForcedTypedXmlSerializer extends XmlSerializerWrapper
            implements TypedXmlSerializer {
        public final XmlSerializer attributeBoolean(String str, String str2, boolean z) {
            return attribute(str, str2, Boolean.toString(z));
        }

        public final XmlSerializer attributeBytesBase64(String str, String str2, byte[] bArr) {
            return attribute(str, str2, Base64.encodeToString(bArr, 2));
        }

        public final XmlSerializer attributeBytesHex(String str, String str2, byte[] bArr) {
            return attribute(str, str2, HexDump.toHexString(bArr));
        }

        public final XmlSerializer attributeDouble(String str, String str2, double d) {
            return attribute(str, str2, Double.toString(d));
        }

        public final XmlSerializer attributeFloat(String str, String str2, float f) {
            return attribute(str, str2, Float.toString(f));
        }

        public final XmlSerializer attributeInt(String str, String str2, int i) {
            return attribute(str, str2, Integer.toString(i));
        }

        public final XmlSerializer attributeIntHex(String str, String str2, int i) {
            return attribute(str, str2, Integer.toString(i, 16));
        }

        public final XmlSerializer attributeInterned(String str, String str2, String str3) {
            return attribute(str, str2, str3);
        }

        public final XmlSerializer attributeLong(String str, String str2, long j) {
            return attribute(str, str2, Long.toString(j));
        }

        public final XmlSerializer attributeLongHex(String str, String str2, long j) {
            return attribute(str, str2, Long.toString(j, 16));
        }
    }

    public interface ReadMapCallback {
        Object readThisUnknownObjectXml(TypedXmlPullParser typedXmlPullParser, String str)
                throws XmlPullParserException, IOException;
    }

    public interface WriteMapCallback {
        void writeUnknownObject(Object obj, String str, TypedXmlSerializer typedXmlSerializer)
                throws XmlPullParserException, IOException;
    }

    public static final void beginDocument(XmlPullParser xmlPullParser, String str)
            throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        if (xmlPullParser.getName().equals(str)) {
            return;
        }
        throw new XmlPullParserException(
                "Unexpected start tag: found " + xmlPullParser.getName() + ", expected " + str);
    }

    public static final boolean convertValueToBoolean(CharSequence charSequence, boolean z) {
        return TextUtils.isEmpty(charSequence)
                ? z
                : charSequence.equals("1")
                        || charSequence.equals("true")
                        || charSequence.equals("TRUE");
    }

    public static final int convertValueToInt(CharSequence charSequence, int i) {
        int i2;
        int i3;
        if (TextUtils.isEmpty(charSequence)) {
            return i;
        }
        String charSequence2 = charSequence.toString();
        int length = charSequence2.length();
        if ('-' == charSequence2.charAt(0)) {
            i3 = -1;
            i2 = 1;
        } else {
            i2 = 0;
            i3 = 1;
        }
        int i4 = 16;
        if ('0' == charSequence2.charAt(i2)) {
            if (i2 == length - 1) {
                return 0;
            }
            int i5 = i2 + 1;
            char charAt = charSequence2.charAt(i5);
            if ('x' == charAt || 'X' == charAt) {
                i2 += 2;
            } else {
                i4 = 8;
                i2 = i5;
            }
        } else if ('#' == charSequence2.charAt(i2)) {
            i2++;
        } else {
            i4 = 10;
        }
        return Integer.parseInt(charSequence2.substring(i2), i4) * i3;
    }

    public static final int convertValueToList(CharSequence charSequence, String[] strArr, int i) {
        if (!TextUtils.isEmpty(charSequence)) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (charSequence.equals(strArr[i2])) {
                    return i2;
                }
            }
        }
        return i;
    }

    public static int convertValueToUnsignedInt(String str, int i) {
        return TextUtils.isEmpty(str) ? i : parseUnsignedIntAttribute(str);
    }

    public static TypedXmlPullParser makeTyped(XmlPullParser xmlPullParser) {
        return xmlPullParser instanceof TypedXmlPullParser
                ? (TypedXmlPullParser) xmlPullParser
                : new ForcedTypedXmlPullParser(xmlPullParser);
    }

    public static TypedXmlSerializer makeTyped(XmlSerializer xmlSerializer) {
        return xmlSerializer instanceof TypedXmlSerializer
                ? (TypedXmlSerializer) xmlSerializer
                : new ForcedTypedXmlSerializer(xmlSerializer);
    }

    public static final void nextElement(XmlPullParser xmlPullParser)
            throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                return;
            }
        } while (next != 1);
    }

    public static boolean nextElementWithin(XmlPullParser xmlPullParser, int i)
            throws IOException, XmlPullParserException {
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return false;
            }
            if (next == 3 && xmlPullParser.getDepth() == i) {
                return false;
            }
            if (next == 2 && xmlPullParser.getDepth() == i + 1) {
                return true;
            }
        }
    }

    public static int parseUnsignedIntAttribute(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int length = charSequence2.length();
        int i = 0;
        int i2 = 16;
        if ('0' != charSequence2.charAt(0)) {
            if ('#' != charSequence2.charAt(0)) {
                i2 = 10;
                return (int) Long.parseLong(charSequence2.substring(i), i2);
            }
            i = 1;
            return (int) Long.parseLong(charSequence2.substring(i), i2);
        }
        if (length - 1 == 0) {
            return 0;
        }
        char charAt = charSequence2.charAt(1);
        if ('x' == charAt || 'X' == charAt) {
            i = 2;
            return (int) Long.parseLong(charSequence2.substring(i), i2);
        }
        i2 = 8;
        i = 1;
        return (int) Long.parseLong(charSequence2.substring(i), i2);
    }

    public static Bitmap readBitmapAttribute(XmlPullParser xmlPullParser, String str) {
        byte[] readByteArrayAttribute = readByteArrayAttribute(xmlPullParser, str);
        if (readByteArrayAttribute != null) {
            return BitmapFactory.decodeByteArray(
                    readByteArrayAttribute, 0, readByteArrayAttribute.length);
        }
        return null;
    }

    public static boolean readBooleanAttribute(XmlPullParser xmlPullParser, String str) {
        return readBooleanAttribute(xmlPullParser, str, false);
    }

    public static boolean readBooleanAttribute(XmlPullParser xmlPullParser, String str, boolean z) {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            return ((TypedXmlPullParser) xmlPullParser).getAttributeBoolean((String) null, str, z);
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return TextUtils.isEmpty(attributeValue) ? z : Boolean.parseBoolean(attributeValue);
    }

    public static byte[] readByteArrayAttribute(XmlPullParser xmlPullParser, String str) {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            try {
                return ((TypedXmlPullParser) xmlPullParser)
                        .getAttributeBytesBase64((String) null, str);
            } catch (XmlPullParserException unused) {
                return null;
            }
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return null;
        }
        return Base64.decode(attributeValue, 0);
    }

    public static float readFloatAttribute(XmlPullParser xmlPullParser, String str)
            throws IOException {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            try {
                return ((TypedXmlPullParser) xmlPullParser).getAttributeFloat((String) null, str);
            } catch (XmlPullParserException e) {
                throw new ProtocolException(e.getMessage());
            }
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        try {
            return Float.parseFloat(attributeValue);
        } catch (NumberFormatException unused) {
            throw new ProtocolException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "problem parsing ", str, "=", attributeValue, " as long"));
        }
    }

    public static int readIntAttribute(XmlPullParser xmlPullParser, String str) throws IOException {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            try {
                return ((TypedXmlPullParser) xmlPullParser).getAttributeInt((String) null, str);
            } catch (XmlPullParserException e) {
                throw new ProtocolException(e.getMessage());
            }
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        try {
            return Integer.parseInt(attributeValue);
        } catch (NumberFormatException unused) {
            throw new ProtocolException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "problem parsing ", str, "=", attributeValue, " as int"));
        }
    }

    public static int readIntAttribute(XmlPullParser xmlPullParser, String str, int i) {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            return ((TypedXmlPullParser) xmlPullParser).getAttributeInt((String) null, str, i);
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return i;
        }
        try {
            return Integer.parseInt(attributeValue);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static final ArrayList readListXml(InputStream inputStream)
            throws XmlPullParserException, IOException {
        TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        return (ArrayList) readValueXml(newFastPullParser, new String[1]);
    }

    public static long readLongAttribute(XmlPullParser xmlPullParser, String str)
            throws IOException {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            try {
                return ((TypedXmlPullParser) xmlPullParser).getAttributeLong((String) null, str);
            } catch (XmlPullParserException e) {
                throw new ProtocolException(e.getMessage());
            }
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        try {
            return Long.parseLong(attributeValue);
        } catch (NumberFormatException unused) {
            throw new ProtocolException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "problem parsing ", str, "=", attributeValue, " as long"));
        }
    }

    public static long readLongAttribute(XmlPullParser xmlPullParser, String str, long j) {
        if (xmlPullParser instanceof TypedXmlPullParser) {
            return ((TypedXmlPullParser) xmlPullParser).getAttributeLong((String) null, str, j);
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return j;
        }
        try {
            return Long.parseLong(attributeValue);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static final HashMap readMapXml(InputStream inputStream)
            throws XmlPullParserException, IOException {
        TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        return (HashMap) readValueXml(newFastPullParser, new String[1]);
    }

    public static final HashSet readSetXml(InputStream inputStream)
            throws XmlPullParserException, IOException {
        TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        return (HashSet) readValueXml(newFastPullParser, new String[1]);
    }

    public static String readStringAttribute(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue(null, str);
    }

    public static final ArrayMap readThisArrayMapXml(
            TypedXmlPullParser typedXmlPullParser,
            String str,
            String[] strArr,
            ReadMapCallback readMapCallback)
            throws XmlPullParserException, IOException {
        ArrayMap arrayMap = new ArrayMap();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                arrayMap.put(
                        strArr[0],
                        readThisValueXml(typedXmlPullParser, strArr, readMapCallback, true));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return arrayMap;
                }
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Expected ", str, " end tag at: ")));
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final boolean[] readThisBooleanArrayXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "num");
        typedXmlPullParser.next();
        boolean[] zArr = new boolean[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    new StringBuilder("Expected item tag at: ")));
                }
                zArr[i] = typedXmlPullParser.getAttributeBoolean((String) null, "value");
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return zArr;
                }
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Expected ", str, " end tag at: ")));
                }
                i++;
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final byte[] readThisByteArrayXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "num");
        byte[] bArr = new byte[0];
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 4) {
                if (attributeInt > 0) {
                    String text = typedXmlPullParser.getText();
                    if (text == null || text.length() != attributeInt * 2) {
                        throw new XmlPullParserException(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                        "Invalid value found in byte-array: ", text));
                    }
                    bArr = HexEncoding.decode(text);
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return bArr;
                }
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Expected ", str, " end tag at: ")));
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final double[] readThisDoubleArrayXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "num");
        typedXmlPullParser.next();
        double[] dArr = new double[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    new StringBuilder("Expected item tag at: ")));
                }
                dArr[i] = typedXmlPullParser.getAttributeDouble((String) null, "value");
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return dArr;
                }
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Expected ", str, " end tag at: ")));
                }
                i++;
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final int[] readThisIntArrayXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "num");
        typedXmlPullParser.next();
        int[] iArr = new int[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    new StringBuilder("Expected item tag at: ")));
                }
                iArr[i] = typedXmlPullParser.getAttributeInt((String) null, "value");
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return iArr;
                }
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Expected ", str, " end tag at: ")));
                }
                i++;
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final ArrayList readThisListXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        return readThisListXml(typedXmlPullParser, str, strArr, null, false);
    }

    private static final ArrayList readThisListXml(
            TypedXmlPullParser typedXmlPullParser,
            String str,
            String[] strArr,
            ReadMapCallback readMapCallback,
            boolean z)
            throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                arrayList.add(readThisValueXml(typedXmlPullParser, strArr, readMapCallback, z));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return arrayList;
                }
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Expected ", str, " end tag at: ")));
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final long[] readThisLongArrayXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "num");
        typedXmlPullParser.next();
        long[] jArr = new long[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    new StringBuilder("Expected item tag at: ")));
                }
                jArr[i] = typedXmlPullParser.getAttributeLong((String) null, "value");
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return jArr;
                }
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Expected ", str, " end tag at: ")));
                }
                i++;
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final HashMap readThisMapXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        return readThisMapXml(typedXmlPullParser, str, strArr, null);
    }

    public static final HashMap readThisMapXml(
            TypedXmlPullParser typedXmlPullParser,
            String str,
            String[] strArr,
            ReadMapCallback readMapCallback)
            throws XmlPullParserException, IOException {
        HashMap hashMap = new HashMap();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                hashMap.put(
                        strArr[0],
                        readThisValueXml(typedXmlPullParser, strArr, readMapCallback, false));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return hashMap;
                }
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Expected ", str, " end tag at: ")));
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    private static final Object readThisPrimitiveValueXml(
            TypedXmlPullParser typedXmlPullParser, String str)
            throws XmlPullParserException, IOException {
        if (str.equals("int")) {
            return Integer.valueOf(typedXmlPullParser.getAttributeInt((String) null, "value"));
        }
        if (str.equals("long")) {
            return Long.valueOf(typedXmlPullParser.getAttributeLong((String) null, "value"));
        }
        if (str.equals("float")) {
            return Float.valueOf(typedXmlPullParser.getAttributeFloat((String) null, "value"));
        }
        if (str.equals("double")) {
            return Double.valueOf(typedXmlPullParser.getAttributeDouble((String) null, "value"));
        }
        if (str.equals("boolean")) {
            return Boolean.valueOf(typedXmlPullParser.getAttributeBoolean((String) null, "value"));
        }
        return null;
    }

    public static final HashSet readThisSetXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        return readThisSetXml(typedXmlPullParser, str, strArr, null, false);
    }

    private static final HashSet readThisSetXml(
            TypedXmlPullParser typedXmlPullParser,
            String str,
            String[] strArr,
            ReadMapCallback readMapCallback,
            boolean z)
            throws XmlPullParserException, IOException {
        HashSet hashSet = new HashSet();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                hashSet.add(readThisValueXml(typedXmlPullParser, strArr, readMapCallback, z));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return hashSet;
                }
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Expected ", str, " end tag at: ")));
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    public static final String[] readThisStringArrayXml(
            TypedXmlPullParser typedXmlPullParser, String str, String[] strArr)
            throws XmlPullParserException, IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "num");
        typedXmlPullParser.next();
        String[] strArr2 = new String[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    new StringBuilder("Expected item tag at: ")));
                }
                strArr2[i] = typedXmlPullParser.getAttributeValue((String) null, "value");
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return strArr2;
                }
                if (!typedXmlPullParser.getName().equals("item")) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Expected ", str, " end tag at: ")));
                }
                i++;
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m("Document ended before ", str, " end tag"));
    }

    private static final Object readThisValueXml(
            TypedXmlPullParser typedXmlPullParser,
            String[] strArr,
            ReadMapCallback readMapCallback,
            boolean z)
            throws XmlPullParserException, IOException {
        int next;
        Object obj = null;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        String name = typedXmlPullParser.getName();
        if (!name.equals("null")) {
            if (name.equals("string")) {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    int next2 = typedXmlPullParser.next();
                    if (next2 == 1) {
                        throw new XmlPullParserException("Unexpected end of document in <string>");
                    }
                    if (next2 == 3) {
                        if (!typedXmlPullParser.getName().equals("string")) {
                            throw new XmlPullParserException(
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            typedXmlPullParser,
                                            new StringBuilder("Unexpected end tag in <string>: ")));
                        }
                        strArr[0] = attributeValue;
                        return sb.toString();
                    }
                    if (next2 == 4) {
                        sb.append(typedXmlPullParser.getText());
                    } else if (next2 == 2) {
                        throw new XmlPullParserException(
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        typedXmlPullParser,
                                        new StringBuilder("Unexpected start tag in <string>: ")));
                    }
                }
            } else {
                obj = readThisPrimitiveValueXml(typedXmlPullParser, name);
                if (obj == null) {
                    if (name.equals("byte-array")) {
                        byte[] readThisByteArrayXml =
                                readThisByteArrayXml(typedXmlPullParser, "byte-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisByteArrayXml;
                    }
                    if (name.equals("int-array")) {
                        int[] readThisIntArrayXml =
                                readThisIntArrayXml(typedXmlPullParser, "int-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisIntArrayXml;
                    }
                    if (name.equals("long-array")) {
                        long[] readThisLongArrayXml =
                                readThisLongArrayXml(typedXmlPullParser, "long-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisLongArrayXml;
                    }
                    if (name.equals("double-array")) {
                        double[] readThisDoubleArrayXml =
                                readThisDoubleArrayXml(typedXmlPullParser, "double-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisDoubleArrayXml;
                    }
                    if (name.equals("string-array")) {
                        String[] readThisStringArrayXml =
                                readThisStringArrayXml(typedXmlPullParser, "string-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisStringArrayXml;
                    }
                    if (name.equals("boolean-array")) {
                        boolean[] readThisBooleanArrayXml =
                                readThisBooleanArrayXml(
                                        typedXmlPullParser, "boolean-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisBooleanArrayXml;
                    }
                    if (name.equals("map")) {
                        typedXmlPullParser.next();
                        Object readThisArrayMapXml =
                                z
                                        ? readThisArrayMapXml(
                                                typedXmlPullParser, "map", strArr, readMapCallback)
                                        : readThisMapXml(
                                                typedXmlPullParser, "map", strArr, readMapCallback);
                        strArr[0] = attributeValue;
                        return readThisArrayMapXml;
                    }
                    if (name.equals("list")) {
                        typedXmlPullParser.next();
                        ArrayList readThisListXml =
                                readThisListXml(
                                        typedXmlPullParser, "list", strArr, readMapCallback, z);
                        strArr[0] = attributeValue;
                        return readThisListXml;
                    }
                    if (name.equals("set")) {
                        typedXmlPullParser.next();
                        HashSet readThisSetXml =
                                readThisSetXml(
                                        typedXmlPullParser, "set", strArr, readMapCallback, z);
                        strArr[0] = attributeValue;
                        return readThisSetXml;
                    }
                    if (readMapCallback == null) {
                        throw new XmlPullParserException("Unknown tag: ".concat(name));
                    }
                    Object readThisUnknownObjectXml =
                            readMapCallback.readThisUnknownObjectXml(typedXmlPullParser, name);
                    strArr[0] = attributeValue;
                    return readThisUnknownObjectXml;
                }
            }
        }
        do {
            next = typedXmlPullParser.next();
            if (next == 1) {
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Unexpected end of document in <", name, ">"));
            }
            if (next == 3) {
                if (!typedXmlPullParser.getName().equals(name)) {
                    throw new XmlPullParserException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    typedXmlPullParser,
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Unexpected end tag in <", name, ">: ")));
                }
                strArr[0] = attributeValue;
                return obj;
            }
            if (next == 4) {
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser,
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Unexpected text in <", name, ">: ")));
            }
        } while (next != 2);
        throw new XmlPullParserException(
                XmlUtils$$ExternalSyntheticOutline0.m(
                        typedXmlPullParser,
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Unexpected start tag in <", name, ">: ")));
    }

    public static Uri readUriAttribute(XmlPullParser xmlPullParser, String str) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (attributeValue != null) {
            return Uri.parse(attributeValue);
        }
        return null;
    }

    public static final Object readValueXml(TypedXmlPullParser typedXmlPullParser, String[] strArr)
            throws XmlPullParserException, IOException {
        int eventType = typedXmlPullParser.getEventType();
        while (eventType != 2) {
            if (eventType == 3) {
                throw new XmlPullParserException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                typedXmlPullParser, new StringBuilder("Unexpected end tag at: ")));
            }
            if (eventType == 4) {
                throw new XmlPullParserException(
                        "Unexpected text: " + typedXmlPullParser.getText());
            }
            eventType = typedXmlPullParser.next();
            if (eventType == 1) {
                throw new XmlPullParserException("Unexpected end of document");
            }
        }
        return readThisValueXml(typedXmlPullParser, strArr, null, false);
    }

    public static void skipCurrentTag(XmlPullParser xmlPullParser)
            throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        }
    }

    @Deprecated
    public static void writeBitmapAttribute(XmlSerializer xmlSerializer, String str, Bitmap bitmap)
            throws IOException {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
            writeByteArrayAttribute(xmlSerializer, str, byteArrayOutputStream.toByteArray());
        }
    }

    public static final void writeBooleanArrayXml(
            boolean[] zArr, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (zArr == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "boolean-array");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((String) null, "num", zArr.length);
        for (boolean z : zArr) {
            typedXmlSerializer.startTag((String) null, "item");
            typedXmlSerializer.attributeBoolean((String) null, "value", z);
            typedXmlSerializer.endTag((String) null, "item");
        }
        typedXmlSerializer.endTag((String) null, "boolean-array");
    }

    public static void writeBooleanAttribute(XmlSerializer xmlSerializer, String str, boolean z)
            throws IOException {
        if (xmlSerializer instanceof TypedXmlSerializer) {
            ((TypedXmlSerializer) xmlSerializer).attributeBoolean((String) null, str, z);
        } else {
            xmlSerializer.attribute(null, str, Boolean.toString(z));
        }
    }

    public static void writeByteArrayAttribute(XmlSerializer xmlSerializer, String str, byte[] bArr)
            throws IOException {
        if (bArr != null) {
            if (xmlSerializer instanceof TypedXmlSerializer) {
                ((TypedXmlSerializer) xmlSerializer).attributeBytesBase64((String) null, str, bArr);
            } else {
                xmlSerializer.attribute(null, str, Base64.encodeToString(bArr, 0));
            }
        }
    }

    public static final void writeByteArrayXml(
            byte[] bArr, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (bArr == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "byte-array");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((String) null, "num", bArr.length);
        typedXmlSerializer.text(HexEncoding.encodeToString(bArr).toLowerCase());
        typedXmlSerializer.endTag((String) null, "byte-array");
    }

    public static final void writeDoubleArrayXml(
            double[] dArr, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (dArr == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "double-array");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((String) null, "num", dArr.length);
        for (double d : dArr) {
            typedXmlSerializer.startTag((String) null, "item");
            typedXmlSerializer.attributeDouble((String) null, "value", d);
            typedXmlSerializer.endTag((String) null, "item");
        }
        typedXmlSerializer.endTag((String) null, "double-array");
    }

    public static void writeFloatAttribute(XmlSerializer xmlSerializer, String str, float f)
            throws IOException {
        if (xmlSerializer instanceof TypedXmlSerializer) {
            ((TypedXmlSerializer) xmlSerializer).attributeFloat((String) null, str, f);
        } else {
            xmlSerializer.attribute(null, str, Float.toString(f));
        }
    }

    public static final void writeIntArrayXml(
            int[] iArr, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (iArr == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "int-array");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((String) null, "num", iArr.length);
        for (int i : iArr) {
            typedXmlSerializer.startTag((String) null, "item");
            typedXmlSerializer.attributeInt((String) null, "value", i);
            typedXmlSerializer.endTag((String) null, "item");
        }
        typedXmlSerializer.endTag((String) null, "int-array");
    }

    public static void writeIntAttribute(XmlSerializer xmlSerializer, String str, int i)
            throws IOException {
        if (xmlSerializer instanceof TypedXmlSerializer) {
            ((TypedXmlSerializer) xmlSerializer).attributeInt((String) null, str, i);
        } else {
            xmlSerializer.attribute(null, str, Integer.toString(i));
        }
    }

    public static final void writeListXml(List list, OutputStream outputStream)
            throws XmlPullParserException, IOException {
        TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((String) null, Boolean.TRUE);
        newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        writeListXml(list, null, newFastSerializer);
        newFastSerializer.endDocument();
    }

    public static final void writeListXml(
            List list, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (list == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "list");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            writeValueXml(list.get(i), (String) null, typedXmlSerializer);
        }
        typedXmlSerializer.endTag((String) null, "list");
    }

    public static final void writeLongArrayXml(
            long[] jArr, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (jArr == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "long-array");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((String) null, "num", jArr.length);
        for (long j : jArr) {
            typedXmlSerializer.startTag((String) null, "item");
            typedXmlSerializer.attributeLong((String) null, "value", j);
            typedXmlSerializer.endTag((String) null, "item");
        }
        typedXmlSerializer.endTag((String) null, "long-array");
    }

    public static void writeLongAttribute(XmlSerializer xmlSerializer, String str, long j)
            throws IOException {
        if (xmlSerializer instanceof TypedXmlSerializer) {
            ((TypedXmlSerializer) xmlSerializer).attributeLong((String) null, str, j);
        } else {
            xmlSerializer.attribute(null, str, Long.toString(j));
        }
    }

    public static final void writeMapXml(
            Map map, TypedXmlSerializer typedXmlSerializer, WriteMapCallback writeMapCallback)
            throws XmlPullParserException, IOException {
        if (map == null) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            writeValueXml(
                    entry.getValue(),
                    (String) entry.getKey(),
                    typedXmlSerializer,
                    writeMapCallback);
        }
    }

    public static final void writeMapXml(Map map, OutputStream outputStream)
            throws XmlPullParserException, IOException {
        TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((String) null, Boolean.TRUE);
        newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        writeMapXml(map, (String) null, newFastSerializer);
        newFastSerializer.endDocument();
    }

    public static final void writeMapXml(Map map, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        writeMapXml(map, str, typedXmlSerializer, null);
    }

    public static final void writeMapXml(
            Map map,
            String str,
            TypedXmlSerializer typedXmlSerializer,
            WriteMapCallback writeMapCallback)
            throws XmlPullParserException, IOException {
        if (map == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "map");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        writeMapXml(map, typedXmlSerializer, writeMapCallback);
        typedXmlSerializer.endTag((String) null, "map");
    }

    public static final void writeSetXml(Set set, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (set == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "set");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            writeValueXml(it.next(), (String) null, typedXmlSerializer);
        }
        typedXmlSerializer.endTag((String) null, "set");
    }

    public static final void writeStringArrayXml(
            String[] strArr, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        if (strArr == null) {
            typedXmlSerializer.startTag((String) null, "null");
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((String) null, "string-array");
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((String) null, "num", strArr.length);
        for (String str2 : strArr) {
            typedXmlSerializer.startTag((String) null, "item");
            typedXmlSerializer.attribute((String) null, "value", str2);
            typedXmlSerializer.endTag((String) null, "item");
        }
        typedXmlSerializer.endTag((String) null, "string-array");
    }

    public static void writeStringAttribute(
            XmlSerializer xmlSerializer, String str, CharSequence charSequence) throws IOException {
        if (charSequence != null) {
            xmlSerializer.attribute(null, str, charSequence.toString());
        }
    }

    public static void writeUriAttribute(XmlSerializer xmlSerializer, String str, Uri uri)
            throws IOException {
        if (uri != null) {
            xmlSerializer.attribute(null, str, uri.toString());
        }
    }

    public static final void writeValueXml(
            Object obj, String str, TypedXmlSerializer typedXmlSerializer)
            throws XmlPullParserException, IOException {
        writeValueXml(obj, str, typedXmlSerializer, null);
    }

    private static final void writeValueXml(
            Object obj,
            String str,
            TypedXmlSerializer typedXmlSerializer,
            WriteMapCallback writeMapCallback)
            throws XmlPullParserException, IOException {
        if (obj == null) {
            typedXmlSerializer.startTag((String) null, "null");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.endTag((String) null, "null");
            return;
        }
        if (obj instanceof String) {
            typedXmlSerializer.startTag((String) null, "string");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.text(obj.toString());
            typedXmlSerializer.endTag((String) null, "string");
            return;
        }
        if (obj instanceof Integer) {
            typedXmlSerializer.startTag((String) null, "int");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.attributeInt((String) null, "value", ((Integer) obj).intValue());
            typedXmlSerializer.endTag((String) null, "int");
            return;
        }
        if (obj instanceof Long) {
            typedXmlSerializer.startTag((String) null, "long");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.attributeLong((String) null, "value", ((Long) obj).longValue());
            typedXmlSerializer.endTag((String) null, "long");
            return;
        }
        if (obj instanceof Float) {
            typedXmlSerializer.startTag((String) null, "float");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.attributeFloat((String) null, "value", ((Float) obj).floatValue());
            typedXmlSerializer.endTag((String) null, "float");
            return;
        }
        if (obj instanceof Double) {
            typedXmlSerializer.startTag((String) null, "double");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.attributeDouble(
                    (String) null, "value", ((Double) obj).doubleValue());
            typedXmlSerializer.endTag((String) null, "double");
            return;
        }
        if (obj instanceof Boolean) {
            typedXmlSerializer.startTag((String) null, "boolean");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.attributeBoolean(
                    (String) null, "value", ((Boolean) obj).booleanValue());
            typedXmlSerializer.endTag((String) null, "boolean");
            return;
        }
        if (obj instanceof byte[]) {
            writeByteArrayXml((byte[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof int[]) {
            writeIntArrayXml((int[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof long[]) {
            writeLongArrayXml((long[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof double[]) {
            writeDoubleArrayXml((double[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof String[]) {
            writeStringArrayXml((String[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof boolean[]) {
            writeBooleanArrayXml((boolean[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof Map) {
            writeMapXml((Map) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof List) {
            writeListXml((List) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof Set) {
            writeSetXml((Set) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof CharSequence) {
            typedXmlSerializer.startTag((String) null, "string");
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "name", str);
            }
            typedXmlSerializer.text(obj.toString());
            typedXmlSerializer.endTag((String) null, "string");
            return;
        }
        if (writeMapCallback != null) {
            writeMapCallback.writeUnknownObject(obj, str, typedXmlSerializer);
        } else {
            throw new RuntimeException("writeValueXml: unable to write value " + obj);
        }
    }

    @Deprecated
    public static final void writeValueXml(Object obj, String str, XmlSerializer xmlSerializer)
            throws XmlPullParserException, IOException {
        writeValueXml(obj, str, makeTyped(xmlSerializer));
    }
}
