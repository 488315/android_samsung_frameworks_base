package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public class QuoteSpan implements LeadingMarginSpan, ParcelableSpan {
    public static final int STANDARD_COLOR = -16776961;
    public static final int STANDARD_GAP_WIDTH_PX = 2;
    public static final int STANDARD_STRIPE_WIDTH_PX = 2;
    private final int mColor;
    private final int mGapWidth;
    private final int mStripeWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 9;
    }

    public QuoteSpan() {
        this(-16776961, 2, 2);
    }

    public QuoteSpan(int i) {
        this(i, 2, 2);
    }

    public QuoteSpan(int i, int i2, int i3) {
        this.mColor = i;
        this.mStripeWidth = i2;
        this.mGapWidth = i3;
    }

    public QuoteSpan(Parcel parcel) {
        this.mColor = parcel.readInt();
        this.mStripeWidth = parcel.readInt();
        this.mGapWidth = parcel.readInt();
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
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mStripeWidth);
        parcel.writeInt(this.mGapWidth);
    }

    public int getColor() {
        return this.mColor;
    }

    public int getStripeWidth() {
        return this.mStripeWidth;
    }

    public int getGapWidth() {
        return this.mGapWidth;
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return this.mStripeWidth + this.mGapWidth;
    }

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        Paint.Style style = paint.getStyle();
        int color = paint.getColor();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.mColor);
        canvas.drawRect(i, i3, i + (i2 * this.mStripeWidth), i5, paint);
        paint.setStyle(style);
        paint.setColor(color);
    }

    public String toString() {
        return "QuoteSpan{color=" + String.format("#%08X", Integer.valueOf(getColor())) + ", stripeWidth=" + getStripeWidth() + ", gapWidth=" + getGapWidth() + '}';
    }
}
