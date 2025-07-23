package com.android.systemui.media.mediaoutput.ext;

import java.util.Arrays;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BitmapExtKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Byte b = (Byte) obj;
        b.byteValue();
        return String.format("%02x", Arrays.copyOf(new Object[]{b}, 1));
    }
}
