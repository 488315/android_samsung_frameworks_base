package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SipDialogState implements Parcelable {
    public static final Parcelable.Creator<SipDialogState> CREATOR = new Parcelable.Creator<SipDialogState>() { // from class: android.telephony.ims.SipDialogState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipDialogState createFromParcel(Parcel parcel) {
            return new SipDialogState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipDialogState[] newArray(int i) {
            return new SipDialogState[i];
        }
    };
    public static final int STATE_CLOSED = 2;
    public static final int STATE_CONFIRMED = 1;
    public static final int STATE_EARLY = 0;
    private final int mState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SipDialogStateCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mState;

        public Builder(int i) {
            this.mState = i;
        }

        public SipDialogState build() {
            return new SipDialogState(this);
        }
    }

    private SipDialogState(Builder builder) {
        this.mState = builder.mState;
    }

    private SipDialogState(Parcel parcel) {
        this.mState = parcel.readInt();
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mState);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mState == ((SipDialogState) obj).mState;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mState));
    }
}
