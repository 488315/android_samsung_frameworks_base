package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;

public final class MinimumTilesResourceRepository implements MinimumTilesRepository {
    public final int minNumberOfTiles;

    public MinimumTilesResourceRepository(Resources resources) {
        this.minNumberOfTiles = resources.getInteger(R.integer.quick_settings_min_num_tiles);
    }
}
