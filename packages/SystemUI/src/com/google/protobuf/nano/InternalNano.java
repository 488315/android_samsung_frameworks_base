package com.google.protobuf.nano;

import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public final class InternalNano {
    public static final Object LAZY_INIT_LOCK;
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    static {
        Charset.forName("ISO-8859-1");
        LAZY_INIT_LOCK = new Object();
    }

    private InternalNano() {
    }
}
