package com.android.systemui.qs.pipeline.shared;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TileSpecKt {
    public static final String getMetricSpec(TileSpec tileSpec) {
        if (tileSpec instanceof TileSpec.Invalid) {
            return "";
        }
        if (tileSpec instanceof TileSpec.PlatformTileSpec) {
            return ((TileSpec.PlatformTileSpec) tileSpec).spec;
        }
        if (tileSpec instanceof TileSpec.CustomTileSpec) {
            return ((TileSpec.CustomTileSpec) tileSpec).componentName.getPackageName();
        }
        if (tileSpec instanceof TileSpec.Empty) {
            return "empty";
        }
        throw new NoWhenBranchMatchedException();
    }
}
