package androidx.core.app;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NotificationCompat$Style {
    public CharSequence mBigContentTitle;
    public NotificationCompat$Builder mBuilder;
    public CharSequence mSummaryText;
    public boolean mSummaryTextSet = false;

    public void addCompatExtras(Bundle bundle) {
        if (this.mSummaryTextSet) {
            bundle.putCharSequence("android.summaryText", this.mSummaryText);
        }
        CharSequence charSequence = this.mBigContentTitle;
        if (charSequence != null) {
            bundle.putCharSequence("android.title.big", charSequence);
        }
        String className = getClassName();
        if (className != null) {
            bundle.putString("androidx.core.app.extra.COMPAT_TEMPLATE", className);
        }
    }

    public void clearCompatExtraKeys(Bundle bundle) {
        bundle.remove("android.summaryText");
        bundle.remove("android.title.big");
        bundle.remove("androidx.core.app.extra.COMPAT_TEMPLATE");
    }

    public String getClassName() {
        return null;
    }

    public void restoreFromCompatExtras(Bundle bundle) {
        if (bundle.containsKey("android.summaryText")) {
            this.mSummaryText = bundle.getCharSequence("android.summaryText");
            this.mSummaryTextSet = true;
        }
        this.mBigContentTitle = bundle.getCharSequence("android.title.big");
    }

    public final void setBuilder(NotificationCompat$Builder notificationCompat$Builder) {
        if (this.mBuilder != notificationCompat$Builder) {
            this.mBuilder = notificationCompat$Builder;
            if (notificationCompat$Builder == null || notificationCompat$Builder.mStyle == this) {
                return;
            }
            notificationCompat$Builder.mStyle = this;
            setBuilder(notificationCompat$Builder);
        }
    }

    public void apply(NotificationCompatBuilder notificationCompatBuilder) {
    }
}
