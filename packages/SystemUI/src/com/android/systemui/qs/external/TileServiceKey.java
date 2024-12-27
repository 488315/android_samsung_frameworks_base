package com.android.systemui.qs.external;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TileServiceKey {
    public final ComponentName componentName;
    public final String string;
    public final int user;

    public TileServiceKey(ComponentName componentName, int i) {
        this.componentName = componentName;
        this.user = i;
        this.string = componentName.flattenToString() + ":" + i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileServiceKey)) {
            return false;
        }
        TileServiceKey tileServiceKey = (TileServiceKey) obj;
        return Intrinsics.areEqual(this.componentName, tileServiceKey.componentName) && this.user == tileServiceKey.user;
    }

    public final int hashCode() {
        return Integer.hashCode(this.user) + (this.componentName.hashCode() * 31);
    }

    public final String toString() {
        return this.string;
    }
}
