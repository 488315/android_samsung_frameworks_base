package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.os.AsyncTask;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationInlineImageCache implements NotificationInlineImageResolver.ImageCache {
    public final ConcurrentHashMap mCache = new ConcurrentHashMap();
    public NotificationInlineImageResolver mResolver;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PreloadImageTask extends AsyncTask {
        public final NotificationInlineImageResolver mResolver;

        public PreloadImageTask(NotificationInlineImageResolver notificationInlineImageResolver) {
            this.mResolver = notificationInlineImageResolver;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return this.mResolver.resolveImage(((Uri[]) objArr)[0]);
        }
    }
}
