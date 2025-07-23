package android.service.credentials;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BeginGetCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<BeginGetCredentialRequest> CREATOR = new Parcelable.Creator<BeginGetCredentialRequest>() { // from class: android.service.credentials.BeginGetCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialRequest createFromParcel(Parcel parcel) {
            return new BeginGetCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialRequest[] newArray(int i) {
            return new BeginGetCredentialRequest[i];
        }
    };
    private final List<BeginGetCredentialOption> mBeginGetCredentialOptions;
    private final CallingAppInfo mCallingAppInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BeginGetCredentialRequest(CallingAppInfo callingAppInfo, List<BeginGetCredentialOption> list) {
        this.mCallingAppInfo = callingAppInfo;
        this.mBeginGetCredentialOptions = list;
    }

    private BeginGetCredentialRequest(Parcel parcel) {
        this.mCallingAppInfo = (CallingAppInfo) parcel.readTypedObject(CallingAppInfo.CREATOR);
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, BeginGetCredentialOption.CREATOR);
        this.mBeginGetCredentialOptions = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeTypedList(this.mBeginGetCredentialOptions);
    }

    public CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public List<BeginGetCredentialOption> getBeginGetCredentialOptions() {
        return this.mBeginGetCredentialOptions;
    }

    public static final class Builder {
        private CallingAppInfo mCallingAppInfo = null;
        private List<BeginGetCredentialOption> mBeginGetCredentialOptions = new ArrayList();

        public Builder setCallingAppInfo(CallingAppInfo callingAppInfo) {
            this.mCallingAppInfo = callingAppInfo;
            return this;
        }

        public Builder setBeginGetCredentialOptions(List<BeginGetCredentialOption> list) {
            Preconditions.checkCollectionNotEmpty(list, "getBeginCredentialOptions");
            Preconditions.checkCollectionElementsNotNull(list, "getBeginCredentialOptions");
            this.mBeginGetCredentialOptions = list;
            return this;
        }

        public Builder addBeginGetCredentialOption(BeginGetCredentialOption beginGetCredentialOption) {
            Objects.requireNonNull(beginGetCredentialOption, "beginGetCredentialOption must not be null");
            this.mBeginGetCredentialOptions.add(beginGetCredentialOption);
            return this;
        }

        public BeginGetCredentialRequest build() {
            Preconditions.checkCollectionNotEmpty(this.mBeginGetCredentialOptions, "beginGetCredentialOptions");
            return new BeginGetCredentialRequest(this.mCallingAppInfo, this.mBeginGetCredentialOptions);
        }
    }
}
