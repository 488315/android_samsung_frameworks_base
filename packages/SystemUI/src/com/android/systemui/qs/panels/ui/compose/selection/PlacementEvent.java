package com.android.systemui.qs.panels.ui.compose.selection;

import com.android.systemui.qs.pipeline.shared.TileSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PlacementEvent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
