package com.android.systemui.model;

import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public final class SysUIStateDispatcher {
    public final CopyOnWriteArrayList listeners = new CopyOnWriteArrayList();

    public final void dispatchSysUIStateChange(int i, long j) {
        if (i != 0) {
            ShadeWindowGoesAround.INSTANCE.getClass();
            if (!ShadeWindowGoesAround.FLAG.isTrue()) {
                return;
            }
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((SysUiState.SysUiStateCallback) it.next()).onSystemUiStateChanged(i, j);
        }
    }
}
