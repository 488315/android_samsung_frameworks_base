package com.android.systemui.qs.panels.ui.compose.selection;

import com.android.systemui.qs.pipeline.shared.TileSpec;

/* loaded from: classes2.dex */
public interface PlacementEvent {

    public final class PlaceToIndex implements PlacementEvent {
        public final TileSpec movingSpec;
        public final int targetIndex;

        public PlaceToIndex(TileSpec tileSpec, int i) {
            this.movingSpec = tileSpec;
            this.targetIndex = i;
        }

        @Override // com.android.systemui.qs.panels.ui.compose.selection.PlacementEvent
        public final TileSpec getMovingSpec() {
            return this.movingSpec;
        }
    }

    public final class PlaceToTileSpec implements PlacementEvent {
        public final TileSpec movingSpec;
        public final TileSpec targetSpec;

        public PlaceToTileSpec(TileSpec tileSpec, TileSpec tileSpec2) {
            this.movingSpec = tileSpec;
            this.targetSpec = tileSpec2;
        }

        @Override // com.android.systemui.qs.panels.ui.compose.selection.PlacementEvent
        public final TileSpec getMovingSpec() {
            return this.movingSpec;
        }
    }

    TileSpec getMovingSpec();
}
