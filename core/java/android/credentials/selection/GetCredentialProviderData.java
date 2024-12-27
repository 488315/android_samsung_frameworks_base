package android.credentials.selection;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.util.ArrayList;
import java.util.List;

public final class GetCredentialProviderData extends ProviderData implements Parcelable {
    public static final Parcelable.Creator<GetCredentialProviderData> CREATOR =
            new Parcelable.Creator<GetCredentialProviderData>() { // from class:
                // android.credentials.selection.GetCredentialProviderData.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GetCredentialProviderData createFromParcel(Parcel in) {
                    return new GetCredentialProviderData(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GetCredentialProviderData[] newArray(int size) {
                    return new GetCredentialProviderData[size];
                }
            };
    private final List<Entry> mActionChips;
    private final List<AuthenticationEntry> mAuthenticationEntries;
    private final List<Entry> mCredentialEntries;
    private final Entry mRemoteEntry;

    public GetCredentialProviderData(
            String providerFlattenedComponentName,
            List<Entry> credentialEntries,
            List<Entry> actionChips,
            List<AuthenticationEntry> authenticationEntries,
            Entry remoteEntry) {
        super(providerFlattenedComponentName);
        this.mCredentialEntries = new ArrayList(credentialEntries);
        this.mActionChips = new ArrayList(actionChips);
        this.mAuthenticationEntries = new ArrayList(authenticationEntries);
        this.mRemoteEntry = remoteEntry;
    }

    public GetCredentialProviderInfo toGetCredentialProviderInfo() {
        return new GetCredentialProviderInfo(
                getProviderFlattenedComponentName(),
                this.mCredentialEntries,
                this.mActionChips,
                this.mAuthenticationEntries,
                this.mRemoteEntry);
    }

    public List<Entry> getCredentialEntries() {
        return this.mCredentialEntries;
    }

    public List<Entry> getActionChips() {
        return this.mActionChips;
    }

    public List<AuthenticationEntry> getAuthenticationEntries() {
        return this.mAuthenticationEntries;
    }

    public Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    private GetCredentialProviderData(Parcel in) {
        super(in);
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, Entry.CREATOR);
        this.mCredentialEntries = arrayList;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCredentialEntries);
        ArrayList arrayList2 = new ArrayList();
        in.readTypedList(arrayList2, Entry.CREATOR);
        this.mActionChips = arrayList2;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mActionChips);
        ArrayList arrayList3 = new ArrayList();
        in.readTypedList(arrayList3, AuthenticationEntry.CREATOR);
        this.mAuthenticationEntries = arrayList3;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mAuthenticationEntries);
        Entry remoteEntry = (Entry) in.readTypedObject(Entry.CREATOR);
        this.mRemoteEntry = remoteEntry;
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.mCredentialEntries);
        dest.writeTypedList(this.mActionChips);
        dest.writeTypedList(this.mAuthenticationEntries);
        dest.writeTypedObject(this.mRemoteEntry, flags);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private String mProviderFlattenedComponentName;
        private List<Entry> mCredentialEntries = new ArrayList();
        private List<Entry> mActionChips = new ArrayList();
        private List<AuthenticationEntry> mAuthenticationEntries = new ArrayList();
        private Entry mRemoteEntry = null;

        public Builder(String providerFlattenedComponentName) {
            this.mProviderFlattenedComponentName = providerFlattenedComponentName;
        }

        public Builder setCredentialEntries(List<Entry> credentialEntries) {
            this.mCredentialEntries = credentialEntries;
            return this;
        }

        public Builder setActionChips(List<Entry> actionChips) {
            this.mActionChips = actionChips;
            return this;
        }

        public Builder setAuthenticationEntries(List<AuthenticationEntry> authenticationEntry) {
            this.mAuthenticationEntries = authenticationEntry;
            return this;
        }

        public Builder setRemoteEntry(Entry remoteEntry) {
            this.mRemoteEntry = remoteEntry;
            return this;
        }

        public GetCredentialProviderData build() {
            return new GetCredentialProviderData(
                    this.mProviderFlattenedComponentName,
                    this.mCredentialEntries,
                    this.mActionChips,
                    this.mAuthenticationEntries,
                    this.mRemoteEntry);
        }
    }
}
