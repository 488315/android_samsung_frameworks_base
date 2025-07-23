package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class UiccPortInfo implements Parcelable {
    public static final Parcelable.Creator<UiccPortInfo> CREATOR = new Parcelable.Creator<UiccPortInfo>() { // from class: android.telephony.UiccPortInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccPortInfo createFromParcel(Parcel parcel) {
            return new UiccPortInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccPortInfo[] newArray(int i) {
            return new UiccPortInfo[i];
        }
    };
    public static final String ICCID_REDACTED = "FFFFFFFFFFFFFFFFFFFF";
    private final String mIccId;
    private final boolean mIsActive;
    private final int mLogicalSlotIndex;
    private final int mPortIndex;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UiccPortInfo(Parcel parcel) {
        this.mIccId = parcel.readString8();
        this.mPortIndex = parcel.readInt();
        this.mLogicalSlotIndex = parcel.readInt();
        this.mIsActive = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mIccId);
        parcel.writeInt(this.mPortIndex);
        parcel.writeInt(this.mLogicalSlotIndex);
        parcel.writeBoolean(this.mIsActive);
    }

    public UiccPortInfo(String str, int i, int i2, boolean z) {
        this.mIccId = str;
        this.mPortIndex = i;
        this.mLogicalSlotIndex = i2;
        this.mIsActive = z;
    }

    public String getIccId() {
        return this.mIccId;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    public boolean isActive() {
        return this.mIsActive;
    }

    public int getLogicalSlotIndex() {
        return this.mLogicalSlotIndex;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UiccPortInfo uiccPortInfo = (UiccPortInfo) obj;
            if (Objects.equals(this.mIccId, uiccPortInfo.mIccId) && this.mPortIndex == uiccPortInfo.mPortIndex && this.mLogicalSlotIndex == uiccPortInfo.mLogicalSlotIndex && this.mIsActive == uiccPortInfo.mIsActive) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mIccId, Integer.valueOf(this.mPortIndex), Integer.valueOf(this.mLogicalSlotIndex), Boolean.valueOf(this.mIsActive));
    }

    public String toString() {
        return "UiccPortInfo (isActive=" + this.mIsActive + ", iccId=" + SubscriptionInfo.getPrintableId(this.mIccId) + ", portIndex=" + this.mPortIndex + ", mLogicalSlotIndex=" + this.mLogicalSlotIndex + NavigationBarInflaterView.KEY_CODE_END;
    }
}
