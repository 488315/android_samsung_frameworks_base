package android.text.style;

import android.graphics.text.LineBreakConfig;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class LineBreakConfigSpan implements ParcelableSpan {
    private final LineBreakConfig mLineBreakConfig;
    private static final LineBreakConfig sNoHyphenationConfig = new LineBreakConfig.Builder().setHyphenation(0).build();
    private static final LineBreakConfig sNoBreakConfig = new LineBreakConfig.Builder().setLineBreakStyle(4).build();
    public static final Parcelable.Creator<LineBreakConfigSpan> CREATOR = new Parcelable.Creator<LineBreakConfigSpan>() { // from class: android.text.style.LineBreakConfigSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LineBreakConfigSpan createFromParcel(Parcel parcel) {
            return new LineBreakConfigSpan((LineBreakConfig) parcel.readParcelable(LineBreakConfig.class.getClassLoader(), LineBreakConfig.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LineBreakConfigSpan[] newArray(int i) {
            return new LineBreakConfigSpan[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 30;
    }

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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LineBreakConfigSpan) {
            return Objects.equals(this.mLineBreakConfig, ((LineBreakConfigSpan) obj).mLineBreakConfig);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mLineBreakConfig);
    }

    public String toString() {
        return "LineBreakConfigSpan{mLineBreakConfig=" + this.mLineBreakConfig + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeParcelable(this.mLineBreakConfig, i);
    }
}
