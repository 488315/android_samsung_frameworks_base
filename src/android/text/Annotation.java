package android.text;

import android.os.Parcel;

/* loaded from: classes4.dex */
public class Annotation implements ParcelableSpan {
    private final String mKey;
    private final String mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 18;
    }

    public Annotation(String str, String str2) {
        this.mKey = str;
        this.mValue = str2;
    }

    public Annotation(Parcel parcel) {
        this.mKey = parcel.readString();
        this.mValue = parcel.readString();
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
        parcel.writeString(this.mKey);
        parcel.writeString(this.mValue);
    }

    public String getKey() {
        return this.mKey;
    }

    public String getValue() {
        return this.mValue;
    }
}
