package android.telecom;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class PhoneAccountSuggestion implements Parcelable {
    public static final Parcelable.Creator<PhoneAccountSuggestion> CREATOR = new Parcelable.Creator<PhoneAccountSuggestion>() { // from class: android.telecom.PhoneAccountSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneAccountSuggestion createFromParcel(Parcel parcel) {
            return new PhoneAccountSuggestion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneAccountSuggestion[] newArray(int i) {
            return new PhoneAccountSuggestion[i];
        }
    };
    public static final int REASON_FREQUENT = 2;
    public static final int REASON_INTRA_CARRIER = 1;
    public static final int REASON_NONE = 0;
    public static final int REASON_OTHER = 4;
    public static final int REASON_USER_SET = 3;
    private PhoneAccountHandle mHandle;
    private int mReason;
    private boolean mShouldAutoSelect;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuggestionReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PhoneAccountSuggestion(PhoneAccountHandle phoneAccountHandle, int i, boolean z) {
        this.mHandle = phoneAccountHandle;
        this.mReason = i;
        this.mShouldAutoSelect = z;
    }

    private PhoneAccountSuggestion(Parcel parcel) {
        this.mHandle = (PhoneAccountHandle) parcel.readParcelable(PhoneAccountHandle.class.getClassLoader(), PhoneAccountHandle.class);
        this.mReason = parcel.readInt();
        this.mShouldAutoSelect = parcel.readByte() != 0;
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mHandle;
    }

    public int getReason() {
        return this.mReason;
    }

    public boolean shouldAutoSelect() {
        return this.mShouldAutoSelect;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mHandle, i);
        parcel.writeInt(this.mReason);
        parcel.writeByte(this.mShouldAutoSelect ? (byte) 1 : (byte) 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PhoneAccountSuggestion phoneAccountSuggestion = (PhoneAccountSuggestion) obj;
            if (this.mReason == phoneAccountSuggestion.mReason && this.mShouldAutoSelect == phoneAccountSuggestion.mShouldAutoSelect && Objects.equals(this.mHandle, phoneAccountSuggestion.mHandle)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mHandle, Integer.valueOf(this.mReason), Boolean.valueOf(this.mShouldAutoSelect));
    }
}
