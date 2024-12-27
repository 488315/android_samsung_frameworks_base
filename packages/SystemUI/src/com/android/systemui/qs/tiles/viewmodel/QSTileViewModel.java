package com.android.systemui.qs.tiles.viewmodel;

import android.os.UserHandle;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface QSTileViewModel {
    void destroy();

    void forceUpdate();

    QSTileConfig getConfig();

    SharedFlow getState();

    StateFlow isAvailable();

    void onActionPerformed(QSTileUserAction qSTileUserAction);

    void onUserChanged(UserHandle userHandle);
}
