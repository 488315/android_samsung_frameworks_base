package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.IntArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SystemSelectionSpecifier implements Parcelable {
    public static final Parcelable.Creator<SystemSelectionSpecifier> CREATOR = new Parcelable.Creator<SystemSelectionSpecifier>() { // from class: android.telephony.satellite.SystemSelectionSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemSelectionSpecifier createFromParcel(Parcel parcel) {
            return new SystemSelectionSpecifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemSelectionSpecifier[] newArray(int i) {
            return new SystemSelectionSpecifier[i];
        }
    };
    private int[] mBands;
    private int[] mEarfcns;
    private String mMccMnc;
    private List<SatelliteInfo> mSatelliteInfos;
    private int[] mTagIds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SystemSelectionSpecifier(String str, IntArray intArray, IntArray intArray2, SatelliteInfo[] satelliteInfoArr, IntArray intArray3) {
        this.mMccMnc = str;
        this.mBands = intArray.toArray();
        this.mEarfcns = intArray2.toArray();
        this.mSatelliteInfos = Arrays.stream(satelliteInfoArr).toList();
        this.mTagIds = intArray3.toArray();
    }

    public SystemSelectionSpecifier(Builder builder) {
        this.mMccMnc = builder.mMccMnc;
        this.mBands = builder.mBands;
        this.mEarfcns = builder.mEarfcns;
        this.mSatelliteInfos = builder.mSatelliteInfos;
        this.mTagIds = builder.mTagIds;
    }

    public static final class Builder {
        private int[] mBands;
        private int[] mEarfcns;
        private String mMccMnc;
        private List<SatelliteInfo> mSatelliteInfos;
        private int[] mTagIds;

        public Builder setMccMnc(String str) {
            this.mMccMnc = str;
            return this;
        }

        public Builder setBands(int[] iArr) {
            this.mBands = iArr;
            return this;
        }

        public Builder setEarfcns(int[] iArr) {
            this.mEarfcns = iArr;
            return this;
        }

        public Builder setSatelliteInfos(List<SatelliteInfo> list) {
            this.mSatelliteInfos = list;
            return this;
        }

        public Builder setTagIds(int[] iArr) {
            this.mTagIds = iArr;
            return this;
        }

        public SystemSelectionSpecifier build() {
            return new SystemSelectionSpecifier(this);
        }
    }

    private SystemSelectionSpecifier(Parcel parcel) throws ClassNotFoundException, IOException {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        String strEmptyIfNull = TextUtils.emptyIfNull(this.mMccMnc);
        this.mMccMnc = strEmptyIfNull;
        parcel.writeString8(strEmptyIfNull);
        int[] iArr = this.mBands;
        int i2 = 0;
        if (iArr != null && iArr.length > 0) {
            parcel.writeInt(iArr.length);
            int i3 = 0;
            while (true) {
                int[] iArr2 = this.mBands;
                if (i3 >= iArr2.length) {
                    break;
                }
                parcel.writeInt(iArr2[i3]);
                i3++;
            }
        } else {
            parcel.writeInt(0);
        }
        int[] iArr3 = this.mEarfcns;
        if (iArr3 != null && iArr3.length > 0) {
            parcel.writeInt(iArr3.length);
            int i4 = 0;
            while (true) {
                int[] iArr4 = this.mEarfcns;
                if (i4 >= iArr4.length) {
                    break;
                }
                parcel.writeInt(iArr4[i4]);
                i4++;
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeTypedArray((SatelliteInfo[]) this.mSatelliteInfos.toArray(new SatelliteInfo[0]), i);
        int[] iArr5 = this.mTagIds;
        if (iArr5 != null) {
            parcel.writeInt(iArr5.length);
            while (true) {
                int[] iArr6 = this.mTagIds;
                if (i2 >= iArr6.length) {
                    return;
                }
                parcel.writeInt(iArr6[i2]);
                i2++;
            }
        } else {
            parcel.writeInt(0);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("mccmnc:");
        sb.append(this.mMccMnc);
        sb.append(",bands:");
        int[] iArr = this.mBands;
        int i = 0;
        if (iArr != null && iArr.length > 0) {
            int i2 = 0;
            while (true) {
                int[] iArr2 = this.mBands;
                if (i2 >= iArr2.length) {
                    break;
                }
                sb.append(iArr2[i2]);
                sb.append(",");
                i2++;
            }
        } else {
            sb.append("none,");
        }
        sb.append("earfcs:");
        int[] iArr3 = this.mEarfcns;
        if (iArr3 != null && iArr3.length > 0) {
            int i3 = 0;
            while (true) {
                int[] iArr4 = this.mEarfcns;
                if (i3 >= iArr4.length) {
                    break;
                }
                sb.append(iArr4[i3]);
                sb.append(",");
                i3++;
            }
        } else {
            sb.append("none");
        }
        sb.append("mSatelliteInfos:");
        List<SatelliteInfo> list = this.mSatelliteInfos;
        if (list != null && list.size() > 0) {
            Iterator<SatelliteInfo> it = this.mSatelliteInfos.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(",");
            }
        } else {
            sb.append("none");
        }
        sb.append("mTagIds:");
        int[] iArr5 = this.mTagIds;
        if (iArr5 != null && iArr5.length > 0) {
            while (true) {
                int[] iArr6 = this.mTagIds;
                if (i >= iArr6.length) {
                    break;
                }
                sb.append(iArr6[i]);
                sb.append(",");
                i++;
            }
        } else {
            sb.append("none");
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        List<SatelliteInfo> list;
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SystemSelectionSpecifier systemSelectionSpecifier = (SystemSelectionSpecifier) obj;
            if (Objects.equals(this.mMccMnc, systemSelectionSpecifier.mMccMnc) && Arrays.equals(this.mBands, systemSelectionSpecifier.mBands) && Arrays.equals(this.mEarfcns, systemSelectionSpecifier.mEarfcns) && ((list = this.mSatelliteInfos) != null ? list.equals(systemSelectionSpecifier.mSatelliteInfos) : systemSelectionSpecifier.mSatelliteInfos == null) && Arrays.equals(this.mTagIds, systemSelectionSpecifier.mTagIds)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mMccMnc, Integer.valueOf(Arrays.hashCode(this.mBands)), Integer.valueOf(Arrays.hashCode(this.mEarfcns)));
    }

    public String getMccMnc() {
        return this.mMccMnc;
    }

    public int[] getBands() {
        return this.mBands;
    }

    public int[] getEarfcns() {
        return this.mEarfcns;
    }

    public List<SatelliteInfo> getSatelliteInfos() {
        return this.mSatelliteInfos;
    }

    public int[] getTagIds() {
        return this.mTagIds;
    }

    private void readFromParcel(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mMccMnc = parcel.readString();
        int i = parcel.readInt();
        this.mBands = new int[i];
        if (i > 0) {
            for (int i2 = 0; i2 < i; i2++) {
                this.mBands[i2] = parcel.readInt();
            }
        }
        int i3 = parcel.readInt();
        this.mEarfcns = new int[i3];
        if (i3 > 0) {
            for (int i4 = 0; i4 < i3; i4++) {
                this.mEarfcns[i4] = parcel.readInt();
            }
        }
        ArrayList arrayList = new ArrayList();
        this.mSatelliteInfos = arrayList;
        parcel.readList(arrayList, SatelliteInfo.class.getClassLoader(), SatelliteInfo.class);
        int i5 = parcel.readInt();
        this.mTagIds = new int[i5];
        if (i5 > 0) {
            for (int i6 = 0; i6 < i5; i6++) {
                this.mTagIds[i6] = parcel.readInt();
            }
        }
    }
}
