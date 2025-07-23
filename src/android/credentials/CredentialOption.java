package android.credentials;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
        public CredentialOption[] newArray(int i) {
            return new CredentialOption[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialOption createFromParcel(Parcel parcel) {
            return new CredentialOption(parcel);
        }
    };
    public static final String SUPPORTED_ELEMENT_KEYS = "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS";
    private final ArraySet<ComponentName> mAllowedProviders;
    private final Bundle mCandidateQueryData;
    private final Bundle mCredentialRetrievalData;
    private final boolean mIsSystemProviderRequired;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mCredentialRetrievalData);
        parcel.writeBundle(this.mCandidateQueryData);
        parcel.writeBoolean(this.mIsSystemProviderRequired);
        parcel.writeArraySet(this.mAllowedProviders);
    }

    public String toString() {
        return "CredentialOption {type=" + this.mType + ", requestData=" + this.mCredentialRetrievalData + ", candidateQueryData=" + this.mCandidateQueryData + ", isSystemProviderRequired=" + this.mIsSystemProviderRequired + ", allowedProviders=" + this.mAllowedProviders + "}";
    }

    private CredentialOption(String str, Bundle bundle, Bundle bundle2, boolean z, ArraySet<ComponentName> arraySet) {
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mCredentialRetrievalData = (Bundle) Objects.requireNonNull(bundle, "requestData must not be null");
        this.mCandidateQueryData = (Bundle) Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        this.mIsSystemProviderRequired = z;
        this.mAllowedProviders = (ArraySet) Objects.requireNonNull(arraySet, "providerFilterSer mustnot be empty");
    }

    public CredentialOption(String str, Bundle bundle, Bundle bundle2, boolean z) {
        this(str, bundle, bundle2, z, new ArraySet());
    }

    private CredentialOption(Parcel parcel) {
        String readString8 = parcel.readString8();
        Bundle readBundle = parcel.readBundle();
        Bundle readBundle2 = parcel.readBundle();
        boolean readBoolean = parcel.readBoolean();
        this.mType = readString8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
        this.mCredentialRetrievalData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
        this.mCandidateQueryData = readBundle2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle2);
        this.mIsSystemProviderRequired = readBoolean;
        ArraySet readArraySet = parcel.readArraySet(null);
        this.mAllowedProviders = readArraySet;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readArraySet);
    }

    public static final class Builder {
        private Bundle mCandidateQueryData;
        private Bundle mCredentialRetrievalData;
        private String mType;
        private boolean mIsSystemProviderRequired = false;
        private ArraySet<ComponentName> mAllowedProviders = new ArraySet<>();

        public Builder(String str, Bundle bundle, Bundle bundle2) {
            this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be null, or empty");
            this.mCredentialRetrievalData = (Bundle) Objects.requireNonNull(bundle, "credentialRetrievalData must not be null");
            this.mCandidateQueryData = (Bundle) Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        }

        public Builder setIsSystemProviderRequired(boolean z) {
            this.mIsSystemProviderRequired = z;
            return this;
        }

        public Builder addAllowedProvider(ComponentName componentName) {
            this.mAllowedProviders.add((ComponentName) Objects.requireNonNull(componentName, "allowedProvider must not be null"));
            return this;
        }

        public Builder setAllowedProviders(Set<ComponentName> set) {
            Preconditions.checkCollectionElementsNotNull(set, "allowedProviders");
            this.mAllowedProviders = new ArraySet<>(set);
            return this;
        }

        public CredentialOption build() {
            return new CredentialOption(this.mType, this.mCredentialRetrievalData, this.mCandidateQueryData, this.mIsSystemProviderRequired, this.mAllowedProviders);
        }
    }
}
