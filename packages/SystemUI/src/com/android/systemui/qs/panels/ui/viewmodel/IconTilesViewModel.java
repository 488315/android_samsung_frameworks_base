package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public interface IconTilesViewModel {
    StateFlow getLargeTiles();

    StateFlow getLargeTilesSpan();

    boolean isIconTile(TileSpec tileSpec);

    void resize(TileSpec tileSpec, boolean z);
}
