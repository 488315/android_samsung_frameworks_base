package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public class UiccSlotInfo implements Parcelable {
    public static final int CARD_STATE_INFO_ABSENT = 1;
    public static final int CARD_STATE_INFO_ERROR = 3;
    public static final int CARD_STATE_INFO_PRESENT = 2;
    public static final int CARD_STATE_INFO_RESTRICTED = 4;
    public static final Parcelable.Creator<UiccSlotInfo> CREATOR = new Parcelable.Creator<UiccSlotInfo>() { // from class: android.telephony.UiccSlotInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccSlotInfo createFromParcel(Parcel parcel) {
            return new UiccSlotInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccSlotInfo[] newArray(int i) {
            return new UiccSlotInfo[i];
        }
    };
    private final String mCardId;
    private final int mCardStateInfo;
    private final boolean mIsActive;
    private final boolean mIsEuicc;
    private final boolean mIsExtendedApduSupported;
    private final boolean mIsRemovable;
    private boolean mLogicalSlotAccessRestricted;
    private final int mLogicalSlotIdx;
    private final List<UiccPortInfo> mPortList;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CardStateInfo {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UiccSlotInfo(Parcel parcel) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsActive = parcel.readBoolean();
        this.mIsEuicc = parcel.readBoolean();
        this.mCardId = parcel.readString8();
        this.mCardStateInfo = parcel.readInt();
        this.mLogicalSlotIdx = parcel.readInt();
        this.mIsExtendedApduSupported = parcel.readBoolean();
        this.mIsRemovable = parcel.readBoolean();
        ArrayList arrayList = new ArrayList();
        this.mPortList = arrayList;
        parcel.readTypedList(arrayList, UiccPortInfo.CREATOR);
        this.mLogicalSlotAccessRestricted = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsActive);
        parcel.writeBoolean(this.mIsEuicc);
        parcel.writeString8(this.mCardId);
        parcel.writeInt(this.mCardStateInfo);
        parcel.writeInt(this.mLogicalSlotIdx);
        parcel.writeBoolean(this.mIsExtendedApduSupported);
        parcel.writeBoolean(this.mIsRemovable);
        parcel.writeTypedList(this.mPortList, i);
        parcel.writeBoolean(this.mLogicalSlotAccessRestricted);
    }

    @Deprecated
    public UiccSlotInfo(boolean z, boolean z2, String str, int i, int i2, boolean z3) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsActive = z;
        this.mIsEuicc = z2;
        this.mCardId = str;
        this.mCardStateInfo = i;
        this.mLogicalSlotIdx = i2;
        this.mIsExtendedApduSupported = z3;
        this.mIsRemovable = false;
        this.mPortList = new ArrayList();
    }

    public UiccSlotInfo(boolean z, String str, int i, boolean z2, boolean z3, List<UiccPortInfo> list) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsEuicc = z;
        this.mCardId = str;
        this.mCardStateInfo = i;
        this.mIsExtendedApduSupported = z2;
        this.mIsRemovable = z3;
        this.mPortList = list;
        this.mIsActive = !list.isEmpty() && list.get(0).isActive();
        this.mLogicalSlotIdx = list.isEmpty() ? -1 : list.get(0).getLogicalSlotIndex();
    }

    @Deprecated
    public boolean getIsActive() {
        if (this.mLogicalSlotAccessRestricted) {
            throw new UnsupportedOperationException("getIsActive() is not supported by UiccSlotInfo. Please Use UiccPortInfo API instead");
        }
        return this.mIsActive;
    }

    public boolean getIsEuicc() {
        return this.mIsEuicc;
    }

    public String getCardId() {
        return this.mCardId;
    }

    public int getCardStateInfo() {
        return this.mCardStateInfo;
    }

    @Deprecated
    public int getLogicalSlotIdx() {
        if (this.mLogicalSlotAccessRestricted) {
            throw new UnsupportedOperationException("getLogicalSlotIdx() is not supported by UiccSlotInfo. Please use UiccPortInfo API instead");
        }
        return this.mLogicalSlotIdx;
    }

    public boolean getIsExtendedApduSupported() {
        return this.mIsExtendedApduSupported;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public Collection<UiccPortInfo> getPorts() {
        return Collections.unmodifiableList(this.mPortList);
    }

    public void setLogicalSlotAccessRestricted(boolean z) {
        this.mLogicalSlotAccessRestricted = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UiccSlotInfo uiccSlotInfo = (UiccSlotInfo) obj;
            if (this.mIsActive == uiccSlotInfo.mIsActive && this.mIsEuicc == uiccSlotInfo.mIsEuicc && Objects.equals(this.mCardId, uiccSlotInfo.mCardId) && this.mCardStateInfo == uiccSlotInfo.mCardStateInfo && this.mLogicalSlotIdx == uiccSlotInfo.mLogicalSlotIdx && this.mIsExtendedApduSupported == uiccSlotInfo.mIsExtendedApduSupported && this.mIsRemovable == uiccSlotInfo.mIsRemovable && Objects.equals(this.mPortList, uiccSlotInfo.mPortList)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsActive), Boolean.valueOf(this.mIsEuicc), this.mCardId, Integer.valueOf(this.mCardStateInfo), Integer.valueOf(this.mLogicalSlotIdx), Boolean.valueOf(this.mIsExtendedApduSupported), Boolean.valueOf(this.mIsRemovable), this.mPortList);
    }

    public String toString() {
        return "UiccSlotInfo (, mIsEuicc=" + this.mIsEuicc + ", mCardId=" + SubscriptionInfo.getPrintableId(this.mCardId) + ", cardState=" + this.mCardStateInfo + ", mIsExtendedApduSupported=" + this.mIsExtendedApduSupported + ", mIsRemovable=" + this.mIsRemovable + ", mPortList=" + this.mPortList + ", mLogicalSlotAccessRestricted=" + this.mLogicalSlotAccessRestricted + NavigationBarInflaterView.KEY_CODE_END;
    }
}
