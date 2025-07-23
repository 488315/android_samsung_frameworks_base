package android.text.style;

import android.graphics.Paint;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import com.android.internal.util.Preconditions;

/* loaded from: classes4.dex */
public interface LineHeightSpan extends ParagraphStyle, WrapTogetherSpan {

    public interface WithDensity extends LineHeightSpan {
        void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt, TextPaint textPaint);
    }

    void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt);

    public static class Standard implements LineHeightSpan, ParcelableSpan {
        private final int mHeight;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 28;
        }

        public Standard(int i) {
            Preconditions.checkArgument(i > 0, "Height: %d must be positive", Integer.valueOf(i));
            this.mHeight = i;
        }

        public Standard(Parcel parcel) {
            this.mHeight = parcel.readInt();
        }

        public int getHeight() {
            return this.mHeight;
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
            parcel.writeInt(this.mHeight);
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
            int i5 = fontMetricsInt.descent - fontMetricsInt.ascent;
            if (i5 <= 0) {
                return;
            }
            fontMetricsInt.descent = Math.round(fontMetricsInt.descent * ((this.mHeight * 1.0f) / i5));
            fontMetricsInt.ascent = fontMetricsInt.descent - this.mHeight;
        }
    }
}
