package com.android.systemui.navigationbar.interactor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.BasicRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DesktopModeInteractor {
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public DesktopModeInteractor$addCallback$2 broadcastReceiver;
    public DesktopModeInteractor$addCallback$5 callback;
    public final IntentFilter intentFilter;
    public final SettingsHelper settingsHelper;
    public boolean userUnlocked;

    public DesktopModeInteractor(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, SettingsHelper settingsHelper) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.bgHandler = handler;
        this.settingsHelper = settingsHelper;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        this.userUnlocked = ((UserManager) context.getSystemService(UserManager.class)).isUserUnlocked(ActivityManager.getCurrentUser());
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
    }

    public final boolean isEnabled() {
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.getClass();
        return (BasicRune.NAVBAR_NEW_DEX && settingsHelper.mItemLists.get("new_dex").getIntValue() != 0) && this.userUnlocked;
    }
}
