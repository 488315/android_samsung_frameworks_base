package android.text.style;

import android.graphics.LeakyTypefaceStorage;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class TypefaceSpan extends MetricAffectingSpan implements ParcelableSpan {
    private final String mFamily;
    private final Typeface mTypeface;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 13;
    }

    public TypefaceSpan(String str) {
        this(str, null);
    }

    public TypefaceSpan(Typeface typeface) {
        this(null, typeface);
    }

    public TypefaceSpan(Parcel parcel) {
        this.mFamily = parcel.readString();
        this.mTypeface = LeakyTypefaceStorage.readTypefaceFromParcel(parcel);
    }

    private TypefaceSpan(String str, Typeface typeface) {
        this.mFamily = str;
        this.mTypeface = typeface;
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
        parcel.writeString(this.mFamily);
        LeakyTypefaceStorage.writeTypefaceToParcel(this.mTypeface, parcel);
    }

    public String getFamily() {
        return this.mFamily;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        updateTypeface(textPaint);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        updateTypeface(textPaint);
    }

    private void updateTypeface(Paint paint) {
        Typeface typeface = this.mTypeface;
        if (typeface != null) {
            paint.setTypeface(typeface);
            return;
        }
        String str = this.mFamily;
        if (str != null) {
            applyFontFamily(paint, str);
        }
    }

    private void applyFontFamily(Paint paint, String str) {
        Typeface typeface = paint.getTypeface();
        int style = typeface == null ? 0 : typeface.getStyle();
        Typeface typefaceCreate = Typeface.create(str, style);
        int i = style & (~typefaceCreate.getStyle());
        if ((i & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((i & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(typefaceCreate);
    }

    public String toString() {
        return "TypefaceSpan{family='" + getFamily() + "', typeface=" + getTypeface() + '}';
    }
}
