package android.os;

import android.os.Parcelable;
import android.util.ArrayMap;

/* loaded from: classes3.dex */
public class HqmStatsImpl implements Parcelable {
    public static final int CF_SERVER = 1;
    public static final Parcelable.Creator<HqmStatsImpl> CREATOR = new Parcelable.Creator<HqmStatsImpl>() { // from class: android.os.HqmStatsImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HqmStatsImpl createFromParcel(Parcel parcel) {
            return new HqmStatsImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HqmStatsImpl[] newArray(int i) {
            return new HqmStatsImpl[i];
        }
    };
    public static final int DV_SERVER = 0;
    public static final int HQM_INTERFACE_API = 1;
    public static final int HQM_INTERFACE_INTENT = 2;
    public static final int HQM_INTERFACE_KERNEL = 0;
    public static final int HQM_INTERFACE_UNKNOWN = -1;
    public static final int NONE_SERVER = -1;
    private ArrayMap<String, HWParamResultData> mHWParamResultDataFromA;
    private ArrayMap<String, HWParamResultData> mHWParamResultDataFromI;
    private ArrayMap<String, HWParamResultData> mHWParamResultDataFromK;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HqmStatsImpl() {
        this.mHWParamResultDataFromK = new ArrayMap<>();
        this.mHWParamResultDataFromA = new ArrayMap<>();
        this.mHWParamResultDataFromI = new ArrayMap<>();
    }

    private HqmStatsImpl(Parcel parcel) {
        this.mHWParamResultDataFromK = new ArrayMap<>();
        this.mHWParamResultDataFromA = new ArrayMap<>();
        this.mHWParamResultDataFromI = new ArrayMap<>();
        readFromParcel(parcel);
    }

    public ArrayMap<String, HWParamResultData> getHWParamResultDataMaps(int i) {
        if (i == 0) {
            return this.mHWParamResultDataFromK;
        }
        if (i == 1) {
            return this.mHWParamResultDataFromA;
        }
        if (i == 2) {
            return this.mHWParamResultDataFromI;
        }
        return null;
    }

    public void addHWParamResultData(int i, String str, HWParamResultData hWParamResultData) {
        if (i == 0) {
            this.mHWParamResultDataFromK.put(str, hWParamResultData);
        } else if (i == 1) {
            this.mHWParamResultDataFromA.put(str, hWParamResultData);
        } else if (i == 2) {
            this.mHWParamResultDataFromI.put(str, hWParamResultData);
        }
    }

    public void writeToParcel(Parcel parcel) {
        int size = this.mHWParamResultDataFromK.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeString(this.mHWParamResultDataFromK.keyAt(i));
            this.mHWParamResultDataFromK.valueAt(i).writeToParcelLocked(parcel);
        }
        int size2 = this.mHWParamResultDataFromA.size();
        parcel.writeInt(size2);
        for (int i2 = 0; i2 < size2; i2++) {
            parcel.writeString(this.mHWParamResultDataFromA.keyAt(i2));
            this.mHWParamResultDataFromA.valueAt(i2).writeToParcelLocked(parcel);
        }
        int size3 = this.mHWParamResultDataFromI.size();
        parcel.writeInt(size3);
        for (int i3 = 0; i3 < size3; i3++) {
            parcel.writeString(this.mHWParamResultDataFromI.keyAt(i3));
            this.mHWParamResultDataFromI.valueAt(i3).writeToParcelLocked(parcel);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mHWParamResultDataFromK.clear();
        for (int i = 0; i < readInt; i++) {
            String readString = parcel.readString();
            HWParamResultData hWParamResultData = new HWParamResultData();
            hWParamResultData.readFromParcelLocked(parcel);
            this.mHWParamResultDataFromK.put(readString, hWParamResultData);
        }
        int readInt2 = parcel.readInt();
        this.mHWParamResultDataFromA.clear();
        for (int i2 = 0; i2 < readInt2; i2++) {
            String readString2 = parcel.readString();
            HWParamResultData hWParamResultData2 = new HWParamResultData();
            hWParamResultData2.readFromParcelLocked(parcel);
            this.mHWParamResultDataFromA.put(readString2, hWParamResultData2);
        }
        int readInt3 = parcel.readInt();
        this.mHWParamResultDataFromI.clear();
        for (int i3 = 0; i3 < readInt3; i3++) {
            String readString3 = parcel.readString();
            HWParamResultData hWParamResultData3 = new HWParamResultData();
            hWParamResultData3.readFromParcelLocked(parcel);
            this.mHWParamResultDataFromI.put(readString3, hWParamResultData3);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel);
    }
}
