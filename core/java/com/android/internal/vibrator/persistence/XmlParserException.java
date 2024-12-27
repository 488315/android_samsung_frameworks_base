package com.android.internal.vibrator.persistence;

import android.text.TextUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public final class XmlParserException extends Exception {
    public static XmlParserException createFromPullParserException(String tagName, XmlPullParserException cause) {
        return new XmlParserException("Error parsing " + tagName, cause);
    }

    public static XmlParserException createFromPullParserException(String tagName, String attributeName, String attributeValue, XmlPullParserException cause) {
        return new XmlParserException(TextUtils.formatSimple("Error parsing %s = %s in tag %s", attributeName, attributeValue, tagName), cause);
    }

    public XmlParserException(String message) {
        super(message);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
