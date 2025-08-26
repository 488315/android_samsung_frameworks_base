package com.android.systemui.qs.panels.shared.model;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class TileRow {
    public final List _tiles = new ArrayList();
    public int availableColumns;
    public final int columns;

    public TileRow(int i) {
        this.columns = i;
        this.availableColumns = i;
    }

    public final boolean maybeAddTile(SizedTile sizedTile) {
        if (this.availableColumns - sizedTile.getWidth() < 0) {
            return false;
        }
        ((ArrayList) this._tiles).add(sizedTile);
        this.availableColumns -= sizedTile.getWidth();
        return true;
    }
}
