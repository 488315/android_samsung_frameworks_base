package com.android.systemui.navigationbar.interactor;

import android.app.ActivityManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.SemRoleManager;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;

public final class TaskBarInteractor {
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public TaskBarInteractor$addCallback$2 broadcastReceiver;
    private SettingsHelper.OnChangedCallback callback;
    public final Context context;
    public final IntentFilter intentFilter;
    public boolean isDefaultHome;
    public final LogWrapper logWrapper;
    public TaskBarInteractor$addCallback$7 roleCallback;
    public final RoleManager roleManager;
    private final SettingsHelper settingsHelper;
    public Boolean userUnlocked;

    public TaskBarInteractor(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, SettingsHelper settingsHelper, LogWrapper logWrapper) {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.bgHandler = handler;
        this.settingsHelper = settingsHelper;
        this.logWrapper = logWrapper;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.userUnlocked = userManager != null ? Boolean.valueOf(userManager.isUserUnlocked(ActivityManager.getCurrentUser())) : null;
        this.roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        this.isDefaultHome = updateHomeStatus();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        logWrapper.d("TaskBarInteractor", "init userUnlocked=" + this.userUnlocked + " for userid=" + ActivityManager.getCurrentUser());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$7] */
    public final void addCallback(final NavBarStoreImpl$initInteractor$7 navBarStoreImpl$initInteractor$7) {
        TaskBarInteractor$addCallback$2 taskBarInteractor$addCallback$2 = this.broadcastReceiver;
        if (taskBarInteractor$addCallback$2 != null) {
            this.broadcastDispatcher.unregisterReceiver(taskBarInteractor$addCallback$2);
        }
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                UserManager userManager;
                String action = intent != null ? intent.getAction() : null;
                if (action != null) {
                    int hashCode = action.hashCode();
                    if (hashCode != 833559602) {
                        if (hashCode == 959232034 && action.equals("android.intent.action.USER_SWITCHED")) {
                            TaskBarInteractor.this.userUnlocked = (context == null || (userManager = (UserManager) context.getSystemService(UserManager.class)) == null) ? Boolean.FALSE : Boolean.valueOf(userManager.isUserUnlocked(ActivityManager.getCurrentUser()));
                        }
                    } else if (action.equals("android.intent.action.USER_UNLOCKED")) {
                        TaskBarInteractor.this.userUnlocked = Boolean.TRUE;
                        Runnable runnable = navBarStoreImpl$initInteractor$7;
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                }
                LogWrapper logWrapper = TaskBarInteractor.this.logWrapper;
                String action2 = intent != null ? intent.getAction() : null;
                logWrapper.d("TaskBarInteractor", "Receive " + action2 + " userUnlocked=" + TaskBarInteractor.this.userUnlocked + " for userid=" + ActivityManager.getCurrentUser());
            }
        };
        IntentFilter intentFilter = this.intentFilter;
        UserHandle userHandle = UserHandle.ALL;
        BroadcastDispatcher.registerReceiverWithHandler$default(this.broadcastDispatcher, r0, intentFilter, this.bgHandler, userHandle, null, 48);
        this.broadcastReceiver = r0;
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$5
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Runnable runnable = navBarStoreImpl$initInteractor$7;
                if (runnable != null) {
                    runnable.run();
                }
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_TASK_BAR), Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE), Settings.Secure.getUriFor("user_setup_complete"));
        TaskBarInteractor$addCallback$7 taskBarInteractor$addCallback$7 = this.roleCallback;
        if (taskBarInteractor$addCallback$7 != null) {
            this.roleManager.removeOnRoleHoldersChangedListenerAsUser(taskBarInteractor$addCallback$7, userHandle);
        }
        this.roleCallback = new OnRoleHoldersChangedListener() { // from class: com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$7
            public final void onRoleHoldersChanged(String str, UserHandle userHandle2) {
                if (str.equals("android.app.role.HOME")) {
                    TaskBarInteractor taskBarInteractor = TaskBarInteractor.this;
                    taskBarInteractor.isDefaultHome = taskBarInteractor.updateHomeStatus();
                    TaskBarInteractor taskBarInteractor2 = TaskBarInteractor.this;
                    taskBarInteractor2.logWrapper.d("TaskBarInteractor", "OnRoleHoldersChangedListener: defaultHome: " + taskBarInteractor2.isDefaultHome);
                    Runnable runnable = navBarStoreImpl$initInteractor$7;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
        };
        RoleManager roleManager = this.roleManager;
        Executor asExecutor = ExecutorsKt.asExecutor(Dispatchers.Default);
        TaskBarInteractor$addCallback$7 taskBarInteractor$addCallback$72 = this.roleCallback;
        Intrinsics.checkNotNull(taskBarInteractor$addCallback$72);
        roleManager.addOnRoleHoldersChangedListenerAsUser(asExecutor, taskBarInteractor$addCallback$72, userHandle);
    }

    public final boolean updateHomeStatus() {
        List roleHolders = new SemRoleManager(this.context).getRoleHolders("android.app.role.HOME");
        return roleHolders.isEmpty() || "com.sec.android.app.launcher".equals(roleHolders.get(0));
    }
}
