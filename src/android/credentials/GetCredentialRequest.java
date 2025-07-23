package android.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class GetCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<GetCredentialRequest> CREATOR = new Parcelable.Creator<GetCredentialRequest>() { // from class: android.credentials.GetCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialRequest[] newArray(int i) {
            return new GetCredentialRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialRequest createFromParcel(Parcel parcel) {
            return new GetCredentialRequest(parcel);
        }
    };
    private final boolean mAlwaysSendAppInfoToProvider;
    private final List<CredentialOption> mCredentialOptions;
    private final Bundle mData;
    private String mOrigin;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<CredentialOption> getCredentialOptions() {
        return this.mCredentialOptions;
    }

    public Bundle getData() {
        return this.mData;
    }

    public String getOrigin() {
        return this.mOrigin;
    }

    public boolean alwaysSendAppInfoToProvider() {
        return this.mAlwaysSendAppInfoToProvider;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mCredentialOptions, i);
        parcel.writeBundle(this.mData);
        parcel.writeBoolean(this.mAlwaysSendAppInfoToProvider);
        parcel.writeString8(this.mOrigin);
    }

    public String toString() {
        return "GetCredentialRequest {credentialOption=" + this.mCredentialOptions + ", data=" + this.mData + ", alwaysSendAppInfoToProvider=" + this.mAlwaysSendAppInfoToProvider + ", origin=" + this.mOrigin + "}";
    }

    private GetCredentialRequest(List<CredentialOption> list, Bundle bundle, boolean z, String str) {
        Preconditions.checkCollectionNotEmpty(list, "credentialOptions");
        Preconditions.checkCollectionElementsNotNull(list, "credentialOptions");
        this.mCredentialOptions = list;
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
        this.mAlwaysSendAppInfoToProvider = z;
        this.mOrigin = str;
    }

    private GetCredentialRequest(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        Bundle readBundle = parcel.readBundle();
        this.mData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
        this.mAlwaysSendAppInfoToProvider = parcel.readBoolean();
        this.mOrigin = parcel.readString8();
    }

    public static final class Builder {
        private final Bundle mData;
        private String mOrigin;
        private List<CredentialOption> mCredentialOptions = new ArrayList();
        private boolean mAlwaysSendAppInfoToProvider = true;

        public Builder(Bundle bundle) {
            this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
        }

        public Builder addCredentialOption(CredentialOption credentialOption) {
            this.mCredentialOptions.add((CredentialOption) Objects.requireNonNull(credentialOption, "credentialOption must not be null"));
            return this;
        }

        public Builder setAlwaysSendAppInfoToProvider(boolean z) {
            this.mAlwaysSendAppInfoToProvider = z;
            return this;
        }

        public Builder setCredentialOptions(List<CredentialOption> list) {
            Preconditions.checkCollectionElementsNotNull(list, "credentialOptions");
            this.mCredentialOptions = new ArrayList(list);
            return this;
        }

        public Builder setOrigin(String str) {
            this.mOrigin = str;
            return this;
        }

        public GetCredentialRequest build() {
            Preconditions.checkCollectionNotEmpty(this.mCredentialOptions, "credentialOptions");
            Preconditions.checkCollectionElementsNotNull(this.mCredentialOptions, "credentialOptions");
            return new GetCredentialRequest(this.mCredentialOptions, this.mData, this.mAlwaysSendAppInfoToProvider, this.mOrigin);
        }
    }
}
