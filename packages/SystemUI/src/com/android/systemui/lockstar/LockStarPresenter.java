package com.android.systemui.lockstar;

import android.os.Bundle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.util.kotlin.MapUtilsKt;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockStarPresenter {
    public final Map callbackMap = new HashMap();

    public final void onChangedLockStarData(boolean z, Bundle bundle) {
        Iterator it = MapUtilsKt.filterValuesNotNull(this.callbackMap).entrySet().iterator();
        while (it.hasNext()) {
            ((PluginLockStarManager.LockStarCallback) ((Map.Entry) it.next()).getValue()).onChangedLockStarData(z);
        }
    }
}
