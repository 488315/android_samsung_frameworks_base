package android.speech;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class AlternativeSpans implements Parcelable {
    public static final Parcelable.Creator<AlternativeSpans> CREATOR = new Parcelable.Creator<AlternativeSpans>() { // from class: android.speech.AlternativeSpans.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AlternativeSpans[] newArray(int i) {
            return new AlternativeSpans[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AlternativeSpans createFromParcel(Parcel parcel) {
            return new AlternativeSpans(parcel);
        }
    };
    private final List<AlternativeSpan> mSpans;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AlternativeSpans(List<AlternativeSpan> list) {
        this.mSpans = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
    }

    public List<AlternativeSpan> getSpans() {
        return this.mSpans;
    }

    public String toString() {
        return "AlternativeSpans { spans = " + this.mSpans + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mSpans, ((AlternativeSpans) obj).mSpans);
    }

    public int hashCode() {
        return 31 + Objects.hashCode(this.mSpans);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableList(this.mSpans, i);
    }

    AlternativeSpans(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, AlternativeSpan.class.getClassLoader(), AlternativeSpan.class);
        this.mSpans = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }
}
