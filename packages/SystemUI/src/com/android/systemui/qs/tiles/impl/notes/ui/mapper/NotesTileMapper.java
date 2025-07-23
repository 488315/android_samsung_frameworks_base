package com.android.systemui.qs.tiles.impl.notes.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotesTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public NotesTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        NotesTileMapper$$ExternalSyntheticLambda0 notesTileMapper$$ExternalSyntheticLambda0 = new NotesTileMapper$$ExternalSyntheticLambda0(this);
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, notesTileMapper$$ExternalSyntheticLambda0);
    }
}
