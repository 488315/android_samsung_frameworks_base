package android.service.assist.classification;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.assist.AssistStructure;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

@SystemApi
/* loaded from: classes3.dex */
public final class FieldClassificationRequest implements Parcelable {
    public static final Parcelable.Creator<FieldClassificationRequest> CREATOR = new Parcelable.Creator<FieldClassificationRequest>() { // from class: android.service.assist.classification.FieldClassificationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldClassificationRequest[] newArray(int i) {
            return new FieldClassificationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldClassificationRequest createFromParcel(Parcel parcel) {
            return new FieldClassificationRequest(parcel);
        }
    };
    private final AssistStructure mAssistStructure;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FieldClassificationRequest(AssistStructure assistStructure) {
        this.mAssistStructure = assistStructure;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) assistStructure);
    }

    public AssistStructure getAssistStructure() {
        return this.mAssistStructure;
    }

    public String toString() {
        return "FieldClassificationRequest { assistStructure = " + this.mAssistStructure + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAssistStructure, i);
    }

    FieldClassificationRequest(Parcel parcel) {
        AssistStructure assistStructure = (AssistStructure) parcel.readTypedObject(AssistStructure.CREATOR);
        this.mAssistStructure = assistStructure;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) assistStructure);
    }
}
