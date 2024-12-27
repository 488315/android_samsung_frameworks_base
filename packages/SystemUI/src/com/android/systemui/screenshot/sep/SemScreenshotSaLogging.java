package com.android.systemui.screenshot.sep;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SemScreenshotSaLogging {
    public static void sendLogForUsabilityLogging(final Context context, final String str) {
        new Thread(new Runnable() { // from class: com.android.systemui.screenshot.sep.SemScreenshotSaLogging$$ExternalSyntheticLambda0
            public final /* synthetic */ String f$0 = "SCTP";

            @Override // java.lang.Runnable
            public final void run() {
                String str2 = this.f$0;
                String str3 = str;
                Context context2 = context;
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", "com.android.systemui.screenshot");
                contentValues.put("feature", str2);
                contentValues.put("extra", str3);
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
                intent.putExtra("data", contentValues);
                intent.setPackage("com.samsung.android.providers.context");
                context2.sendBroadcast(intent);
            }
        }).start();
    }
}
