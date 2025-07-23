package android.service.credentials;

import android.annotation.NonNull;
import android.credentials.CredentialOption;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class GetCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<GetCredentialRequest> CREATOR = new Parcelable.Creator<GetCredentialRequest>() { // from class: android.service.credentials.GetCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialRequest createFromParcel(Parcel parcel) {
            return new GetCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialRequest[] newArray(int i) {
            return new GetCredentialRequest[i];
        }
    };
    private final CallingAppInfo mCallingAppInfo;
    private final List<CredentialOption> mCredentialOptions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GetCredentialRequest(CallingAppInfo callingAppInfo, List<CredentialOption> list) {
        this.mCallingAppInfo = (CallingAppInfo) Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mCredentialOptions = (List) Objects.requireNonNull(list, "credentialOptions must not be null");
    }

    private GetCredentialRequest(Parcel parcel) {
        CallingAppInfo callingAppInfo = (CallingAppInfo) parcel.readTypedObject(CallingAppInfo.CREATOR);
        this.mCallingAppInfo = callingAppInfo;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) callingAppInfo);
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeTypedList(this.mCredentialOptions, i);
    }

    public CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public List<CredentialOption> getCredentialOptions() {
        return this.mCredentialOptions;
    }
}
