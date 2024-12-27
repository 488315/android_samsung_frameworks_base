package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import com.android.systemui.R;
import com.android.systemui.biometrics.Utils;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingSeekBarCreator {
    public final Parcelable[] bundle;
    public final Bitmap car;
    public final Context context;
    public final Paint paint;
    public final float progress;
    public final int progressColor;
    public final List segments;

    public OngoingSeekBarCreator(Context context, Parcelable[] parcelableArr, float f, Icon icon, int i) {
        Drawable loadDrawable;
        this.context = context;
        this.bundle = parcelableArr;
        this.progress = f;
        this.progressColor = i;
        Paint paint = new Paint();
        this.paint = paint;
        this.segments = new ArrayList();
        paint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_progress_stroke_width));
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_progress_thumb_size);
        if (icon != null) {
            try {
                loadDrawable = icon.loadDrawable(context);
            } catch (IllegalStateException unused) {
                this.car = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.re);
            }
        } else {
            loadDrawable = null;
        }
        Bitmap bitmap = Utils.toBitmap(loadDrawable);
        this.car = bitmap == null ? BitmapFactory.decodeResource(context.getResources(), R.drawable.re) : bitmap;
        Bitmap bitmap2 = this.car;
        Intrinsics.checkNotNull(bitmap2);
        this.car = Bitmap.createScaledBitmap(bitmap2, dimensionPixelSize, dimensionPixelSize, true);
        Parcelable[] parcelableArr2 = this.bundle;
        int length = parcelableArr2.length - 1;
        if (length < 0) {
            return;
        }
        int i2 = 0;
        while (true) {
            if (i2 < length) {
                List list = this.segments;
                if (list != null) {
                    ((ArrayList) list).add(new TintedProgressSegment(((Bundle) parcelableArr2[i2]).getInt("android.ongoingActivityNoti.progressSegments.segmentColor"), ((Bundle) parcelableArr2[i2 + 1]).getFloat("android.ongoingActivityNoti.progressSegments.segmentStart") - ((Bundle) parcelableArr2[i2]).getFloat("android.ongoingActivityNoti.progressSegments.segmentStart")));
                }
            } else {
                List list2 = this.segments;
                if (list2 != null) {
                    ((ArrayList) list2).add(new TintedProgressSegment(((Bundle) parcelableArr2[i2]).getInt("android.ongoingActivityNoti.progressSegments.segmentColor"), 1.0f - ((Bundle) parcelableArr2[i2]).getFloat("android.ongoingActivityNoti.progressSegments.segmentStart")));
                }
            }
            if (i2 == length) {
                return;
            } else {
                i2++;
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TintedProgressSegment {
        public final int color;
        public final float width;

        public TintedProgressSegment(int i, float f) {
            this.color = i;
            this.width = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TintedProgressSegment)) {
                return false;
            }
            TintedProgressSegment tintedProgressSegment = (TintedProgressSegment) obj;
            return this.color == tintedProgressSegment.color && Float.compare(this.width, tintedProgressSegment.width) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.width) + (Integer.hashCode(this.color) * 31);
        }

        public final String toString() {
            return "TintedProgressSegment(color=" + this.color + ", width=" + this.width + ")";
        }

        public TintedProgressSegment(Bundle bundle) {
            this(bundle.getInt("android.ongoingActivityNoti.progressSegments.segmentColor"), bundle.getFloat("android.ongoingActivityNoti.progressSegments.segmentStart"));
        }
    }
}
