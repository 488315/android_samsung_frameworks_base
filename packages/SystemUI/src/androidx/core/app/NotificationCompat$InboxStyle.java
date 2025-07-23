package androidx.core.app;

import android.app.Notification;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NotificationCompat$InboxStyle extends NotificationCompat$Style {
    public final ArrayList mTexts = new ArrayList();

    public NotificationCompat$InboxStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(notificationCompatBuilder.mBuilder).setBigContentTitle(this.mBigContentTitle);
        if (this.mSummaryTextSet) {
            bigContentTitle.setSummaryText(this.mSummaryText);
        }
        ArrayList arrayList = this.mTexts;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            bigContentTitle.addLine((CharSequence) obj);
        }
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void clearCompatExtraKeys(Bundle bundle) {
        super.clearCompatExtraKeys(bundle);
        bundle.remove("android.textLines");
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final String getClassName() {
        return "androidx.core.app.NotificationCompat$InboxStyle";
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void restoreFromCompatExtras(Bundle bundle) {
        super.restoreFromCompatExtras(bundle);
        this.mTexts.clear();
        if (bundle.containsKey("android.textLines")) {
            Collections.addAll(this.mTexts, bundle.getCharSequenceArray("android.textLines"));
        }
    }

    public NotificationCompat$InboxStyle(NotificationCompat$Builder notificationCompat$Builder) {
        setBuilder(notificationCompat$Builder);
    }
}
