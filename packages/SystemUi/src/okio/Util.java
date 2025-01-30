package okio;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: okio.-Util, reason: invalid class name */
/* loaded from: classes3.dex */
public abstract class Util {
    public static final void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("size=", j, " offset=");
            m17m.append(j2);
            m17m.append(" byteCount=");
            m17m.append(j3);
            throw new ArrayIndexOutOfBoundsException(m17m.toString());
        }
    }
}
