package com.android.systemui.statusbar.notification.row;

import android.os.AsyncTask;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationInlineImageCache$$ExternalSyntheticLambda1 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        NotificationInlineImageCache.PreloadImageTask preloadImageTask = (NotificationInlineImageCache.PreloadImageTask) obj2;
        if (preloadImageTask.getStatus() != AsyncTask.Status.FINISHED) {
            preloadImageTask.cancel(true);
        }
    }
}
