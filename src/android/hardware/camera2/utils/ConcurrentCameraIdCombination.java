package android.hardware.camera2.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Pair;
import java.util.Set;

/* loaded from: classes2.dex */
public class ConcurrentCameraIdCombination implements Parcelable {
    public static final Parcelable.Creator<ConcurrentCameraIdCombination> CREATOR = new Parcelable.Creator<ConcurrentCameraIdCombination>() { // from class: android.hardware.camera2.utils.ConcurrentCameraIdCombination.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConcurrentCameraIdCombination createFromParcel(Parcel parcel) {
            return new ConcurrentCameraIdCombination(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConcurrentCameraIdCombination[] newArray(int i) {
            return new ConcurrentCameraIdCombination[i];
        }
    };
    private final Set<Pair<String, Integer>> mConcurrentCameraIdDeviceIdPairs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ConcurrentCameraIdCombination(Parcel parcel) {
        this.mConcurrentCameraIdDeviceIdPairs = new ArraySet();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mConcurrentCameraIdDeviceIdPairs.size());
        for (Pair<String, Integer> pair : this.mConcurrentCameraIdDeviceIdPairs) {
            parcel.writeString(pair.first);
            parcel.writeInt(pair.second.intValue());
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mConcurrentCameraIdDeviceIdPairs.clear();
        int readInt = parcel.readInt();
        if (readInt < 0) {
            throw new RuntimeException("cameraCombinationSize " + readInt + " should not be negative");
        }
        for (int i = 0; i < readInt; i++) {
            String readString = parcel.readString();
            if (readString == null) {
                throw new RuntimeException("Failed to read camera id from Parcel");
            }
            this.mConcurrentCameraIdDeviceIdPairs.add(new Pair<>(readString, Integer.valueOf(parcel.readInt())));
        }
    }

    public Set<Pair<String, Integer>> getConcurrentCameraIdCombination() {
        return this.mConcurrentCameraIdDeviceIdPairs;
    }
}
