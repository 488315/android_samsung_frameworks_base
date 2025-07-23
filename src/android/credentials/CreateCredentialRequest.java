package android.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes.dex */
public final class CreateCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<CreateCredentialRequest> CREATOR = new Parcelable.Creator<CreateCredentialRequest>() { // from class: android.credentials.CreateCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialRequest[] newArray(int i) {
            return new CreateCredentialRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialRequest createFromParcel(Parcel parcel) {
            return new CreateCredentialRequest(parcel);
        }
    };
    private final boolean mAlwaysSendAppInfoToProvider;
    private final Bundle mCandidateQueryData;
    private final Bundle mCredentialData;
    private final boolean mIsSystemProviderRequired;
    private final String mOrigin;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getType() {
        return this.mType;
    }

    public Bundle getCredentialData() {
        return this.mCredentialData;
    }

    public Bundle getCandidateQueryData() {
        return this.mCandidateQueryData;
    }

    public boolean isSystemProviderRequired() {
        return this.mIsSystemProviderRequired;
    }

    public boolean alwaysSendAppInfoToProvider() {
        return this.mAlwaysSendAppInfoToProvider;
    }

    public String getOrigin() {
        return this.mOrigin;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mCredentialData);
        parcel.writeBundle(this.mCandidateQueryData);
        parcel.writeBoolean(this.mIsSystemProviderRequired);
        parcel.writeBoolean(this.mAlwaysSendAppInfoToProvider);
        parcel.writeString8(this.mOrigin);
    }

    public String toString() {
        return "CreateCredentialRequest {type=" + this.mType + ", credentialData=" + this.mCredentialData + ", candidateQueryData=" + this.mCandidateQueryData + ", isSystemProviderRequired=" + this.mIsSystemProviderRequired + ", alwaysSendAppInfoToProvider=" + this.mAlwaysSendAppInfoToProvider + ", origin=" + this.mOrigin + "}";
    }

    private CreateCredentialRequest(String str, Bundle bundle, Bundle bundle2, boolean z, boolean z2, String str2) {
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mCredentialData = (Bundle) Objects.requireNonNull(bundle, "credentialData must not be null");
        this.mCandidateQueryData = (Bundle) Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        this.mIsSystemProviderRequired = z;
        this.mAlwaysSendAppInfoToProvider = z2;
        this.mOrigin = str2;
    }

    private CreateCredentialRequest(Parcel parcel) {
        String readString8 = parcel.readString8();
        Bundle readBundle = parcel.readBundle();
        Bundle readBundle2 = parcel.readBundle();
        boolean readBoolean = parcel.readBoolean();
        boolean readBoolean2 = parcel.readBoolean();
        this.mOrigin = parcel.readString8();
        this.mType = readString8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
        this.mCredentialData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
        this.mCandidateQueryData = readBundle2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle2);
        this.mIsSystemProviderRequired = readBoolean;
        this.mAlwaysSendAppInfoToProvider = readBoolean2;
    }

    public static final class Builder {
        private boolean mAlwaysSendAppInfoToProvider = true;
        private final Bundle mCandidateQueryData;
        private final Bundle mCredentialData;
        private boolean mIsSystemProviderRequired;
        private String mOrigin;
        private String mType;

        public Builder(String str, Bundle bundle, Bundle bundle2) {
            this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be null or empty");
            this.mCredentialData = (Bundle) Objects.requireNonNull(bundle, "credentialData must not be null");
            this.mCandidateQueryData = (Bundle) Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        }

        public Builder setAlwaysSendAppInfoToProvider(boolean z) {
            this.mAlwaysSendAppInfoToProvider = z;
            return this;
        }

        public Builder setIsSystemProviderRequired(boolean z) {
            this.mIsSystemProviderRequired = z;
            return this;
        }

        public Builder setOrigin(String str) {
            this.mOrigin = str;
            return this;
        }

        public CreateCredentialRequest build() {
            Preconditions.checkStringNotEmpty(this.mType, "type must not be empty");
            return new CreateCredentialRequest(this.mType, this.mCredentialData, this.mCandidateQueryData, this.mIsSystemProviderRequired, this.mAlwaysSendAppInfoToProvider, this.mOrigin);
        }
    }
}
