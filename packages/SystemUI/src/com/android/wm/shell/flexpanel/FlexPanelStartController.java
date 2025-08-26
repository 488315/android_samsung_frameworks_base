package com.android.wm.shell.flexpanel;

import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes3.dex */
public class FlexPanelStartController {
    public final Context mContext;
    public boolean mFlexModePanelEnabled;
    public final H mHandler;
    public boolean mIsFolded;
    public boolean mIsImeShowing = false;
    public boolean mIsQuickPanelShowing = false;
    public boolean mIsTableMode;
    public boolean mMwEnabled;
    public boolean mNavBarGestureEnabled;
    public boolean mOnRecentsAnimation;
    public final int mRotation;
    public final SemStatusBarManager mSemStatusBarManager;

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
            if (i == 1) {
                flexPanelStartController.getClass();
                Log.d("FlexPanelStartController", "auto run Flex Panel");
                Intent intent = new Intent("android.intent.action.AUTORUN_FLEX_PANEL");
                intent.setComponent(MultiWindowUtils.FLEX_PANEL_COMPONENT_NAME);
                intent.setFlags(337907712);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchDisplayId(0);
                flexPanelStartController.mContext.startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT);
                return;
            }
            if (i == 2) {
                flexPanelStartController.getClass();
                Log.d("FlexPanelStartController", "setCollapseButton");
                Intent intent2 = new Intent("android.intent.action.COLLAPSE_FLEX_PANEL");
                intent2.setPackage(flexPanelStartController.mContext.getPackageName());
                flexPanelStartController.setNavigationBarButton(R.drawable.fab_sys_close, PendingIntent.getBroadcastAsUser(flexPanelStartController.mContext, 0, intent2, 67108864, UserHandle.CURRENT));
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                Log.d("FlexPanelStartController", "handleMessage: RECHECK_CONDITION_MSG");
                flexPanelStartController.onFlexModeChanged();
                return;
            }
            flexPanelStartController.getClass();
            Log.d("FlexPanelStartController", "setExpandButton");
            Intent intent3 = new Intent("android.intent.action.EXPAND_FLEX_PANEL");
            intent3.setComponent(MultiWindowUtils.FLEX_PANEL_COMPONENT_NAME);
            intent3.setFlags(337907712);
            flexPanelStartController.setNavigationBarButton(R.drawable.fab_sys_open_flip, PendingIntent.getActivityAsUser(flexPanelStartController.mContext, 0, intent3, 1107296256, null, UserHandle.CURRENT));
        }
    }

    public FlexPanelStartController(Context context) {
        this.mContext = context;
        H h = new H(context.getMainLooper());
        this.mHandler = h;
        this.mSemStatusBarManager = (SemStatusBarManager) context.getSystemService("sem_statusbar");
        this.mRotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
        this.mFlexModePanelEnabled = Settings.Global.getInt(context.getContentResolver(), "flex_mode_panel_enabled", 1) != 0;
        this.mNavBarGestureEnabled = isNavBarGestureEnabled();
        this.mIsFolded = SemWindowManager.getInstance().isFolded();
        ((DeviceStateManager) context.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(h), new DeviceStateManager.DeviceStateCallback() { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController$$ExternalSyntheticLambda1
            public final void onDeviceStateChanged(DeviceState deviceState) {
                FlexPanelStartController flexPanelStartController = this.f$0;
                boolean z = deviceState.getIdentifier() == 1;
                if (flexPanelStartController.mIsFolded && !z) {
                    SystemClock.elapsedRealtime();
                }
                flexPanelStartController.mIsFolded = z;
                boolean zContains = deviceState.getConfiguration().getPhysicalProperties().contains(2);
                CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("OnDeviceStateChanged: mIsTableMode="), flexPanelStartController.mIsTableMode, ", isTableMode=", zContains, "FlexPanelStartController");
                if (flexPanelStartController.mIsTableMode != zContains) {
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("onFoldStateTableModeChanged: isTableMode=", "FlexPanelStartController", zContains);
                    flexPanelStartController.mIsTableMode = zContains;
                    flexPanelStartController.onFlexModeChanged();
                }
            }
        });
        SharedPreferences sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.FLEX_PANEL_PREF_NAME, 0);
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING && sharedPreferences != null && sharedPreferences.getString(SystemUIAnalytics.FLEX_TOOLBAR_FUNCTION_STATUS, null) == null) {
            Log.d("FlexPanelStartController", "Init FlexPanel StatusPreferences");
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.android.systemui/com.android.wm.shell.controlpanel.ControlPanelService"));
            intent.putExtra("display_floating_icon_clear", true);
            context.startServiceAsUser(intent, UserHandle.CURRENT);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ResponseAxT9Info");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent2) {
                String action = intent2.getAction();
                action.getClass();
                switch (action) {
                    case "ResponseAxT9Info":
                        if (!intent2.getBooleanExtra("AxT9IME.isVisibleWindow", false)) {
                            FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
                            flexPanelStartController.mIsImeShowing = false;
                            flexPanelStartController.onFlexModeChanged();
                            break;
                        } else {
                            FlexPanelStartController flexPanelStartController2 = FlexPanelStartController.this;
                            flexPanelStartController2.mIsImeShowing = true;
                            flexPanelStartController2.clearMessages();
                            FlexPanelStartController.this.clearButton();
                            break;
                        }
                    case "com.samsung.systemui.statusbar.COLLAPSED":
                        FlexPanelStartController flexPanelStartController3 = FlexPanelStartController.this;
                        flexPanelStartController3.mIsQuickPanelShowing = false;
                        flexPanelStartController3.onFlexModeChanged();
                        break;
                    case "com.samsung.systemui.statusbar.EXPANDED":
                        FlexPanelStartController flexPanelStartController4 = FlexPanelStartController.this;
                        flexPanelStartController4.mIsQuickPanelShowing = true;
                        flexPanelStartController4.clearMessages();
                        FlexPanelStartController.this.clearButton();
                        break;
                }
            }
        }, intentFilter, 2);
        this.mMwEnabled = MultiWindowCoreState.MW_ENABLED;
        ActivityThread.currentActivityThread().registerMultiWindowCoreStateListener(new MultiWindowCoreState.MultiWindowCoreStateListener() { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController$$ExternalSyntheticLambda0
            public final void onMultiWindowCoreStateChanged(int i) {
                FlexPanelStartController flexPanelStartController = this.f$0;
                if ((i & 1) != 0) {
                    boolean z = flexPanelStartController.mMwEnabled;
                    boolean z2 = MultiWindowCoreState.MW_ENABLED;
                    if (z != z2) {
                        flexPanelStartController.mMwEnabled = z2;
                        flexPanelStartController.onFlexModeChanged();
                    }
                }
            }
        });
        MultiWindowManager.getInstance().registerRemoteAppTransitionListener(new IRemoteAppTransitionListener.Stub() { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController.1
            public final void onFinishRecentsAnimation(boolean z) {
                FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
                flexPanelStartController.mOnRecentsAnimation = false;
                if (flexPanelStartController.mNavBarGestureEnabled) {
                    flexPanelStartController.onFlexModeChanged();
                }
            }

            public final void onStartHomeAnimation(boolean z) {
                FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
                if (flexPanelStartController.mNavBarGestureEnabled) {
                    flexPanelStartController.getClass();
                }
            }

            public final void onStartRecentsAnimation(boolean z) {
                FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
                flexPanelStartController.mOnRecentsAnimation = true;
                if (flexPanelStartController.mNavBarGestureEnabled) {
                    flexPanelStartController.clearMessages();
                    FlexPanelStartController.this.getClass();
                }
            }

            public final void onWallpaperVisibilityChanged(boolean z, boolean z2) {
            }
        });
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_NAVIGATION_MODE), false, new ContentObserver(h) { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
                if (flexPanelStartController.mNavBarGestureEnabled == flexPanelStartController.isNavBarGestureEnabled()) {
                    return;
                }
                FlexPanelStartController.this.getClass();
                FlexPanelStartController flexPanelStartController2 = FlexPanelStartController.this;
                flexPanelStartController2.mNavBarGestureEnabled = flexPanelStartController2.isNavBarGestureEnabled();
                FlexPanelStartController flexPanelStartController3 = FlexPanelStartController.this;
                boolean z2 = flexPanelStartController3.mNavBarGestureEnabled;
                flexPanelStartController3.clearMessages();
                FlexPanelStartController.this.onFlexModeChanged();
            }
        });
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("flex_mode_panel_enabled"), false, new ContentObserver(h) { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                FlexPanelStartController flexPanelStartController = FlexPanelStartController.this;
                flexPanelStartController.mFlexModePanelEnabled = Settings.Global.getInt(flexPanelStartController.mContext.getContentResolver(), "flex_mode_panel_enabled", 1) != 0;
                FlexPanelStartController.this.clearMessages();
                FlexPanelStartController.this.onFlexModeChanged();
            }
        });
    }

    public final void clearButton() {
        this.mSemStatusBarManager.setNavigationBarShortcut("com.android.wm.shell.controlpanel.activity.FlexPanelActivity", (RemoteViews) null, 0, 11);
    }

    public final void clearMessages() {
        H h = this.mHandler;
        h.removeMessages(1);
        h.removeMessages(2);
        h.removeMessages(3);
    }

    public final boolean isExecutableState() {
        int i = this.mRotation;
        boolean z = i == 0 || i == 2;
        if (this.mMwEnabled && this.mIsTableMode && this.mFlexModePanelEnabled && !this.mIsImeShowing && !this.mIsQuickPanelShowing && !this.mOnRecentsAnimation && z) {
            return true;
        }
        Log.d("FlexPanelStartController", "not support state, mMwEnabled=" + this.mMwEnabled + ", mIsTableMode=" + this.mIsTableMode + ", mDesktopMode=false, mFlexModePanelEnabled=" + this.mFlexModePanelEnabled + ", mIsImeShowing=" + this.mIsImeShowing + ", mIsQuickPanelShowing=" + this.mIsQuickPanelShowing + ", mIsTaskBarAppsEnabled=false, mIsGlobalActionVisible=false, mOnRecentsAnimation=" + this.mOnRecentsAnimation + ", isSupportRotation=" + z + ", mIsKeyguardVisible=false");
        clearButton();
        return false;
    }

    public final boolean isNavBarGestureEnabled() {
        return 2 <= Settings.Secure.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_MODE, 0, -2);
    }

    public final void onFlexModeChanged() {
        if (MultiWindowUtils.isInSubDisplay(this.mContext)) {
            return;
        }
        clearMessages();
        try {
            if (isExecutableState()) {
                throw null;
            }
        } catch (Exception e) {
            Log.e("FlexPanelStartController", "failed to call SemStatusBarManager", e);
        }
    }

    public final void setNavigationBarButton(int i, PendingIntent pendingIntent) {
        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.flex_panel_button_layout);
        remoteViews.setImageViewResource(R.id.icon_area, i);
        remoteViews.setOnClickPendingIntent(R.id.icon_area, pendingIntent);
        if (i == R.drawable.fab_sys_open_fold || i == R.drawable.fab_sys_open_flip) {
            remoteViews.setContentDescription(R.id.icon_area, this.mContext.getString(R.string.flex_mode_open_button));
        } else {
            remoteViews.setContentDescription(R.id.icon_area, this.mContext.getString(R.string.flex_mode_close_button));
        }
        this.mSemStatusBarManager.setNavigationBarShortcut("com.android.wm.shell.controlpanel.activity.FlexPanelActivity", remoteViews, 0, 11);
    }
}
