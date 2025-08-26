package com.android.systemui.media.mediaoutput.ext;

import java.util.Arrays;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class BitmapExtKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Byte b = (Byte) obj;
        b.byteValue();
        return String.format("%02x", Arrays.copyOf(new Object[]{b}, 1));
    }
}
