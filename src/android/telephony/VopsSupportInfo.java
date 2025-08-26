package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes4.dex */
public abstract class VopsSupportInfo implements Parcelable {
    public static final Parcelable.Creator<VopsSupportInfo> CREATOR = new Parcelable.Creator<VopsSupportInfo>() { // from class: android.telephony.VopsSupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VopsSupportInfo createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 3) {
                return LteVopsSupportInfo.createFromParcelBody(parcel);
            }
            if (i == 6) {
                return NrVopsSupportInfo.createFromParcelBody(parcel);
            }
            throw new RuntimeException("Bad VopsSupportInfo Parcel");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VopsSupportInfo[] newArray(int i) {
            return new VopsSupportInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public abstract boolean isEmergencyServiceFallbackSupported();

    public abstract boolean isEmergencyServiceSupported();

    public abstract boolean isVopsSupported();

    @Override // android.os.Parcelable
    public abstract void writeToParcel(Parcel parcel, int i);

    protected void writeToParcel(Parcel parcel, int i, int i2) {
        parcel.writeInt(i2);
    }
}
