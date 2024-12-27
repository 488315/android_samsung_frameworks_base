package android.hardware.devicestate;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

public final class DeviceStateInfo implements Parcelable {
    public static final int CHANGED_BASE_STATE = 2;
    public static final int CHANGED_CURRENT_STATE = 4;
    public static final int CHANGED_SUPPORTED_STATES = 1;
    public static final Parcelable.Creator<DeviceStateInfo> CREATOR =
            new Parcelable.Creator<DeviceStateInfo>() { // from class:
                // android.hardware.devicestate.DeviceStateInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DeviceStateInfo createFromParcel(Parcel source) {
                    int numberOfSupportedStates = source.readInt();
                    ArrayList<DeviceState> supportedStates =
                            new ArrayList<>(numberOfSupportedStates);
                    for (int i = 0; i < numberOfSupportedStates; i++) {
                        DeviceState.Configuration configuration =
                                (DeviceState.Configuration)
                                        source.readTypedObject(DeviceState.Configuration.CREATOR);
                        supportedStates.add(i, new DeviceState(configuration));
                    }
                    DeviceState baseState =
                            new DeviceState(
                                    (DeviceState.Configuration)
                                            source.readTypedObject(
                                                    DeviceState.Configuration.CREATOR));
                    DeviceState currentState =
                            new DeviceState(
                                    (DeviceState.Configuration)
                                            source.readTypedObject(
                                                    DeviceState.Configuration.CREATOR));
                    return new DeviceStateInfo(supportedStates, baseState, currentState);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DeviceStateInfo[] newArray(int size) {
                    return new DeviceStateInfo[size];
                }
            };
    public final DeviceState baseState;
    public final DeviceState currentState;
    public final ArrayList<DeviceState> supportedStates;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeFlags {}

    public DeviceStateInfo(
            ArrayList<DeviceState> supportedStates, DeviceState baseState, DeviceState state) {
        this.supportedStates = supportedStates;
        this.baseState = baseState;
        this.currentState = state;
    }

    public DeviceStateInfo(DeviceStateInfo info) {
        this(new ArrayList(info.supportedStates), info.baseState, info.currentState);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DeviceStateInfo that = (DeviceStateInfo) other;
        if (this.baseState.equals(that.baseState)
                && this.currentState.equals(that.currentState)
                && Objects.equals(this.supportedStates, that.supportedStates)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = Objects.hash(this.baseState, this.currentState);
        return (result * 31) + this.supportedStates.hashCode();
    }

    public int diff(DeviceStateInfo other) {
        int diff = 0;
        if (!this.supportedStates.equals(other.supportedStates)) {
            diff = 0 | 1;
        }
        if (!this.baseState.equals(other.baseState)) {
            diff |= 2;
        }
        if (!this.currentState.equals(other.currentState)) {
            return diff | 4;
        }
        return diff;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.supportedStates.size());
        for (int i = 0; i < this.supportedStates.size(); i++) {
            dest.writeTypedObject(this.supportedStates.get(i).getConfiguration(), flags);
        }
        dest.writeTypedObject(this.baseState.getConfiguration(), flags);
        dest.writeTypedObject(this.currentState.getConfiguration(), flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
