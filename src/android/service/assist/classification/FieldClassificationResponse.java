package android.service.assist.classification;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class FieldClassificationResponse implements Parcelable {
    public static final Parcelable.Creator<FieldClassificationResponse> CREATOR = new Parcelable.Creator<FieldClassificationResponse>() { // from class: android.service.assist.classification.FieldClassificationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldClassificationResponse[] newArray(int i) {
            return new FieldClassificationResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldClassificationResponse createFromParcel(Parcel parcel) {
            return new FieldClassificationResponse(parcel);
        }
    };
    private final Set<FieldClassification> mClassifications;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static Set<FieldClassification> unparcelClassifications(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, FieldClassification.class.getClassLoader(), FieldClassification.class);
        return new ArraySet(arrayList);
    }

    void parcelClassifications(Parcel parcel, int i) {
        parcel.writeParcelableList(new ArrayList(this.mClassifications), i);
    }

    public FieldClassificationResponse(Set<FieldClassification> set) {
        this.mClassifications = set;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) set);
    }

    public Set<FieldClassification> getClassifications() {
        return this.mClassifications;
    }

    public String toString() {
        return "FieldClassificationResponse { classifications = " + this.mClassifications + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcelClassifications(parcel, i);
    }

    FieldClassificationResponse(Parcel parcel) {
        Set<FieldClassification> setUnparcelClassifications = unparcelClassifications(parcel);
        this.mClassifications = setUnparcelClassifications;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) setUnparcelClassifications);
    }
}
