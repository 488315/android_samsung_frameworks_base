package android.credentials.selection;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.util.ArrayList;
import java.util.List;

public final class CreateCredentialProviderData extends ProviderData implements Parcelable {
    public static final Parcelable.Creator<CreateCredentialProviderData> CREATOR =
            new Parcelable.Creator<CreateCredentialProviderData>() { // from class:
                // android.credentials.selection.CreateCredentialProviderData.1
                @Override // android.os.Parcelable.Creator
                public CreateCredentialProviderData createFromParcel(Parcel in) {
                    return new CreateCredentialProviderData(in);
                }

                @Override // android.os.Parcelable.Creator
                public CreateCredentialProviderData[] newArray(int size) {
                    return new CreateCredentialProviderData[size];
                }
            };
    private final Entry mRemoteEntry;
    private final List<Entry> mSaveEntries;

    public CreateCredentialProviderData(
            String providerFlattenedComponentName, List<Entry> saveEntries, Entry remoteEntry) {
        super(providerFlattenedComponentName);
        this.mSaveEntries = new ArrayList(saveEntries);
        this.mRemoteEntry = remoteEntry;
    }

    public CreateCredentialProviderInfo toCreateCredentialProviderInfo() {
        return new CreateCredentialProviderInfo(
                getProviderFlattenedComponentName(), this.mSaveEntries, this.mRemoteEntry);
    }

    public List<Entry> getSaveEntries() {
        return this.mSaveEntries;
    }

    public Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    private CreateCredentialProviderData(Parcel in) {
        super(in);
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, Entry.CREATOR);
        this.mSaveEntries = arrayList;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSaveEntries);
        Entry remoteEntry = (Entry) in.readTypedObject(Entry.CREATOR);
        this.mRemoteEntry = remoteEntry;
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.mSaveEntries);
        dest.writeTypedObject(this.mRemoteEntry, flags);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private String mProviderFlattenedComponentName;
        private List<Entry> mSaveEntries = new ArrayList();
        private Entry mRemoteEntry = null;

        public Builder(String providerFlattenedComponentName) {
            this.mProviderFlattenedComponentName = providerFlattenedComponentName;
        }

        public Builder setSaveEntries(List<Entry> credentialEntries) {
            this.mSaveEntries = credentialEntries;
            return this;
        }

        public Builder setRemoteEntry(Entry remoteEntry) {
            this.mRemoteEntry = remoteEntry;
            return this;
        }

        public CreateCredentialProviderData build() {
            return new CreateCredentialProviderData(
                    this.mProviderFlattenedComponentName, this.mSaveEntries, this.mRemoteEntry);
        }
    }
}
