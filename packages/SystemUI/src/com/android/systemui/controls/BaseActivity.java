package com.android.systemui.controls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class BaseActivity extends AppCompatActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsController controller;
    public final Executor executor;
    public boolean isSkipDisableEdgeToEdge;
    public boolean isSkipUpdateStatusBarColor;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final int BREAKPOINT_RANGE = 479;
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    public final BaseActivity$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.BaseActivity$broadcastReceiver$1
        public final String SYSTEM_DIALOG_REASON_KEY = "reason";
        public final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        public final String SYSTEM_DIALOG_REASON_RECENT_APPS_KEY = "recentapps";

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent != null) {
                BaseActivity baseActivity = this.this$0;
                String action = intent.getAction();
                Log.d(baseActivity.getTag(), "onReceive " + action);
                if (!PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                    if ("android.intent.action.SCREEN_OFF".equals(action) || "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY".equals(action)) {
                        Log.d(baseActivity.getTag(), "onScreenOff");
                        baseActivity.finish();
                        return;
                    }
                    return;
                }
                String stringExtra = intent.getStringExtra(this.SYSTEM_DIALOG_REASON_KEY);
                if (Intrinsics.areEqual(stringExtra, this.SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    baseActivity.onHomeKeyPressed();
                } else if (Intrinsics.areEqual(stringExtra, this.SYSTEM_DIALOG_REASON_RECENT_APPS_KEY)) {
                    baseActivity.onRecentAppsKeyPressed();
                }
            }
        }
    };
    public final BaseActivity$callback$1 callback = new OnBackPressedCallback() { // from class: com.android.systemui.controls.BaseActivity$callback$1
        {
            super(true);
        }

        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {
            Log.d("BaseActivity", "handleOnBackPressed called");
            this.this$0.onBackKeyPressed();
        }
    };
    public final BaseActivity$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.BaseActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = this.this$0.controller.getCurrentUserId();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BaseActivity baseActivity = this.this$0;
            String tag = baseActivity.getTag();
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onUserChanged newUser = ", ", startingUser = ");
            int i2 = this.startingUser;
            RecyclerView$$ExternalSyntheticOutline0.m(i2, tag, sbM);
            if (i != i2) {
                ((UserTrackerImpl) baseActivity.userTracker).removeCallback(this);
                baseActivity.finish();
            }
        }
    };
    public final BaseActivity$settingsCallback$1 settingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.controls.BaseActivity$settingsCallback$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri == null) {
                return;
            }
            if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE)) || uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE))) {
                BaseActivity baseActivity = this.this$0;
                Log.d(baseActivity.getTag(), "isUltraPowerSavingMode changed = " + baseActivity.getSettingsHelper().isUltraPowerSavingMode());
                baseActivity.finish();
            }
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.controls.BaseActivity$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.controls.BaseActivity$callback$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.controls.BaseActivity$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.controls.BaseActivity$settingsCallback$1] */
    public BaseActivity(BroadcastDispatcher broadcastDispatcher, ControlsController controlsController, UserTracker userTracker, Executor executor) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.controller = controlsController;
        this.userTracker = userTracker;
        this.executor = executor;
    }

    public static final void updateStatusBarIconColor$lambda$1$setAppearance(View view, int i, int i2) {
        WindowInsetsController windowInsetsController = view.getWindowInsetsController();
        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsAppearance(i, 8);
            windowInsetsController.setSystemBarsAppearance(i2, 16);
        }
    }

    public BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }

    public String getTag() {
        return "BaseActivity";
    }

    public void onBackKeyPressed() {
        Log.d(getTag(), "onBackKeyPressed");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        View rootView;
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            Configuration configuration = window.getContext().getResources().getConfiguration();
            if (configuration.smallestScreenWidthDp <= BREAKPOINT_RANGE && configuration.orientation == 2) {
                window.addFlags(1024);
            }
            if (!this.isSkipUpdateStatusBarColor) {
                View decorView = window.getDecorView();
                boolean z = (decorView.getContext().getResources().getConfiguration().uiMode & 48) == 32;
                if (TextUtils.isEmpty(this.settingsHelper.getActiveThemePackage()) || z) {
                    if (z) {
                        updateStatusBarIconColor$lambda$1$setAppearance(decorView, 0, 0);
                    } else {
                        updateStatusBarIconColor$lambda$1$setAppearance(decorView, 8, 16);
                    }
                } else if (decorView.getContext().getResources().getBoolean(17892066)) {
                    updateStatusBarIconColor$lambda$1$setAppearance(decorView, 8, 16);
                } else {
                    updateStatusBarIconColor$lambda$1$setAppearance(decorView, 0, 0);
                }
            }
            if (!this.isSkipDisableEdgeToEdge && (rootView = window.getDecorView().getRootView()) != null) {
                rootView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.BaseActivity$disableEdgeToEdge$1
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
                        BaseActivity baseActivity = this.this$0;
                        Configuration configuration2 = view.getContext().getResources().getConfiguration();
                        BaseActivity.Companion companion = BaseActivity.Companion;
                        baseActivity.getClass();
                        if (configuration2.smallestScreenWidthDp <= BaseActivity.BREAKPOINT_RANGE && configuration2.orientation == 2) {
                            view.setPadding(insets.left, 0, insets.right, 0);
                        } else {
                            view.setPadding(0, insets.top, 0, insets.bottom);
                        }
                        return WindowInsets.CONSUMED;
                    }
                });
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY");
        BroadcastDispatcher.registerReceiver$default(getBroadcastDispatcher(), this.broadcastReceiver, intentFilter, null, null, 0, null, 60);
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.executor);
        getOnBackPressedDispatcher().addCallback(this, this.callback);
        this.settingsHelper.registerCallback(this.settingsCallback, Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        try {
            getBroadcastDispatcher().unregisterReceiver(this.broadcastReceiver);
        } catch (Exception unused) {
            Log.d(getTag(), "broadcastReceiver not registered");
        }
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        this.settingsHelper.unregisterCallback(this.settingsCallback);
        super.onDestroy();
    }

    public void onHomeKeyPressed() {
        Log.d(getTag(), "onHomeKeyPressed");
    }

    public void onRecentAppsKeyPressed() {
        Log.d(getTag(), "onRecentAppsKeyPressed");
    }
}
