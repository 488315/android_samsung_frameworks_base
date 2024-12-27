package com.android.systemui.communal.data.db;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalItemRank {
    public final int rank;
    public final long uid;

    public CommunalItemRank(long j, int i) {
        this.uid = j;
        this.rank = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunalItemRank)) {
            return false;
        }
        CommunalItemRank communalItemRank = (CommunalItemRank) obj;
        return this.uid == communalItemRank.uid && this.rank == communalItemRank.rank;
    }

    public final int hashCode() {
        return Integer.hashCode(this.rank) + (Long.hashCode(this.uid) * 31);
    }

    public final String toString() {
        return "CommunalItemRank(uid=" + this.uid + ", rank=" + this.rank + ")";
    }
}
