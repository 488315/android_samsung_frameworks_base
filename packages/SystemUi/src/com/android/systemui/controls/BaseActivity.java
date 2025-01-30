package com.android.systemui.controls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BaseActivity extends AppCompatActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsController controller;
    public final Executor executor;
    public final UserTracker userTracker;
    public final String TAG = "BaseActivity";
    public final BaseActivity$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.BaseActivity$broadcastReceiver$1
        public final String SYSTEM_DIALOG_REASON_KEY = "reason";
        public final String SYSTEM_DIALOG_REASON_HOME_KEY = ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_HOME_KEY;
        public final String SYSTEM_DIALOG_REASON_RECENT_APPS_KEY = ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_RECENTS;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent != null) {
                BaseActivity baseActivity = BaseActivity.this;
                String action = intent.getAction();
                Log.d(baseActivity.getTAG(), "onReceive " + action);
                if (!Intrinsics.areEqual("android.intent.action.CLOSE_SYSTEM_DIALOGS", action)) {
                    if (Intrinsics.areEqual("android.intent.action.SCREEN_OFF", action)) {
                        Log.d(baseActivity.getTAG(), "onScreenOff");
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
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.BaseActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ((ControlsControllerImpl) BaseActivity.this.controller).getCurrentUserId();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BaseActivity baseActivity = BaseActivity.this;
            String tag = baseActivity.getTAG();
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("onUserChanged newUser = ", i, ", startingUser = ");
            int i2 = this.startingUser;
            RecyclerView$$ExternalSyntheticOutline0.m46m(m1m, i2, tag);
            if (i != i2) {
                ((UserTrackerImpl) baseActivity.userTracker).removeCallback(this);
                baseActivity.finish();
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.controls.BaseActivity$broadcastReceiver$1] */
    public BaseActivity(BroadcastDispatcher broadcastDispatcher, ControlsController controlsController, UserTracker userTracker, Executor executor) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.controller = controlsController;
        this.userTracker = userTracker;
        this.executor = executor;
    }

    public BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    public String getTAG() {
        return this.TAG;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        BroadcastDispatcher.registerReceiver$default(getBroadcastDispatcher(), this.broadcastReceiver, intentFilter, null, null, 0, null, 60);
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.executor);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        try {
            getBroadcastDispatcher().unregisterReceiver(this.broadcastReceiver);
        } catch (Exception unused) {
            Log.d(getTAG(), "broadcastReceiver not registered");
        }
        super.onDestroy();
    }

    public void onHomeKeyPressed() {
        Log.d(getTAG(), "onHomeKeyPressed");
    }

    public void onRecentAppsKeyPressed() {
        Log.d(getTAG(), "onRecentAppsKeyPressed");
    }
}
