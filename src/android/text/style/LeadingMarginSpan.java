package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public interface LeadingMarginSpan extends ParagraphStyle {

    public interface LeadingMarginSpan2 extends LeadingMarginSpan, WrapTogetherSpan {
        int getLeadingMarginLineCount();
    }

    void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout);

    int getLeadingMargin(boolean z);

    public static class Standard implements LeadingMarginSpan, ParcelableSpan {
        private final int mFirst;
        private final int mRest;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 10;
        }

        public Standard(int i, int i2) {
            this.mFirst = i;
            this.mRest = i2;
        }

        public Standard(int i) {
            this(i, i);
        }

        public Standard(Parcel parcel) {
            this.mFirst = parcel.readInt();
            this.mRest = parcel.readInt();
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
            parcel.writeInt(this.mFirst);
            parcel.writeInt(this.mRest);
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z) {
            return z ? this.mFirst : this.mRest;
        }
    }
}
