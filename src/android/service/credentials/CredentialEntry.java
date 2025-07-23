package android.service.credentials;

import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class CredentialEntry implements Parcelable {
    public static final Parcelable.Creator<CredentialEntry> CREATOR = new Parcelable.Creator<CredentialEntry>() { // from class: android.service.credentials.CredentialEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialEntry createFromParcel(Parcel parcel) {
            return new CredentialEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialEntry[] newArray(int i) {
            return new CredentialEntry[i];
        }
    };
    private final String mBeginGetCredentialOptionId;
    private final Slice mSlice;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CredentialEntry(String str, String str2, Slice slice) {
        this.mBeginGetCredentialOptionId = (String) Preconditions.checkStringNotEmpty(str, "beginGetCredentialOptionId must not be null, or empty");
        this.mType = (String) Preconditions.checkStringNotEmpty(str2, "type must not be null, or empty");
        this.mSlice = (Slice) Objects.requireNonNull(slice, "slice must not be null");
    }

    public CredentialEntry(BeginGetCredentialOption beginGetCredentialOption, Slice slice) {
        Objects.requireNonNull(beginGetCredentialOption, "beginGetCredentialOption must not be null");
        this.mBeginGetCredentialOptionId = (String) Preconditions.checkStringNotEmpty(beginGetCredentialOption.getId(), "Id in beginGetCredentialOption must not be null");
        this.mType = (String) Preconditions.checkStringNotEmpty(beginGetCredentialOption.getType(), "type in beginGetCredentialOption must not be null");
        this.mSlice = (Slice) Objects.requireNonNull(slice, "slice must not be null");
    }

    public CredentialEntry(String str, Slice slice) {
        this.mBeginGetCredentialOptionId = null;
        this.mType = (String) Objects.requireNonNull(str, "type must not be null");
        this.mSlice = (Slice) Objects.requireNonNull(slice, "slice must not be null");
    }

    private CredentialEntry(Parcel parcel) {
        Objects.requireNonNull(parcel, "parcel must not be null");
        this.mType = parcel.readString8();
        this.mSlice = (Slice) parcel.readTypedObject(Slice.CREATOR);
        this.mBeginGetCredentialOptionId = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeString8(this.mBeginGetCredentialOptionId);
    }

    public String getBeginGetCredentialOptionId() {
        return this.mBeginGetCredentialOptionId;
    }

    public String getType() {
        return this.mType;
    }

    public Slice getSlice() {
        return this.mSlice;
    }
}
