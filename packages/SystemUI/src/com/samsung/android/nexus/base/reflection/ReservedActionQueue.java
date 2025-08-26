package com.samsung.android.nexus.base.reflection;

import com.samsung.android.nexus.base.utils.Log;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ReservedActionQueue {
    public final ArrayList mList = new ArrayList();

    public final void add(ReservedAction reservedAction) {
        synchronized (this.mList) {
            Log.i("ReservedActionQueue", "add action : " + reservedAction.mMethodName);
            this.mList.add(reservedAction);
        }
    }
}
