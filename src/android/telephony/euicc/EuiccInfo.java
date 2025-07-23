package android.telephony.euicc;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class EuiccInfo implements Parcelable {
    public static final Parcelable.Creator<EuiccInfo> CREATOR = new Parcelable.Creator<EuiccInfo>() { // from class: android.telephony.euicc.EuiccInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccInfo createFromParcel(Parcel parcel) {
            return new EuiccInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccInfo[] newArray(int i) {
            return new EuiccInfo[i];
        }
    };
    private final String osVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public EuiccInfo(String str) {
        this.osVersion = str;
    }

    private EuiccInfo(Parcel parcel) {
        this.osVersion = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.osVersion);
    }
}
