package okio.internal;

/* loaded from: classes4.dex */
public final class EocdRecord {
    public final long centralDirectoryOffset;
    public final int commentByteCount;
    public final long entryCount;

    public EocdRecord(long j, long j2, int i) {
        this.entryCount = j;
        this.centralDirectoryOffset = j2;
        this.commentByteCount = i;
    }
}
