package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class StrikethroughSpan extends CharacterStyle implements UpdateAppearance, ParcelableSpan {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 5;
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
    }

    public StrikethroughSpan() {
    }

    public StrikethroughSpan(Parcel parcel) {
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setStrikeThruText(true);
    }

    public String toString() {
        return "StrikethroughSpan{}";
    }
}
