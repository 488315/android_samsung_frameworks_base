package com.android.p038wm.shell.recents;

import com.android.p038wm.shell.common.SingleInstanceRemoteListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$1$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C4081x6ba2c7fb implements SingleInstanceRemoteListener.RemoteCall {
    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
    public final void accept(Object obj) {
        ((IRecentTasksListener) obj).onRecentTasksChanged();
    }
}
