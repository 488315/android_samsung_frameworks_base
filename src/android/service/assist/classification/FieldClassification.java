package android.service.assist.classification;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.view.autofill.AutofillId;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes3.dex */
public final class FieldClassification implements Parcelable {
    public static final Parcelable.Creator<FieldClassification> CREATOR = new Parcelable.Creator<FieldClassification>() { // from class: android.service.assist.classification.FieldClassification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldClassification[] newArray(int i) {
            return new FieldClassification[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldClassification createFromParcel(Parcel parcel) {
            return new FieldClassification(parcel);
        }
    };
    private final AutofillId mAutofillId;
    private final Set<String> mGroupHints;
    private final Set<String> mHints;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    public Set<String> getHints() {
        return this.mHints;
    }

    @SystemApi
    public Set<String> getGroupHints() {
        return this.mGroupHints;
    }

    static Set<String> unparcelHints(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        return new ArraySet(arrayList);
    }

    void parcelHints(Parcel parcel, int i) {
        parcel.writeStringList(new ArrayList(this.mHints));
    }

    static Set<String> unparcelGroupHints(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        return new ArraySet(arrayList);
    }

    void parcelGroupHints(Parcel parcel, int i) {
        parcel.writeStringList(new ArrayList(this.mGroupHints));
    }

    public FieldClassification(AutofillId autofillId, Set<String> set) {
        this(autofillId, set, new ArraySet());
    }

    @SystemApi
    public FieldClassification(AutofillId autofillId, Set<String> set, Set<String> set2) {
        this.mAutofillId = autofillId;
        this.mHints = set;
        this.mGroupHints = set2;
    }

    public String toString() {
        return "FieldClassification { autofillId = " + this.mAutofillId + ", hints = " + this.mHints + ", groupHints = " + this.mGroupHints + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAutofillId, i);
        parcelHints(parcel, i);
        parcelGroupHints(parcel, i);
    }

    FieldClassification(Parcel parcel) {
        AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
        Set<String> setUnparcelHints = unparcelHints(parcel);
        Set<String> setUnparcelGroupHints = unparcelGroupHints(parcel);
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mHints = setUnparcelHints;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) setUnparcelHints);
        this.mGroupHints = setUnparcelGroupHints;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) setUnparcelGroupHints);
    }
}
