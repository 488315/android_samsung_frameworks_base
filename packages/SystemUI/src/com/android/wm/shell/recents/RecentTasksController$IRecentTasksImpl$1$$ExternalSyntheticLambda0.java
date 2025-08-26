package com.android.wm.shell.recents;

import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.recents.RecentTasksController;

/* loaded from: classes3.dex */
public final /* synthetic */ class RecentTasksController$IRecentTasksImpl$1$$ExternalSyntheticLambda0 implements SingleInstanceRemoteListener.RemoteCall {
    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
    public final void accept(Object obj) {
        int i = RecentTasksController.IRecentTasksImpl.AnonymousClass1.$r8$clinit;
        ((IRecentTasksListener) obj).onRecentTasksChanged();
    }
}
