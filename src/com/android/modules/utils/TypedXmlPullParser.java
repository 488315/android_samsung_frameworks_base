package com.android.modules.utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public interface TypedXmlPullParser extends XmlPullParser {
    boolean getAttributeBoolean(int i) throws XmlPullParserException;

    byte[] getAttributeBytesBase64(int i) throws XmlPullParserException;

    byte[] getAttributeBytesHex(int i) throws XmlPullParserException;

    double getAttributeDouble(int i) throws XmlPullParserException;

    float getAttributeFloat(int i) throws XmlPullParserException;

    int getAttributeInt(int i) throws XmlPullParserException;

    int getAttributeIntHex(int i) throws XmlPullParserException;

    long getAttributeLong(int i) throws XmlPullParserException;

    long getAttributeLongHex(int i) throws XmlPullParserException;

    default int getAttributeIndex(String str, String str2) {
        boolean z = str == null;
        int attributeCount = getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if ((z || str.equals(getAttributeNamespace(i))) && str2.equals(getAttributeName(i))) {
                return i;
            }
        }
        return -1;
    }

    default int getAttributeIndexOrThrow(String str, String str2) throws XmlPullParserException {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            return attributeIndex;
        }
        throw new XmlPullParserException("Missing attribute " + str2);
    }

    default byte[] getAttributeBytesHex(String str, String str2) throws XmlPullParserException {
        return getAttributeBytesHex(getAttributeIndexOrThrow(str, str2));
    }

    default byte[] getAttributeBytesBase64(String str, String str2) throws XmlPullParserException {
        return getAttributeBytesBase64(getAttributeIndexOrThrow(str, str2));
    }

    default int getAttributeInt(String str, String str2) throws XmlPullParserException {
        return getAttributeInt(getAttributeIndexOrThrow(str, str2));
    }

    default int getAttributeIntHex(String str, String str2) throws XmlPullParserException {
        return getAttributeIntHex(getAttributeIndexOrThrow(str, str2));
    }

    default long getAttributeLong(String str, String str2) throws XmlPullParserException {
        return getAttributeLong(getAttributeIndexOrThrow(str, str2));
    }

    default long getAttributeLongHex(String str, String str2) throws XmlPullParserException {
        return getAttributeLongHex(getAttributeIndexOrThrow(str, str2));
    }

    default float getAttributeFloat(String str, String str2) throws XmlPullParserException {
        return getAttributeFloat(getAttributeIndexOrThrow(str, str2));
    }

    default double getAttributeDouble(String str, String str2) throws XmlPullParserException {
        return getAttributeDouble(getAttributeIndexOrThrow(str, str2));
    }

    default boolean getAttributeBoolean(String str, String str2) throws XmlPullParserException {
        return getAttributeBoolean(getAttributeIndexOrThrow(str, str2));
    }

    default byte[] getAttributeBytesHex(String str, String str2, byte[] bArr) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeBytesHex(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return bArr;
    }

    default byte[] getAttributeBytesBase64(String str, String str2, byte[] bArr) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeBytesBase64(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return bArr;
    }

    default int getAttributeInt(String str, String str2, int i) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeInt(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return i;
    }

    default int getAttributeIntHex(String str, String str2, int i) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeIntHex(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return i;
    }

    default long getAttributeLong(String str, String str2, long j) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeLong(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return j;
    }

    default long getAttributeLongHex(String str, String str2, long j) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeLongHex(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return j;
    }

    default float getAttributeFloat(String str, String str2, float f) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeFloat(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return f;
    }

    default double getAttributeDouble(String str, String str2, double d) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeDouble(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return d;
    }

    default boolean getAttributeBoolean(String str, String str2, boolean z) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            try {
                return getAttributeBoolean(attributeIndex);
            } catch (Exception unused) {
            }
        }
        return z;
    }
}
