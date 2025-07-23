package com.android.systemui.screenshot.sep;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SemScreenshotSaLogging {
    public static void sendLogForUsabilityLogging(final Context context, final String str) {
        new Thread(new Runnable() { // from class: com.android.systemui.screenshot.sep.SemScreenshotSaLogging$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                String str2 = str;
                Context context2 = context;
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", "com.android.systemui.screenshot");
                contentValues.put("feature", "SCTP");
                contentValues.put("extra", str2);
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
                intent.putExtra("data", contentValues);
                intent.setPackage("com.samsung.android.providers.context");
                context2.sendBroadcast(intent);
            }
        }).start();
    }
}
