package com.samsung.android.nexus.base.reflection;

import com.samsung.android.nexus.base.utils.Log;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReservedActionQueue {
    public final ArrayList mList = new ArrayList();

    public final void add(ReservedAction reservedAction) {
        synchronized (this.mList) {
            Log.m262i("ReservedActionQueue", "add action : " + reservedAction.mMethodName);
            this.mList.add(reservedAction);
        }
    }
}
