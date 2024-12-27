package com.android.systemui.controls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public abstract class BaseActivity extends AppCompatActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsController controller;
    public final Executor executor;
    public final UserTracker userTracker;
    public final BaseActivity$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.BaseActivity$broadcastReceiver$1
        public final String SYSTEM_DIALOG_REASON_KEY = "reason";
        public final String SYSTEM_DIALOG_REASON_HOME_KEY = ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_HOME_KEY;
        public final String SYSTEM_DIALOG_REASON_RECENT_APPS_KEY = ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_RECENTS;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent != null) {
                BaseActivity baseActivity = BaseActivity.this;
                String action = intent.getAction();
                Log.d(baseActivity.getTag(), "onReceive " + action);
                if (!PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
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
            BaseActivity.this.onBackKeyPressed();
        }
    };
    public final BaseActivity$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.BaseActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ((ControlsControllerImpl) BaseActivity.this.controller).currentUser.getIdentifier();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BaseActivity baseActivity = BaseActivity.this;
            String tag = baseActivity.getTag();
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onUserChanged newUser = ", ", startingUser = ");
            int i2 = this.startingUser;
            RecyclerView$$ExternalSyntheticOutline0.m(i2, tag, m);
            if (i != i2) {
                ((UserTrackerImpl) baseActivity.userTracker).removeCallback(this);
                baseActivity.finish();
            }
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.BaseActivity$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.controls.BaseActivity$callback$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.controls.BaseActivity$userTrackerCallback$1] */
    public BaseActivity(BroadcastDispatcher broadcastDispatcher, ControlsController controlsController, UserTracker userTracker, Executor executor) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.controller = controlsController;
        this.userTracker = userTracker;
        this.executor = executor;
    }

    public BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    public String getTag() {
        return "BaseActivity";
    }

    public void onBackKeyPressed() {
        Log.d(getTag(), "onBackKeyPressed");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BroadcastDispatcher.registerReceiver$default(getBroadcastDispatcher(), this.broadcastReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), null, null, 0, null, 60);
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.executor);
        getOnBackPressedDispatcher().addCallback(this, this.callback);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        try {
            getBroadcastDispatcher().unregisterReceiver(this.broadcastReceiver);
        } catch (Exception unused) {
            Log.d(getTag(), "broadcastReceiver not registered");
        }
        super.onDestroy();
    }

    public void onHomeKeyPressed() {
        Log.d(getTag(), "onHomeKeyPressed");
    }

    public void onRecentAppsKeyPressed() {
        Log.d(getTag(), "onRecentAppsKeyPressed");
    }
}
