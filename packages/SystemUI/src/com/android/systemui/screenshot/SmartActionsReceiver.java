package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* loaded from: classes2.dex */
public class SmartActionsReceiver extends BroadcastReceiver {
    public final ScreenshotSmartActions mScreenshotSmartActions;

    public SmartActionsReceiver(ScreenshotSmartActions screenshotSmartActions) {
        this.mScreenshotSmartActions = screenshotSmartActions;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) throws PendingIntent.CanceledException {
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("android:screenshot_action_intent", PendingIntent.class);
        Intent intent2 = (Intent) intent.getParcelableExtra("android:screenshot_action_intent_fillin", Intent.class);
        intent.getStringExtra("android:screenshot_action_type");
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
        try {
            pendingIntent.send(context, 0, intent2, null, null, null, activityOptionsMakeBasic.toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.e("SmartActionsReceiver", "Pending intent canceled", e);
        }
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        intent.getStringExtra("android:screenshot_id");
        pendingIntent.getIntent();
        screenshotSmartActions.getClass();
        try {
            ((ScreenshotNotificationSmartActionsProvider) screenshotSmartActions.mScreenshotNotificationSmartActionsProviderProvider.get()).getClass();
        } catch (Throwable th) {
            Log.e("Screenshot", "Error in notifyScreenshotAction: ", th);
        }
    }
}
