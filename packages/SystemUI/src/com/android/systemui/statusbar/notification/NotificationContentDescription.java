package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NotificationContentDescription {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.CharSequence] */
    public static final CharSequence contentDescForNotification(Context context, Notification notification2) {
        String loadHeaderAppName = notification2.loadHeaderAppName(context);
        String str = "";
        if (loadHeaderAppName == null) {
            loadHeaderAppName = "";
        }
        Bundle bundle = notification2.extras;
        String charSequence = bundle != null ? bundle.getCharSequence("android.title") : null;
        Bundle bundle2 = notification2.extras;
        ?? charSequence2 = bundle2 != null ? bundle2.getCharSequence("android.text") : null;
        ?? r6 = notification2.tickerText;
        boolean equals = TextUtils.equals(charSequence, loadHeaderAppName);
        String str2 = charSequence;
        if (equals) {
            str2 = charSequence2;
        }
        if (!TextUtils.isEmpty(str2)) {
            str = str2;
        } else if (!TextUtils.isEmpty(r6)) {
            str = r6;
        }
        return context.getString(R.string.accessibility_desc_notification_icon, loadHeaderAppName, str);
    }
}
