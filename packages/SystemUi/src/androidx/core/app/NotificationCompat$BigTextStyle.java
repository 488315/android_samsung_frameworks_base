package androidx.core.app;

import android.app.Notification;
import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationCompat$BigTextStyle extends NotificationCompat$Style {
    public CharSequence mBigText;

    public NotificationCompat$BigTextStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void addCompatExtras(Bundle bundle) {
        super.addCompatExtras(bundle);
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
