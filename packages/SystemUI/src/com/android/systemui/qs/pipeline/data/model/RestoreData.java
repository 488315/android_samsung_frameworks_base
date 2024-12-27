package com.android.systemui.qs.pipeline.data.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RestoreData {
    public final Set restoredAutoAddedTiles;
    public final List restoredTiles;
    public final int userId;

    public RestoreData(List<? extends TileSpec> list, Set<? extends TileSpec> set, int i) {
        this.restoredTiles = list;
        this.restoredAutoAddedTiles = set;
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RestoreData)) {
            return false;
        }
        RestoreData restoreData = (RestoreData) obj;
        return Intrinsics.areEqual(this.restoredTiles, restoreData.restoredTiles) && Intrinsics.areEqual(this.restoredAutoAddedTiles, restoreData.restoredAutoAddedTiles) && this.userId == restoreData.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + ((this.restoredAutoAddedTiles.hashCode() + (this.restoredTiles.hashCode() * 31)) * 31);
    }

    public final String toString() {
        List list = this.restoredTiles;
        Set set = this.restoredAutoAddedTiles;
        StringBuilder sb = new StringBuilder("RestoreData(restoredTiles=");
        sb.append(list);
        sb.append(", restoredAutoAddedTiles=");
        sb.append(set);
        sb.append(", userId=");
        return Anchor$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
    }
}
