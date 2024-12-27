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
    public static final Parcelable.Creator<GetCredentialRequest> CREATOR =
            new Parcelable.Creator<
                    GetCredentialRequest>() { // from class:
                                              // android.service.credentials.GetCredentialRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GetCredentialRequest createFromParcel(Parcel in) {
                    return new GetCredentialRequest(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GetCredentialRequest[] newArray(int size) {
                    return new GetCredentialRequest[size];
                }
            };
    private final CallingAppInfo mCallingAppInfo;
    private final List<CredentialOption> mCredentialOptions;

    public GetCredentialRequest(
            CallingAppInfo callingAppInfo, List<CredentialOption> credentialOptions) {
        this.mCallingAppInfo =
                (CallingAppInfo)
                        Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mCredentialOptions =
                (List)
                        Objects.requireNonNull(
                                credentialOptions, "credentialOptions must not be null");
    }

    private GetCredentialRequest(Parcel in) {
        this.mCallingAppInfo = (CallingAppInfo) in.readTypedObject(CallingAppInfo.CREATOR);
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCallingAppInfo);
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCredentialOptions);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mCallingAppInfo, flags);
        dest.writeTypedList(this.mCredentialOptions, flags);
    }

    public CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public List<CredentialOption> getCredentialOptions() {
        return this.mCredentialOptions;
    }
}
