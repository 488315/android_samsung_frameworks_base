package android.text.style;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public final class NoWritingToolsSpan implements ParcelableSpan {
    public static final Parcelable.Creator<NoWritingToolsSpan> CREATOR = new Parcelable.Creator<NoWritingToolsSpan>() { // from class: android.text.style.NoWritingToolsSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoWritingToolsSpan createFromParcel(Parcel parcel) {
            return new NoWritingToolsSpan();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoWritingToolsSpan[] newArray(int i) {
            return new NoWritingToolsSpan[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 31;
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    public String toString() {
        return "NoWritingToolsSpan{}";
    }
}
