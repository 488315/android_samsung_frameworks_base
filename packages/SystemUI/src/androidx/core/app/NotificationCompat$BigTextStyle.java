package androidx.core.app;

import android.app.Notification;
import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NotificationCompat$BigTextStyle extends NotificationCompat$Style {
    public CharSequence mBigText;

    public NotificationCompat$BigTextStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        Notification.BigTextStyle bigText = new Notification.BigTextStyle(notificationCompatBuilder.mBuilder).setBigContentTitle(this.mBigContentTitle).bigText(this.mBigText);
        if (this.mSummaryTextSet) {
            bigText.setSummaryText(this.mSummaryText);
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
