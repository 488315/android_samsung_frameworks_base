package com.samsung.android.mocca;

import java.util.Arrays;

/* loaded from: classes6.dex */
public class SemMdContextEvent {
    public final byte[] data;
    public final long timestamp;
    public final String type;

    protected SemMdContextEvent(long j, String str, byte[] bArr) {
        this.timestamp = j;
        this.type = str;
        this.data = Arrays.copyOf(bArr, bArr.length);
    }
}
