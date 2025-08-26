package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Spanned;

/* loaded from: classes4.dex */
public class BulletSpan implements LeadingMarginSpan, ParcelableSpan {
    private static final int STANDARD_BULLET_RADIUS = 4;
    private static final int STANDARD_COLOR = 0;
    public static final int STANDARD_GAP_WIDTH = 2;
    private final int mBulletRadius;
    private final int mColor;
    private final int mGapWidth;
    private final boolean mWantColor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 8;
    }

    public BulletSpan() {
        this(2, 0, false, 4);
    }

    public BulletSpan(int i) {
        this(i, 0, false, 4);
    }

    public BulletSpan(int i, int i2) {
        this(i, i2, true, 4);
    }

    public BulletSpan(int i, int i2, int i3) {
        this(i, i2, true, i3);
    }

    public BulletSpan(int i, int i2, boolean z, int i3) {
        this.mGapWidth = i;
        this.mBulletRadius = i3;
        this.mColor = i2;
        this.mWantColor = z;
    }

    public BulletSpan(Parcel parcel) {
        this.mGapWidth = parcel.readInt();
        this.mWantColor = parcel.readInt() != 0;
        this.mColor = parcel.readInt();
        this.mBulletRadius = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeInt(this.mGapWidth);
        parcel.writeInt(this.mWantColor ? 1 : 0);
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mBulletRadius);
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return (this.mBulletRadius * 2) + this.mGapWidth;
    }

    public int getGapWidth() {
        return this.mGapWidth;
    }

    public int getBulletRadius() {
        return this.mBulletRadius;
    }

    public int getColor() {
        return this.mColor;
    }

    public boolean getWantColor() {
        return this.mWantColor;
    }

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        int color;
        if (((Spanned) charSequence).getSpanStart(this) == i6) {
            Paint.Style style = paint.getStyle();
            if (this.mWantColor) {
                color = paint.getColor();
                paint.setColor(this.mColor);
            } else {
                color = 0;
            }
            paint.setStyle(Paint.Style.FILL);
            if (layout != null) {
                i5 -= layout.getLineExtra(layout.getLineForOffset(i6));
            }
            canvas.drawCircle(i + (i2 * r7), (i3 + i5) / 2.0f, this.mBulletRadius, paint);
            if (this.mWantColor) {
                paint.setColor(color);
            }
            paint.setStyle(style);
        }
    }

    public String toString() {
        return "BulletSpan{gapWidth=" + getGapWidth() + ", bulletRadius=" + getBulletRadius() + ", color=" + String.format("%08X", Integer.valueOf(getColor())) + '}';
    }
}
