package com.android.systemui.qs.tiles.base.ui.model;

import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;

/* loaded from: classes2.dex */
public interface QSTileDataToStateMapper {
    QSTileState map(QSTileConfig qSTileConfig, Object obj);
}
