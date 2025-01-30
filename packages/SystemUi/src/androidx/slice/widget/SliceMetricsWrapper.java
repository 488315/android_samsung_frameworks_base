package androidx.slice.widget;

import android.content.Context;
import android.net.Uri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceMetricsWrapper extends SliceMetrics {
    public final android.app.slice.SliceMetrics mSliceMetrics;

    public SliceMetricsWrapper(Context context, Uri uri) {
        this.mSliceMetrics = new android.app.slice.SliceMetrics(context, uri);
    }

    public final void logHidden() {
        this.mSliceMetrics.logHidden();
    }

    public final void logVisible() {
        this.mSliceMetrics.logVisible();
    }
}
