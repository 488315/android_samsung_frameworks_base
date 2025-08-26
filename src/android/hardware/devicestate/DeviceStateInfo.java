package android.hardware.devicestate;

import android.hardware.devicestate.DeviceState;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DeviceStateInfo implements Parcelable {
    public static final int CHANGED_BASE_STATE = 2;
    public static final int CHANGED_CURRENT_STATE = 4;
    public static final int CHANGED_SUPPORTED_STATES = 1;
    public static final Parcelable.Creator<DeviceStateInfo> CREATOR = new Parcelable.Creator<DeviceStateInfo>() { // from class: android.hardware.devicestate.DeviceStateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceStateInfo createFromParcel(Parcel parcel) {
            return new DeviceStateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceStateInfo[] newArray(int i) {
            return new DeviceStateInfo[i];
        }
    };
    public final DeviceState baseState;
    public final DeviceState currentState;
    public final ArrayList<DeviceState> supportedStates;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceStateInfo(ArrayList<DeviceState> arrayList, DeviceState deviceState, DeviceState deviceState2) {
        this.supportedStates = arrayList;
        this.baseState = deviceState;
        this.currentState = deviceState2;
    }

    public DeviceStateInfo(DeviceStateInfo deviceStateInfo) {
        this(new ArrayList(deviceStateInfo.supportedStates), deviceStateInfo.baseState, deviceStateInfo.currentState);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DeviceStateInfo deviceStateInfo = (DeviceStateInfo) obj;
            if (this.baseState.equals(deviceStateInfo.baseState) && this.currentState.equals(deviceStateInfo.currentState) && Objects.equals(this.supportedStates, deviceStateInfo.supportedStates)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(this.baseState, this.currentState) * 31) + this.supportedStates.hashCode();
    }

    public int diff(DeviceStateInfo deviceStateInfo) {
        int i = !this.supportedStates.equals(deviceStateInfo.supportedStates) ? 1 : 0;
        if (!this.baseState.equals(deviceStateInfo.baseState)) {
            i |= 2;
        }
        return !this.currentState.equals(deviceStateInfo.currentState) ? i | 4 : i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.supportedStates.size());
        for (int i2 = 0; i2 < this.supportedStates.size(); i2++) {
            parcel.writeTypedObject(this.supportedStates.get(i2).getConfiguration(), i);
        }
        parcel.writeTypedObject(this.baseState.getConfiguration(), i);
        parcel.writeTypedObject(this.currentState.getConfiguration(), i);
    }

    private DeviceStateInfo(Parcel parcel) {
        int i = parcel.readInt();
        ArrayList<DeviceState> arrayList = new ArrayList<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(i2, new DeviceState((DeviceState.Configuration) Objects.requireNonNull((DeviceState.Configuration) parcel.readTypedObject(DeviceState.Configuration.CREATOR))));
        }
        this.supportedStates = arrayList;
        this.baseState = new DeviceState((DeviceState.Configuration) Objects.requireNonNull((DeviceState.Configuration) parcel.readTypedObject(DeviceState.Configuration.CREATOR)));
        this.currentState = new DeviceState((DeviceState.Configuration) Objects.requireNonNull((DeviceState.Configuration) parcel.readTypedObject(DeviceState.Configuration.CREATOR)));
    }
}
