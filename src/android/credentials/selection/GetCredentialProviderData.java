package android.credentials.selection;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class GetCredentialProviderData extends ProviderData implements Parcelable {
    public static final Parcelable.Creator<GetCredentialProviderData> CREATOR = new Parcelable.Creator<GetCredentialProviderData>() { // from class: android.credentials.selection.GetCredentialProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialProviderData createFromParcel(Parcel parcel) {
            return new GetCredentialProviderData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialProviderData[] newArray(int i) {
            return new GetCredentialProviderData[i];
        }
    };
    private final List<Entry> mActionChips;
    private final List<AuthenticationEntry> mAuthenticationEntries;
    private final List<Entry> mCredentialEntries;
    private final Entry mRemoteEntry;

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GetCredentialProviderData(String str, List<Entry> list, List<Entry> list2, List<AuthenticationEntry> list3, Entry entry) {
        super(str);
        this.mCredentialEntries = new ArrayList(list);
        this.mActionChips = new ArrayList(list2);
        this.mAuthenticationEntries = new ArrayList(list3);
        this.mRemoteEntry = entry;
    }

    public GetCredentialProviderInfo toGetCredentialProviderInfo() {
        return new GetCredentialProviderInfo(getProviderFlattenedComponentName(), this.mCredentialEntries, this.mActionChips, this.mAuthenticationEntries, this.mRemoteEntry);
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

    private GetCredentialProviderData(Parcel parcel) {
        super(parcel);
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, Entry.CREATOR);
        this.mCredentialEntries = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        ArrayList arrayList2 = new ArrayList();
        parcel.readTypedList(arrayList2, Entry.CREATOR);
        this.mActionChips = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList2);
        ArrayList arrayList3 = new ArrayList();
        parcel.readTypedList(arrayList3, AuthenticationEntry.CREATOR);
        this.mAuthenticationEntries = arrayList3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList3);
        this.mRemoteEntry = (Entry) parcel.readTypedObject(Entry.CREATOR);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mCredentialEntries);
        parcel.writeTypedList(this.mActionChips);
        parcel.writeTypedList(this.mAuthenticationEntries);
        parcel.writeTypedObject(this.mRemoteEntry, i);
    }

    public static final class Builder {
        private String mProviderFlattenedComponentName;
        private List<Entry> mCredentialEntries = new ArrayList();
        private List<Entry> mActionChips = new ArrayList();
        private List<AuthenticationEntry> mAuthenticationEntries = new ArrayList();
        private Entry mRemoteEntry = null;

        public Builder(String str) {
            this.mProviderFlattenedComponentName = str;
        }

        public Builder setCredentialEntries(List<Entry> list) {
            this.mCredentialEntries = list;
            return this;
        }

        public Builder setActionChips(List<Entry> list) {
            this.mActionChips = list;
            return this;
        }

        public Builder setAuthenticationEntries(List<AuthenticationEntry> list) {
            this.mAuthenticationEntries = list;
            return this;
        }

        public Builder setRemoteEntry(Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public GetCredentialProviderData build() {
            return new GetCredentialProviderData(this.mProviderFlattenedComponentName, this.mCredentialEntries, this.mActionChips, this.mAuthenticationEntries, this.mRemoteEntry);
        }
    }
}
