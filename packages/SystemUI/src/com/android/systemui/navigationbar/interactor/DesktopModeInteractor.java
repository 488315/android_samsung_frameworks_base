package com.android.systemui.navigationbar.interactor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.SystemUIInitializer;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.wm.shell.dagger.WMComponent;
import com.android.wm.shell.desktopmode.DesktopMode;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class DesktopModeInteractor {
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public DesktopModeInteractor$addCallback$2 broadcastReceiver;
    public final DesktopMode desktopMode;
    public final IntentFilter intentFilter;
    public boolean isDefaultDisplayDesktopMode;
    public final Executor mainExecutor;
    public Boolean userUnlocked;

    public DesktopModeInteractor(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Handler handler) {
        WMComponent wMComponent;
        Optional desktopMode;
        this.broadcastDispatcher = broadcastDispatcher;
        this.mainExecutor = executor;
        this.bgHandler = handler;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        DesktopMode desktopMode2 = null;
        this.userUnlocked = userManager != null ? Boolean.valueOf(userManager.isUserUnlocked(ActivityManager.getCurrentUser())) : null;
        SystemUIAppComponentFactoryBase.Companion.getClass();
        SystemUIInitializer systemUIInitializer = SystemUIAppComponentFactoryBase.systemUIInitializer;
        if (systemUIInitializer != null && (wMComponent = systemUIInitializer.getWMComponent()) != null && (desktopMode = wMComponent.getDesktopMode()) != null) {
            desktopMode2 = (DesktopMode) desktopMode.orElse(null);
        }
        this.desktopMode = desktopMode2;
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
    }

    public final boolean isEnabled() {
        if (this.isDefaultDisplayDesktopMode) {
            Boolean bool = this.userUnlocked;
            if (bool != null ? bool.booleanValue() : false) {
                return true;
            }
        }
        return false;
    }
}
