package android.text.style;

import android.graphics.text.LineBreakConfig;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;

import java.util.Objects;

public final class LineBreakConfigSpan implements ParcelableSpan {
    private final LineBreakConfig mLineBreakConfig;
    private static final LineBreakConfig sNoHyphenationConfig =
            new LineBreakConfig.Builder().setHyphenation(0).build();
    private static final LineBreakConfig sNoBreakConfig =
            new LineBreakConfig.Builder().setLineBreakStyle(4).build();
    public static final Parcelable.Creator<LineBreakConfigSpan> CREATOR =
            new Parcelable.Creator<
                    LineBreakConfigSpan>() { // from class: android.text.style.LineBreakConfigSpan.1
                @Override // android.os.Parcelable.Creator
                public LineBreakConfigSpan createFromParcel(Parcel source) {
                    LineBreakConfig lbc =
                            (LineBreakConfig)
                                    source.readParcelable(
                                            LineBreakConfig.class.getClassLoader(),
                                            LineBreakConfig.class);
                    return new LineBreakConfigSpan(lbc);
                }

                @Override // android.os.Parcelable.Creator
                public LineBreakConfigSpan[] newArray(int size) {
                    return new LineBreakConfigSpan[size];
                }
            };

    public LineBreakConfigSpan(LineBreakConfig lineBreakConfig) {
        this.mLineBreakConfig = lineBreakConfig;
    }

    public LineBreakConfig getLineBreakConfig() {
        return this.mLineBreakConfig;
    }

    public static LineBreakConfigSpan createNoBreakSpan() {
        return new LineBreakConfigSpan(sNoBreakConfig);
    }

    public static LineBreakConfigSpan createNoHyphenationSpan() {
        return new LineBreakConfigSpan(sNoHyphenationConfig);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineBreakConfigSpan)) {
            return false;
        }
        LineBreakConfigSpan that = (LineBreakConfigSpan) o;
        return Objects.equals(this.mLineBreakConfig, that.mLineBreakConfig);
    }

    public int hashCode() {
        return Objects.hash(this.mLineBreakConfig);
    }

    public String toString() {
        return "LineBreakConfigSpan{mLineBreakConfig=" + this.mLineBreakConfig + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 30;
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeParcelable(this.mLineBreakConfig, flags);
    }
}
