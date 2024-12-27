package com.android.systemui.navigationbar.interactor;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$prepareHandleEvent$2;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DesktopModeInteractor {
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public DesktopModeInteractor$addCallback$2 broadcastReceiver;
    private SettingsHelper.OnChangedCallback callback;
    public final IntentFilter intentFilter;
    private final SettingsHelper settingsHelper;
    public Boolean userUnlocked;

    public DesktopModeInteractor(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, SettingsHelper settingsHelper) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.bgHandler = handler;
        this.settingsHelper = settingsHelper;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.userUnlocked = userManager != null ? Boolean.valueOf(userManager.isUserUnlocked(ActivityManager.getCurrentUser())) : null;
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2] */
    public final void addCallback(final NavBarStoreImpl$prepareHandleEvent$2 navBarStoreImpl$prepareHandleEvent$2) {
        DesktopModeInteractor$addCallback$2 desktopModeInteractor$addCallback$2 = this.broadcastReceiver;
        if (desktopModeInteractor$addCallback$2 != null) {
            this.broadcastDispatcher.unregisterReceiver(desktopModeInteractor$addCallback$2);
        }
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent != null ? intent.getAction() : null;
                if (action != null && action.hashCode() == 833559602 && action.equals("android.intent.action.USER_UNLOCKED")) {
                    DesktopModeInteractor desktopModeInteractor = DesktopModeInteractor.this;
                    desktopModeInteractor.userUnlocked = Boolean.TRUE;
                    Consumer consumer = navBarStoreImpl$prepareHandleEvent$2;
                    if (consumer != null) {
                        consumer.accept(Boolean.valueOf(desktopModeInteractor.isEnabled()));
                    }
                }
            }
        };
        BroadcastDispatcher.registerReceiverWithHandler$default(this.broadcastDispatcher, r0, this.intentFilter, this.bgHandler, UserHandle.ALL, null, 48);
        this.broadcastReceiver = r0;
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$5
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$prepareHandleEvent$2;
                if (consumer != null) {
                    consumer.accept(Boolean.valueOf(this.isEnabled()));
                }
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.System.getUriFor(SettingsHelper.INDEX_NEW_DEX_MODE));
        navBarStoreImpl$prepareHandleEvent$2.accept(Boolean.valueOf(isEnabled()));
    }

    public final boolean isEnabled() {
        Boolean bool = this.userUnlocked;
        if (bool != null) {
            return this.settingsHelper.isNewDexModeEnabled() && bool.booleanValue();
        }
        return false;
    }
}
