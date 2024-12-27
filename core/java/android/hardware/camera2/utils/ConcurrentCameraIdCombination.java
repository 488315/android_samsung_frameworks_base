package android.hardware.camera2.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Pair;

import java.util.Set;

public class ConcurrentCameraIdCombination implements Parcelable {
    public static final Parcelable.Creator<ConcurrentCameraIdCombination> CREATOR =
            new Parcelable.Creator<ConcurrentCameraIdCombination>() { // from class:
                // android.hardware.camera2.utils.ConcurrentCameraIdCombination.1
                @Override // android.os.Parcelable.Creator
                public ConcurrentCameraIdCombination createFromParcel(Parcel in) {
                    return new ConcurrentCameraIdCombination(in);
                }

                @Override // android.os.Parcelable.Creator
                public ConcurrentCameraIdCombination[] newArray(int size) {
                    return new ConcurrentCameraIdCombination[size];
                }
            };
    private final Set<Pair<String, Integer>> mConcurrentCameraIdDeviceIdPairs;

    private ConcurrentCameraIdCombination(Parcel in) {
        this.mConcurrentCameraIdDeviceIdPairs = new ArraySet();
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mConcurrentCameraIdDeviceIdPairs.size());
        for (Pair<String, Integer> cameraIdDeviceIdPair : this.mConcurrentCameraIdDeviceIdPairs) {
            dest.writeString(cameraIdDeviceIdPair.first);
            dest.writeInt(cameraIdDeviceIdPair.second.intValue());
        }
    }

    public void readFromParcel(Parcel in) {
        this.mConcurrentCameraIdDeviceIdPairs.clear();
        int cameraCombinationSize = in.readInt();
        if (cameraCombinationSize < 0) {
            throw new RuntimeException(
                    "cameraCombinationSize " + cameraCombinationSize + " should not be negative");
        }
        for (int i = 0; i < cameraCombinationSize; i++) {
            String cameraId = in.readString();
            if (cameraId == null) {
                throw new RuntimeException("Failed to read camera id from Parcel");
            }
            int deviceId = in.readInt();
            this.mConcurrentCameraIdDeviceIdPairs.add(
                    new Pair<>(cameraId, Integer.valueOf(deviceId)));
        }
    }

    public Set<Pair<String, Integer>> getConcurrentCameraIdCombination() {
        return this.mConcurrentCameraIdDeviceIdPairs;
    }
}
