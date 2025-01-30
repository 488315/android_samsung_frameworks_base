package com.google.protobuf.nano;

import java.nio.charset.Charset;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InternalNano {
    public static final Object LAZY_INIT_LOCK;

    static {
        Charset.forName("UTF-8");
        Charset.forName("ISO-8859-1");
        LAZY_INIT_LOCK = new Object();
    }

    private InternalNano() {
    }
}
