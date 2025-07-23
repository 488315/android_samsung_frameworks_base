package com.android.systemui.qs.tiles.impl.modes.ui;

import android.content.res.Resources;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesDndTileModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesDndTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ModesDndTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        ModesDndTileMapper$$ExternalSyntheticLambda0 modesDndTileMapper$$ExternalSyntheticLambda0 = new ModesDndTileMapper$$ExternalSyntheticLambda0((ModesDndTileModel) obj, this);
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, modesDndTileMapper$$ExternalSyntheticLambda0);
    }
}
