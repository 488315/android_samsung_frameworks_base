package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class ScaleXSpan extends MetricAffectingSpan implements ParcelableSpan {
    private final float mProportion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 4;
    }

    public ScaleXSpan(float f) {
        this.mProportion = f;
    }

    public ScaleXSpan(Parcel parcel) {
        this.mProportion = parcel.readFloat();
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
        parcel.writeFloat(this.mProportion);
    }

    public float getScaleX() {
        return this.mProportion;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setTextScaleX(textPaint.getTextScaleX() * this.mProportion);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setTextScaleX(textPaint.getTextScaleX() * this.mProportion);
    }

    public String toString() {
        return "ScaleXSpan{scaleX=" + getScaleX() + '}';
    }
}
