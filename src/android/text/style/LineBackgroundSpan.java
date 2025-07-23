package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public interface LineBackgroundSpan extends ParagraphStyle {
    void drawBackground(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, int i8);

    public static class Standard implements LineBackgroundSpan, ParcelableSpan {
        private final int mColor;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 27;
        }

        public Standard(int i) {
            this.mColor = i;
        }

        public Standard(Parcel parcel) {
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

        public final int getColor() {
            return this.mColor;
        }

        @Override // android.text.style.LineBackgroundSpan
        public void drawBackground(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, int i8) {
            int color = paint.getColor();
            paint.setColor(this.mColor);
            canvas.drawRect(i, i3, i2, i5, paint);
            paint.setColor(color);
        }
    }
}
