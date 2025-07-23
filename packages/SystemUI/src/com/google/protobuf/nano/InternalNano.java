package com.google.protobuf.nano;

import java.nio.charset.Charset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
