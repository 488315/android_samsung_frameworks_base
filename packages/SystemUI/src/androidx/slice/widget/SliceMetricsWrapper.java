package androidx.slice.widget;

import android.content.Context;
import android.net.Uri;

/* loaded from: classes.dex */
public class SliceMetricsWrapper extends SliceMetrics {
    public final android.app.slice.SliceMetrics mSliceMetrics;

    public SliceMetricsWrapper(Context context, Uri uri) {
        this.mSliceMetrics = new android.app.slice.SliceMetrics(context, uri);
    }
}
