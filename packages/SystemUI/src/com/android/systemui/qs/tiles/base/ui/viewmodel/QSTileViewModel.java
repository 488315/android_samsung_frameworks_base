package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.os.UserHandle;
import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
