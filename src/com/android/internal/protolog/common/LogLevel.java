package com.android.internal.protolog.common;

import android.app.blob.XmlTags;

/* loaded from: classes3.dex */
public enum LogLevel {
    DEBUG(XmlTags.ATTR_DESCRIPTION, 1),
    VERBOSE("v", 2),
    INFO("i", 3),
    WARN("w", 4),
    ERROR("e", 5),
    WTF("wtf", 6);

    public final int id;
    public final String shortCode;

    LogLevel(String str, int i) {
        this.shortCode = str;
        this.id = i;
    }
}
