package com.samsung.android.nexus.base.reflection;

import com.samsung.android.nexus.base.utils.Log;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
