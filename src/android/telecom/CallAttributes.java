package android.telecom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CallAttributes implements Parcelable {
    public static final int AUDIO_CALL = 1;
    public static final String CALLER_PID_KEY = "CallerPid";
    public static final String CALLER_UID_KEY = "CallerUid";
    public static final String CALL_CAPABILITIES_KEY = "TelecomCapabilities";
    public static final Parcelable.Creator<CallAttributes> CREATOR = new Parcelable.Creator<CallAttributes>() { // from class: android.telecom.CallAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallAttributes createFromParcel(Parcel parcel) {
            return new CallAttributes((PhoneAccountHandle) parcel.readParcelable(getClass().getClassLoader(), PhoneAccountHandle.class), parcel.readCharSequence(), (Uri) parcel.readParcelable(getClass().getClassLoader(), Uri.class), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallAttributes[] newArray(int i) {
            return new CallAttributes[i];
        }
    };
    public static final int DIRECTION_INCOMING = 1;
    public static final int DIRECTION_OUTGOING = 2;
    public static final String DISPLAY_NAME_KEY = "DisplayName";
    public static final int SUPPORTS_SET_INACTIVE = 2;
    public static final int SUPPORTS_STREAM = 4;
    public static final int SUPPORTS_TRANSFER = 8;
    public static final int SUPPORTS_VIDEO_CALLING = 16;
    public static final int VIDEO_CALL = 2;
    private final Uri mAddress;
    private final int mCallCapabilities;
    private final int mCallType;
    private final int mDirection;
    private final CharSequence mDisplayName;
    private final PhoneAccountHandle mPhoneAccountHandle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CallAttributes(PhoneAccountHandle phoneAccountHandle, CharSequence charSequence, Uri uri, int i, int i2, int i3) {
        this.mPhoneAccountHandle = phoneAccountHandle;
        this.mDisplayName = charSequence;
        this.mAddress = uri;
        this.mDirection = i;
        this.mCallType = i2;
        this.mCallCapabilities = i3;
    }

    public static final class Builder {
        private final Uri mAddress;
        private final int mDirection;
        private final CharSequence mDisplayName;
        private final PhoneAccountHandle mPhoneAccountHandle;
        private int mCallType = 1;
        private int mCallCapabilities = 2;

        private boolean isInRange(int i, int i2, int i3) {
            return i3 >= i && i3 <= i2;
        }

        public Builder(PhoneAccountHandle phoneAccountHandle, int i, CharSequence charSequence, Uri uri) {
            if (!isInRange(1, 2, i)) {
                throw new IllegalArgumentException(TextUtils.formatSimple("CallDirection=[%d] is invalid. CallDirections value should be within [%d, %d]", Integer.valueOf(i), 1, 2));
            }
            Objects.requireNonNull(phoneAccountHandle);
            Objects.requireNonNull(charSequence);
            Objects.requireNonNull(uri);
            this.mPhoneAccountHandle = phoneAccountHandle;
            this.mDirection = i;
            this.mDisplayName = charSequence;
            this.mAddress = uri;
        }

        public Builder setCallType(int i) {
            if (!isInRange(1, 2, i)) {
                throw new IllegalArgumentException(TextUtils.formatSimple("CallType=[%d] is invalid. CallTypes value should be within [%d, %d]", Integer.valueOf(i), 1, 2));
            }
            this.mCallType = i;
            return this;
        }

        public Builder setCallCapabilities(int i) {
            this.mCallCapabilities = i;
            return this;
        }

        public CallAttributes build() {
            return new CallAttributes(this.mPhoneAccountHandle, this.mDisplayName, this.mAddress, this.mDirection, this.mCallType, this.mCallCapabilities);
        }
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public int getCallCapabilities() {
        return this.mCallCapabilities;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPhoneAccountHandle, i);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeParcelable(this.mAddress, i);
        parcel.writeInt(this.mDirection);
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mCallCapabilities);
    }

    public String toString() {
        return "{ CallAttributes: [phoneAccountHandle: " + this.mPhoneAccountHandle + "], [contactName: " + Log.pii(this.mDisplayName) + "], [address=" + Log.pii(this.mAddress) + "], [direction=" + this.mDirection + "], [callType=" + this.mCallType + "], [mCallCapabilities=" + this.mCallCapabilities + "]  }";
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            CallAttributes callAttributes = (CallAttributes) obj;
            if (this.mDirection == callAttributes.mDirection && this.mCallType == callAttributes.mCallType && this.mCallCapabilities == callAttributes.mCallCapabilities && Objects.equals(this.mPhoneAccountHandle, callAttributes.mPhoneAccountHandle) && Objects.equals(this.mAddress, callAttributes.mAddress) && Objects.equals(this.mDisplayName, callAttributes.mDisplayName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPhoneAccountHandle, this.mAddress, this.mDisplayName, Integer.valueOf(this.mDirection), Integer.valueOf(this.mCallType), Integer.valueOf(this.mCallCapabilities));
    }
}
