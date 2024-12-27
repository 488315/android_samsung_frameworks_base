package com.android.systemui.recents;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.Lazy;

public final class ScreenPinningRequest implements View.OnClickListener, NavigationModeController.ModeChangedListener, CoreStartable, ConfigurationController.ConfigurationListener {
    public final AccessibilityManager mAccessibilityService;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public int mNavBarMode;
    public final Lazy mNavigationBarControllerLazy;
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.recents.ScreenPinningRequest.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ScreenPinningRequest.this.getClass();
        }
    };
    public final UserTracker mUserTracker;
    public final WindowManager mWindowManager;

    public ScreenPinningRequest(Context context, NavigationModeController navigationModeController, Lazy lazy, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker) {
        navigationModeController.addListener(this);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() != R.id.screen_pinning_ok_button) {
            return;
        }
        try {
            ActivityTaskManager.getService().startSystemLockTaskMode(0);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
    }
}
