package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.IntArray;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SystemSelectionSpecifier implements Parcelable {
    public static final Parcelable.Creator<SystemSelectionSpecifier> CREATOR = new Parcelable.Creator<SystemSelectionSpecifier>() { // from class: android.telephony.satellite.SystemSelectionSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemSelectionSpecifier createFromParcel(Parcel in) {
            return new SystemSelectionSpecifier(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemSelectionSpecifier[] newArray(int size) {
            return new SystemSelectionSpecifier[size];
        }
    };
    private IntArray mBands;
    private IntArray mEarfcs;
    private String mMccMnc;

    public SystemSelectionSpecifier(String mccmnc, IntArray bands, IntArray earfcs) {
        this.mMccMnc = mccmnc;
        this.mBands = bands;
        this.mEarfcs = earfcs;
    }

    private SystemSelectionSpecifier(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        this.mMccMnc = TextUtils.emptyIfNull(this.mMccMnc);
        out.writeString8(this.mMccMnc);
        if (this.mBands != null && this.mBands.size() > 0) {
            out.writeInt(this.mBands.size());
            for (int i = 0; i < this.mBands.size(); i++) {
                out.writeInt(this.mBands.get(i));
            }
        } else {
            out.writeInt(0);
        }
        if (this.mEarfcs != null && this.mEarfcs.size() > 0) {
            out.writeInt(this.mEarfcs.size());
            for (int i2 = 0; i2 < this.mEarfcs.size(); i2++) {
                out.writeInt(this.mEarfcs.get(i2));
            }
            return;
        }
        out.writeInt(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mccmnc:");
        sb.append(this.mMccMnc);
        sb.append(",");
        sb.append("bands:");
        if (this.mBands != null && this.mBands.size() > 0) {
            for (int i = 0; i < this.mBands.size(); i++) {
                sb.append(this.mBands.get(i));
                sb.append(",");
            }
        } else {
            sb.append("none,");
        }
        sb.append("earfcs:");
        if (this.mEarfcs != null && this.mEarfcs.size() > 0) {
            for (int i2 = 0; i2 < this.mEarfcs.size(); i2++) {
                sb.append(this.mEarfcs.get(i2));
                sb.append(",");
            }
        } else {
            sb.append("none");
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemSelectionSpecifier that = (SystemSelectionSpecifier) o;
        if (Objects.equals(this.mMccMnc, that.mMccMnc) && Objects.equals(this.mBands, that.mBands) && Objects.equals(this.mEarfcs, that.mEarfcs)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mMccMnc, this.mBands, this.mEarfcs);
    }

    public String getMccMnc() {
        return this.mMccMnc;
    }

    public IntArray getBands() {
        return this.mBands;
    }

    public IntArray getEarfcs() {
        return this.mEarfcs;
    }

    private void readFromParcel(Parcel in) {
        this.mMccMnc = in.readString();
        this.mBands = new IntArray();
        int numBands = in.readInt();
        if (numBands > 0) {
            for (int i = 0; i < numBands; i++) {
                this.mBands.add(in.readInt());
            }
        }
        this.mEarfcs = new IntArray();
        int numEarfcs = in.readInt();
        if (numEarfcs > 0) {
            for (int i2 = 0; i2 < numEarfcs; i2++) {
                this.mEarfcs.add(in.readInt());
            }
        }
    }
}
