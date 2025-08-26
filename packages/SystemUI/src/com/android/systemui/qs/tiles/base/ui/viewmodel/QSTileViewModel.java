package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.os.UserHandle;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public interface QSTileViewModel {
    void destroy();

    void forceUpdate();

    QSTileConfig getConfig();

    int getCurrentTileUser();

    StateFlow getState();

    default TileDetailsViewModel getTileDetailsViewModel() {
        return null;
    }

    StateFlow isAvailable();

    void onActionPerformed(QSTileUserAction qSTileUserAction);

    void onUserChanged(UserHandle userHandle);
}
