package androidx.core.app;

import android.app.Notification;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationCompat$InboxStyle extends NotificationCompat$Style {
    public final ArrayList mTexts = new ArrayList();

    public NotificationCompat$InboxStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(notificationCompatBuilder.mBuilder).setBigContentTitle(this.mBigContentTitle);
        if (this.mSummaryTextSet) {
            bigContentTitle.setSummaryText(this.mSummaryText);
        }
        Iterator it = this.mTexts.iterator();
        while (it.hasNext()) {
            bigContentTitle.addLine((CharSequence) it.next());
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
        ArrayList arrayList = this.mTexts;
        arrayList.clear();
        if (bundle.containsKey("android.textLines")) {
            Collections.addAll(arrayList, bundle.getCharSequenceArray("android.textLines"));
        }
    }

    public NotificationCompat$InboxStyle(NotificationCompat$Builder notificationCompat$Builder) {
        setBuilder(notificationCompat$Builder);
    }
}
