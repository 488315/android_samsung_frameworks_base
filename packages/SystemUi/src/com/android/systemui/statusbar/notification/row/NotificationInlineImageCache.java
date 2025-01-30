package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.os.AsyncTask;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationInlineImageCache implements NotificationInlineImageResolver.ImageCache {
    public final ConcurrentHashMap mCache = new ConcurrentHashMap();
    public NotificationInlineImageResolver mResolver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
