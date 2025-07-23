package android.credentials.selection;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class CreateCredentialProviderData extends ProviderData implements Parcelable {
    public static final Parcelable.Creator<CreateCredentialProviderData> CREATOR = new Parcelable.Creator<CreateCredentialProviderData>() { // from class: android.credentials.selection.CreateCredentialProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialProviderData createFromParcel(Parcel parcel) {
            return new CreateCredentialProviderData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialProviderData[] newArray(int i) {
            return new CreateCredentialProviderData[i];
        }
    };
    private final Entry mRemoteEntry;
    private final List<Entry> mSaveEntries;

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CreateCredentialProviderData(String str, List<Entry> list, Entry entry) {
        super(str);
        this.mSaveEntries = new ArrayList(list);
        this.mRemoteEntry = entry;
    }

    public CreateCredentialProviderInfo toCreateCredentialProviderInfo() {
        return new CreateCredentialProviderInfo(getProviderFlattenedComponentName(), this.mSaveEntries, this.mRemoteEntry);
    }

    public List<Entry> getSaveEntries() {
        return this.mSaveEntries;
    }

    public Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    private CreateCredentialProviderData(Parcel parcel) {
        super(parcel);
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, Entry.CREATOR);
        this.mSaveEntries = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mRemoteEntry = (Entry) parcel.readTypedObject(Entry.CREATOR);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mSaveEntries);
        parcel.writeTypedObject(this.mRemoteEntry, i);
    }

    public static final class Builder {
        private String mProviderFlattenedComponentName;
        private List<Entry> mSaveEntries = new ArrayList();
        private Entry mRemoteEntry = null;

        public Builder(String str) {
            this.mProviderFlattenedComponentName = str;
        }

        public Builder setSaveEntries(List<Entry> list) {
            this.mSaveEntries = list;
            return this;
        }

        public Builder setRemoteEntry(Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public CreateCredentialProviderData build() {
            return new CreateCredentialProviderData(this.mProviderFlattenedComponentName, this.mSaveEntries, this.mRemoteEntry);
        }
    }
}
