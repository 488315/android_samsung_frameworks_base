package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.ComponentName;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.List;
import java.util.Set;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UserTilesAndComponents {
    public final Set installedComponents;
    public final List knoxBlockTiles;
    public final List tiles;
    public final int userId;

    public UserTilesAndComponents(int i, List list, Set set, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, list, set, (i2 & 8) != 0 ? EmptyList.INSTANCE : list2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserTilesAndComponents)) {
            return false;
        }
        UserTilesAndComponents userTilesAndComponents = (UserTilesAndComponents) obj;
        return this.userId == userTilesAndComponents.userId && Intrinsics.areEqual(this.tiles, userTilesAndComponents.tiles) && Intrinsics.areEqual(this.installedComponents, userTilesAndComponents.installedComponents) && Intrinsics.areEqual(this.knoxBlockTiles, userTilesAndComponents.knoxBlockTiles);
    }

    public final int hashCode() {
        return this.knoxBlockTiles.hashCode() + ((this.installedComponents.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.tiles, Integer.hashCode(this.userId) * 31, 31)) * 31);
    }

    public final String toString() {
        return "UserTilesAndComponents(userId=" + this.userId + ", tiles=" + this.tiles + ", installedComponents=" + this.installedComponents + ", knoxBlockTiles=" + this.knoxBlockTiles + ")";
    }

    public UserTilesAndComponents(int i, List<? extends TileSpec> list, Set<ComponentName> set, List<? extends TileSpec> list2) {
        this.userId = i;
        this.tiles = list;
        this.installedComponents = set;
        this.knoxBlockTiles = list2;
    }
}
