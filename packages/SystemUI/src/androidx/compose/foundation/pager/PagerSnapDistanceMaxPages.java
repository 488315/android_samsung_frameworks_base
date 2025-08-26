package androidx.compose.foundation.pager;

/* loaded from: classes.dex */
public final class PagerSnapDistanceMaxPages implements PagerSnapDistance {
    public final int pagesLimit;

    public PagerSnapDistanceMaxPages(int i) {
        this.pagesLimit = i;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PagerSnapDistanceMaxPages) {
            if (this.pagesLimit == ((PagerSnapDistanceMaxPages) obj).pagesLimit) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.pagesLimit);
    }
}
