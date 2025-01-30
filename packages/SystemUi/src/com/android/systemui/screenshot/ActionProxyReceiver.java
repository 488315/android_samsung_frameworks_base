package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.RemoteAnimationAdapter;
import android.view.WindowManagerGlobal;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ActionProxyReceiver extends BroadcastReceiver {
    public final ActivityManagerWrapper mActivityManagerWrapper;
    public final ActivityStarter mActivityStarter;
    public final DisplayTracker mDisplayTracker;
    public final ScreenshotSmartActions mScreenshotSmartActions;

    public ActionProxyReceiver(ActivityManagerWrapper activityManagerWrapper, ScreenshotSmartActions screenshotSmartActions, DisplayTracker displayTracker, ActivityStarter activityStarter) {
        this.mActivityManagerWrapper = activityManagerWrapper;
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mDisplayTracker = displayTracker;
        this.mActivityStarter = activityStarter;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, final Intent intent) {
        this.mActivityStarter.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.screenshot.ActionProxyReceiver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActionProxyReceiver actionProxyReceiver = ActionProxyReceiver.this;
                Intent intent2 = intent;
                Context context2 = context;
                actionProxyReceiver.mActivityManagerWrapper.getClass();
                try {
                    ActivityManager.getService().closeSystemDialogs("screenshot");
                } catch (RemoteException e) {
                    Log.w("ActivityManagerWrapper", "Failed to close system windows", e);
                }
                PendingIntent pendingIntent = (PendingIntent) intent2.getParcelableExtra("android:screenshot_action_intent");
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setDisallowEnterPictureInPictureWhileLaunching(intent2.getBooleanExtra("android:screenshot_disallow_enter_pip", false));
                try {
                    pendingIntent.send(context2, 0, null, null, null, null, makeBasic.toBundle());
                    if (intent2.getBooleanExtra("android:screenshot_override_transition", false)) {
                        RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(ScreenshotController.SCREENSHOT_REMOTE_RUNNER, 0L, 0L);
                        try {
                            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                            actionProxyReceiver.mDisplayTracker.getClass();
                            windowManagerService.overridePendingAppTransitionRemote(remoteAnimationAdapter, 0);
                        } catch (Exception e2) {
                            Log.e("ActionProxyReceiver", "Error overriding screenshot app transition", e2);
                        }
                    }
                } catch (PendingIntent.CanceledException e3) {
                    Log.e("ActionProxyReceiver", "Pending intent canceled", e3);
                }
            }
        }, null, true, true, true);
        if (intent.getBooleanExtra("android:smart_actions_enabled", false)) {
            "android.intent.action.EDIT".equals(intent.getAction());
            ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
            intent.getStringExtra("android:screenshot_id");
            screenshotSmartActions.notifyScreenshotAction();
        }
    }
}
