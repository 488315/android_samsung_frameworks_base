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
    public static final Parcelable.Creator<FieldClassificationRequest> CREATOR =
            new Parcelable.Creator<
                    FieldClassificationRequest>() { // from class:
                                                    // android.service.assist.classification.FieldClassificationRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FieldClassificationRequest[] newArray(int size) {
                    return new FieldClassificationRequest[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FieldClassificationRequest createFromParcel(Parcel in) {
                    return new FieldClassificationRequest(in);
                }
            };
    private final AssistStructure mAssistStructure;

    public FieldClassificationRequest(AssistStructure assistStructure) {
        this.mAssistStructure = assistStructure;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mAssistStructure);
    }

    public AssistStructure getAssistStructure() {
        return this.mAssistStructure;
    }

    public String toString() {
        return "FieldClassificationRequest { assistStructure = " + this.mAssistStructure + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mAssistStructure, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FieldClassificationRequest(Parcel in) {
        AssistStructure assistStructure =
                (AssistStructure) in.readTypedObject(AssistStructure.CREATOR);
        this.mAssistStructure = assistStructure;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mAssistStructure);
    }

    @Deprecated
    private void __metadata() {}
}
