package com.android.server.notification.sec.runestone;

/* loaded from: classes2.dex */
public class CollectionContract$Notification$Log {
    public int cancelReason;
    public String category;
    public String channelId;

    /* renamed from: id */
    public int f1718id;
    public String pkg;
    public String tag;
    public long enqueuedTimeMs = -1;
    public long canceledTimeMs = -1;
    public long firstExpandedTimeMs = -1;
    public long firstShownTimeMs = -1;

    public String toString() {
        return "RunestoneNotiLog[pkg=" + this.pkg + ", id=" + this.f1718id + ", cancelReason=" + this.cancelReason + ", category=" + this.category + ", channelId=" + this.channelId + ", tag= " + this.tag + ", enqueuedTimeMs=" + this.enqueuedTimeMs + ", canceledTimeMs=" + this.canceledTimeMs + ", firstExpandedTimeMs=" + this.firstExpandedTimeMs + ", firstShownTimeMs=" + this.firstShownTimeMs + "]";
    }
}
