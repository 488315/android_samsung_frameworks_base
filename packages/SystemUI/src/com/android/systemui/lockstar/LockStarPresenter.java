package com.android.systemui.lockstar;

import android.os.Bundle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.util.kotlin.MapUtilsKt;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LockStarPresenter {
    public final Map callbackMap = new HashMap();

    public final void onChangedLockStarData(boolean z, Bundle bundle) {
        Iterator it = MapUtilsKt.filterValuesNotNull(this.callbackMap).entrySet().iterator();
        while (it.hasNext()) {
            ((PluginLockStarManager.LockStarCallback) ((Map.Entry) it.next()).getValue()).onChangedLockStarData(z);
        }
    }
}
