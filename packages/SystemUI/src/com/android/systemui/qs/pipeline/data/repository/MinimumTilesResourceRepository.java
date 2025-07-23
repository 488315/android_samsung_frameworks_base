package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MinimumTilesResourceRepository implements MinimumTilesRepository {
    public final int minNumberOfTiles;

    public MinimumTilesResourceRepository(Resources resources) {
        this.minNumberOfTiles = resources.getInteger(R.integer.sec_quick_settings_min_num_tiles);
    }
}
