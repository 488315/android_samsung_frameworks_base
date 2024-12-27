package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.os.AsyncTask;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationInlineImageCache implements NotificationInlineImageResolver.ImageCache {
    public final ConcurrentHashMap mCache = new ConcurrentHashMap();
    public NotificationInlineImageResolver mResolver;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PreloadImageTask extends AsyncTask {
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
