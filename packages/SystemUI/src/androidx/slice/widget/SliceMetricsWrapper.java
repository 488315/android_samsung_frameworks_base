package androidx.slice.widget;

import android.content.Context;
import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SliceMetricsWrapper extends SliceMetrics {
    public final android.app.slice.SliceMetrics mSliceMetrics;

    public SliceMetricsWrapper(Context context, Uri uri) {
        this.mSliceMetrics = new android.app.slice.SliceMetrics(context, uri);
    }
}
