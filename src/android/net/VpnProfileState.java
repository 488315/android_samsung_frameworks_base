package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public final class VpnProfileState implements Parcelable {
    public static final Parcelable.Creator<VpnProfileState> CREATOR = new Parcelable.Creator<VpnProfileState>() { // from class: android.net.VpnProfileState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnProfileState createFromParcel(Parcel parcel) {
            return new VpnProfileState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnProfileState[] newArray(int i) {
            return new VpnProfileState[i];
        }
    };
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_FAILED = 3;
    private final boolean mAlwaysOn;
    private final boolean mLockdown;
    private final String mSessionKey;
    private final int mState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VpnProfileState(int i, String str, boolean z, boolean z2) {
        this.mState = i;
        this.mSessionKey = str;
        this.mAlwaysOn = z;
        this.mLockdown = z2;
    }

    public int getState() {
        return this.mState;
    }

    public String getSessionId() {
        return this.mSessionKey;
    }

    public boolean isAlwaysOn() {
        return this.mAlwaysOn;
    }

    public boolean isLockdownEnabled() {
        return this.mLockdown;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeString(this.mSessionKey);
        parcel.writeBoolean(this.mAlwaysOn);
        parcel.writeBoolean(this.mLockdown);
    }

    private VpnProfileState(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mSessionKey = parcel.readString();
        this.mAlwaysOn = parcel.readBoolean();
        this.mLockdown = parcel.readBoolean();
    }

    private String convertStateToString(int i) {
        if (i == 0) {
            return "DISCONNECTED";
        }
        if (i == 1) {
            return "CONNECTING";
        }
        if (i == 2) {
            return "CONNECTED";
        }
        if (i == 3) {
            return "FAILED";
        }
        return "UNKNOWN";
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("State: " + convertStateToString(getState()));
        stringJoiner.add("SessionId: " + getSessionId());
        stringJoiner.add("Always-on: " + isAlwaysOn());
        stringJoiner.add("Lockdown: " + isLockdownEnabled());
        return stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VpnProfileState)) {
            return false;
        }
        VpnProfileState vpnProfileState = (VpnProfileState) obj;
        return getState() == vpnProfileState.getState() && Objects.equals(getSessionId(), vpnProfileState.getSessionId()) && isAlwaysOn() == vpnProfileState.isAlwaysOn() && isLockdownEnabled() == vpnProfileState.isLockdownEnabled();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(getState()), getSessionId(), Boolean.valueOf(isAlwaysOn()), Boolean.valueOf(isLockdownEnabled()));
    }
}
