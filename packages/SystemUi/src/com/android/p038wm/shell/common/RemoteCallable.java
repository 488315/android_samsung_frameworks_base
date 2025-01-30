package com.android.p038wm.shell.common;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface RemoteCallable {
    Context getContext();

    ShellExecutor getRemoteCallExecutor();
}
