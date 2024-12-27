package com.android.systemui.qs.tiles.base.interactor;

import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface QSTileDataToStateMapper {
    QSTileState map(QSTileConfig qSTileConfig, Object obj);
}
