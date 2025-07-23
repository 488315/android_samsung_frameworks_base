package com.android.systemui.pluginlock;

import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.samsung.android.cover.CoverState;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginLockManager {
    String getDynamicLockData();

    String getLockStarItemLocationInfo(String str);

    PluginLock getPluginLock();

    boolean getShortcutTaskState(String str);

    void onCoverStateChanged(CoverState coverState);

    void onFolderStateChanged(boolean z);

    void registerSystemUIViewCallback(PluginLockListener.State state);

    void removeShortcutTaskListener();

    void removeSystemUIViewCallback(PluginLockListener.State state);

    void updateShortcutTaskState(String str);
}
