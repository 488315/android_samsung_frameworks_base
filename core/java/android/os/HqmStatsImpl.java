package android.os;

import android.util.ArrayMap;

public class HqmStatsImpl implements Parcelable {
    public static final int CF_SERVER = 1;
    public static final Parcelable.Creator<HqmStatsImpl> CREATOR =
            new Parcelable.Creator<HqmStatsImpl>() { // from class: android.os.HqmStatsImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public HqmStatsImpl createFromParcel(Parcel in) {
                    return new HqmStatsImpl(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public HqmStatsImpl[] newArray(int size) {
                    return new HqmStatsImpl[size];
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

    public HqmStatsImpl() {
        this.mHWParamResultDataFromK = new ArrayMap<>();
        this.mHWParamResultDataFromA = new ArrayMap<>();
        this.mHWParamResultDataFromI = new ArrayMap<>();
    }

    private HqmStatsImpl(Parcel in) {
        this.mHWParamResultDataFromK = new ArrayMap<>();
        this.mHWParamResultDataFromA = new ArrayMap<>();
        this.mHWParamResultDataFromI = new ArrayMap<>();
        readFromParcel(in);
    }

    public ArrayMap<String, HWParamResultData> getHWParamResultDataMaps(int interfaceType) {
        if (interfaceType == 0) {
            return this.mHWParamResultDataFromK;
        }
        if (interfaceType == 1) {
            return this.mHWParamResultDataFromA;
        }
        if (interfaceType == 2) {
            return this.mHWParamResultDataFromI;
        }
        return null;
    }

    public void addHWParamResultData(int interfaceType, String keyName, HWParamResultData value) {
        if (interfaceType == 0) {
            this.mHWParamResultDataFromK.put(keyName, value);
        } else if (interfaceType == 1) {
            this.mHWParamResultDataFromA.put(keyName, value);
        } else if (interfaceType == 2) {
            this.mHWParamResultDataFromI.put(keyName, value);
        }
    }

    public void writeToParcel(Parcel out) {
        int NH = this.mHWParamResultDataFromK.size();
        out.writeInt(NH);
        for (int ih = 0; ih < NH; ih++) {
            out.writeString(this.mHWParamResultDataFromK.keyAt(ih));
            HWParamResultData item = this.mHWParamResultDataFromK.valueAt(ih);
            item.writeToParcelLocked(out);
        }
        int NH2 = this.mHWParamResultDataFromA.size();
        out.writeInt(NH2);
        for (int ih2 = 0; ih2 < NH2; ih2++) {
            out.writeString(this.mHWParamResultDataFromA.keyAt(ih2));
            HWParamResultData item2 = this.mHWParamResultDataFromA.valueAt(ih2);
            item2.writeToParcelLocked(out);
        }
        int NH3 = this.mHWParamResultDataFromI.size();
        out.writeInt(NH3);
        for (int ih3 = 0; ih3 < NH3; ih3++) {
            out.writeString(this.mHWParamResultDataFromI.keyAt(ih3));
            HWParamResultData item3 = this.mHWParamResultDataFromI.valueAt(ih3);
            item3.writeToParcelLocked(out);
        }
    }

    public void readFromParcel(Parcel in) {
        int numHWParams = in.readInt();
        this.mHWParamResultDataFromK.clear();
        for (int ih = 0; ih < numHWParams; ih++) {
            String keyName = in.readString();
            HWParamResultData item = new HWParamResultData();
            item.readFromParcelLocked(in);
            this.mHWParamResultDataFromK.put(keyName, item);
        }
        int numHWParams2 = in.readInt();
        this.mHWParamResultDataFromA.clear();
        for (int ih2 = 0; ih2 < numHWParams2; ih2++) {
            String keyName2 = in.readString();
            HWParamResultData item2 = new HWParamResultData();
            item2.readFromParcelLocked(in);
            this.mHWParamResultDataFromA.put(keyName2, item2);
        }
        int numHWParams3 = in.readInt();
        this.mHWParamResultDataFromI.clear();
        for (int ih3 = 0; ih3 < numHWParams3; ih3++) {
            String keyName3 = in.readString();
            HWParamResultData item3 = new HWParamResultData();
            item3.readFromParcelLocked(in);
            this.mHWParamResultDataFromI.put(keyName3, item3);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        writeToParcel(out);
    }
}
