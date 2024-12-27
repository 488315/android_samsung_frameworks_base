package com.android.systemui.qs.tiles.base.interactor;

import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;

public interface QSTileDataToStateMapper {
    QSTileState map(QSTileConfig qSTileConfig, Object obj);
}
