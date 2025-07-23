package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public class AccessibilityReplacementSpan extends ReplacementSpan implements ParcelableSpan {
    public static final Parcelable.Creator<AccessibilityReplacementSpan> CREATOR = new Parcelable.Creator<AccessibilityReplacementSpan>() { // from class: android.text.style.AccessibilityReplacementSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityReplacementSpan createFromParcel(Parcel parcel) {
            return new AccessibilityReplacementSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityReplacementSpan[] newArray(int i) {
            return new AccessibilityReplacementSpan[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 29;
    }

    public AccessibilityReplacementSpan(CharSequence charSequence) {
        setContentDescription(charSequence);
    }

    public AccessibilityReplacementSpan(Parcel parcel) {
        setContentDescription(parcel.readCharSequence());
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
        parcel.writeCharSequence(getContentDescription());
    }
}
