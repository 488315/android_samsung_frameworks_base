package com.android.systemui.screenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DeleteScreenshotReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor mBackgroundExecutor;
    public final ScreenshotSmartActions mScreenshotSmartActions;

    public DeleteScreenshotReceiver(ScreenshotSmartActions screenshotSmartActions, Executor executor) {
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mBackgroundExecutor = executor;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        if (intent.hasExtra("android:screenshot_uri_id")) {
            final Uri parse = Uri.parse(intent.getStringExtra("android:screenshot_uri_id"));
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.DeleteScreenshotReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Context context2 = context;
                    Uri uri = parse;
                    int i = DeleteScreenshotReceiver.$r8$clinit;
                    context2.getContentResolver().delete(uri, null, null);
                }
            });
            if (intent.getBooleanExtra("android:smart_actions_enabled", false)) {
                ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
                intent.getStringExtra("android:screenshot_id");
                screenshotSmartActions.notifyScreenshotAction();
            }
        }
    }
}
