package com.android.internal.vibrator.persistence;

import android.text.TextUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class XmlParserException extends Exception {
    public static XmlParserException createFromPullParserException(String str, XmlPullParserException xmlPullParserException) {
        return new XmlParserException("Error parsing " + str, xmlPullParserException);
    }

    public static XmlParserException createFromPullParserException(String str, String str2, String str3, XmlPullParserException xmlPullParserException) {
        return new XmlParserException(TextUtils.formatSimple("Error parsing %s = %s in tag %s", str2, str3, str), xmlPullParserException);
    }

    public XmlParserException(String str) {
        super(str);
    }

    public XmlParserException(String str, Throwable th) {
        super(str, th);
    }
}
