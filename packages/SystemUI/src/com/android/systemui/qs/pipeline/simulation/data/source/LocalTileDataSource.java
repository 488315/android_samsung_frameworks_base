package com.android.systemui.qs.pipeline.simulation.data.source;

import android.content.Context;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LocalTileDataSource implements TileDataSource {
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LocalTileDataSource(Context context) {
        this.context = context;
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final boolean getQqsEdited() {
        return false;
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getQqsTiles() {
        return this.context.getString(R.string.test_qs_tiles);
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final boolean getQsEdited() {
        return false;
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getRemovedTiles() {
        return this.context.getString(R.string.test_removed_qs_tiles);
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getSepVersion() {
        return "";
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getTiles() {
        return this.context.getString(R.string.test_qs_tiles);
    }
}
