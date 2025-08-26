package android.credentials;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.credentials.CredentialEntry;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class CredentialDescription implements Parcelable {
    public static final Parcelable.Creator<CredentialDescription> CREATOR = new Parcelable.Creator<CredentialDescription>() { // from class: android.credentials.CredentialDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialDescription createFromParcel(Parcel parcel) {
            return new CredentialDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialDescription[] newArray(int i) {
            return new CredentialDescription[i];
        }
    };
    private static final int MAX_ALLOWED_ENTRIES_PER_DESCRIPTION = 16;
    private final List<CredentialEntry> mCredentialEntries;
    private final Set<String> mSupportedElementKeys;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CredentialDescription(String str, Set<String> set, List<CredentialEntry> list) {
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mSupportedElementKeys = (Set) Objects.requireNonNull(set);
        this.mCredentialEntries = (List) Objects.requireNonNull(list);
        Preconditions.checkArgument(list.size() <= 16, "The number of Credential Entries exceed 16.");
        Preconditions.checkArgument(compareEntryTypes(str, list) == 0, "Credential Entry type(s) do not match the request type.");
    }

    private CredentialDescription(Parcel parcel) {
        String string8 = parcel.readString8();
        ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, CredentialEntry.CREATOR);
        this.mType = string8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string8);
        HashSet hashSet = new HashSet(arrayListCreateStringArrayList);
        this.mSupportedElementKeys = hashSet;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) hashSet);
        this.mCredentialEntries = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }

    private static int compareEntryTypes(final String str, List<CredentialEntry> list) {
        return list.stream().filter(new Predicate() { // from class: android.credentials.CredentialDescription$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CredentialDescription.lambda$compareEntryTypes$0(str, (CredentialEntry) obj);
            }
        }).toList().size();
    }

    static /* synthetic */ boolean lambda$compareEntryTypes$0(String str, CredentialEntry credentialEntry) {
        return !credentialEntry.getType().equals(str);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeStringList(this.mSupportedElementKeys.stream().toList());
        parcel.writeTypedList(this.mCredentialEntries, i);
    }

    public String getType() {
        return this.mType;
    }

    public Set<String> getSupportedElementKeys() {
        return new HashSet(this.mSupportedElementKeys);
    }

    public List<CredentialEntry> getCredentialEntries() {
        return this.mCredentialEntries;
    }

    public int hashCode() {
        return Objects.hash(this.mType, this.mSupportedElementKeys);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CredentialDescription)) {
            return false;
        }
        CredentialDescription credentialDescription = (CredentialDescription) obj;
        return this.mType.equals(credentialDescription.mType) && this.mSupportedElementKeys.equals(credentialDescription.mSupportedElementKeys);
    }
}
