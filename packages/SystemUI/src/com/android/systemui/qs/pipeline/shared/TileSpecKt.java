package com.android.systemui.qs.pipeline.shared;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.NoWhenBranchMatchedException;

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
