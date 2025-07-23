package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface IconTilesViewModel {
    StateFlow getLargeTiles();

    StateFlow getLargeTilesSpan();

    boolean isIconTile(TileSpec tileSpec);

    void resize(TileSpec tileSpec, boolean z);
}
