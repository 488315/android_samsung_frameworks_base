package com.android.systemui.statusbar.notification.row;

import android.os.AsyncTask;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationInlineImageCache$$ExternalSyntheticLambda1 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        NotificationInlineImageCache.PreloadImageTask preloadImageTask = (NotificationInlineImageCache.PreloadImageTask) obj2;
        if (preloadImageTask.getStatus() != AsyncTask.Status.FINISHED) {
            preloadImageTask.cancel(true);
        }
    }
}
