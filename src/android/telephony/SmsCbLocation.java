package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes4.dex */
public final class SmsCbLocation implements Parcelable {
    public static final Parcelable.Creator<SmsCbLocation> CREATOR = new Parcelable.Creator<SmsCbLocation>() { // from class: android.telephony.SmsCbLocation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbLocation createFromParcel(Parcel parcel) {
            return new SmsCbLocation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbLocation[] newArray(int i) {
            return new SmsCbLocation[i];
        }
    };
    private final int mCid;
    private final int mLac;
    private final String mPlmn;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmsCbLocation() {
        this.mPlmn = "";
        this.mLac = -1;
        this.mCid = -1;
    }

    public SmsCbLocation(String str) {
        this.mPlmn = str;
        this.mLac = -1;
        this.mCid = -1;
    }

    public SmsCbLocation(String str, int i, int i2) {
        this.mPlmn = str;
        this.mLac = i;
        this.mCid = i2;
    }

    public SmsCbLocation(Parcel parcel) {
        this.mPlmn = parcel.readString();
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
    }

    public String getPlmn() {
        return this.mPlmn;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int hashCode() {
        return (((this.mPlmn.hashCode() * 31) + this.mLac) * 31) + this.mCid;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && (obj instanceof SmsCbLocation)) {
            SmsCbLocation smsCbLocation = (SmsCbLocation) obj;
            if (this.mPlmn.equals(smsCbLocation.mPlmn) && this.mLac == smsCbLocation.mLac && this.mCid == smsCbLocation.mCid) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return NavigationBarInflaterView.SIZE_MOD_START + this.mPlmn + ',' + this.mLac + ',' + this.mCid + ']';
    }

    public boolean isInLocationArea(SmsCbLocation smsCbLocation) {
        int i = this.mCid;
        if (i != -1 && i != smsCbLocation.mCid) {
            return false;
        }
        int i2 = this.mLac;
        if (i2 == -1 || i2 == smsCbLocation.mLac) {
            return this.mPlmn.equals(smsCbLocation.mPlmn);
        }
        return false;
    }

    public boolean isInLocationArea(String str, int i, int i2) {
        if (!this.mPlmn.equals(str)) {
            return false;
        }
        int i3 = this.mLac;
        if (i3 != -1 && i3 != i) {
            return false;
        }
        int i4 = this.mCid;
        return i4 == -1 || i4 == i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPlmn);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
    }
}
