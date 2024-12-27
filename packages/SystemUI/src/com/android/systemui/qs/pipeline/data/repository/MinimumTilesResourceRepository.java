package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MinimumTilesResourceRepository implements MinimumTilesRepository {
    public final int minNumberOfTiles;

    public MinimumTilesResourceRepository(Resources resources) {
        this.minNumberOfTiles = resources.getInteger(R.integer.quick_settings_min_num_tiles);
    }
}
