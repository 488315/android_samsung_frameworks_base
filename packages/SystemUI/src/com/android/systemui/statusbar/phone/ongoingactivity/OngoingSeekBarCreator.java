package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class OngoingSeekBarCreator {
    public final Parcelable[] bundle;
    public final Bitmap car;
    public final Context context;
    public final Paint paint;
    public final float progress;
    public final int progressColor;
    public final List segments;

    public OngoingSeekBarCreator(Context context, Parcelable[] parcelableArr, float f, Icon icon, int i) throws Resources.NotFoundException {
        Drawable drawableLoadDrawable;
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
                drawableLoadDrawable = icon.loadDrawable(context);
            } catch (IllegalStateException unused) {
                this.car = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.re);
            }
        } else {
            drawableLoadDrawable = null;
        }
        Bitmap bitmap = Utils.toBitmap(drawableLoadDrawable);
        this.car = bitmap == null ? BitmapFactory.decodeResource(context.getResources(), R.drawable.re) : bitmap;
        Bitmap bitmap2 = this.car;
        bitmap2.getClass();
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

    public final Bitmap makeImage(OngoingType ongoingType) throws Resources.NotFoundException {
        int ongoingCardWidth;
        TintedProgressSegment tintedProgressSegment;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_progress_thumb_size);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && ongoingType == OngoingType.SUB) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = this.context;
            ongoingActivityLayoutUtil.getClass();
            ongoingCardWidth = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).getSubScreenCardWidth(context);
        } else if (ongoingType == OngoingType.ENR) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil2 = OngoingActivityLayoutUtil.INSTANCE;
            Context context2 = this.context;
            ongoingActivityLayoutUtil2.getClass();
            ongoingCardWidth = OngoingActivityLayoutUtil.getENRCardWidth(context2);
        } else {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil3 = OngoingActivityLayoutUtil.INSTANCE;
            Context context3 = this.context;
            ongoingActivityLayoutUtil3.getClass();
            ongoingCardWidth = OngoingActivityLayoutUtil.getOngoingCardWidth(context3);
        }
        int dimensionPixelSize2 = (ongoingCardWidth - this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_progress_start_margin)) - this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_content_end_margin);
        int dimensionPixelSize3 = this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_expanded_view_content_upper_progress_height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize2, dimensionPixelSize3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, (Paint) null);
        float f = dimensionPixelSize3 / 2;
        int size = ((ArrayList) this.segments).size() - 1;
        List list = this.segments;
        if (list != null && (tintedProgressSegment = (TintedProgressSegment) ((ArrayList) list).get(size)) != null) {
            float f2 = dimensionPixelSize2;
            float f3 = tintedProgressSegment.width;
            float strokeWidth = f2 - (f2 * f3);
            if (f3 == 1.0f) {
                strokeWidth = this.paint.getStrokeWidth() / 2;
            }
            this.paint.setStrokeCap(Paint.Cap.ROUND);
            this.paint.setColor(tintedProgressSegment.color);
            canvas.drawLine(strokeWidth, f, f2 - (this.paint.getStrokeWidth() / 2), f, this.paint);
        }
        List list2 = this.segments;
        if (list2 != null) {
            ArrayList arrayList = (ArrayList) list2;
            int size2 = arrayList.size();
            int i = 0;
            float f4 = 0.0f;
            int i2 = 0;
            while (i < size2) {
                int i3 = i + 1;
                TintedProgressSegment tintedProgressSegment2 = (TintedProgressSegment) arrayList.get(i);
                if (i2 != size) {
                    float f5 = dimensionPixelSize2 * tintedProgressSegment2.width;
                    if (i2 == 0) {
                        this.paint.setStrokeCap(Paint.Cap.ROUND);
                    } else {
                        this.paint.setStrokeCap(Paint.Cap.BUTT);
                    }
                    this.paint.setColor(tintedProgressSegment2.color);
                    float strokeWidth2 = i2 == 0 ? this.paint.getStrokeWidth() / 2 : f4;
                    f4 += f5;
                    canvas.drawLine(strokeWidth2, f, f4, f, this.paint);
                    i2++;
                }
                i = i3;
            }
        }
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint = this.paint;
        int color = this.progressColor;
        if (color == 0) {
            color = this.context.getColor(R.color.ongoing_activity_expand_progress_gray_bg_color);
        }
        paint.setColor(color);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        float f6 = dimensionPixelSize2;
        float f7 = (this.progress / 100.0f) * f6;
        float f8 = 2;
        canvas.drawLine(this.paint.getStrokeWidth() / f8, f, f7, f, this.paint);
        this.car.getClass();
        float height = f - (r6.getHeight() / 2);
        float f9 = dimensionPixelSize / 2;
        float strokeWidth3 = f7 < f9 ? this.paint.getStrokeWidth() / f8 : f7 + f9 >= f6 ? dimensionPixelSize2 - dimensionPixelSize : f7 - f9;
        Bitmap bitmap = this.car;
        bitmap.getClass();
        canvas.drawBitmap(bitmap, strokeWidth3, height, (Paint) null);
        return bitmapCreateBitmap;
    }

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
