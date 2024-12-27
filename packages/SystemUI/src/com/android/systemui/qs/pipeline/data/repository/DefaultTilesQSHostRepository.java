package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;

public final class DefaultTilesQSHostRepository implements DefaultTilesRepository {
    public final Resources resources;

    public DefaultTilesQSHostRepository(Resources resources) {
        this.resources = resources;
    }
}
