package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public class ModemInfo implements Parcelable {
    public static final Parcelable.Creator<ModemInfo> CREATOR = new Parcelable.Creator() { // from class: android.telephony.ModemInfo.1
        @Override // android.os.Parcelable.Creator
        public ModemInfo createFromParcel(Parcel parcel) {
            return new ModemInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ModemInfo[] newArray(int i) {
            return new ModemInfo[i];
        }
    };
    public final boolean isDataSupported;
    public final boolean isVoiceSupported;
    public final int modemId;
    public final int rat;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ModemInfo(int i) {
        this(i, 0, true, true);
    }

    public ModemInfo(int i, int i2, boolean z, boolean z2) {
        this.modemId = i;
        this.rat = i2;
        this.isVoiceSupported = z;
        this.isDataSupported = z2;
    }

    public ModemInfo(Parcel parcel) {
        this.modemId = parcel.readInt();
        this.rat = parcel.readInt();
        this.isVoiceSupported = parcel.readBoolean();
        this.isDataSupported = parcel.readBoolean();
    }

    public String toString() {
        return "modemId=" + this.modemId + " rat=" + this.rat + " isVoiceSupported:" + this.isVoiceSupported + " isDataSupported:" + this.isDataSupported;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.modemId), Integer.valueOf(this.rat), Boolean.valueOf(this.isVoiceSupported), Boolean.valueOf(this.isDataSupported));
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ModemInfo) && hashCode() == obj.hashCode()) {
            if (this == obj) {
                return true;
            }
            ModemInfo modemInfo = (ModemInfo) obj;
            if (this.modemId == modemInfo.modemId && this.rat == modemInfo.rat && this.isVoiceSupported == modemInfo.isVoiceSupported && this.isDataSupported == modemInfo.isDataSupported) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.modemId);
        parcel.writeInt(this.rat);
        parcel.writeBoolean(this.isVoiceSupported);
        parcel.writeBoolean(this.isDataSupported);
    }
}
