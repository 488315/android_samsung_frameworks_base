package com.android.internal.protolog.common;

import android.app.blob.XmlTags;

/* loaded from: classes5.dex */
public enum LogLevel {
    DEBUG(XmlTags.ATTR_DESCRIPTION),
    VERBOSE("v"),
    INFO("i"),
    WARN("w"),
    ERROR("e"),
    WTF("wtf");

    public final String shortCode;

    LogLevel(String shortCode) {
        this.shortCode = shortCode;
    }
}
