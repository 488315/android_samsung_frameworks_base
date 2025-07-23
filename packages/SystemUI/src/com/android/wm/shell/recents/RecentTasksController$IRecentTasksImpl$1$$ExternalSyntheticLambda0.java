package com.android.wm.shell.recents;

import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.recents.RecentTasksController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$IRecentTasksImpl$1$$ExternalSyntheticLambda0 implements SingleInstanceRemoteListener.RemoteCall {
    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
    public final void accept(Object obj) {
        int i = RecentTasksController.IRecentTasksImpl.AnonymousClass1.$r8$clinit;
        ((IRecentTasksListener) obj).onRecentTasksChanged();
    }
}
