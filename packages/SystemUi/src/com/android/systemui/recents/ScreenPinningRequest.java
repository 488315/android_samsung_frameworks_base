package com.android.systemui.recents;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenPinningRequest implements View.OnClickListener, NavigationModeController.ModeChangedListener {
    public final AccessibilityManager mAccessibilityService;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy mCentralSurfacesOptionalLazy;
    public int mNavBarMode;
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.recents.ScreenPinningRequest.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ScreenPinningRequest.this.getClass();
        }
    };
    public final UserTracker mUserTracker;
    public final WindowManager mWindowManager;

    public ScreenPinningRequest(Context context, Lazy lazy, NavigationModeController navigationModeController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker) {
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mAccessibilityService = (AccessibilityManager) context.getSystemService("accessibility");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mNavBarMode = navigationModeController.addListener(this);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
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

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }
}
