package android.text.style;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class StyleSpan extends MetricAffectingSpan implements ParcelableSpan {
    private final int mFontWeightAdjustment;
    private final int mStyle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 7;
    }

    public StyleSpan(int i) {
        this(i, Integer.MAX_VALUE);
    }

    public StyleSpan(int i, int i2) {
        this.mStyle = i;
        this.mFontWeightAdjustment = i2;
    }

    public StyleSpan(Parcel parcel) {
        this.mStyle = parcel.readInt();
        this.mFontWeightAdjustment = parcel.readInt();
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
        parcel.writeInt(this.mStyle);
        parcel.writeInt(this.mFontWeightAdjustment);
    }

    public int getStyle() {
        return this.mStyle;
    }

    public int getFontWeightAdjustment() {
        return this.mFontWeightAdjustment;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        apply(textPaint, this.mStyle, this.mFontWeightAdjustment);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        apply(textPaint, this.mStyle, this.mFontWeightAdjustment);
    }

    private static void apply(Paint paint, int i, int i2) {
        Typeface create;
        Typeface typeface = paint.getTypeface();
        int style = (typeface == null ? 0 : typeface.getStyle()) | i;
        if (paint.getTextSkewX() == -0.25f && i == 1 && typeface == Typeface.defaultFromStyle(2)) {
            typeface = Typeface.defaultFromStyle(1);
        }
        if (typeface == null) {
            create = Typeface.defaultFromStyle(style);
        } else {
            create = Typeface.create(typeface, style);
        }
        if ((i & 1) != 0 && i2 != 0 && i2 != Integer.MAX_VALUE) {
            create = Typeface.create(create, Math.min(Math.max(create.getWeight() + i2, 1), 1000), (style & 2) != 0);
        }
        int i3 = (~create.getStyle()) & style;
        if ((i3 & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((i3 & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(create);
    }

    public String toString() {
        return "StyleSpan{style=" + getStyle() + ", fontWeightAdjustment=" + getFontWeightAdjustment() + '}';
    }
}
