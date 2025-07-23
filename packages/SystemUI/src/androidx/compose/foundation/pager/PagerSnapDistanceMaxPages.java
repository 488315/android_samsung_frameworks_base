package androidx.compose.foundation.pager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
