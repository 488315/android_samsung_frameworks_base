package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DeviceStateSensorOrientationMap {
    public static final long FOLDED = 4;
    public static final long NORMAL = 0;
    private final HashMap<Long, Integer> mDeviceStateOrientationMap;
    private final long[] mElements;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceState {
    }

    public DeviceStateSensorOrientationMap(long[] jArr) {
        this.mElements = (long[]) Objects.requireNonNull(jArr, "elements must not be null");
        this.mDeviceStateOrientationMap = new HashMap<>();
        if (jArr.length % 2 != 0) {
            throw new IllegalArgumentException("Device state sensor orientation map length " + jArr.length + " is not even!");
        }
        for (int i = 0; i < jArr.length; i += 2) {
            int i2 = i + 1;
            if (jArr[i2] % 90 != 0) {
                throw new IllegalArgumentException("Sensor orientation not divisible by 90: " + jArr[i2]);
            }
            this.mDeviceStateOrientationMap.put(Long.valueOf(jArr[i]), Integer.valueOf(Math.toIntExact(jArr[i2])));
        }
    }

    private DeviceStateSensorOrientationMap(ArrayList<Long> arrayList, HashMap<Long, Integer> map) {
        this.mElements = new long[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            this.mElements[i] = arrayList.get(i).longValue();
        }
        this.mDeviceStateOrientationMap = map;
    }

    public int getSensorOrientation(long j) {
        if (!this.mDeviceStateOrientationMap.containsKey(Long.valueOf(j))) {
            throw new IllegalArgumentException("Invalid device state: " + j);
        }
        return this.mDeviceStateOrientationMap.get(Long.valueOf(j)).intValue();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof DeviceStateSensorOrientationMap) {
            return Arrays.equals(this.mElements, ((DeviceStateSensorOrientationMap) obj).mElements);
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mElements);
    }

    public static final class Builder {
        private final ArrayList<Long> mElements = new ArrayList<>();
        private final HashMap<Long, Integer> mDeviceStateOrientationMap = new HashMap<>();

        public Builder addOrientationForState(long j, long j2) {
            if (j2 % 90 != 0) {
                throw new IllegalArgumentException("Sensor orientation not divisible by 90: " + j2);
            }
            this.mDeviceStateOrientationMap.put(Long.valueOf(j), Integer.valueOf(Math.toIntExact(j2)));
            this.mElements.add(Long.valueOf(j));
            this.mElements.add(Long.valueOf(j2));
            return this;
        }

        public DeviceStateSensorOrientationMap build() {
            if (this.mElements.size() == 0) {
                throw new IllegalStateException("Cannot build a DeviceStateSensorOrientationMap with zero elements.");
            }
            return new DeviceStateSensorOrientationMap(this.mElements, this.mDeviceStateOrientationMap);
        }
    }
}
