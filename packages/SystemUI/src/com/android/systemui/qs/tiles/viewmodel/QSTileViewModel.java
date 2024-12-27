package com.android.systemui.qs.tiles.viewmodel;

import android.os.UserHandle;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

public interface QSTileViewModel {
    void destroy();

    void forceUpdate();

    QSTileConfig getConfig();

    SharedFlow getState();

    StateFlow isAvailable();

    void onActionPerformed(QSTileUserAction qSTileUserAction);

    void onUserChanged(UserHandle userHandle);
}
