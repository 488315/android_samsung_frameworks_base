package android.media;

import java.util.ArrayList;
import java.util.List;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlNode {
    public final String mAttributes;
    public final List<TtmlNode> mChildren = new ArrayList();
    public final long mEndTimeMs;
    public final String mName;
    public final TtmlNode mParent;
    public final long mRunId;
    public final long mStartTimeMs;
    public final String mText;

    public TtmlNode(String str, String str2, String str3, long j, long j2, TtmlNode ttmlNode, long j3) {
        this.mName = str;
        this.mAttributes = str2;
        this.mText = str3;
        this.mStartTimeMs = j;
        this.mEndTimeMs = j2;
        this.mParent = ttmlNode;
        this.mRunId = j3;
    }

    public boolean isActive(long j, long j2) {
        return this.mEndTimeMs > j && this.mStartTimeMs < j2;
    }
}
