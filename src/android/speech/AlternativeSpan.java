package android.speech;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class AlternativeSpan implements Parcelable {
    public static final Parcelable.Creator<AlternativeSpan> CREATOR = new Parcelable.Creator<AlternativeSpan>() { // from class: android.speech.AlternativeSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AlternativeSpan[] newArray(int i) {
            return new AlternativeSpan[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AlternativeSpan createFromParcel(Parcel parcel) {
            return new AlternativeSpan(parcel);
        }
    };
    private final List<String> mAlternatives;
    private final int mEndPosition;
    private final int mStartPosition;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void onConstructed() {
        Preconditions.checkArgumentNonnegative(this.mStartPosition, "The range start must be non-negative.");
        int i = this.mStartPosition;
        Preconditions.checkArgument(i < this.mEndPosition, "Illegal range [%d, %d), must be start < end.", Integer.valueOf(i), Integer.valueOf(this.mEndPosition));
        Preconditions.checkCollectionNotEmpty(this.mAlternatives, "List of alternative strings must not be empty.");
    }

    public AlternativeSpan(int i, int i2, List<String> list) {
        this.mStartPosition = i;
        this.mEndPosition = i2;
        this.mAlternatives = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        onConstructed();
    }

    public int getStartPosition() {
        return this.mStartPosition;
    }

    public int getEndPosition() {
        return this.mEndPosition;
    }

    public List<String> getAlternatives() {
        return this.mAlternatives;
    }

    public String toString() {
        return "AlternativeSpan { startPosition = " + this.mStartPosition + ", endPosition = " + this.mEndPosition + ", alternatives = " + this.mAlternatives + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AlternativeSpan alternativeSpan = (AlternativeSpan) obj;
            if (this.mStartPosition == alternativeSpan.mStartPosition && this.mEndPosition == alternativeSpan.mEndPosition && Objects.equals(this.mAlternatives, alternativeSpan.mAlternatives)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mStartPosition + 31) * 31) + this.mEndPosition) * 31) + Objects.hashCode(this.mAlternatives);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStartPosition);
        parcel.writeInt(this.mEndPosition);
        parcel.writeStringList(this.mAlternatives);
    }

    AlternativeSpan(Parcel parcel) {
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.mStartPosition = i;
        this.mEndPosition = i2;
        this.mAlternatives = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        onConstructed();
    }
}
