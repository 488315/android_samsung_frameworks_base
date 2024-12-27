package com.android.systemui.statusbar.notification.row;

import android.os.AsyncTask;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
