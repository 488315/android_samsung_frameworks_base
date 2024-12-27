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
        public CredentialDescription createFromParcel(Parcel in) {
            return new CredentialDescription(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialDescription[] newArray(int size) {
            return new CredentialDescription[size];
        }
    };
    private static final int MAX_ALLOWED_ENTRIES_PER_DESCRIPTION = 16;
    private final List<CredentialEntry> mCredentialEntries;
    private final Set<String> mSupportedElementKeys;
    private final String mType;

    public CredentialDescription(String type, Set<String> supportedElementKeys, List<CredentialEntry> credentialEntries) {
        this.mType = (String) Preconditions.checkStringNotEmpty(type, "type must not be empty");
        this.mSupportedElementKeys = (Set) Objects.requireNonNull(supportedElementKeys);
        this.mCredentialEntries = (List) Objects.requireNonNull(credentialEntries);
        Preconditions.checkArgument(credentialEntries.size() <= 16, "The number of Credential Entries exceed 16.");
        Preconditions.checkArgument(compareEntryTypes(type, credentialEntries) == 0, "Credential Entry type(s) do not match the request type.");
    }

    private CredentialDescription(Parcel in) {
        String type = in.readString8();
        List<String> descriptions = in.createStringArrayList();
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, CredentialEntry.CREATOR);
        this.mType = type;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mType);
        this.mSupportedElementKeys = new HashSet(descriptions);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSupportedElementKeys);
        this.mCredentialEntries = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCredentialEntries);
    }

    private static int compareEntryTypes(final String type, List<CredentialEntry> credentialEntries) {
        return credentialEntries.stream().filter(new Predicate() { // from class: android.credentials.CredentialDescription$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CredentialDescription.lambda$compareEntryTypes$0(type, (CredentialEntry) obj);
            }
        }).toList().size();
    }

    static /* synthetic */ boolean lambda$compareEntryTypes$0(String type, CredentialEntry credentialEntry) {
        return !credentialEntry.getType().equals(type);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mType);
        dest.writeStringList(this.mSupportedElementKeys.stream().toList());
        dest.writeTypedList(this.mCredentialEntries, flags);
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
        CredentialDescription other = (CredentialDescription) obj;
        return this.mType.equals(other.mType) && this.mSupportedElementKeys.equals(other.mSupportedElementKeys);
    }
}
