package com.android.systemui.qs.tiles.impl.modes.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel;

/* loaded from: classes2.dex */
public final class ModesTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ModesTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        ModesTileMapper$$ExternalSyntheticLambda0 modesTileMapper$$ExternalSyntheticLambda0 = new ModesTileMapper$$ExternalSyntheticLambda0((ModesTileModel) obj, this);
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, modesTileMapper$$ExternalSyntheticLambda0);
    }
}
