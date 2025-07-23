package android.media;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public final class NearbyDevice implements Parcelable {
    public static final int RANGE_CLOSE = 3;
    public static final int RANGE_FAR = 1;
    public static final int RANGE_LONG = 2;
    public static final int RANGE_UNKNOWN = 0;
    public static final int RANGE_WITHIN_REACH = 4;
    private final String mMediaRoute2Id;
    private final int mRangeZone;
    private static final List<Integer> RANGE_WEIGHT_LIST = Arrays.asList(0, 1, 2, 3, 4);
    public static final Parcelable.Creator<NearbyDevice> CREATOR = new Parcelable.Creator<NearbyDevice>() { // from class: android.media.NearbyDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NearbyDevice createFromParcel(Parcel parcel) {
            return new NearbyDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NearbyDevice[] newArray(int i) {
            return new NearbyDevice[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface RangeZone {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String rangeZoneToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "FAR";
        }
        if (i == 2) {
            return "LONG";
        }
        if (i == 3) {
            return "CLOSE";
        }
        if (i == 4) {
            return "WITHIN_REACH";
        }
        return "Invalid";
    }

    public NearbyDevice(String str, int i) {
        this.mMediaRoute2Id = str;
        this.mRangeZone = i;
    }

    private NearbyDevice(Parcel parcel) {
        this.mMediaRoute2Id = parcel.readString8();
        this.mRangeZone = parcel.readInt();
    }

    public static int compareRangeZones(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        List<Integer> list = RANGE_WEIGHT_LIST;
        return list.indexOf(Integer.valueOf(i)) > list.indexOf(Integer.valueOf(i2)) ? -1 : 1;
    }

    public String toString() {
        return "NearbyDevice{mediaRoute2Id=" + this.mMediaRoute2Id + " rangeZone=" + rangeZoneToString(this.mRangeZone) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mMediaRoute2Id);
        parcel.writeInt(this.mRangeZone);
    }

    public String getMediaRoute2Id() {
        return this.mMediaRoute2Id;
    }

    public int getRangeZone() {
        return this.mRangeZone;
    }
}
