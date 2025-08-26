package androidx.core.app;

import android.app.Notification;
import android.os.Bundle;

/* loaded from: classes.dex */
public class NotificationCompat$BigTextStyle extends NotificationCompat$Style {
    public CharSequence mBigText;

    public NotificationCompat$BigTextStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        Notification.BigTextStyle bigTextStyleBigText = new Notification.BigTextStyle(notificationCompatBuilder.mBuilder).setBigContentTitle(this.mBigContentTitle).bigText(this.mBigText);
        if (this.mSummaryTextSet) {
            bigTextStyleBigText.setSummaryText(this.mSummaryText);
        }
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void clearCompatExtraKeys(Bundle bundle) {
        super.clearCompatExtraKeys(bundle);
        bundle.remove("android.bigText");
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final String getClassName() {
        return "androidx.core.app.NotificationCompat$BigTextStyle";
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void restoreFromCompatExtras(Bundle bundle) {
        super.restoreFromCompatExtras(bundle);
        this.mBigText = bundle.getCharSequence("android.bigText");
    }

    public NotificationCompat$BigTextStyle(NotificationCompat$Builder notificationCompat$Builder) {
        setBuilder(notificationCompat$Builder);
    }
}
