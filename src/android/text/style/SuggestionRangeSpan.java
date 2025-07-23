package android.text.style;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import android.text.TextPaint;

/* loaded from: classes4.dex */
public final class SuggestionRangeSpan extends CharacterStyle implements ParcelableSpan {
    public static final Parcelable.Creator<SuggestionRangeSpan> CREATOR = new Parcelable.Creator<SuggestionRangeSpan>() { // from class: android.text.style.SuggestionRangeSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestionRangeSpan createFromParcel(Parcel parcel) {
            return new SuggestionRangeSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestionRangeSpan[] newArray(int i) {
            return new SuggestionRangeSpan[i];
        }
    };
    private int mBackgroundColor;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 21;
    }

    public SuggestionRangeSpan() {
        this.mBackgroundColor = 0;
    }

    public SuggestionRangeSpan(Parcel parcel) {
        this.mBackgroundColor = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeInt(this.mBackgroundColor);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.bgColor = this.mBackgroundColor;
    }
}
