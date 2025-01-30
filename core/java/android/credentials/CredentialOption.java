package android.credentials;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.ArraySet;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class CredentialOption implements Parcelable {
    public static final Parcelable.Creator<CredentialOption> CREATOR = new Parcelable.Creator<CredentialOption>() { // from class: android.credentials.CredentialOption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialOption[] newArray(int size) {
            return new CredentialOption[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialOption createFromParcel(Parcel in) {
            return new CredentialOption(in);
        }
    };
    public static final String SUPPORTED_ELEMENT_KEYS = "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS";
    private final ArraySet<ComponentName> mAllowedProviders;
    private final Bundle mCandidateQueryData;
    private final Bundle mCredentialRetrievalData;
    private final boolean mIsSystemProviderRequired;
    private final String mType;

    public String getType() {
        return this.mType;
    }

    public Bundle getCredentialRetrievalData() {
        return this.mCredentialRetrievalData;
    }

    public Bundle getCandidateQueryData() {
        return this.mCandidateQueryData;
    }

    public boolean isSystemProviderRequired() {
        return this.mIsSystemProviderRequired;
    }

    public Set<ComponentName> getAllowedProviders() {
        return this.mAllowedProviders;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mType);
        dest.writeBundle(this.mCredentialRetrievalData);
        dest.writeBundle(this.mCandidateQueryData);
        dest.writeBoolean(this.mIsSystemProviderRequired);
        dest.writeArraySet(this.mAllowedProviders);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "CredentialOption {type=" + this.mType + ", requestData=" + this.mCredentialRetrievalData + ", candidateQueryData=" + this.mCandidateQueryData + ", isSystemProviderRequired=" + this.mIsSystemProviderRequired + ", allowedProviders=" + this.mAllowedProviders + "}";
    }

    private CredentialOption(String type, Bundle credentialRetrievalData, Bundle candidateQueryData, boolean isSystemProviderRequired, ArraySet<ComponentName> allowedProviders) {
        this.mType = (String) Preconditions.checkStringNotEmpty(type, "type must not be empty");
        this.mCredentialRetrievalData = (Bundle) Objects.requireNonNull(credentialRetrievalData, "requestData must not be null");
        this.mCandidateQueryData = (Bundle) Objects.requireNonNull(candidateQueryData, "candidateQueryData must not be null");
        this.mIsSystemProviderRequired = isSystemProviderRequired;
        this.mAllowedProviders = (ArraySet) Objects.requireNonNull(allowedProviders, "providerFilterSer mustnot be empty");
    }

    public CredentialOption(String type, Bundle credentialRetrievalData, Bundle candidateQueryData, boolean isSystemProviderRequired) {
        this(type, credentialRetrievalData, candidateQueryData, isSystemProviderRequired, new ArraySet());
    }

    private CredentialOption(Parcel in) {
        String type = in.readString8();
        Bundle data = in.readBundle();
        Bundle candidateQueryData = in.readBundle();
        boolean isSystemProviderRequired = in.readBoolean();
        this.mType = type;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) type);
        this.mCredentialRetrievalData = data;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) data);
        this.mCandidateQueryData = candidateQueryData;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) candidateQueryData);
        this.mIsSystemProviderRequired = isSystemProviderRequired;
        ArraySet readArraySet = in.readArraySet(null);
        this.mAllowedProviders = readArraySet;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readArraySet);
    }

    public static final class Builder {
        private Bundle mCandidateQueryData;
        private Bundle mCredentialRetrievalData;
        private String mType;
        private boolean mIsSystemProviderRequired = false;
        private ArraySet<ComponentName> mAllowedProviders = new ArraySet<>();

        public Builder(String type, Bundle credentialRetrievalData, Bundle candidateQueryData) {
            this.mType = (String) Preconditions.checkStringNotEmpty(type, "type must not be null, or empty");
            this.mCredentialRetrievalData = (Bundle) Objects.requireNonNull(credentialRetrievalData, "credentialRetrievalData must not be null");
            this.mCandidateQueryData = (Bundle) Objects.requireNonNull(candidateQueryData, "candidateQueryData must not be null");
        }

        public Builder setIsSystemProviderRequired(boolean isSystemProviderRequired) {
            this.mIsSystemProviderRequired = isSystemProviderRequired;
            return this;
        }

        public Builder addAllowedProvider(ComponentName allowedProvider) {
            this.mAllowedProviders.add((ComponentName) Objects.requireNonNull(allowedProvider, "allowedProvider must not be null"));
            return this;
        }

        public Builder setAllowedProviders(Set<ComponentName> allowedProviders) {
            Preconditions.checkCollectionElementsNotNull(allowedProviders, "allowedProviders");
            this.mAllowedProviders = new ArraySet<>(allowedProviders);
            return this;
        }

        public CredentialOption build() {
            return new CredentialOption(this.mType, this.mCredentialRetrievalData, this.mCandidateQueryData, this.mIsSystemProviderRequired, this.mAllowedProviders);
        }
    }
}
