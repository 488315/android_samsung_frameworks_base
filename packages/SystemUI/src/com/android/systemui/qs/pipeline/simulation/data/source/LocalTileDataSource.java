package com.android.systemui.qs.pipeline.simulation.data.source;

import android.content.Context;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LocalTileDataSource implements TileDataSource {
    public final Context context;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
