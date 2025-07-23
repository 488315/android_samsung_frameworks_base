package android.service.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class BeginGetCredentialOption implements Parcelable {
    private static final String BUNDLE_ID_KEY = "android.service.credentials.BeginGetCredentialOption.BUNDLE_ID_KEY";
    public static final Parcelable.Creator<BeginGetCredentialOption> CREATOR = new Parcelable.Creator<BeginGetCredentialOption>() { // from class: android.service.credentials.BeginGetCredentialOption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialOption[] newArray(int i) {
            return new BeginGetCredentialOption[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginGetCredentialOption createFromParcel(Parcel parcel) {
            return new BeginGetCredentialOption(parcel);
        }
    };
    private final Bundle mCandidateQueryData;
    private final String mId;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.mId;
    }

    public String getType() {
        return this.mType;
    }

    public Bundle getCandidateQueryData() {
        return this.mCandidateQueryData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mCandidateQueryData);
        parcel.writeString8(this.mId);
    }

    public String toString() {
        return "GetCredentialOption {type=" + this.mType + ", candidateQueryData=" + this.mCandidateQueryData + ", id=" + this.mId + "}";
    }

    public BeginGetCredentialOption(String str, String str2, Bundle bundle) {
        this.mId = (String) Preconditions.checkStringNotEmpty(str, "id must not be empty");
        this.mType = (String) Preconditions.checkStringNotEmpty(str2, "type must not be empty");
        Bundle bundle2 = new Bundle();
        bundle2.putAll(bundle);
        this.mCandidateQueryData = bundle2;
        addIdToBundle();
    }

    private void addIdToBundle() {
        this.mCandidateQueryData.putString(BUNDLE_ID_KEY, this.mId);
    }

    private BeginGetCredentialOption(Parcel parcel) {
        String readString8 = parcel.readString8();
        Bundle readBundle = parcel.readBundle();
        String readString82 = parcel.readString8();
        this.mType = readString8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString8);
        this.mCandidateQueryData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
        this.mId = readString82;
    }
}
