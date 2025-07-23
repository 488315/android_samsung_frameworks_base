package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.ComponentName;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DataWithUserChange {
    public final Set installedComponents;
    public final List knoxBlockTiles;
    public final List tiles;
    public final boolean userChange;
    public final int userId;

    public DataWithUserChange(int i, List<? extends TileSpec> list, Set<ComponentName> set, List<? extends TileSpec> list2, boolean z) {
        this.userId = i;
        this.tiles = list;
        this.installedComponents = set;
        this.knoxBlockTiles = list2;
        this.userChange = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataWithUserChange)) {
            return false;
        }
        DataWithUserChange dataWithUserChange = (DataWithUserChange) obj;
        return this.userId == dataWithUserChange.userId && Intrinsics.areEqual(this.tiles, dataWithUserChange.tiles) && Intrinsics.areEqual(this.installedComponents, dataWithUserChange.installedComponents) && Intrinsics.areEqual(this.knoxBlockTiles, dataWithUserChange.knoxBlockTiles) && this.userChange == dataWithUserChange.userChange;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.userChange) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.knoxBlockTiles, (this.installedComponents.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.tiles, Integer.hashCode(this.userId) * 31, 31)) * 31, 31);
    }

    public final String toString() {
        List list = this.tiles;
        Set set = this.installedComponents;
        List list2 = this.knoxBlockTiles;
        StringBuilder sb = new StringBuilder("DataWithUserChange(userId=");
        sb.append(this.userId);
        sb.append(", tiles=");
        sb.append(list);
        sb.append(", installedComponents=");
        sb.append(set);
        sb.append(", knoxBlockTiles=");
        sb.append(list2);
        sb.append(", userChange=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.userChange, ")");
    }
}
