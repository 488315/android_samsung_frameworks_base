package com.android.systemui.qs.panels.shared.model;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
