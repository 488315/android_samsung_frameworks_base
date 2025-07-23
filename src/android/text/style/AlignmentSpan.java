package android.text.style;

import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public interface AlignmentSpan extends ParagraphStyle {
    Layout.Alignment getAlignment();

    public static class Standard implements AlignmentSpan, ParcelableSpan {
        private final Layout.Alignment mAlignment;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 1;
        }

        public Standard(Layout.Alignment alignment) {
            this.mAlignment = alignment;
        }

        public Standard(Parcel parcel) {
            this.mAlignment = Layout.Alignment.valueOf(parcel.readString());
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
            parcel.writeString(this.mAlignment.name());
        }

        @Override // android.text.style.AlignmentSpan
        public Layout.Alignment getAlignment() {
            return this.mAlignment;
        }

        public String toString() {
            return "AlignmentSpan.Standard{alignment=" + getAlignment() + '}';
        }
    }
}
