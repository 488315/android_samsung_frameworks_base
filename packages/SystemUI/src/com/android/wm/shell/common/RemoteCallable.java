package com.android.wm.shell.common;

import android.content.Context;

/* loaded from: classes3.dex */
public interface RemoteCallable {
    Context getContext();

    ShellExecutor getRemoteCallExecutor();
}
