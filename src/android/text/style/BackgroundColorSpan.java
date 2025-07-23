package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public class BackgroundColorSpan extends CharacterStyle implements UpdateAppearance, ParcelableSpan {
    private final int mColor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 12;
    }

    public BackgroundColorSpan(int i) {
        this.mColor = i;
    }

    public BackgroundColorSpan(Parcel parcel) {
        this.mColor = parcel.readInt();
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
    }

    public int getBackgroundColor() {
        return this.mColor;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.bgColor = this.mColor;
    }

    public String toString() {
        return "BackgroundColorSpan{color=#" + String.format("%08X", Integer.valueOf(getBackgroundColor())) + '}';
    }
}
